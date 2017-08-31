package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.metamodel.SingularAttribute;

@Entity
@Table(name = "GCMS_BUSINESS_PROFILE_VIEW")
public class BusinessProfileModel implements BaseModel {

	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 1L;

	public static final String FIELD_COUNTRY_NAME = "country";
	public static final String FIELD_PROFILE_TYPE = "profileType";
	public static final String FIELD_BP_ID = "id";
	@Id
	@Column(name = "BP_ID")
	private BigDecimal id;
	
	@Column(name = "PROFILE_TYPE_ID")
	private String profileType;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "ORGANISATION_NAME")
	private String organisationName;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "ADDR_LN_1_TXT")
	private String address;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "REGION")
	private String region;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getProfileType() {
		return profileType;
	}

	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOrganisationName() {
		return organisationName;
	}

	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	

	
	

}
