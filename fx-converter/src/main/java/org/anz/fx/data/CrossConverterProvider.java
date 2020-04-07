package org.anz.fx.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.anz.fx.exception.RateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * Provider class to prepare the required static data to process the request/query.
 * 
 * @author amitkumar.wali
 *
 */
@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CrossConverterProvider {
	private static Logger LOGGER = LoggerFactory.getLogger(CrossConverterProvider.class);
	
	private static final Map<String, String> hm = new HashMap<String, String>();
	
	@Value("${fx.csv.mapping.file}")
    private Resource csvResource;
	
	/**
	 * @throws Exception
	 */
	@PostConstruct
	public void init() throws Exception {
		final String method = CrossConverterProvider.class.getName() + ".init()";
		LOGGER.info("BEGIN METHOD - {}", method);
		
		LOGGER.debug("Loading static data - START");
		loadData();
		LOGGER.debug("Loading static data - END");
		
		LOGGER.info("END METHOD - {}", method);
	}
	
	/**
	 * @param fromCurrency
	 * @param toCurrency
	 * @return
	 * @throws Exception
	 */
	public static String getCrossConverter(String fromCurrency, String toCurrency) throws RateNotFoundException {
		final String method = CrossConverterProvider.class.getName() + ".getCrossConverter()";
		LOGGER.info("BEGIN METHOD - {}", method);
		
		LOGGER.debug("INPUT: fromCurrency - {}, toCurrency - {}", fromCurrency, toCurrency);
		
		final String mappedKey = fromCurrency.concat(toCurrency);
		final String inverseMappedKey = toCurrency.concat(fromCurrency);
		String key = null;
		if(hm.containsKey(mappedKey)) {
			final String conversionMapper = hm.get(mappedKey); 
			
			LOGGER.debug("CHECK: Matrix mapping for {} is {}", mappedKey, conversionMapper);
			
			switch(conversionMapper.toUpperCase()) {
			//D = direct feed - eg. the currency pair can be looked up directly.
			case "D" :
				key = mappedKey;
				break;
				//Inv = inverted - eg. the currency pair can be looked up if base and terms are flipped (and rate = 1/rate)
			case "INV" :
				key = inverseMappedKey;
				break;
				//1:1 = unity - the rate is always 1.
			case "1" :
				break;
				//CCY = in order to calculate this rate, you need to cross via this currency.
			case "USD" :
			case "EUR" :
					StringBuilder strBuilder = new StringBuilder();
					strBuilder.append(getCrossConverter(fromCurrency, conversionMapper));
					strBuilder.append("|");
					strBuilder.append(getCrossConverter(conversionMapper, toCurrency));
					key = strBuilder.toString();
				break;
			default:
				raiseNotFoundException(fromCurrency, toCurrency);
			}
		}
		else {
			raiseNotFoundException(fromCurrency, toCurrency);
		}
		
		LOGGER.info("END METHOD - {}", method);
		return key;
	}

	/**
	 * @throws Exception
	 */
	private void loadData() throws Exception {
		
		LOGGER.debug("LOAD: Loading static data using CSV - START");
		final List<String[]> loadValueFromCSV = loadValueFromCSV();

		final String[] header = loadValueFromCSV.get(0);
		
		for(int counter = 1; counter < loadValueFromCSV.size(); counter++) {
			String[] bodies = loadValueFromCSV.get(counter);
			for(int innerCounter = 1; innerCounter< header.length; innerCounter++) {
				hm.put(bodies[0].trim() + header[innerCounter].trim(), bodies[innerCounter].trim());
			}
		}
		LOGGER.debug("LOAD: Loading static data using CSV - END");
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	private List<String[]> loadValueFromCSV() throws Exception {
		LOGGER.debug("READ: Load CSV file and read data for mapping - START");
		try {

	        final CsvMapper mapper = new CsvMapper();
	        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
	        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withSkipFirstDataRow(false);
	        MappingIterator<String[]> readValues = mapper.reader().forType(String[].class).with(bootstrapSchema).readValues(csvResource.getInputStream());
	        
	        LOGGER.debug("READ: Load CSV file and read data for mapping - END");
	        
	        //if Successful, return the results
			return readValues.readAll();
		} catch (IOException e) {
			LOGGER.error("ERROR: Unable to read CSV file", e);
			throw new Exception("ERROR: Unable to read CSV file");
		}
	}
	
	/**
	 * @param fromCurrency
	 * @param toCurrency
	 * @throws Exception
	 */
	private static void raiseNotFoundException(String fromCurrency, String toCurrency) throws RateNotFoundException {
		final String errorMessage = String.format("Unable to find rate for %s/%s", fromCurrency, toCurrency);
		LOGGER.error("ERROR: {} ", errorMessage);
		throw new RateNotFoundException(errorMessage);
	}

}
