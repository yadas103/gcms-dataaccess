package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;

/**
 * @author prayadu LookupModel is  a POJO classes,annotated with hibernate mappings and they are responsible 
 * for holding instances of data objects.This holds the Lookup data object.This class is used to have all 
 * the look up references for the look up value tables and consists of name and value fields.
 */

//@Entity
//@Table(name = "Lookup")
@Deprecated
public class LookupModel extends AbstractCountryModel {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal id;
	private String name;
	private String value;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
