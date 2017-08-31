package com.pfizer.gcms.dataaccess.repository;

import com.pfizer.gcms.dataaccess.model.GCMSCountryModel;

public class GCMSCountryRepository extends AbstractRepository<GCMSCountryModel> {

	public GCMSCountryRepository() {
		setModelType(GCMSCountryModel.class);
	}
}
