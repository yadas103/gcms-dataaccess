package com.pfizer.gcms.dataaccess.model.error;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.pfizer.gcms.dataaccess.model.BaseModel;

/**
 * @author rnarne
 * ErrorReferenceLovModel is a POJO classes,annotated with hibernate mappings and they are responsible for 
 * holding instances of data objects. This holds the ErrorReferenceLov data object.
 */
@Entity
@Table(name = "T_REF_ERROR")
public class ErrorReferenceLovModel implements BaseModel {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
		
	@Id
	@Column(name = "ERROR_CD_ID")
	private BigDecimal id;
	
	@Column(name = "ERR_CD")
	private String errorCode;
	
	@Column(name = "ERR_DESC")
	private String errorDescription;
	
	@Column(name = "CONCAT_FLG")
	private Character concatFlag;
	
	@Transient
	private Date 	createdDate;
	
	@Transient
	private String 	createdBy;
	
	@Transient
	private Date	updatedDate;
	
	@Transient
	private String 	updatedBy;
	
	/**
	 * Default constructor.
	 */
	public ErrorReferenceLovModel() { }

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * @return the concatFlag
	 */
	public Character getConcatFlag() {
		return concatFlag;
	}

	/**
	 * @param concatFlag the concatFlag to set
	 */
	public void setConcatFlag(Character concatFlag) {
		this.concatFlag = concatFlag;
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
	
	/**
	 * Date instance directly passes the reference which allows unauthorized modification
	 * so cloning and sending it is the suggested way.
	 * @param date
	 * 		(Date)  - The date
	 * @return the date
	 */
	public Date cloneDate(Date date) {
		if (date != null) {
			return (Date) date.clone();
		}
		return null;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return cloneDate(createdDate);
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = cloneDate(createdDate);
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return cloneDate(updatedDate);
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = cloneDate(updatedDate);
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
