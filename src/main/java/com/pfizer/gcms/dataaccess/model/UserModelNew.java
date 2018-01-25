/**
 * 
 */
package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author khans129
 * UserModel is a POJO classes,annotated with hibernate mappings and they are responsible for 
 * holding instances of data objects.This holds the User data object.
 */
@Entity
@Table(name = "GCMS_ODS.GCMS_USERS")
public class UserModelNew extends AbstractModel {
	/**
	 * The constant user profiles.
	 */
	public static final String FIELD_USERPROFILES = "userProfiles";
	/**
	 * The constant user name.
	 */
	public static final String FIELD_USERNAME = "userName";
	
	public static final String FIELD_FIRSTNAME = "firstName";
	
	public static final String FIELD_LASTNAME = "lastName";
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "USRNM")
	private String userName;

	@Column(name = "FRS_NM")
	private String firstName;

	@Column(name = "LST_NM")
	private String lastName;

	@Column(name = "MDL_NM")
	private String middleName;

	@Column(name = "SFX")
	private String suffix;
	
	/**
	 * Default constructor.
	 */
	public UserModelNew() {
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
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
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName
	 *            the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * @param suffix
	 *            the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	

	
}
