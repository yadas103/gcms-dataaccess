package com.pfizer.gcms.dataaccess.repository;

import java.util.Locale;

import com.pfizer.gcms.dataaccess.model.RolesModel;

/**
 * @author rnarne
 * The Roles Repository will provide methods for accessing Roles stored in the database.
 */
public class RolesRepository extends AbstractRepository<RolesModel> {
	/**
	 * Creates a new instance of the Roles Repository and configures the abstract class with the correct models.
	 * 
	 * @param country
	 *            (Locale) – The country by which to limit the result list
	 */
	public RolesRepository(Locale country) {
		setModelType(RolesModel.class);
	}
}
