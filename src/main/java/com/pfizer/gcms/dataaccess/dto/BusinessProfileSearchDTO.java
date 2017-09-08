package com.pfizer.gcms.dataaccess.dto;

import java.math.BigDecimal;

import com.pfizer.gcms.dataaccess.model.BusinessProfileModel;

public class BusinessProfileSearchDTO extends  BaseSearchDTO<BusinessProfileModel> {
	
	private static final long serialVersionUID = 1L;
	public static final String FIELD_COUNTRY_NAME = "country";
	public static final String FIELD_PROFILE_TYPE = "profileType";
	public static final String FIELD_BP_ID = "id";


	private String profileType;
	private String firstName;
	private String lastName;
	private String organisationName;
	private String country;
	private String address;
	private String city;
	private String region;
	private BigDecimal id;
	
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
