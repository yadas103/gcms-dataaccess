package com.pfizer.gcms.dataaccess.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author rnarne
 * PagingSearchResultDTO is used get the pagination results required for search requests.
 * @param <S>
 * 		(S) - The Representation
 */
public class PagingSearchResultDTO<S> implements Serializable{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	private Long 	totalRecordsCount;
	private List<S> currentPageData;
	
	/**
	 * Default constructor.
	 */
	public PagingSearchResultDTO() { }

	/**
	 * @return the totalRecordsCount
	 */
	public Long getTotalRecordsCount() {
		return totalRecordsCount;
	}

	/**
	 * @param totalRecordsCount the totalRecordsCount to set
	 */
	public void setTotalRecordsCount(Long totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}

	/**
	 * @return the currentPageData
	 */
	public List<S> getCurrentPageData() {
		return currentPageData;
	}

	/**
	 * @param currentPageData the currentPageData to set
	 */
	public void setCurrentPageData(List<S> currentPageData) {
		this.currentPageData = currentPageData;
	}

	@Override
	public String toString() {
		return "PagingSearchResultDTO [totalRecordsCount=" + totalRecordsCount + ", currentPageData=" + currentPageData
				+ "]";
	}	
	
	
}
