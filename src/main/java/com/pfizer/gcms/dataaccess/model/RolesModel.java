package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author rnarne 
 * RoleLovModel is a POJO classes,annotated with hibernate  mappings and they are responsible for 
 * holding instances of data objects.This holds the roles data object.
 */
@Entity
@Table(name = "GCMS_ODS.GCMS_ROLES")
public class RolesModel extends AbstractModel {
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ROLE_ID")
	@GeneratedValue
	private BigDecimal id;

	@Column(name = "ROLE_NM")
	private String roleName;

	@Column(name = "ROLE_DSCR")
	private String roleDescription;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "roles")
	private List<UserProfileModel> userProfiles;

	@OneToMany
	@JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
	private List<RolePermissionsModel> rolePermissions;

	/**
	 * @return the userProfiles
	 */
	public List<UserProfileModel> getUserProfiles() {
		return userProfiles;
	}

	/**
	 * @param userProfiles the userProfiles to set
	 */
	public void setUserProfiles(List<UserProfileModel> userProfiles) {
		this.userProfiles = userProfiles;
	}

	/**
	 * Default constructor.
	 */
	public RolesModel() {
	}

	/**
	 * @return the id
	 */
	public BigDecimal getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(BigDecimal id) {
		this.id = id;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the roleDescription
	 */
	public String getRoleDescription() {
		return roleDescription;
	}

	/**
	 * @param roleDescription
	 *            the roleDescription to set
	 */
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	/**
	 * @return the rolePermissions
	 */
	public List<RolePermissionsModel> getRolePermissions() {
		return rolePermissions;
	}

	/**
	 * @param rolePermissions
	 *            the rolePermissions to set
	 */
	public void setRolePermissions(List<RolePermissionsModel> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}
}
