package com.pfizer.gcms.dataaccess.repository;

import java.util.Locale;

import com.pfizer.gcms.dataaccess.model.ConsentAnnexModel;

/**
 * @author khans129
 * The ConsentAnnex Repository will provide methods for accessing data stored in the database.
 */
public class ConsentAnnexRepository extends AbstractRepository<ConsentAnnexModel> {
	/**
	 * Creates a new instance of the ConsentAnnex Repository and configures the abstract class with the correct models.
	 * 
	 * @param country
	 *            (Locale) – The country by which to limit the result list
	 */
	public ConsentAnnexRepository(Locale country) {
		super(country);
		setModelType(ConsentAnnexModel.class);
	}
}
