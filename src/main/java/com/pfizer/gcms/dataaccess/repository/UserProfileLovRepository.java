package com.pfizer.gcms.dataaccess.repository;

import java.util.Locale;


import com.pfizer.gcms.dataaccess.model.UserProfileLovModel;

/**
 * @author KASWAS
 *
 */
public class UserProfileLovRepository extends AbstractCountryRepository<UserProfileLovModel> { 

	public UserProfileLovRepository(Locale country) {
		super(country);
		setModelType(UserProfileLovModel.class);
	}
	
	
	/* 
	 * @see com.pfizer.gcms.dataaccess.repository.AbstractRepository#sortByField()
	 */
	@Override
	public String sortByField() {
		return UserProfileLovModel.FIELD_USERNAME;
	}
}

