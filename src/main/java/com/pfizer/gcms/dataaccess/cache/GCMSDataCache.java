/**
 * 
 */
package com.pfizer.gcms.dataaccess.cache;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.pfizer.gcms.dataaccess.common.NumericUtil;

/**
 * @author rtalapaneni
 * TRSDataCache Class is used to keep required data in cache in order to reduce the repetitive calls to database.
 */
public class GCMSDataCache {
	private static GCMSDataCache instance;
	
	private Map<String, BigDecimal> numericValueCacheData;
	
	/**
	 * Default constructor with private scope to make it singleton.
	 */
	private GCMSDataCache() {
		clearCache();
	}
	
	/**
	 * Gives the singleton instance of the class.
	 * @return the current instance
	 */
	public static GCMSDataCache getInstance() {
		if (instance == null) {
			instance = new GCMSDataCache();
		}
		return instance;
	}

	/**
	 * Registers the given data into cache.
	 * Re-Registers the new value if provided is not matching with already existing data in cache.
	 * @param key - Name of the key
	 * @param value - value for the key
	 */
	public void registerData(String key, BigDecimal value) {
		if (!StringUtils.isBlank(key) && NumericUtil.isNotNullAndGreaterThanZero(value)) {
			if (numericValueCacheData.containsKey(key) && !value.equals(numericValueCacheData.get(key))) {
				numericValueCacheData.remove(key);
			}
			numericValueCacheData.put(key, value);
		}
	}
	
	/**
	 * Gives the numeric value mapped against given key in cache.
	 * Returns null if not cached before.
	 * @param key - Name of the key
	 * @return - BigDecimal
	 */
	public BigDecimal getNumericValue(String key) {
		return numericValueCacheData.get(key);
	}
	
	/**
	 * Clears the numeric value mapped against given key in cache.
	 */
	public void clearCache() {
		numericValueCacheData = new HashMap<String, BigDecimal>();
	}

}
