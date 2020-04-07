package org.anz.fx.calculator.type.impl;

import org.anz.fx.calculator.type.FxCalculator;
import org.anz.fx.model.Query;
import org.anz.fx.model.QueryOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * FX Calculator implementation for converting amount into same currency
 * 
 * @author amitkumar.wali
 *
 */
@Component
public class SameCurrFxCalculator extends FxCalculator {
	
	private static Logger LOGGER = LoggerFactory.getLogger(SameCurrFxCalculator.class);

	@Override
	public QueryOutput calculate(Query q, String conversionKey) {
		final String method = SameCurrFxCalculator.class.getName() + ".calculate()";
		LOGGER.info("BEGIN METHOD - {}", method);
		
		String property = this.env.getProperty("fx.std.precision.".concat(q.getToCurrency()), "2");
		QueryOutput qo = new QueryOutput(q, q.getFromAmount(), Integer.valueOf(property));
		
		LOGGER.info("END METHOD - {}", method);
		return qo;
	}
}
