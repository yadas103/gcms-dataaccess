/**
 * 
 */
package com.pfizer.gcms.dataaccess.common.exception;

/**
 * @author rtalapaneni This exception should be used in case of Internal server
 *         error passed to methods.
 */
public class GCMSInternalServerError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public GCMSInternalServerError() {
	}

	/**
	 * Constructor with a message.
	 * 
	 * @param message
	 *            - exception message
	 */
	public GCMSInternalServerError(String message) {
		super(message);
	}

	/**
	 * Constructor with cause.
	 * 
	 * @param cause
	 *            - cause for the exception
	 */
	public GCMSInternalServerError(Throwable cause) {
		super(cause);
	}
}
