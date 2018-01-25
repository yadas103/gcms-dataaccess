package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author prayadu
 * RolePermissionsModel is  a POJO classes,annotated with hibernate mappings and they are responsible 
 * for holding instances of data objects.This holds the role permission data object.
 */
@Entity
@Table(name = "GCMS_ODS.GCMS_PERMISSIONS")
public class RolePermissionsModel extends AbstractModel {
	
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PRMS_KEY")
	private BigDecimal id;
	
	
	@Column(name = "ROLE_ID")
	private BigDecimal roleID;
	
	@ManyToOne
	@JoinColumns(
	{
	@JoinColumn(name = "ACTN_ID", referencedColumnName = "ACTN_ID"),
	@JoinColumn(name = "ENTY_TYP_ID", referencedColumnName = "ENTY_TYP_ID")
	})
	private EntityActionsModel entityActions;

	/**
	 * @return the entityActions
	 */
	public EntityActionsModel getEntityActions() {
		return entityActions;
	}
	/**
	 * @param entityActions the entityActions to set
	 */
	public void setEntityActions(EntityActionsModel entityActions) {
		this.entityActions = entityActions;
	}
	/**
	 * DefaultConstructor.
	 */
	public RolePermissionsModel () { }
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
	 * @return the roleID
	 */
	public BigDecimal getRoleID() {
		return roleID;
	}

	/**
	 * @param roleID the roleID to set
	 */
	public void setRoleID(BigDecimal roleID) {
		this.roleID = roleID;
	}

	/**
	 * @return the entityActionsModel
	 */
	public EntityActionsModel getEntityActionsModel() {
		return entityActions;
	}

	/**
	 * @param entityActionsModel the entityActionsModel to set
	 */
	public void setEntityActionsModel(EntityActionsModel entityActionsModel) {
		this.entityActions = entityActionsModel;
	}
}
