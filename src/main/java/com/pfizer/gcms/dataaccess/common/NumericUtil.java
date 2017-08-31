/**
 * 
 */
package com.pfizer.gcms.dataaccess.common;

import java.math.BigDecimal;

/**
 * @author rtalapaneni
 *
 */
public class NumericUtil {

	/**
	 * 
	 */
	public NumericUtil() { }

	/**
	 * Checks for the not null and value is greater than zero.
	 * @param value
	 * @return
	 */
	public static boolean isNotNullAndGreaterThanZero(BigDecimal value) {
		return !isNullOrLessThanOrEqualToZero(value);
	}
	
	/**
	 * Checks for the not null and value is greater than zero.
	 * @param value
	 * @return
	 */
	public static boolean isNullOrLessThanOrEqualToZero(BigDecimal value) {
		if (value == null || value.compareTo(new BigDecimal(0)) <= 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks for the not null and value is greater than zero.
	 * @param value
	 * @return
	 */
	public static boolean isNullOrLessThanOrEqualToZero(Integer value) {
		if (value == null || value <= 0) {
			return true;
		}
		return false;
	}
}
