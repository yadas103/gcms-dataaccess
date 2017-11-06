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
	
	
	public static final String FIELD_TASKID = "taskId";
	public static final String FIELD_TRID = "trId";
	public static final String FIELD_FIRSTNAME = "firstName";
	public static final String FIELD_LASTNAME = "lastName";
	public static final String FIELD_CONSENTSTAUS = "consentStatus";
	public static final String FIELD_TASKSTATUS = "taskStatus";
	public static final String FIELD_PAGENO = "pageNumber";
	public static final String FIELD_PAGESIZE = "pageSize";
	public static final String FIELD_EVENTNAME = "eventName";
	public static final String FIELD_INITIATEDBY = "initiatedBy";
	public static final String FIELD_COUNTRY = "country";
	public static final String FIELD_SORTBY = "sortBy";
	public static final String FIELD_SORTDESCENDING = "sortDescending";

	private String taskId;
	private String trId;
	private String firstName;
	private String lastName;
	private String consentStatus;
	private String taskStatus;
	private String pageNumber;
	private String pageSize;
	private String eventName;
	private String initiatedBy;
	private String country;
	private String sortBy;
	private String sortDescending ;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTrId() {
		return trId;
	}
	public void setTrId(String trId) {
		this.trId = trId;
	}
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getSortDescending() {
		return sortDescending;
	}
	public void setSortDescending(String sortDescending) {
		this.sortDescending = sortDescending;
	}
	
	
}