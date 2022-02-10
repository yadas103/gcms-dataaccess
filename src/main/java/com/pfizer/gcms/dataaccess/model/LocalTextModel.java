package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author joser05
 * LocalTextModel is a POJO class, annotated with hibernate mapping and they are responsible for 
 * holding instances of data objects. This holds the Local Text data object.
 */
@Entity
@Table(name = "GCMS_ODS.GCMS_LOCAL_LANGUAGE_TEXT")
public class LocalTextModel extends AbstractModel {
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The constant user name.
	 */
	public static final String LOCAL_LANG_TEXT = "localLangText";
	
	@Id
	@Column(name = "LOCAL_LANG_TEXT_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GCMS_LANG_SEQ")
	@SequenceGenerator(name = "GCMS_LANG_SEQ", sequenceName = "GCMS_LANG_SEQ", allocationSize = 1)
	private BigDecimal id;
	
	@Column(name = "ATTRIBUTE_NAME")
	private String attributeName;
	
	@Column(name = "LOCAL_LANG_NM")
	private String languageName;
	
	@Column(name = "ISO_LANG_CD")
	private String languageCode;
	
	@Column(name = "LOCAL_LANG_TEXT")
	private String localLangText;
	
	@Column(name = "DELETE_FLAG")
	private Character deleted;
	
	/**
	 * Default constructor.
	 */
	public LocalTextModel() { }

	
	/**
	 * @return the attributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * @param attributeName the attributeName to set
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
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
	 * @return the localLangText
	 */
	public String getLocalLangText() {
		return localLangText;
	}

	/**
	 * @param localLangText the localLangText to set
	 */
	public void setLocalLangText(String localLangText) {
		this.localLangText = localLangText;
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
