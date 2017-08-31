/**
 * 
 */
package com.pfizer.gcms.dataaccess.common.exception;

/**
 * @author rtalapaneni
 * This exception should be used in case of invalid data passed to methods.
 */
public class GCMSBadDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 */
	public GCMSBadDataException() { }
	
	/**
	 * Constructor with message.
	 * @param message - exception message 
	 */
	public GCMSBadDataException(String message) {
		super(message);
	}
	
	/**
	 * Constructor with cause.
	 * @param cause - cause for the exception
	 */
	public GCMSBadDataException(Throwable cause) {
		super(cause);
	}

}
