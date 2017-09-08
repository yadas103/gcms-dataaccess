package com.pfizer.gcms.dataaccess.repository;

import java.util.Locale;

import com.pfizer.gcms.dataaccess.model.ProfileRequestModel;

/**
 * @author Kaswas
 * The ProfileRequests Repository will provide methods for accessing ProfileRequests stored in the database.
 */
public class ProfileRequestRepository extends AbstractRepository<ProfileRequestModel>  {
	/**
	 * Creates a new instance of the ProfileRequest Repository and configures the abstract class with the correct models.
	 * 
	 * @param country
	 *            (Locale) – The country by which to limit the result list
	 */
	public ProfileRequestRepository(Locale country) {
		super(country);
		setModelType(ProfileRequestModel.class);
	}
}
