package org.anz.fx.calculator.type.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.anz.fx.calculator.type.FxCalculator;
import org.anz.fx.model.Query;
import org.anz.fx.model.QueryOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * FX Calculator implementation for converting amount using inverse method
 * 
 * @author amitkumar.wali
 *
 */
@Component
public class InverseFxCalculator extends FxCalculator {
	
	private static Logger LOGGER = LoggerFactory.getLogger(InverseFxCalculator.class);
	
	@Override
	public QueryOutput calculate(Query q, String conversionKey) {
		final String method = InverseFxCalculator.class.getName() + ".calculate()";
		LOGGER.info("BEGIN METHOD - {}", method);
	
		BigDecimal conversionRate = new BigDecimal(this.getRate(conversionKey));
		String property = this.env.getProperty("fx.std.precision.".concat(q.getToCurrency()), "2");
		QueryOutput qo = new QueryOutput(q, q.getFromAmount().divide(conversionRate, 2, RoundingMode.HALF_UP), Integer.valueOf(property));
		
		LOGGER.info("END METHOD - {}", method);
		return qo;
	}

}
