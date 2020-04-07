/**
 * 
 */
package org.anz.fx.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.anz.fx.exception.RateNotFoundException;
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
class CrossConverterProviderTest {
	
	@Test
	void inrUsd_notFound_test() {
		RateNotFoundException exception = assertThrows(RateNotFoundException.class, () -> { 
			CrossConverterProvider.getCrossConverter("INR", "USD");
	    });
	 
	    String expectedMessage = "Unable to find rate for";
	    String actualMessage = exception.getMessage();
	 
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void eurNok_directConversion_test() {
		try {
			assertEquals("EURNOK", CrossConverterProvider.getCrossConverter("EUR", "NOK"));
		} catch (RateNotFoundException e) {
			fail();		
		}
	}
	
	@Test
	void nzdUsd_directConversion_test() {
		try {
			assertEquals("NZDUSD", CrossConverterProvider.getCrossConverter("NZD", "USD"));
		} catch (RateNotFoundException e) {
			fail();		
		}
	}
	
	@Test
	void nokEur_containsInversionCurr_test() {
		try {
			assertTrue(CrossConverterProvider.getCrossConverter("NOK", "EUR").contains("EUR"));
		} catch (RateNotFoundException e) {
			fail();	
		}
	}
	
	
	@Test
	void nokEur_invConversion_test() {
		try {
			assertEquals("EURNOK", CrossConverterProvider.getCrossConverter("NOK", "EUR"));
		} catch (RateNotFoundException e) {
			fail();		
		}
	}
	
	
	@Test
	void usdNzd_containsInversionCurr_test() {
		try {
			assertTrue(CrossConverterProvider.getCrossConverter("USD", "NZD").contains("NZD"));
		} catch (RateNotFoundException e) {
			fail();	
		}
	}
	
	
	@Test
	void usdNzd_invConversion_test() {
		try {
			assertEquals("NZDUSD", CrossConverterProvider.getCrossConverter("USD", "NZD"));
		} catch (RateNotFoundException e) {
			fail();		
		}
	}
	
	@Test
	void eurCny_containsCrossCurr_test() {
		try {
			assertTrue(CrossConverterProvider.getCrossConverter("EUR", "CNY").contains("USD"));
		} catch (RateNotFoundException e) {
			fail();	
		}
	}
	
	
	@Test
	void eurCny_completeCrossTraversal_test() {
		try {
			assertEquals("EURUSD|CNYUSD", CrossConverterProvider.getCrossConverter("EUR", "CNY"));
		} catch (RateNotFoundException e) {
			fail();		
		}
	}
	
	
	@Test
	void cadAud_containsCrossCurr_test() {
		try {
			assertTrue(CrossConverterProvider.getCrossConverter("CAD", "AUD").contains("USD"));
		} catch (RateNotFoundException e) {
			fail();	
		}
	}
	
	
	@Test
	void cadAud_completeCrossTraversal_test() {
		try {
			assertEquals("CADUSD|AUDUSD", CrossConverterProvider.getCrossConverter("CAD", "AUD"));
		} catch (RateNotFoundException e) {
			fail();		
		}
	}

	@Test
	void nokCzk_containsCrossCurr_test() {
		try {
			assertTrue(CrossConverterProvider.getCrossConverter("NOK", "CZK").contains("EUR"));
		} catch (RateNotFoundException e) {
			fail();	
		}
	}
	
	
	@Test
	void nokCzk_completeCrossTraversal_test() {
		try {
			assertEquals("EURNOK|EURCZK", CrossConverterProvider.getCrossConverter("NOK", "CZK"));
		} catch (RateNotFoundException e) {
			fail();		
		}
	}
	
	@Test
	void jpyJpy_sameCurrency_test() {
		try {
			assertNull(CrossConverterProvider.getCrossConverter("JPY", "JPY"));
		} catch (RateNotFoundException e) {
			fail();		
		}
	}
	
	@Test
	void dkkDkk_sameCurrency_test() {
		try {
			assertNull(CrossConverterProvider.getCrossConverter("DKK", "DKK"));
		} catch (RateNotFoundException e) {
			fail();		
		}
	}
	
	
	@Test
	void jpyDkk_containsCrossCurr_test() {
		try {
			assertTrue(CrossConverterProvider.getCrossConverter("JPY", "DKK").contains("USD"));
		} catch (RateNotFoundException e) {
			fail();		
		}
	}
	
	
	@Test
	void jpyDkk_completeCrossTraversal_test() {
		try {
			assertEquals("USDJPY|EURUSD|EURDKK", CrossConverterProvider.getCrossConverter("JPY", "DKK"));
		} catch (RateNotFoundException e) {
			fail();		
		}
	}
	
	@Test
	void init_test(@Autowired CrossConverterProvider ccp) {
		assertDoesNotThrow(() -> ccp.init(), "");
	}

}
