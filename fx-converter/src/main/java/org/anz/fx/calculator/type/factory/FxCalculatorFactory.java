package org.anz.fx.calculator.type.factory;

import org.anz.fx.calculator.type.FxCalculator;
import org.anz.fx.calculator.type.impl.CrossFxCalculator;
import org.anz.fx.calculator.type.impl.DirectFxCalculator;
import org.anz.fx.calculator.type.impl.InverseFxCalculator;
import org.anz.fx.calculator.type.impl.SameCurrFxCalculator;
import org.anz.fx.model.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory implementation to choose the required calculator.
 * 
 * @author amitkumar.wali
 *
 */
@Component
public class FxCalculatorFactory {

	@Autowired
	private DirectFxCalculator directFxCalculator;

	@Autowired
	private InverseFxCalculator inverseFxCalculator;
	
	@Autowired
	private CrossFxCalculator crossFxCalculator;
	
	@Autowired
	private SameCurrFxCalculator sameCurrFxCalculator;

	public FxCalculator getFxCalculator(Query q, String conversionKey) {
		if (!q.getFromCurrency().equalsIgnoreCase(q.getToCurrency())) {
			if (!conversionKey.contains("|")) {
				if (conversionKey.startsWith(q.getFromCurrency())) { // Direct Conversion
					return directFxCalculator;
				} 
				else if (conversionKey.startsWith(q.getToCurrency())) { // Indirect
					return inverseFxCalculator;
				}
			}
			else {
				return crossFxCalculator; //Cross Conversion
			}
		}
		else {
			return sameCurrFxCalculator; //Same currency Conversion
		}
		
		return null;
	}

}
