package com.pfizer.gcms.dataaccess.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;

/**
 * @author rnarne 
 * EntityTypesModel is a POJO classes,annotated with hibernate mappings and they are responsible 
 * for holding instances of data objects.This holds the EntityTypes data object.
 */
@Entity
@Table(name = "GCMS_ODS.GCMS_ENTITY_ACTIONS")
public class EntityActionsModel extends AbstractModel {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name = "ACTN_ID", referencedColumnName = "ACTN_ID")
	private RoleActionsModel roleActions;

	@Id
	@ManyToOne
	@JoinColumn(name = "ENTY_TYP_ID", referencedColumnName = "ENTY_TYP_ID")
	private EntityTypeIdentifierLovModel entityType;

	/**
	 * Default constructor.
	 */
	public EntityActionsModel() {
	}

	/**
	 * @return the roleActions
	 */
	public RoleActionsModel getRoleActions() {
		return roleActions;
	}

	/**
	 * @param roleActions the roleActions to set
	 */
	public void setRoleActions(RoleActionsModel roleActions) {
		this.roleActions = roleActions;
	}

	/**
	 * @return the entityType
	 */
	public EntityTypeIdentifierLovModel getEntityType() {
		return entityType;
	}

	/**
	 * @param entityType the entityType to set
	 */
	public void setEntityType(EntityTypeIdentifierLovModel entityType) {
		this.entityType = entityType;
	}

}
