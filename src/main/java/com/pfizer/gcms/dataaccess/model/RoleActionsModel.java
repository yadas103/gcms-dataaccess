package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author prayadu
 * RoleActionsModel is  a POJO classes,annotated with hibernate mappings and they are responsible 
 * for holding instances of data objects.This holds the role action data object.
 */
@Entity
@Table(name = "GCMS_ODS.GCMS_ROLE_ACTIONS")
public class RoleActionsModel  extends AbstractModel{
	
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ACTN_ID")
	private BigDecimal id;
	
	@Column(name = "ACTN_NM")
	private String actionName;
		
	@Column(name = "DELETE_FLAG")
	private Character deleted;
	
	/**
	 * Default constructor.
	 */
	 public RoleActionsModel() { }
	


	/**
	 * @return the roleActionId
	 */
	public BigDecimal getRoleActionId() {
		return id;
	}

	/**
	 * @param roleActionId the roleActionId to set
	 */
	public void setRoleActionId(BigDecimal roleActionId) {
		this.id = roleActionId;
	}

	/**
	 * @return the actionName
	 */
	public String getActionName() {
		return actionName;
	}

	/**
	 * @param actionName the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
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
}
