package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author rnarne
 * EntityTypeIdentifierLov Model is  a POJO classes,annotated with hibernate mappings and they are responsible 
 * for holding instances of data objects.This holds the EntityTypeIdentifier data object.
 */
@Entity
@Table(name = "GCMS_ENTITY_TYPES")
public class EntityTypeIdentifierLovModel extends AbstractModel {
	
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	//public static final String FIELD_TABLES = "tables";
	
	@Id	
	@Column(name = "ENTY_TYP_ID")
	@GeneratedValue
	private BigDecimal id;
	
	@Column(name = "ENTY_TYP_NM")
	private String entityTypeName;
	
	/*@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private  TablesModel tables;*/
	
	/**
	 * Default constructor.
	 */
	public EntityTypeIdentifierLovModel() { }

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
	 * @return the entityTypeName
	 */
	public String getEntityTypeName() {
		return entityTypeName;
	}

	/**
	 * @param entityTypeName the entityTypeName to set
	 */
	public void setEntityTypeName(String entityTypeName) {
		this.entityTypeName = entityTypeName;
	}
}
