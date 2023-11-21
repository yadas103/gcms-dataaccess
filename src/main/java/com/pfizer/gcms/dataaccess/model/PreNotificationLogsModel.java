package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "GCMS_ODS.GCMS_PRE_NOTIFICATION_LOGS")
public class PreNotificationLogsModel extends AbstractModel {
	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1857861035677191815L;

	@Id
	@Column(name = "EMAIL_LOG_KEY")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GCMS_PRE_NOTIFICATION_SEQ")
	@SequenceGenerator(name = "GCMS_PRE_NOTIFICATION_SEQ", sequenceName = "GCMS_PRE_NOTIFICATION_SEQ", allocationSize = 1)
	private BigDecimal id;
	
	@Column(name = "EMAIL_TO")
	private String emailTo;
	
	@Column(name = "EMAIL_FROM")
	private String emailFrom;
	
	@Column(name = "EMAIL_MSG")
	private String emailMsg;
	
	@Column(name = "EMAIL_SUBJECT")
	private String emailSubject;
	
	@Column(name = "ISO_CNTRY_CD")
	private String emailCountryCode;
	
	@Column(name = "EMAIL_TMPL_TYPE")
	private String emailTemplateType;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getEmailMsg() {
		return emailMsg;
	}

	public void setEmailMsg(String emailMsg) {
		this.emailMsg = emailMsg;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailCountryCode() {
		return emailCountryCode;
	}

	public void setEmailCountryCode(String emailCountryCode) {
		this.emailCountryCode = emailCountryCode;
	}

	public String getEmailTemplateType() {
		return emailTemplateType;
	}

	public void setEmailTemplateType(String emailTemplateType) {
		this.emailTemplateType = emailTemplateType;
	}

	@Override
	public String toString() {
		return "PreNotificationLogsModel [id=" + id + ", emailTo=" + emailTo + ", emailFrom=" + emailFrom
				+ ", emailMsg=" + emailMsg + ", emailSubject=" + emailSubject + ", emailCountryCode=" + emailCountryCode
				+ ", emailTemplateType=" + emailTemplateType + "]";
	}
	


	

	
}
