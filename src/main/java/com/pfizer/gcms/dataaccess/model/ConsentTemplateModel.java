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

@Entity
@Table(name = "GCMS_CONSENT_TEMPLATE")
public class ConsentTemplateModel extends AbstractModel {

	/**
	 * selim
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FIELD_TMPL_CODE = "tmpl_code";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GCMS_SEQ")
	@SequenceGenerator(name = "GCMS_SEQ", sequenceName = "GCMS_SEQ", allocationSize = 1)
	@Column(name = "COSN_TMPL_ID")
	private BigDecimal id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
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
	
	@Column(name = "DELETE_FLAG")
	private Character deleted;
		
	@Column(name = "TMPL_DESC")
	private String tmpl_desc;

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
	
	
}
