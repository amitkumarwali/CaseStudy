/**
 * 
 */
package org.anz.fx.calculator.type.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
class SameCurrFxCalculatorTest {
	
	@Autowired
	SameCurrFxCalculator sameCurrFxCalculator;

	@Test
	void jpyJpy_sameCal_test() {
		Query q = new Query("JPY", new BigDecimal(181), "JPY");
		assertEquals("JPY 181 = JPY 181", sameCurrFxCalculator.calculate(q, "JPYJPY").toString());
	}
	
	@Test
	void usdUsd_sameCal_test() {
		Query q = new Query("USD", new BigDecimal(120), "USD");
		assertEquals("USD 120.00 = USD 120.00", sameCurrFxCalculator.calculate(q, "USDUSD").toString());
	}

}
