package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author KASWAS
 * CredentialModel is a POJO classes,annotated with hibernate mappings and they are responsible for 
 * holding instances of data objects.
 */
@Entity
@Table(name = "GCMS_ODS.GCMS_CREDENTIALS")
public class CredentialModel extends AbstractModel {
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="seq",sequenceName="GCMS_ODS.GCMS_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	@Column(name = "CRDNL_ID")
	private BigDecimal   id;
	
	@Column(name = "CRDNL_CD")
	private String credentialCode;

	@Column(name = "CRDNL_NM")
	private String credentialDesc;

	@Column(name = "BATCH_ID")
	private BigDecimal batchId;
	
	@Column(name = "FILE_ID")
	private BigDecimal fileId;
	
	@Column(name = "CNTRY_ID")
	private BigDecimal countryId;
	
	@Column(name = "ISO_CNTRY_CD")
	private String countryCode;
	
	@Column(name = "DELETE_FLAG")
	private String delete;

	@Column(name = "COMMENTS")
	private String comments;
	
	@Column(name = "PARTY_TYPE")
	private String partType;
	
	/**
	 * @return the profileReqId
	 */
	public BigDecimal getId() {
		return id;
	}


	/**
	 * @param profileReqId the profileReqId to set
	 */
	public void setId(BigDecimal id) {
		this.id = id;
	}


	public String getCredentialCode() {
		return credentialCode;
	}


	public void setCredentialCode(String credentialCode) {
		this.credentialCode = credentialCode;
	}


	public String getCredentialDesc() {
		return credentialDesc;
	}


	public void setCredentialDesc(String credentialDesc) {
		this.credentialDesc = credentialDesc;
	}


	public String getPartType() {
		return partType;
	}


	public void setPartType(String partType) {
		this.partType = partType;
	}


	public BigDecimal getBatchId() {
		return batchId;
	}


	public void setBatchId(BigDecimal batchId) {
		this.batchId = batchId;
	}


	public BigDecimal getFileId() {
		return fileId;
	}


	public void setFileId(BigDecimal fileId) {
		this.fileId = fileId;
	}


	public BigDecimal getCountryId() {
		return countryId;
	}


	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}


	public String getDelete() {
		return delete;
	}


	public void setDelete(String delete) {
		this.delete = delete;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
