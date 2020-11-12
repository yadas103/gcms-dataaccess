package com.pfizer.gcms.dataaccess.dto;

import java.math.BigDecimal;
import com.pfizer.gcms.dataaccess.model.TaskModel;


/**
 * @author khans129
 * This holds the search criteria to be used for fetching task.
 */
public class TaskSearchDTO {
	/**
	 * The Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FIELD_FIRSTNAME = "firstName";
	public static final String FIELD_LASTNAME = "lastName";
	public static final String FIELD_CONSENTSTAUS = "consentStatus";
	public static final String FIELD_TASKSTATUS = "taskStatus";
	public static final String FIELD_PAGENO = "pageNumber";
	public static final String FIELD_PAGESIZE = "pageSize";
	public static final String FIELD_EVENTNAME = "eventName";
	public static final String FIELD_INITIATEDBY = "initiatedBy";
	public static final String FIELD_UPDATEDDATE = "updateddate";
	public static final String FIELD_PROFILE_COUNTRY = "profilecountry";
	public static final String FIELD_PAYER_COUNTRY = "payercountry";
	public static final String FIELD_SORTBY = "sortBy";
	public static final String FIELD_SORTDESCENDING = "sortDescending";
	public static final String FIELD_REGIONID = "regionId";
	public static final String FIELD_TEMPPROFILE = "tempProfile";


	private String firstName;
	private String lastName;
	private String consentStatus;
	private String taskStatus;
	private String pageNumber;
	private String pageSize;
	private String eventName;
	private String initiatedBy;
	private String updateddate;
	private String profilecountry;
	private String payercountry;
	private String sortBy;
	private BigDecimal regionId;
	private boolean	sortDescending = false;
	private String tempProfile;
	
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getConsentStatus() {
		return consentStatus;
	}
	public void setConsentStatus(String consentStatus) {
		this.consentStatus = consentStatus;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getInitiatedBy() {
		return initiatedBy;
	}
	public void setInitiatedBy(String initiatedBy) {
		this.initiatedBy = initiatedBy;
	}
	
	public String getProfilecountry() {
		return profilecountry;
	}
	public void setProfilecountry(String profilecountry) {
		this.profilecountry = profilecountry;
	}
	
	public String getPayercountry() {
		return payercountry;
	}
	public void setPayercountry(String payercountry) {
		this.payercountry = payercountry;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public boolean isSortDescending() {
		return sortDescending;
	}
	public void setSortDescending(boolean sortDescending) {
		this.sortDescending = sortDescending;
	}
	public String getUpdateddate() {
		return updateddate;
	}
	public void setUpdateddate(String updateddate) {
		this.updateddate = updateddate;
	}
	public BigDecimal getRegionId() {
		return regionId;
	}
	public void setRegionId(BigDecimal regionId) {
		this.regionId = regionId;
	}
	/**
	 * @return the tempProfile
	 */
	public String getTempProfile() {
		return tempProfile;
	}
	/**
	 * @param tempProfile the tempProfile to set
	 */
	public void setTempProfile(String tempProfile) {
		this.tempProfile = tempProfile;
	}
	
	
}