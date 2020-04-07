package org.anz.fx.calculator.type.impl;

import java.math.BigDecimal;

import org.anz.fx.calculator.type.FxCalculator;
import org.anz.fx.model.Query;
import org.anz.fx.model.QueryOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * FX Calculator implementation for converting amount using direct method
 * 
 * @author amitkumar.wali
 *
 */
@Component
public class DirectFxCalculator extends FxCalculator {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DirectFxCalculator.class);
	
	@Override
	public QueryOutput calculate(Query q, String conversionKey) {
		final String method = DirectFxCalculator.class.getName() + ".calculate()";
		LOGGER.debug("BEGIN METHOD - {}", method);
	
		BigDecimal conversionRate = new BigDecimal(this.getRate(conversionKey));
		String property = this.env.getProperty("fx.std.precision.".concat(q.getToCurrency()), "2");
		QueryOutput qo = new QueryOutput(q, q.getFromAmount().multiply(conversionRate), Integer.valueOf(property));
		
		LOGGER.debug("END METHOD - {}", method);
		return qo;
	}

}
