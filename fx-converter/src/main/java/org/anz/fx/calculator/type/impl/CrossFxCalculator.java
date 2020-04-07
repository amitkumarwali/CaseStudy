package org.anz.fx.calculator.type.impl;

import java.math.BigDecimal;

import org.anz.fx.calculator.type.FxCalculator;
import org.anz.fx.exception.RateNotFoundException;
import org.anz.fx.model.Query;
import org.anz.fx.model.QueryOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FX Calculator implementation for converting amount using cross method
 * 
 * @author amitkumar.wali
 *
 */
@Component
public class CrossFxCalculator extends FxCalculator {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CrossFxCalculator.class);
	
	@Autowired
	private DirectFxCalculator directFxCalculator;
	
	@Autowired
	private InverseFxCalculator inverseFxCalculator;
	
	/**
	 * @throws RateNotFoundException 
	 *
	 */
	@Override
	public QueryOutput calculate(Query q, String conversionKey) throws RateNotFoundException {
		final String method = CrossFxCalculator.class.getName() + ".calculate()";
		LOGGER.info("BEGIN METHOD - {}", method);
		
		final String[] splits = conversionKey.split("\\|");
		BigDecimal prod = BigDecimal.ONE;
		String terminatingCurr = "";
		int terminatingPre = -1;
		for(int i = 0; i< splits.length; i++) {
			QueryOutput q0 = crossCalculator(q, splits[i], terminatingCurr);
			prod = prod.multiply(q0.getToAmount());
			terminatingCurr = q0.getToCurrency();
			terminatingPre = q0.getPrecision();
		}
		QueryOutput qo = new QueryOutput(q, q.getFromAmount().multiply(prod), terminatingPre);
		
		LOGGER.info("END METHOD - {}", method);
		return qo;
	}
	
	/**
	 * @param queryInput
	 * @param conversionKey
	 * @param processedCurrency
	 * @return
	 * @throws RateNotFoundException 
	 */
	private QueryOutput crossCalculator(Query queryInput, final String conversionKey, String processedCurrency) throws RateNotFoundException {
		final String method = CrossFxCalculator.class.getName() + ".crossCalculator()";
		LOGGER.debug("BEGIN METHOD - {}", method);
		
		QueryOutput qo = null;
		
		if(isValidConversionKey(conversionKey)) {
			Query queryProto = (Query) queryInput.getClone();
			if(conversionKey.startsWith(queryInput.getFromCurrency())) {
				final String substring = conversionKey.substring(3);
				queryProto.setToCurrency(substring);
				queryProto.setFromAmount(BigDecimal.ONE);
				qo = directFxCalculator.calculate(queryProto, conversionKey);
				LOGGER.debug("CROSS CAL : intermediate calculation - {}", qo);
			}
			else if(conversionKey.endsWith(queryInput.getToCurrency())) {
				final String substring = conversionKey.substring(0, 3);
				queryProto.setFromCurrency(substring);
				queryProto.setFromAmount(BigDecimal.ONE);
				qo = directFxCalculator.calculate(queryProto, conversionKey);
				LOGGER.debug("CROSS CAL : intermediate calculation - {}", qo);
			}
			
			else if(conversionKey.endsWith(queryInput.getFromCurrency())) {
				final String substring = conversionKey.substring(0, 3);
				queryProto.setToCurrency(substring);
				queryProto.setFromAmount(BigDecimal.ONE);
				qo = inverseFxCalculator.calculate(queryProto, conversionKey);
				LOGGER.debug("CROSS CAL : intermediate calculation - {}", qo);
			}
			
			else if(conversionKey.startsWith(queryInput.getToCurrency())) {
				final String substring = conversionKey.substring(3);
				queryProto.setFromCurrency(substring);
				queryProto.setFromAmount(BigDecimal.ONE);
				qo = inverseFxCalculator.calculate(queryProto, conversionKey);
				LOGGER.debug("CROSS CAL : intermediate calculation - {}", qo);
			} 
			
			else if(conversionKey.endsWith(processedCurrency)) {
				queryProto.setToCurrency(conversionKey.substring(0, 3));
				queryProto.setFromCurrency(conversionKey.substring(3));
				queryProto.setFromAmount(BigDecimal.ONE);
				qo = inverseFxCalculator.calculate(queryProto, conversionKey);
				LOGGER.debug("CROSS CAL : intermediate calculation - {}", qo);
			}
			
			else if(conversionKey.startsWith(processedCurrency)) {
				queryProto.setFromCurrency(conversionKey.substring(0, 3));
				queryProto.setToCurrency(conversionKey.substring(3));
				queryProto.setFromAmount(BigDecimal.ONE);
				qo = directFxCalculator.calculate(queryProto, conversionKey);
				LOGGER.debug("CROSS CAL : intermediate calculation - {}", qo);
			}
		}
		else { //Attempt to check if reverse conversion is available
			String newConversionKey = reverseConversionKey(conversionKey, processedCurrency);
			
			if(isValidConversionKey(newConversionKey)) {
				Query queryProto = (Query) queryInput.getClone();
				queryProto.setFromCurrency(newConversionKey.substring(0, 3));
				queryProto.setToCurrency(newConversionKey.substring(3));
				queryProto.setFromAmount(BigDecimal.ONE);
				qo = inverseFxCalculator.calculate(queryProto, newConversionKey);
			}
			else {
				throw new RateNotFoundException(String.format("Unable to find rate for %s", conversionKey));
			}
		}
		
		LOGGER.debug("END METHOD - {}", method);
		return qo;
	}

	private String reverseConversionKey(final String conversionKey, String processedCurrency) {
		StringBuilder strBuild = new StringBuilder();
		if(conversionKey.startsWith(processedCurrency)) { //reverse the conversion key for translation
			strBuild.append(conversionKey.substring(0, 3));
			strBuild.append(conversionKey.substring(3));
		} else {
			strBuild.append(conversionKey.substring(3));
			strBuild.append(conversionKey.substring(0, 3));
		}
		return strBuild.toString();
	}

}
