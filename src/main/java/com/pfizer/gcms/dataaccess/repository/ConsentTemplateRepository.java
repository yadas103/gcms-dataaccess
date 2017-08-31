package com.pfizer.gcms.dataaccess.repository;

import com.pfizer.gcms.dataaccess.model.ConsentTemplateModel;

public class ConsentTemplateRepository extends AbstractRepository<ConsentTemplateModel> {

	public ConsentTemplateRepository() {
		setModelType(ConsentTemplateModel.class);
	}
}
