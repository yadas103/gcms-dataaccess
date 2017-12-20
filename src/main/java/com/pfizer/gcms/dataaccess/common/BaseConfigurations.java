/**
 * 
 */
package com.pfizer.gcms.dataaccess.common;

import java.util.Locale;

/**
 * @author kaswas
 * This class provides base configuration values like default locale,
 * language and mock_hibernate or not.
 */
public class BaseConfigurations {
	/**
	 * The mock hibernate value.
	 */
	public static final Boolean MOCK_HIBERNATE = Boolean.FALSE;
	/**
	 * The default locale value.
	 */
	public static final Locale DEFAULT_LOCALE = Locale.GERMANY;
	/**
	 * The default language.
	 */
	public static final String DEFAULT_LANGUAGE = "EN";
}
