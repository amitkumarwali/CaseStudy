/**
 * 
 */
package org.anz.fx.cli;

import static org.junit.jupiter.api.Assertions.*;

import org.anz.fx.exception.RateNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author amitkumar.wali
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class FXConverterCLITest {

	@Autowired
	CommandLineRunner cliRunner;
	
	@Test
	void audToUsd_test() {
		assertDoesNotThrow(() -> cliRunner.run("AUD 10 in USD"), "");
	}
	
	@Test
	void usdToAud_test() {
		assertDoesNotThrow(() -> cliRunner.run("USD 5 in AUD"), "");
	}
	
	@Test
	void cadToAud_test() {
		assertDoesNotThrow(() -> cliRunner.run("CAD 4 in AUD"), "");
	}
	
	@Test
	void audToAud_test() {
		assertDoesNotThrow(() -> cliRunner.run("AUD 9 in AUD"), "");
	}
	
	@Test
	void audToDkk_test() {
		assertDoesNotThrow(() -> cliRunner.run("AUD 10 in DKK"), "");
	}
	
	
	@Test
	void rateNotFound_test() {
		RateNotFoundException exception = assertThrows(RateNotFoundException.class, () -> { 
			cliRunner.run("KRW 1000.00 in FJD");
	    });
	 
	    String expectedMessage = "Unable to find rate for";
	    String actualMessage = exception.getMessage();
	 
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void multipleValidInputs_test() {
		assertDoesNotThrow(() -> cliRunner.run("AUD 1 in DKK", "AUD 1 in USD", "USD 1 in AUD", "CAD 1 in AUD"), "");
	}
	
	
	@Test
	void multipleValidOneInvalidInputs_test() {
		RateNotFoundException exception = assertThrows(RateNotFoundException.class, () -> { 
			cliRunner.run("AUD 1 in DKK", "AUD 1 in USD", "USD 1 in AUD", "CAD 1 in AUD", "KRW 1000.00 in FJD");
	    });
	 
	    String expectedMessage = "Unable to find rate for";
	    String actualMessage = exception.getMessage();
	 
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void zeroAmount_test() {
		Exception exception = assertThrows(Exception.class, () -> { 
			cliRunner.run("USD 0 in AUD");
	    });
	 
	    String expectedMessage = "Supplied amount is either ZERO or NEGATIVE";
	    String actualMessage = exception.getMessage();
	 
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	
	@Test
	void negativeAmout_test() {
		Exception exception = assertThrows(Exception.class, () -> { 
			cliRunner.run("CAD -4.00 in AUD");
	    });
	 
	    String expectedMessage = "Supplied amount is either ZERO or NEGATIVE";
	    String actualMessage = exception.getMessage();
	 
	    assertTrue(actualMessage.contains(expectedMessage));
	}

}
