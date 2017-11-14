/**
 * 
 */
package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * @author rtalapaneni
 * The Abstract model is a super class.Abstract Models are POJO classes,
 * annotated with Hibernate mappings, they are responsible for holding instances of data objects
 */
@MappedSuperclass
public abstract class AbstractModel implements  BaseModel {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The constant JDBC batch count.
	 */	
	public static final int CONSTANT_JDBC_BATCH_COUNT = 100;
	
	/**
	 * Default file id to be used by portal.
	 */
	public static final BigDecimal CONSTANT_DEFAULT_FILE_ID = new BigDecimal(-1);
	
	/**
	 * The constant ID.
	 */	
	public static final String FIELD_ID = "id";
	/**
	 * The constant deleted.
	 */	
	public static final String FIELD_DELETED = "deleted";
	/**
	 * The constant created date.
	 */	
	public static final String FIELD_CREATED_DATE = "createdDate";
	/**
	 * The constant created by.
	 */
	public static final String FIELD_CREATED_BY = "createdBy";
	/**
	 * The constant updated date.
	 */	
	public static final String FIELD_UPDATED_DATE = "updatedDate";
	/**
	 * The constant updated by.
	 */	
	public static final String FIELD_UPDATED_BY = "updatedBy";
	
	@Column(name = "CRTD_DT", insertable = true, updatable = false)
	protected Date 	createdDate;
	
	@Column(name = "CRTD_BY")
	protected String 	createdBy;
	
	@Column(name = "UPDTD_DT", insertable = true, updatable = true)
	private Date	updatedDate;
	
	@Column(name = "UPDTD_BY")
	private String 	updatedBy;
	
	@Transient
	private boolean deleteRecord;
	
	/**
	 * Default constructor.
	 */
	public AbstractModel() { }
	
	/**
	 * Updates the child references for the instance.
	 * Classes should override this when child references needs to be updated.
	 * @return the map 
	 */
	public Map<Class<?>, List<BigDecimal>> updateChildReferences() {  
		return new HashMap<Class<?>, List<BigDecimal>> ();
	}
	
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return cloneDate(createdDate);
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = cloneDate(createdDate);
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return cloneDate(updatedDate);
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = cloneDate(updatedDate);
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	/**
	 * Date instance directly passes the reference which allows unauthorized modification
	 * so cloning and sending it is the suggested way.
	 * @param date
	 * 		(Date) - The date
	 * @return the date
	 */
	protected Date cloneDate(Date date) {
		if (date != null) {
			return (Date) date.clone();
		}
		return null;
	}

	/**
	 * @return the deleteRecord
	 */
	public boolean isDeleteRecord() {
		return deleteRecord;
	}

	/**
	 * @param deleteRecord the deleteRecord to set
	 */
	public void setDeleteRecord(boolean deleteRecord) {
		this.deleteRecord = deleteRecord;
	}
}
