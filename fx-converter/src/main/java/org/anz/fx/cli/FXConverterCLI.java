package org.anz.fx.cli;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.anz.fx.calculator.type.FxCalculator;
import org.anz.fx.calculator.type.factory.FxCalculatorFactory;
import org.anz.fx.data.CrossConverterProvider;
import org.anz.fx.exception.RateNotFoundException;
import org.anz.fx.model.Query;
import org.anz.fx.model.QueryOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author amitkumar.wali
 *
 */
@Component
public class FXConverterCLI implements CommandLineRunner {

	private static Logger LOGGER = LoggerFactory.getLogger(FXConverterCLI.class);
	
	@Autowired
	private FxCalculatorFactory factory;
	
	/**
	 * @throws Exception 
	 *
	 */
	@Override
	public void run(String... args) throws Exception  {
		final String method = FXConverterCLI.class.getName() + ".run()";
		LOGGER.info("BEGIN METHOD - {}", method);
		
		final List<Query> queries = new ArrayList<Query>();
		final Pattern splitPattern = Pattern.compile("\\s+");
		
		//Step 1 - Process the arguments
		//Fallback situation - If more than one query provided, then it should be handled.
		for (int i = 0; i < args.length; ++i) {
			String clArgs = args[i].trim();
			String[] queryParams = splitPattern.split(clArgs);

			final BigDecimal fromAmount = new BigDecimal(queryParams[1]);
			if(fromAmount.compareTo(BigDecimal.ZERO) <= 0) {
				throw new Exception(String.format("Supplied amount is either ZERO or NEGATIVE ==> %s", clArgs));
			}
			queries.add(new Query(queryParams[0], fromAmount, queryParams[3]));
        }
		
		//Step 2 - Validate and Compute FX
		for (Query q : queries) {
			try {
				String conversionKey = CrossConverterProvider.getCrossConverter(q.getFromCurrency(), q.getToCurrency());

				final FxCalculator fxCalculator = factory.getFxCalculator(q, conversionKey);
				
				final QueryOutput qo = fxCalculator.calculate(q, conversionKey);

				//Step 3 - Display the results
				LOGGER.info(String.format("INPUT : %s", q));
				LOGGER.info(String.format("OUTPUT : %s", qo));
				System.out.println(qo);
			} catch (RateNotFoundException ex) {
				LOGGER.error(ex.getMessage());
				throw ex;
			}
		}
		
		LOGGER.info("END METHOD - {}", method);
	}
		
}
