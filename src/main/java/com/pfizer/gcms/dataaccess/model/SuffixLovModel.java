package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;

/**
 * @author prayadu
 * SuffixLovModel is  a POJO classes,annotated with hibernate mappings and they are responsible for
 * holding instances of data objects.This holds the SuffixLov data object.
 */
@Deprecated
public class SuffixLovModel extends AbstractCountryModel {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal id;
	private String individualSuffix;
	private Status status;

	/**
	 * Default constructor.
	 */
	public SuffixLovModel() { }
	
	/**
	 * @return id the unique identifier of the model 
	 * 			
	 */
	public BigDecimal getId() {
		return id;
	}

	/**
	 * @param id the unique identifier of the model
	 */
	public void setId(BigDecimal id) {
		this.id = id;
	}
	
	/**
	 * @return the individualSuffix
	 */
	public String getIndividualSuffix() {
		return individualSuffix;
	}

	/**
	 * @param individualSuffix
	 *            the individualSuffix to set
	 */
	public void setIndividualSuffix(String individualSuffix) {
		this.individualSuffix = individualSuffix;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

}
