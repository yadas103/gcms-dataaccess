package com.pfizer.gcms.dataaccess.repository;

import java.util.Locale;

import com.pfizer.gcms.dataaccess.model.ConsentTemplateModel;

public class ConsentTemplateRepository extends AbstractRepository<ConsentTemplateModel> {

	public ConsentTemplateRepository(Locale country) {
		setModelType(ConsentTemplateModel.class);
	}
	
	/**
	 * khans129
	 * Soft deletes the quick link model.
	 * @param item
	 * 		(ConsentTemplateModel) - The item representation
	 * @throws Exception
	 * 		If delete is unsuccessful
	 */
	@Override
	public void delete(ConsentTemplateModel item) throws Exception {
		ConsentTemplateModel model = find(item.getId());
		if (model != null) {
			model.setDeleted('Y');
			update(model);
		}		
	}
	
	/**
	 * khans129
	 * Returns a flag indicating it supports soft delete.
	 * @return the soft delete enabled Flag
	 */
	@Override
	public boolean isSoftDeleteEnabled() {
		return true;
	}
}
