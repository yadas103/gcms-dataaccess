package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;
import java.util.Date;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author VENKAD09 ConsentTemplateModel is a POJO classes,annotated with
 *         hibernate mappings and they are responsible for holding instances of
 *         data objects.This holds the Consent Template data object.
 */

@Entity
@Table(name = "GCMS_CONSENT_TEMPLATE")
public class ConsentTemplateModel extends AbstractModel {

	private static final long serialVersionUID = 1L;

	public static final String FIELD_TMPL_CODE = "tmpl_code";
	
	public static final String FIELD_TMPL_STAT = "tmpl_status";
	
	public static final String FIELD_TMPL_ID = "id";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GCMS_SEQ")
	@SequenceGenerator(name = "GCMS_SEQ", sequenceName = "GCMS_SEQ", allocationSize = 1)
	@Column(name = "COSN_TMPL_ID")
	private BigDecimal id;

	@ManyToOne(fetch = FetchType.LAZY) 
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "CNTRY_ID", referencedColumnName = "CNTRY_ID")
	private CountryModel cntry_id;

	@Column(name = "TMPL_NAME")
	private String tmpl_name;

	@Column(name = "TMPL_CODE")
	private String tmpl_code;

	@Column(name = "TMPL_TYPE")
	private String tmpl_type;

	@Column(name = "TOTAL_PAGES")
	private int total_pages;

	@Column(name = "DATES_RANGE")
	private char dates_rages;

	@Column(name = "VALIDITY_START_DATE")
	private Date validity_start_date;

	@Column(name = "VALIDITY_END_DATE")
	private Date validity_end_date;

	@Column(name = "TMPL_LOCATION")
	private String tmpl_location;

	@Column(name = "TMPL_STATUS")
	private String tmpl_status;
	
	@Column(name = "CONSENT_CHK_FLAG")
	private Character consentCheck;

	@Column(name = "MANUAL_UPLOAD_TXT")
	private String manualUpload;

	@Column(name = "DELETE_FLAG")
	private Character deleted;

	@Column(name = "TMPL_DESC")
	private String tmpl_desc;

	@Column(name = "SIGNATURE_PAGE_NO")
	private BigDecimal signPageNo;
	
	@Column(name = "CONSENT_PAGE_NO")
	private BigDecimal consentPageNo;
	
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public CountryModel getCntry_id() {
		return cntry_id;
	}

	public void setCntry_id(CountryModel cntry_id) {
		this.cntry_id = cntry_id;
	}

	public String getTmpl_name() {
		return tmpl_name;
	}

	public void setTmpl_name(String tmpl_name) {
		this.tmpl_name = tmpl_name;
	}

	public String getTmpl_code() {
		return tmpl_code;
	}

	public void setTmpl_code(String tmpl_code) {
		this.tmpl_code = tmpl_code;
	}

	public String getTmpl_type() {
		return tmpl_type;
	}

	public void setTmpl_type(String tmpl_type) {
		this.tmpl_type = tmpl_type;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public void setTotal_pages(int total_pages) {
		this.total_pages = total_pages;
	}

	public char getDates_rages() {
		return dates_rages;
	}

	public void setDates_rages(char dates_rages) {
		this.dates_rages = dates_rages;
	}

	public Date getValidity_start_date() {
		return validity_start_date;
	}

	public void setValidity_start_date(Date validity_start_date) {
		this.validity_start_date = validity_start_date;
	}

	public Date getValidity_end_date() {
		return validity_end_date;
	}

	public void setValidity_end_date(Date validity_end_date) {
		this.validity_end_date = validity_end_date;
	}

	public String getTmpl_location() {
		return tmpl_location;
	}

	public void setTmpl_location(String tmpl_location) {
		this.tmpl_location = tmpl_location;
	}

	public String getTmpl_status() {
		return tmpl_status;
	}

	public void setTmpl_status(String tmpl_status) {
		this.tmpl_status = tmpl_status;
	}

	public Character getDeleted() {
		return deleted;
	}

	public void setDeleted(Character deleted) {
		this.deleted = deleted;
	}

	public String getTmpl_desc() {
		return tmpl_desc;
	}

	public void setTmpl_desc(String tmpl_desc) {
		this.tmpl_desc = tmpl_desc;
	}

	

	public Character getConsentCheck() {
		return consentCheck;
	}

	public void setConsentCheck(Character consentCheck) {
		this.consentCheck = consentCheck;
	}

	

	public BigDecimal getSignPageNo() {
		return signPageNo;
	}

	public void setSignPageNo(BigDecimal signPageNo) {
		this.signPageNo = signPageNo;
	}

	public BigDecimal getConsentPageNo() {
		return consentPageNo;
	}

	public void setConsentPageNo(BigDecimal consentPageNo) {
		this.consentPageNo = consentPageNo;
	}

	public String getManualUpload() {
		return manualUpload;
	}

	public void setManualUpload(String manualUpload) {
		this.manualUpload = manualUpload;
	}
	
}
