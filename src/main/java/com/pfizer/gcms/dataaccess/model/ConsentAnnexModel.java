package com.pfizer.gcms.dataaccess.model;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author khans129 ConsentAnnex is a POJO classes,annotated with hibernate
 *         mappings and they are responsible for holding instances of data
 *         objects.This holds the Consent Annex data object.
 */

@Entity
@Table(name = "GCMS_CONSENT_ANNEX")
public class ConsentAnnexModel extends AbstractModel {

	private static final long serialVersionUID = 1L;
	public static final String FIELD_ID = "id";
	public static final String FIELD_BPID = "bpid";
	public static final String FIELD_PAYERCOUNTRY = "payercountry";
	public static final String FIELD_PROFILECOUNTRY = "profilecountry";
	public static final String FIELD_EVENTNAME = "eventname";
	public static final String FIELD_CONSENT = "consentstatus";
	public static final String FIELD_TEMPLATEID = "tmpl_id";

	@Id
	@SequenceGenerator(name = "seq", sequenceName = "GCMS_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@Column(name = "COSN_ANNEX_ID")
	private BigDecimal id;

	@ManyToOne/*(fetch = FetchType.LAZY)*///commented to improve task page performance
	@JoinColumn(name = "PAYER_COUNTRY", referencedColumnName = "CNTRY_ID")	
	private CountryModel payercountry;

	@ManyToOne/*(fetch = FetchType.LAZY)*///commented to improve task page performance
	@JoinColumn(name = "PROFILE_COUNTRY", referencedColumnName = "CNTRY_ID")
	private CountryModel profilecountry;

	@ManyToOne/*(fetch = FetchType.LAZY)*///commented to improve task page performance
	@JoinColumn(name = "COSN_TMPL_ID", referencedColumnName = "COSN_TMPL_ID")
	private ConsentTemplateModel tmpl_id;

	@Column(name = "TEMPLATE_TYPE")
	private String templatetype;

	@Column(name = "PROFILE_TYPE")
	private String profileType;

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

	@Column(name = "REVOCATION_REASON")
	private String revocationReason;

	@Column(name = "REVOCATION_REASON_DOC")
	private String revocationDocLink;
	
	@Column(name = "CONSENT_STATUS_CHANGE_REASON")
	private String consentstatuschangeReason;
	
	@Column(name = "EVENT_END_DATE")
	private Date eventEndDate;
	
	@Column(name = "CONSENT_CNTRY_TYPE")
	private String consentType;

	

	public Date getEventEndDate() {
		return eventEndDate;
	}

	public void setEventEndDate(Date eventEndDate) {
		this.eventEndDate = eventEndDate;
	}

	@ManyToOne/*(fetch = FetchType.LAZY)*///commented to improve task page performance
	@JoinColumn(name = "BP_ID", referencedColumnName = "BP_ID")
	private BusinessProfileModel bpid;

	@ManyToOne/*(fetch = FetchType.LAZY)*///commented to improve task page performance
	@JoinColumn(name = "CONSENT_STATUS", referencedColumnName = "CNSN_STS_ID")
	private ConsentLovModel consentstatus;

	/**
	 * Default constructor.
	 */
	public ConsentAnnexModel() {
	}

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

	public ConsentTemplateModel getTmpl_id() {
		return tmpl_id;
	}

	public void setTmpl_id(ConsentTemplateModel tmpl_id) {
		this.tmpl_id = tmpl_id;
	}

	public String getTemplatetype() {
		return templatetype;
	}

	public void setTemplatetype(String templatetype) {
		this.templatetype = templatetype;
	}

	public String getProfileType() {
		return profileType;
	}

	public void setProfileType(String profileType) {
		this.profileType = profileType;
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

	public String getRevocationReason() {
		return revocationReason;
	}

	public void setRevocationReason(String revocationReason) {
		this.revocationReason = revocationReason;
	}

	public String getRevocationDocLink() {
		return revocationDocLink;
	}

	public void setRevocationDocLink(String revocationDocLink) {
		this.revocationDocLink = revocationDocLink;
	}
	public String getConsentstatuschangeReason() {
		return consentstatuschangeReason;
	}

	public void setConsentstatuschangeReason(String consentstatuschangeReason) {
		this.consentstatuschangeReason = consentstatuschangeReason;
	}

	public String getConsentType() {
		return consentType;
	}

	public void setConsentType(String consentType) {
		this.consentType = consentType;
	}
}
