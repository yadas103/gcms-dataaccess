package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

/**
 * @author VENKAD09 BusinessProfileModel is a POJO classes,annotated with hibernate
 *         mappings and they are responsible for holding instances of data
 *         objects.This holds the Business Profile data object.
 */
@Entity
@Table(name = "GCMS_ODS.GCMS_BUS_PROFILE_MVIEW_NEW")
@Where(clause = "UNIQUE_TYPE_CODE = 'TR-ID'")
public class BusinessProfileModel implements BaseModel {

	private static final long serialVersionUID = 1L;

	public static final String FIELD_COUNTRY_NAME = "country";
	public static final String FIELD_PROFILE_TYPE = "profileType";
	public static final String FIELD_LAST_NAME = "lastName";
	public static final String FIELD_FIRST_NAME = "firstName";
	public static final String FIELD_ORGANISATION_NAME = "organisationName";
	public static final String FIELD_SPECIALITY = "speciality";
	public static final String FIELD_CREDENTIAL = "credential";
	public static final String FIELD_REGION_ID = "regionId";
	public static final String FIELD_MASTER_ID = "masterId";

	public static final String FIELD_BP_ID = "id";
	@Id
	@Column(name = "BP_ID")
	private BigDecimal id;
	
	@Column(name = "MASTER_BP_ID")
	private BigDecimal masterId;
	
	@Column(name = "UNQ_ID_VAL")
	private String uniqueTypeId;

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

	@Column(name = "ADDR_LN_2_TXT")
	private String address2;

	@Column(name = "ADDR_LN_3_TXT")
	private String address3;

	@Column(name = "ADDR_LN_4_TXT")
	private String address4;

	@Column(name = "CITY")
	private String city;

	@Column(name = "REGION")
	private String region;

	@Column(name = "SPECIALITY")
	private String speciality;
	
	@Column(name = "UNIQUE_TYPE_CODE")
	private String uniqueTypeCode;
	
	@Column(name = "REG_ID")
	private BigDecimal regionId;
	
	@Column(name = "CREDENTIAL")
	private String credential;

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

	public BigDecimal getMasterId() {
		return masterId;
	}

	public void setMasterId(BigDecimal masterId) {
		this.masterId = masterId;
	}

	public String getUniqueTypeId() {
		return uniqueTypeId;
	}

	public void setUniqueTypeId(String uniqueTypeId) {
		this.uniqueTypeId = uniqueTypeId;
	}

	public BigDecimal getRegionId() {
		return regionId;
	}

	public void setRegionId(BigDecimal regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return the credential
	 */
	public String getCredential() {
		return credential;
	}

	/**
	 * @param credential - the credential to set
	 */
	public void setCredential(String credential) {
		this.credential = credential;
	}

	@Override
	public String toString() {
		return "BusinessProfileModel [id=" + id + ", masterId=" + masterId + ", uniqueTypeId=" + uniqueTypeId
				+ ", profileType=" + profileType + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", organisationName=" + organisationName + ", country=" + country + ", address=" + address
				+ ", address2=" + address2 + ", address3=" + address3 + ", address4=" + address4 + ", city=" + city
				+ ", region=" + region + ", speciality=" + speciality + ", uniqueTypeCode=" + uniqueTypeCode
				+ ", regionId=" + regionId + ", credential=" + credential + "]";
	}
	
	
	
}
