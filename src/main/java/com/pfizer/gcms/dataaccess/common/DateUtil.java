/**
 * 
 */
package com.pfizer.gcms.dataaccess.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author kaswas
 * DateUtil class is used to convert dates to the required format.
 */
public class DateUtil {
	/**
	 * The date format value.
	 */
	public static final String DB_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	/**
	 * The default date format value.
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
	
	private DateFormat dateFormat;
	private String pattern;
	
	/**
	 * Default constructor.
	 */
	public DateUtil() { 
		this.pattern = DEFAULT_DATE_FORMAT;
	}
	
	/**
	 * Constructor with date pattern.
	 * @param pattern - Date pattern
	 */
	public DateUtil(String pattern) {
		if (pattern != null && !pattern.trim().isEmpty()) { 
			this.pattern = pattern;
		} else {
			this.pattern = DEFAULT_DATE_FORMAT;
		}
	}
	
	/**
	 * Converts the given text to date.
	 * @param date - date to convert to date
	 * @return Date
	 * @throws ParseException
	 * 		If the conversion to date is failed. 
	 */
	public Date convertToDate(String date) throws ParseException {
		if (date != null && !date.trim().isEmpty()) {
			java.util.Date utilDate = getDateFormat().parse(date);
			
			if (utilDate != null) {
				return new Date(utilDate.getTime());
			}
		}
		return null;
	}
	
	/**
	 * Converts the given text to date.
	 * @param date - date to convert to string
	 * @return String
	 * @throws ParseException
	 * 		If the date conversion to string is failed. 
	 */
	public String convertToString(Date date) throws ParseException {
		String dateString = null;
		if (date != null) {
			 dateString = getDateFormat().format(date);
		}
		return dateString;
	}
	
	/**
	 * Initializes the date format if not already initialized to "yyyy-mm-dd".
	 * @return DateFormat
	 */
	public DateFormat getDateFormat() {
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat(pattern);
		}
		return dateFormat;
	}
	
	/**
	 * Gives the current date.
	 * @return Date
	 */
	public Date getCurrentDateTimeInMillis() {
		return new Date(Calendar.getInstance().getTimeInMillis());
	}
}
