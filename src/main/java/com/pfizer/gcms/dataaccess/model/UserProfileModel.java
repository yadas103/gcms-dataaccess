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
 * @author rnarne
 * UserProfileModel is  a POJO classes,annotated with hibernate mapping and they are responsible for 
 * holding instances of data objects.This holds the User profile data object.
 */
@Entity
@Table(name = "GCMS_USER_PROFILES")
public class UserProfileModel extends AbstractCountryModel {
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	//public static final String FIELD_USERPROFILES = "userProfiles";
	/**
	 * The constant user name.
	 */
	public static final String FIELD_USERNAME = "userName";
	/**
	 * The constant role ID.
	 */
	public static final String FIELD_ROLEID = "roleId";
	/**
	 * The constant default profile indicator.
	 */
	public static final String FIELD_DEFAULTPROFILEINDICATOR = "defaultProfileIndicator";
	//public static final String FIELD_DELETED = "deleted";
	
	@Id
	@Column(name = "USR_PRFL_KEY")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_PRFL_KEY")
	@SequenceGenerator(name = "SEQ_USER_PRFL_KEY", sequenceName = "SEQ_USER_PRFL_KEY", allocationSize = 1)
	private BigDecimal id;
	
	@ManyToOne
	@JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID", insertable = false, updatable = false)
	private RolesModel roles;
	
	@Column(name = "ROLE_ID")
	private BigDecimal roleId;

	@Column(name = "DFLT_PRFL_IND")
	private Character defaultProfileIndicator;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USRNM", referencedColumnName = "USRNM", insertable = false, updatable = false)
	private UserModel user;
	
	@Column(name = "USRNM")
	private String userName;
	
	@Column(name = "DELETE_FLAG")
	private Character deleted;
	
	/**
	 * Default constructor.
	 */
	public UserProfileModel() { }

	
	/**
	 * @return the roleId
	 */
	public BigDecimal getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
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
	 * @return the defaultProfileIndicator
	 */
	public Character getDefaultProfileIndicator() {
		return defaultProfileIndicator;
	}

	/**
	 * @param defaultProfileIndicator the defaultProfileIndicator to set
	 */
	public void setDefaultProfileIndicator(Character defaultProfileIndicator) {
		this.defaultProfileIndicator = defaultProfileIndicator;
	}

	/**
	 * @return the user
	 */
	public UserModel getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserModel user) {
		this.user = user;
	}

	/**
	 * @return the roles
	 */
	public RolesModel getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(RolesModel roles) {
		this.roles = roles;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
