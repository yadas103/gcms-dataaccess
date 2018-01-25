package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



/**
 * @author KUMARK99
 * CountryModel is a POJO classes,annotated with hibernate mappings and they are responsible for 
 * holding instances of data objects.This holds the Country data object.
 */

@Entity
@Table(name = "GCMS_ODS.GCMS_COUNTRY_REVIEWER_DATA")
public class CountryReviewerModel extends AbstractModel {

	


	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name = "CNTRY_REV_ID")
	private BigDecimal	id;
	
	
	
	
	
	@Column(name = "CNTRY_CONTACT")
	private String cntryContact;
	
	@Column(name = "CNTRY_REVIEWERS")
	private String cntryReviewer;
	
	
	

	
	
	
	@ManyToOne
	@JoinColumn(name = "CNTRY_ID", referencedColumnName = "CNTRY_ID",insertable = true, updatable = true)
	private CountryModel countries;	
	/**
	 * Default constructor.
	 */
	public CountryReviewerModel() { }



	public CountryModel getCountries() {
		return countries;
	}

	public void setCountries(CountryModel countries) {
		this.countries = countries;
	}


	public BigDecimal getId() {
		return id;
	}


	public void setId(BigDecimal id) {
		this.id = id;
	}


	public String getCntryContact() {
		return cntryContact;
	}

	public void setCntryContact(String cntryContact) {
		this.cntryContact = cntryContact;
	}




	public String getCntryReviewer() {
		return cntryReviewer;
	}

	public void setCntryReviewer(String cntryReviewer) {
		this.cntryReviewer = cntryReviewer;
	}

	
	
}