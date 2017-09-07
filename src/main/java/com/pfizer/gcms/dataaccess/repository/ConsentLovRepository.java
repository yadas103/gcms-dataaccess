package com.pfizer.gcms.dataaccess.repository;

import java.util.Locale;

import com.pfizer.gcms.dataaccess.model.ConsentLovModel;

/**
 * @author khans129
 * The ConsentLov Repository will provide methods for accessing, creating and updating the ConsentLov 
 * stored in the database. This ConsentLovRepository is used as the lookup for the ConsentLov.
 */
public class ConsentLovRepository extends AbstractRepository<ConsentLovModel> {


	/**
	 * Creates a new instance of the ConsentLov Repository and configures the
	 * abstract class with the correct models.
	 * 
	 * @param country
	 *            (Locale) – The country by which to limit the result list
	 */
	public ConsentLovRepository(Locale country) {
		super(country);
		setModelType(ConsentLovModel.class);
	}
	
	/**
	 * Overrides the method for applying sorting on consent name.
	 * @return String
	 */
	@Override
	public String sortByField() {
		return ConsentLovModel.FIELD_CONSENT_NAME;
	}
}
