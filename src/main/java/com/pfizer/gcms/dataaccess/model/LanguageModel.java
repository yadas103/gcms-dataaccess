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
 * @author joser05
 * LanguageModel is a POJO class, annotated with hibernate mapping and they are responsible for 
 * holding instances of data objects. This holds the Language data object.
 */
@Entity
@Table(name = "GCMS_ODS.GCMS_COUNTRY_LANGUAGES")
public class LanguageModel extends AbstractModel {
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The constant language name.
	 */
	public static final String LOCAL_LANG_NM = "languageName";
	
	/**
	 * The constant country ID.
	 */
	public static final String FIELD_COUNTRY_ID = "countryId";
	
	@Id
	@Column(name = "CNTRY_LANG_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GCMS_LANG_SEQ")
	@SequenceGenerator(name = "GCMS_LANG_SEQ", sequenceName = "GCMS_LANG_SEQ", allocationSize = 1)
	private BigDecimal id;
	
	@Column(name = "CNTRY_ID")
	private BigDecimal countryId;
	
	@Column(name = "CNTRY_NM")
	private String countryName;
	
	@Column(name = "ISO_CNTRY_CD")
	private String countryCode;
	
	@Column(name = "LOCAL_LANG_NM")
	private String languageName;
	
	@Column(name = "ISO_LANG_CD")
	private String languageCode;
	
	@Column(name = "DEFAULT_FLAG")
	private String isDefault;
	
	@Column(name = "DELETE_FLAG")
	private Character deleted;
	
	
	/**
	 * Default constructor.
	 */
	public LanguageModel() { }

	/**
	 * @return the countryId
	 */
	public BigDecimal getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
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
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the languageName
	 */
	public String getLanguageName() {
		return languageName;
	}

	/**
	 * @param languageName the languageName to set
	 */
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	/**
	 * @return the languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * @param user the user to set
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @return the isDefault
	 */
	public String getIsDefault() {
		return isDefault;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
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
