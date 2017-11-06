package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * CountryModel is a POJO classes,annotated with hibernate mappings and they are responsible for 
 * holding instances of data objects.This holds the Country data object.
 */
@Entity
@Table(name = "GCMS_COUNTRIES_VIEW")
public class CountryModel extends AbstractModel {

	/**
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
	/**
	 * The constant country id.
	 */	
	public static final String FIELD_COUNTRY_ID = "id";
	
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
	
	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "country", fetch = FetchType.LAZY)
	//@JoinColumn(name = "CNTRY_ID", referencedColumnName = "CNTRY_ID")
	//private List<CountryAttributeModel> attributes;
	
	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "country", fetch = FetchType.LAZY)
	//private List<CountryDefaultUniqueIdentifierTypesModel> displayUniqueIdentifierType;
	
	/**
	 * Default constructor.
	 */
	public CountryModel() { }

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
/*
	*//**
	 * @return the attributes
	 *//*
	public List<CountryAttributeModel> getAttributes() {
		return attributes;
	}

	*//**
	 * @param attributes the attributes to set
	 *//*
	public void setAttributes(List<CountryAttributeModel> attributes) {
		this.attributes = attributes;
	}

	*//**
	 * @return the displayUniqueIdentifierType
	 *//*
	public List<CountryDefaultUniqueIdentifierTypesModel> getDisplayUniqueIdentifierType() {
		return displayUniqueIdentifierType;
	}

	*//**
	 * @param displayUniqueIdentifierType the displayUniqueIdentifierType to set
	 *//*
	public void setDisplayUniqueIdentifierType(
			List<CountryDefaultUniqueIdentifierTypesModel> displayUniqueIdentifierType) {
		this.displayUniqueIdentifierType = displayUniqueIdentifierType;
	}
*/
}
