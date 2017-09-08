package com.pfizer.gcms.dataaccess.dto;

import java.math.BigDecimal;

import com.pfizer.gcms.dataaccess.model.ConsentTemplateModel;

public class ConsentTemplateSearchDTO extends  BaseSearchDTO<ConsentTemplateModel> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal id;
	private String tmpl_location;
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getTmpl_location() {
		return tmpl_location;
	}
	public void setTmpl_location(String tmpl_location) {
		this.tmpl_location = tmpl_location;
	}


}
