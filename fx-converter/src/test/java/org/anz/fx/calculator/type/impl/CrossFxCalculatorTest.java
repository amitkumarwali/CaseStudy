/**
 * 
 */
package org.anz.fx.calculator.type.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;

import org.anz.fx.exception.RateNotFoundException;
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
class CrossFxCalculatorTest {
	
	@Autowired
	private CrossFxCalculator crossFxCalculator;

	@Test
	void eurCny_CrossCal_test() {
		Query q = new Query("EUR", new BigDecimal(12), "CNY");
		assertDoesNotThrow(() -> crossFxCalculator.calculate(q, "EURUSD|CNYUSD").toString(), "");
	}
	
	@Test
	void eurCny_CrossCalValue_test() {
		try {
			Query q = new Query("EUR", new BigDecimal(12), "CNY");
			assertEquals("EUR 12.00 = CNY 2.36", crossFxCalculator.calculate(q, "EURUSD|CNYUSD").toString());
		} catch (RateNotFoundException e) {
			fail();
		}
	}
	
	@Test
	void cadAud_CrossCal_test() {
		Query q = new Query("cad", new BigDecimal(15), "AUD");
		assertDoesNotThrow(() -> crossFxCalculator.calculate(q, "CADUSD|AUDUSD").toString(), "");
	}
	
	@Test
	void cadAud_CrossCalValue_test() {
		try {
			Query q = new Query("cad", new BigDecimal(15), "AUD");
			assertEquals("CAD 15.00 = AUD 15.55", crossFxCalculator.calculate(q, "CADUSD|AUDUSD").toString());
		} catch (RateNotFoundException e) {
			fail();
		}
	}
	
	
	@Test
	void audUsdDkk_CrossCal_test() {
		Query q = new Query("AUD", new BigDecimal(215), "DkK");
		assertDoesNotThrow(() -> crossFxCalculator.calculate(q, "AUDUSD|EURUSD|EURDKK").toString(), "");
	}
	
	@Test
	void audUsdDkk_CrossCalValue_test() {
		try {
			Query q = new Query("AUD", new BigDecimal(215), "DkK");
			assertEquals("AUD 215.00 = DKK 1084.68", crossFxCalculator.calculate(q, "AUDUSD|EURUSD|EURDKK").toString());
		} catch (RateNotFoundException e) {
			fail();
		}
	}
}
