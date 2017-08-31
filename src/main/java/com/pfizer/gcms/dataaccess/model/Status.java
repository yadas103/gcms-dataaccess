package com.pfizer.gcms.dataaccess.model;

/**
 * @author prayadu
 * This Enum class is used to create the status either active or inactive.
 */
@Deprecated
public enum Status {
	/**
	 * The Constant ACTIVE value true.
	 */		
	Active(true) , 
	/**
	 * The Constant INACTIVE value false.
	 */		
	Inactive(false);
	private final boolean statusCode;
	/**
	 * @param statusCode
	 */
	Status(boolean statusCode) {
	    this.statusCode = statusCode;
	}
}
