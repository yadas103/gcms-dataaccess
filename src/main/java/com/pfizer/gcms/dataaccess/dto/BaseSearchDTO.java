/**
 * 
 */
package com.pfizer.gcms.dataaccess.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rtalapaneni
 * BaseSearchDTO is used as a criteria to use for search where DTO data have the pagination.
 * It provides the flexibility to provide paging attributes.
 * @param <S>
 * 		(S) - The Representation
 */
public class BaseSearchDTO<S> implements ISearchDTO<S> {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The Constant COuntry code.
	 */	
	public static final String FIELD_COUNTRYCODE = "countryCode";
	/**
	 * The Constant enable paging.
	 */	
	public static final String FIELD_ENABLE_PAGING = "enablePaging";
	/**
	 * The Constant page index.
	 */	
	public static final String FIELD_PAGEINDEX = "pageIndex";
	/**
	 * The Constant maximum results.
	 */	
	public static final String FIELD_MAXRESULTS = "maxResults";
	/**
	 * The Constant sort by.
	 */	
	public static final String FIELD_SORTBY = "sortBy";
	/**
	 * The Constant sort Descending.
	 */
	public static final String FIELD_SORTDESCENDING = "sortDescending";

	private BigDecimal id;
	private String 	countryCode;
	private boolean enablePaging = false;
	private int 	pageIndex;
	private int 	maxResults;
	private String 	sortBy;
	private boolean	sortDescending = false;
	private S		model;
	
	
	
	
	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the enablePaging
	 */
	public boolean isEnablePaging() {
		return enablePaging;
	}

	/**
	 * @param enablePaging the enablePaging to set
	 */
	public void setEnablePaging(boolean enablePaging) {
		this.enablePaging = enablePaging;
	}

	/**
	 * @return pageIndex Integer
	 */
	@Override
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * @return maxResults - Integer max results
	 */
	@Override
	public int getMaxResults() {
		return maxResults;
	}
	
	/**
	 * @param maxResults the maxResults to set
	 */
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	/**
	 * @return the sortBy
	 */
	public String getSortBy() {
		return sortBy;
	}

	/**
	 * @param sortBy the sortBy to set
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	/**
	 * @return the sortDescending
	 */
	public boolean isSortDescending() {
		return sortDescending;
	}

	/**
	 * @param sortDescending the sortDescending to set
	 */
	public void setSortDescending(boolean sortDescending) {
		this.sortDescending = sortDescending;
	}

	/**
	 * @return model
	 */
	@Override
	public S getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(S model) {
		this.model = model;
	}
	/**
	 * Date instance directly passes the reference which allows unauthorized modification
	 * so cloning and sending it is the suggested way.
	 * @param date - date to be cloned
	 * @return Date
	 */
	protected Date cloneDate(Date date) {
		if (date != null) {
			return (Date) date.clone();
		}
		return null;
	}

	/**
	 * @return the id
	 */
	public BigDecimal getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(BigDecimal id) {
		this.id = id;
	}	
}
