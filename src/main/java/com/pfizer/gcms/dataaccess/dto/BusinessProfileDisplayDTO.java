package com.pfizer.gcms.dataaccess.dto;

import java.math.BigDecimal;

public class BusinessProfileDisplayDTO {


	private static final long serialVersionUID = 1L;
	
	private BigDecimal id;
	private String profileType;
	private String firstName;
	private String lastName;
	private String organisationName;
	private String country;
	private String address;
	private String address2;
	private String address3;
	private String address4;
	private String city;
	private String region;
	private String speciality;
	private String uniqueTypeCode;
	private String uniqueTypeId;
	private String isTempProfile;
	private BigDecimal regionId;
	
	
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
	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
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

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getUniqueTypeCode() {
		return uniqueTypeCode;
	}
	public void setUniqueTypeCode(String uniqueTypeCode) {
		this.uniqueTypeCode = uniqueTypeCode;
	}
	public String getUniqueTypeId() {
		return uniqueTypeId;
	}
	public void setUniqueTypeId(String uniqueTypeId) {
		this.uniqueTypeId = uniqueTypeId;
	}
	
	/**
	 * @return the isTempProfile
	 */
	public String getIsTempProfile() {
		return isTempProfile;
	}
	/**
	 * @param isTempProfile the isTempProfile to set
	 */
	public void setIsTempProfile(String isTempProfile) {
		this.isTempProfile = isTempProfile;
	}
	public BigDecimal getRegionId() {
		return regionId;
	}
	public void setRegionId(BigDecimal regionId) {
		this.regionId = regionId;
	}
}
