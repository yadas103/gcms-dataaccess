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
 * @author KASWAS
 *ProfileRequestModel is a POJO classes,annotated with hibernate mappings and they are responsible for 
 * holding instances of data objects.
 */
@Entity
@Table(name = "GCMS_ODS.GCMS_PROFILE_REQUEST")
public class ProfileRequestModel extends AbstractModel {
	
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="seq",sequenceName="GCMS_ODS.GCMS_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	@Column(name = "PROFILE_REQ_ID")
	private BigDecimal   id;
	
	@Column(name = "PROFILE_TYPE_ID")
	private String   profileTypeId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "ORGANISATION_NAME")
	private String organizationName;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CITY")
	private String city;

	@Column(name = "SPECILITY")
	private String specility;

	@Column(name = "NOTES")
	private String notes;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "BP_ID")
	private BigDecimal bpid;
	
	//R2.0
	@Column(name = "TEMP_BP_ID")
	private BigDecimal tempBpid;
	
	//R2.0
	@Column(name = "CCID")
	private BigDecimal uniqueTypeCodeForCCID;
	//R2.0
	@Column(name = "NIT")
	private BigDecimal uniqueTypeCodeForNIT;
	//R2.0
	@Column(name = "REG_ID")
	private BigDecimal regionId;

	//R2.3
	@Column(name = "Credential")
	private String credential;
		
	/**
	 * @return the profileReqId
	 */
	public BigDecimal getId() {
		return id;
	}


	/**
	 * @param profileReqId the profileReqId to set
	 */
	public void setId(BigDecimal id) {
		this.id = id;
	}

	/**
	 * @return the profileTypeId
	 */
	public String getProfileTypeId() {
		return profileTypeId;
	}

	/**
	 * @param profileTypeId the profileTypeId to set
	 */
	public void setProfileTypeId(String profileTypeId) {
		this.profileTypeId = profileTypeId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	
	

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the organizationName
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * @param organizationName the organizationName to set
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the specility
	 */
	public String getSpecility() {
		return specility;
	}

	/**
	 * @param specility the specility to set
	 */
	public void setSpecility(String specility) {
		this.specility = specility;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return the bpid
	 */
	public BigDecimal getBpid() {
		return bpid;
	}


	/**
	 * @param bpid
	 */
	public void setBpid(BigDecimal bpid) {
		this.bpid = bpid;
	}


	/**
	 * @return the tempBpid
	 */
	public BigDecimal getTempBpid() {
		return tempBpid;
	}


	/**
	 * @param tempBpid the tempBpid to set
	 */
	public void setTempBpid(BigDecimal tempBpid) {
		this.tempBpid = tempBpid;
	}


	/**
	 * @return the uniqueTypeCodeForCCID
	 */
	public BigDecimal getUniqueTypeCodeForCCID() {
		return uniqueTypeCodeForCCID;
	}


	/**
	 * @param uniqueTypeCodeForCCID the uniqueTypeCodeForCCID to set
	 */
	public void setUniqueTypeCodeForCCID(BigDecimal uniqueTypeCodeForCCID) {
		this.uniqueTypeCodeForCCID = uniqueTypeCodeForCCID;
	}


	/**
	 * @return the uniqueTypeCodeForNIT
	 */
	public BigDecimal getUniqueTypeCodeForNIT() {
		return uniqueTypeCodeForNIT;
	}


	/**
	 * @param uniqueTypeCodeForNIT the uniqueTypeCodeForNIT to set
	 */
	public void setUniqueTypeCodeForNIT(BigDecimal uniqueTypeCodeForNIT) {
		this.uniqueTypeCodeForNIT = uniqueTypeCodeForNIT;
	}


	/**
	 * @return the regionId
	 */
	public BigDecimal getRegionId() {
		return regionId;
	}


	/**
	 * @param regionId the regionId to set
	 */
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
		return "ProfileRequestModel [id=" + id + ", profileTypeId=" + profileTypeId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", organizationName=" + organizationName + ", country=" + country
				+ ", address=" + address + ", city=" + city + ", specility=" + specility + ", notes=" + notes
				+ ", status=" + status + ", bpid=" + bpid + ", tempBpid=" + tempBpid + ", uniqueTypeCodeForCCID="
				+ uniqueTypeCodeForCCID + ", uniqueTypeCodeForNIT=" + uniqueTypeCodeForNIT + ", regionId=" + regionId
				+ ", credential=" + credential + "]";
	}
	
	

}
