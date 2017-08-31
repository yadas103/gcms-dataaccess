/**
 * 
 */
package com.pfizer.gcms.dataaccess.model.error;

import org.hibernate.annotations.DiscriminatorOptions;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author rtalapaneni
 * Error Audit Model is  a POJO classes,annotated with hibernate mappings and they are responsible 
 * for holding instances of data objects.This holds the Error Audit data object.
 */
@Entity
@Table(name = "T_ERROR_AUDIT_PORTAL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TGT_TBL_NM", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorOptions(force = true)
public abstract class ErrorModel implements Serializable {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The constant active indicator.
	 */
	public static final String FIELD_ACTIVE_INDICATOR = "activeIndicator";
	/**
	 * The constant target table name.
	 */
	public static final String FIELD_TARGET_TABLE_NAME = "targetTableName";

	/*The below columns are available in database but not used by portal as these are not considered in error handling from portal.
	 * @Column(name = "HOP_ID")
	 * @Column(name = "PRS_TBL_NM")
	 * @Column(name = "SRC_TBL_NM")
	 * @Column(name = "SRC_ATTR_NM")
	 * @Column(name = "SRC_ATTR_VAL")
	 * @Column(name = "SOURCE_SYS_CODE")
	 * @Column(name = "SOURCE_SUB_SYS_CODE")
	 * @Column(name = "SRC_BUSINESS_KEYS")
	 * @Column(name = "SRC_BUSINESS_KEY_VALUES")
	 * */
		
	@Id
	@Column(name = "ERR_AUDIT_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ERR_AUDIT_ID")
	@SequenceGenerator(name = "SEQ_ERR_AUDIT_ID", sequenceName = "SEQ_ERR_AUDIT_ID", allocationSize = 1)
	private BigDecimal id;
	
	@Column(name = "ERR_CD_ID")
	private BigDecimal errorCodeID;
	
	@Column(name = "TGT_TBL_NM", insertable = false, updatable = false)
	private String targetTableName;
	
	@Column(name = "TGT_ATTR_NM")
	private String targetAttributeName;
	
	@Column(name = "TGT_ATTR_VAL")
	private String targetAttributeValue;
	
	@Column(name = "FILE_ID", updatable = false)
	private BigDecimal fileID = new BigDecimal(-1);
	
	@Column(name = "BATCH_ID", updatable = false)
	private BigDecimal batchID = new BigDecimal(-1);
	
	@Column(name = "ERR_TYPE")
	private String errorType;
	
	@Column(name = "ACTIVE_IND")
	private Character activeIndicator;
	
	@Column(name = "ODS_BUSINESS_KEYS")
	private String odsBusinessKeys;
	
	@Column(name = "CRTD_DT")
	private Date createdDate;
	
	@Column(name = "UPDTD_DT")
	private Date updatedDate;
	
	@Column(name = "ERROR_DESC")
	private String errorDescription;
	
	@Column(name = "SRC_RECORD_ID")
	private BigDecimal sourceRecordID;
	
	//This column is not available in database but used for representation of errors
	@Transient
	protected String 	createdBy;
	
	//This column is not available in database but used for representation of errors
	@Transient
	private String 	updatedBy;
	
	/**
	 * Default constructor.
	 */
	public ErrorModel() { }

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
	 * @return the errorCodeID
	 */
	public BigDecimal getErrorCodeID() {
		return errorCodeID;
	}

	/**
	 * @param errorCodeID the errorCodeID to set
	 */
	public void setErrorCodeID(BigDecimal errorCodeID) {
		this.errorCodeID = errorCodeID;
	}

	/**
	 * @return the targetTableName
	 */
	public String getTargetTableName() {
		return targetTableName;
	}

	/**
	 * @param targetTableName the targetTableName to set
	 */
	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}

	/**
	 * @return the targetAttributeName
	 */
	public String getTargetAttributeName() {
		return targetAttributeName;
	}

	/**
	 * @param targetAttributeName the targetAttributeName to set
	 */
	public void setTargetAttributeName(String targetAttributeName) {
		this.targetAttributeName = targetAttributeName;
	}

	/**
	 * @return the targetAttributeValue
	 */
	public String getTargetAttributeValue() {
		return targetAttributeValue;
	}

	/**
	 * @param targetAttributeValue the targetAttributeValue to set
	 */
	public void setTargetAttributeValue(String targetAttributeValue) {
		this.targetAttributeValue = targetAttributeValue;
	}

	/**
	 * @return the fileID
	 */
	public BigDecimal getFileID() {
		return fileID;
	}

	/**
	 * @param fileID the fileID to set
	 */
	public void setFileID(BigDecimal fileID) {
		this.fileID = fileID;
	}

	/**
	 * @return the batchID
	 */
	public BigDecimal getBatchID() {
		return batchID;
	}

	/**
	 * @param batchID the batchID to set
	 */
	public void setBatchID(BigDecimal batchID) {
		this.batchID = batchID;
	}

	/**
	 * @return the errorType
	 */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * @param errorType the errorType to set
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	/**
	 * @return the activeIndicator
	 */
	public Character getActiveIndicator() {
		return activeIndicator;
	}

	/**
	 * @param activeIndicator the activeIndicator to set
	 */
	public void setActiveIndicator(Character activeIndicator) {
		this.activeIndicator = activeIndicator;
	}

	/**
	 * @return the odsBusinessKeys
	 */
	public String getOdsBusinessKeys() {
		return odsBusinessKeys;
	}

	/**
	 * @param odsBusinessKeys the odsBusinessKeys to set
	 */
	public void setOdsBusinessKeys(String odsBusinessKeys) {
		this.odsBusinessKeys = odsBusinessKeys;
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
	 * @param createdDate the createDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = cloneDate(createdDate);
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
	 * @return the sourceRecordID
	 */
	public BigDecimal getSourceRecordID() {
		return sourceRecordID;
	}

	/**
	 * @param sourceRecordID the sourceRecordID to set
	 */
	public void setSourceRecordID(BigDecimal sourceRecordID) {
		this.sourceRecordID = sourceRecordID;
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
