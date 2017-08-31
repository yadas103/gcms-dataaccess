/**
 * 
 */
package com.pfizer.gcms.dataaccess.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * @author rtalapaneni
 * AbstractCountryModel is  a POJO classes,annotated with hibernate mappings, 
 * they are responsible for holding instances of data objects.
 * This holds the AbstractCountry data object.
 */
 
@MappedSuperclass
public abstract class AbstractCountryModel extends AbstractModel {
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The constant country.
	 */
	public static final String FIELD_COUNTRY = "country";
	/**
	 * The constant country ID.
	 */
	public static final String FIELD_COUNTRY_ID = "countryId";
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CNTRY_ID", referencedColumnName = "CNTRY_ID", insertable = false, updatable = false)
	private CountryModel country;
	
	@Column(name = "CNTRY_ID")
	private BigDecimal countryId;
	
	/**
	 * Default constructor.
	 */
	public AbstractCountryModel() {}
	
	/**
	 * @return the country
	 */
	public CountryModel getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(CountryModel country) {
		this.country = country;
	}

	/**
	 * @return the countryId
	 */
	public BigDecimal getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
}
