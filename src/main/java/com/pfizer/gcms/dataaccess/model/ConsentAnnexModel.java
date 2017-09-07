package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author khans129
 * ConsentAnnex is a POJO classes,annotated with hibernate mappings and they are responsible for 
 * holding instances of data objects.This holds the Consent Annex data object.
 */


@Entity
@Table(name = "GCMS_CONSENT_ANNEX")
public class ConsentAnnexModel extends AbstractModel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue
	@Column(name = "COSN_ANNEX_ID")
	private BigDecimal id;

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYER_COUNTRY", referencedColumnName = "CNTRY_ID")
	private CountryModel payercountry;

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROFILE_COUNTRY", referencedColumnName = "CNTRY_ID")
	private CountryModel profilecountry;

	
   
	/*@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COSN_TMPL_ID", referencedColumnName = "COSN_TMPL_ID")
	private ConsentTemplateModel templateid;*/
	
	@Column(name = "COSN_TMPL_ID")
	private BigDecimal templateid;
	
	
	@Column(name = "TEMPLATE_TYPE")
	private BigDecimal templatetype;
	
	@Column(name = "PROFILE_TYPE")
	private BigDecimal profiletype;
	
	@Column(name = "EVENT_NAME")
	private String eventname;
	
	@Column(name = "PO_CODE")
	private String pocode;

	@Column(name = "ACM_CODE")
	private String acmcode;

	@Column(name = "CONSENT_START_DATE")
	private Date consentstartdate;
	
	@Column(name = "CONSENT_END_DATE")
	private Date consentenddate;

	@Column(name = "QR_CODE")
	private BigDecimal qrcode;

	@Column(name = "ANNEX_LOCATION")
	private String annnexlocation;

	

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BP_ID", referencedColumnName = "BP_ID")
	private BusinessProfileModel bpid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONSENT_STATUS", referencedColumnName = "CNSN_STS_ID")
	private ConsentLovModel consentstatus;
	
	
	/**
	 * Default constructor.
	 */
	public ConsentAnnexModel() { }



	public BigDecimal getId() {
		return id;
	}



	public void setId(BigDecimal id) {
		this.id = id;
	}


    

	public CountryModel getPayercountry() {
		return payercountry;
	}



	public void setPayercountry(CountryModel payercountry) {
		this.payercountry = payercountry;
	}



	public CountryModel getProfilecountry() {
		return profilecountry;
	}



	public void setProfilecountry(CountryModel profilecountry) {
		this.profilecountry = profilecountry;
	}



	


	/*public ConsentTemplateModel getTemplateid() {
		return templateid;
	}



	public void setTemplateid(ConsentTemplateModel templateid) {
		this.templateid = templateid;
	}
*/


	public BigDecimal getTemplateid() {
		return templateid;
	}



	public void setTemplateid(BigDecimal templateid) {
		this.templateid = templateid;
	}



	public BigDecimal getTemplatetype() {
		return templatetype;
	}



	public void setTemplatetype(BigDecimal templatetype) {
		this.templatetype = templatetype;
	}



	public BigDecimal getProfiletype() {
		return profiletype;
	}



	public void setProfiletype(BigDecimal profiletype) {
		this.profiletype = profiletype;
	}



	public String getEventname() {
		return eventname;
	}



	public void setEventname(String eventname) {
		this.eventname = eventname;
	}



	public String getPocode() {
		return pocode;
	}



	public void setPocode(String pocode) {
		this.pocode = pocode;
	}



	public String getAcmcode() {
		return acmcode;
	}



	public void setAcmcode(String acmcode) {
		this.acmcode = acmcode;
	}



	public Date getConsentstartdate() {
		return cloneDate(consentstartdate);
	}



	public void setConsentstartdate(Date consentstartdate) {
		this.consentstartdate = cloneDate(consentstartdate);
	}



	public Date getConsentenddate() {
		return cloneDate(consentenddate);
	}



	public void setConsentenddate(Date consentenddate) {
		this.consentenddate = cloneDate(consentenddate);
	}



	public BigDecimal getQrcode() {
		return qrcode;
	}



	public void setQrcode(BigDecimal qrcode) {
		this.qrcode = qrcode;
	}



	public String getAnnnexlocation() {
		return annnexlocation;
	}



	public void setAnnnexlocation(String annnexlocation) {
		this.annnexlocation = annnexlocation;
	}





	


	public ConsentLovModel getConsentstatus() {
		return consentstatus;
	}



	public void setConsentstatus(ConsentLovModel consentstatus) {
		this.consentstatus = consentstatus;
	}



	public BusinessProfileModel getBpid() {
		return bpid;
	}



	public void setBpid(BusinessProfileModel bpid) {
		this.bpid = bpid;
	}



	



	

		





		
	



	
	
}
