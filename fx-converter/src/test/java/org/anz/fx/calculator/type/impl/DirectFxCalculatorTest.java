package org.anz.fx.calculator.type.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.anz.fx.model.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class DirectFxCalculatorTest {

	@Autowired
	DirectFxCalculator directFxCalculator;
	
	@Test
	void audUsd_DirectCal_test() {
		Query q = new Query("AUD", BigDecimal.ONE, "USD");
		assertEquals("AUD 1.00 = USD 0.84", directFxCalculator.calculate(q, "AUDUSD").toString());
	}
	
	@Test
	void cadUsd_DirectCal_test() {
		Query q = new Query("CAD", BigDecimal.TEN, "USD");
		assertEquals("CAD 10.00 = USD 8.71", directFxCalculator.calculate(q, "CADUSD").toString());
	}
	
	@Test
	void eurCzk_DirectCal_test() {
		Query q = new Query("EUR", BigDecimal.ONE, "CZK");
		assertEquals("EUR 1.00 = CZK 27.60", directFxCalculator.calculate(q, "EURCZK").toString());
	}
	
	@Test
	void gbpUsd_DirectCal_test() {
		Query q = new Query("GBP", BigDecimal.TEN, "USD");
		assertEquals("GBP 10.00 = USD 15.68", directFxCalculator.calculate(q, "GBPUSD").toString());
	}

}
