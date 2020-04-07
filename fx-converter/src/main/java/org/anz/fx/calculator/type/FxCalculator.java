/**
 * 
 */
package org.anz.fx.calculator.type;

import java.util.HashMap;
import java.util.Map;

import org.anz.fx.exception.RateNotFoundException;
import org.anz.fx.model.Query;
import org.anz.fx.model.QueryOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author amitkumar.wali
 *
 */
@Component
public abstract class FxCalculator {
	
	@Value("#{${fx.std.conversion.rates}}") 
	private final Map<String, String> conversionRates = new HashMap<String, String>();
	
	@Autowired
	protected Environment env;
	
	public abstract QueryOutput calculate(Query q, final String conversionKey) throws RateNotFoundException;

	public String getRate(String conversionKey) {
		return this.conversionRates.get(conversionKey);
	}

	public boolean isValidConversionKey(String conversionKey) {
		return this.conversionRates.containsKey(conversionKey);
	}

}
