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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * @author khans129
 * TaskModel is a POJO classes,annotated with hibernate mappings and they are responsible for 
 * holding instances of data objects.This holds the Task data object.
 */


@Entity
@Table(name = "GCMS_ODS.GCMS_TASKS")
public class TaskModel extends AbstractModel {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String FIELD_TASK_ID = "id";
	public static final String FIELD_ASSIGNED_TO = "assignedto";
	public static final String FIELD_CONS = "consannexid";
	public static final String FIELD_TASKSTATUS = "taskstatus";
	
	@Id
	@SequenceGenerator(name="seq",sequenceName="GCMS_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	@Column(name = "TASK_ID")
	private BigDecimal	id;
	
	@OneToOne/*(fetch = FetchType.LAZY)*/
	@JoinColumn(name = "COSN_ANNEX_ID", referencedColumnName = "COSN_ANNEX_ID",insertable = true, updatable = true)	
	@Cascade({CascadeType.ALL})
	private ConsentAnnexModel consannexid;
	
	@ManyToOne/*(fetch = FetchType.LAZY)*/
	@JoinColumn(name = "ASSIGNED_TO", referencedColumnName = "USRNM")	
	private UserModelNew assignedto;
	
	@Column(name = "TASK_STATUS")
	private String 	taskstatus;
	
	@Column(name = "DELETE_FLAG")
	private Character deleted;
	
	@Column(name = "DELETE_REASON")
	private String    deleteReason;
	
	
@Column(name = "DELETE_REASON_DESC")
	private String 	deleteReasonDesc;
	
@Column(name = "REASSIGN_REASON ")
private String 	reassignReason;
@Column(name = "UNDELETE_REASON")
private String 	undeleteReason;

 





	/**
	 * Default constructor.
	 */
	public TaskModel() { }




	public BigDecimal getId() {
		return id;
	}




	public void setId(BigDecimal id) {
		this.id = id;
	}




	public ConsentAnnexModel getConsannexid() {
		return consannexid;
	}




	public void setConsannexid(ConsentAnnexModel consannexid) {
		this.consannexid = consannexid;
	}


	public UserModelNew getAssignedto() {
		return assignedto;
	}




	public void setAssignedto(UserModelNew assignedto) {
		this.assignedto = assignedto;
	}






	public String getTaskstatus() {
		return taskstatus;
	}




	public void setTaskstatus(String taskstatus) {
		this.taskstatus = taskstatus;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Character getDeleted() {
		return deleted;
	}




	public void setDeleted(Character deleted) {
		this.deleted = deleted;
	}




	public String getDeleteReason() {
		return deleteReason;
	}




	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}




	public String getDeleteReasonDesc() {
		return deleteReasonDesc;
	}




	public void setDeleteReasonDesc(String deleteReasonDesc) {
		this.deleteReasonDesc = deleteReasonDesc;
	}

	
	public String getUndeleteReason() {
	return undeleteReason;
}




public String getReassignReason() {
		return reassignReason;
	}




	public void setReassignReason(String reassignReason) {
		this.reassignReason = reassignReason;
	}




public void setUndeleteReason(String undeleteReason) {
	this.undeleteReason = undeleteReason;
}
}
