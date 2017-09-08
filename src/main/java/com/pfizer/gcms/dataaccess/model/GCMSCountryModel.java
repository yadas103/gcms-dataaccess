package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GCMS_COUNTRIES_VIEW")
public class GCMSCountryModel extends AbstractModel{/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The constant ISO code.
	 */		
	public static final String FIELD_ISO_CODE = "isoCode";
	/**
	 * The constant country code.
	 */		
	public static final String FIELD_COUNTRY_CODE = "code";
	/**
	 * The constant country name.
	 */		
	public static final String FIELD_COUNTRY_NAME = "name";
	
	@Id
	@Column(name = "CNTRY_ID")
	private BigDecimal	id;
	
	@Column(name = "ISO_CNTRY_CD")
	private String 	isoCode;
	
	@Column(name = "CNTRY_CD_3_CHAR")
	private String 	code;
	
	@Column(name = "CNTRY_NM")
	private String 	name;
	
	@Column(name = "UNQ_ID_TYP_ID")
	private BigDecimal uniqueIDTypeID;
	
	@Column(name = "CRNC_ID")
	private BigDecimal currencyID;
	
	@Column(name = "CNTRY_CONTACT")
	private String cntry_contact;
	
	@Column(name = "CNTRY_REVIEWERS")
	private String cntry_reviewers;
	
	/**
	 * Default constructor.
	 */
	public GCMSCountryModel() { }

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
	 * @return the isoCode
	 */
	public String getIsoCode() {
		return isoCode;
	}

	/**
	 * @param isoCode
	 *            the isoCode to set
	 */
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the uniqueIDTypeID
	 */
	public BigDecimal getUniqueIDTypeID() {
		return uniqueIDTypeID;
	}

	/**
	 * @param uniqueIDTypeID the uniqueIDTypeID to set
	 */
	public void setUniqueIDTypeID(BigDecimal uniqueIDTypeID) {
		this.uniqueIDTypeID = uniqueIDTypeID;
	}

	/**
	 * @return the currencyID
	 */
	public BigDecimal getCurrencyID() {
		return currencyID;
	}

	/**
	 * @param currencyID the currencyID to set
	 */
	public void setCurrencyID(BigDecimal currencyID) {
		this.currencyID = currencyID;
	}

	


}

