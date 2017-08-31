package com.pfizer.gcms.dataaccess.dto;

import java.io.Serializable;


/**
 * @author rtalapaneni
 * SearchDTO is used as a criteria to use for search when model data can't be passed in.
 * It provides the flexibility to provide paging attributes.
 * @param <S>
 * 		(S) -  The representation
 */
public interface ISearchDTO<S> extends Serializable {
	
	/**
	 * Gives the country code to be used with search criteria.
	 * @return the country code
	 */
	public String getCountryCode();
	
	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode);
	
	/**
	 * Informs whether paging needs to be included or not.
	 * @return the enablePaging
	 */
	public boolean isEnablePaging();
	
	/**
	 * @param enablePaging the enablePaging to set
	 */
	public void setEnablePaging(boolean enablePaging);
	
	/**
	 * Page index for which search results needs to be fetched.
	 * Index for first page will be 0
	 * @return Integer - Page index to be used for fetching the results
	 */
	public  int getPageIndex();
	
	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(int pageIndex);
	
	/**
	 * Indicates maximum number of records to be fetched.
	 * @return Integer - Maximum number of results to be fetched
	 */
	public int getMaxResults();
	
	/**
	 * @param maxResults the maxResults to set
	 */
	public void setMaxResults(int maxResults);
	
	/**
	 * Gives the field to be used for sorting.
	 * @return the sort by
	 */
	public String getSortBy();
	
	/**
	 * @param sortBy the sortBy to set
	 */
	public void setSortBy(String sortBy);
	
	/**
	 * Gives whether sorting needs to be performed descending or ascending.
	 * @return the sort descending
	 */
	public boolean isSortDescending();
	
	/**
	 * @param sortDescending the sortDescending to set
	 */
	public void setSortDescending(boolean sortDescending);
	
	/**
	 * Model to be used by search process.
	 * It is not a mandatory attribute can return null when no model is associated
	 * @return S
	 */
	public S getModel();
	
	/**
	 * @param model the model to set
	 */
	public void setModel(S model);
}
