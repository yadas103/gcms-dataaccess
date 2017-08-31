package com.pfizer.gcms.dataaccess.repository;

import java.util.Locale;

import com.pfizer.gcms.dataaccess.model.error.ErrorReferenceLovModel;

/**
 * @author rnarne
 * The ErrorReferenceLov Repository will provide methods for accessing, creating and updating the 
 * ErrorReferenceLov stored in the database. This ErrorReferenceLov Repository is used as the 
 * lookup for the ErrorReferenceLov.
 */
public class ErrorReferenceLovRepository extends AbstractRepository<ErrorReferenceLovModel> {

	/**
	 * Creates a new instance of the ErrorReferenceLov Repository and configures the
	 * abstract class with the correct models.
	 * @param country
	 *            (Locale) – The country by which to limit the result list
	 */
	public ErrorReferenceLovRepository(Locale country) {
		setModelType(ErrorReferenceLovModel.class);
	}
}
