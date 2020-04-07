package org.anz.fx.exception;

/**
 * Raise this exception in case Rate is not available
 * 
 * @author amitkumar.wali
 *
 */
public class RateNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1854244595600223865L;

	public RateNotFoundException(String message) {
		super(message);
	}

}
