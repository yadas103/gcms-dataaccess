package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * /**
 * @author rtalapaneni
 * Consent Lov Model is a POJO classes,annotated with hibernate mappings and they are responsible for 
 * holding instances of data objects.This holds the Consent data object.
 

 */
@Entity
@Table(name = "GCMS_ODS.GCMS_CONSENT_STATUS_VIEW")
public class ConsentLovModel extends AbstractModel{
	
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The constant consent name.
	 */		
	public static final String FIELD_CONSENT_NAME = "consentName";
	public static final String FIELD_CONSENT_STS_ID = "id";
	
	@Id
	@Column(name = "CNSN_STS_ID")
	@GeneratedValue
	private BigDecimal id;
	
	@Column(name = "CNSN_STS_CD")
	private String consentStatusCode;
	@Column(name = "CNSN_STS_NM")
	private String consentName;
	@Column(name = "DELETE_FLAG")
	private Character deleted;
	
	/**
	 * Default constructor.
	 */
	public ConsentLovModel() { }
	
	/**
	 * @return id the unique identifier of the model 
	 * 			
	 */
	public BigDecimal getId() {
		return id;
	}

	/**
	 * @param id the unique identifier of the model
	 */
	public void setId(BigDecimal id) {
		this.id = id;
	}
	
	/**
	 * @return consentStatus
	 */
	public String getConsentStatus() {
		return consentStatusCode;
	}
	/**
	 * @param consentStatus
	 * 			the consentStatus to set
	 * 
	 */
	public void setConsentStatus(String consentStatus) {
		this.consentStatusCode = consentStatus;
	}
	/**
	 * @return consentName
	 */
	public String getConsentName() {
		return consentName;
	}
	/**
	 * @param consentName
	 * 		the consentName to set
	 */
	public void setConsentName(String consentName) {
		this.consentName = consentName;
	}
	/**
	 * @return the consentStatusCode
	 */
	public String getConsentStatusCode() {
		return consentStatusCode;
	}
	/**
	 * @param consentStatusCode the consentStatusCode to set
	 */
	public void setConsentStatusCode(String consentStatusCode) {
		this.consentStatusCode = consentStatusCode;
	}

	/**
	 * @return the deleted
	 */
	public Character getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Character deleted) {
		this.deleted = deleted;
	}
}
