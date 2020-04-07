/**
 * 
 */
package org.anz.fx.calculator.type.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.anz.fx.model.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author amitkumar.wali
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class InverseFxCalculatorTest {
	
	@Autowired
	InverseFxCalculator inverseFxCalculator;
	
	@Test
	void usdGbp_InverseCal_test() {
		Query q = new Query("USD", new BigDecimal(15), "gbp");
		assertEquals("USD 15.00 = GBP 9.56", inverseFxCalculator.calculate(q, "GBPUSD").toString());
	}
	
	@Test
	void nokEUR_InverseCal_test() {
		Query q = new Query("NOK", new BigDecimal(15), "EUR");
		assertEquals("NOK 15.00 = EUR 1.73", inverseFxCalculator.calculate(q, "EURNOK").toString());
	}
	
	@Test
	void jspUsd_InverseCal_test() {
		Query q = new Query("JPY", new BigDecimal(15), "USD");
		assertEquals("JPY 15.00 = USD 0.13", inverseFxCalculator.calculate(q, "USDJPY").toString());
	}
	
	@Test
	void usdAud_InverseCal_test() {
		Query q = new Query("USD", new BigDecimal(15), "AUD");
		assertEquals("USD 15.00 = AUD 17.92", inverseFxCalculator.calculate(q, "AUDUSD").toString());
	}

}
