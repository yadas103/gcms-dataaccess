package com.pfizer.gcms.dataaccess.repository;

import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pfizer.gcms.dataaccess.model.ConsentTemplateModel;

public class ConsentTemplateRepository extends AbstractRepository<ConsentTemplateModel> {
	private static final Log LOG = LogFactory.getLog(AbstractRepository.class);

	public ConsentTemplateRepository(Locale country) {
		setModelType(ConsentTemplateModel.class);
	}
	
	
	
	/**
	 * selim
	 * Returns the representation of the current Model.
	 * @param templatecode
	 *            (ModelType code) – The representation containing the data that was just updated
	 * @return ModelType
	 * @throws Exception
	 * 		If id is invalid and able to found
	 */
	public ConsentTemplateModel findCode(String code) throws Exception {
		LOG.debug("Inside method find(String code)");
		StopWatch timer = new StopWatch();
        timer.start();
        
				
		EntityManager entityManager = getEntityManager();
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<ConsentTemplateModel> query = builder.createQuery(getModelType());
		Root<ConsentTemplateModel> root = query.from(getModelType());
		Predicate codePredicate = builder.equal(root.get(ConsentTemplateModel.FIELD_TMPL_CODE), code);
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		if (deleteFilter != null) {
			query.where(deleteFilter);
		}
		Predicate predicate = deleteFilter == null 
				? codePredicate : builder.and(codePredicate, deleteFilter);
		query.where(predicate);
		query = query.select(root);
		List<ConsentTemplateModel> models = entityManager.createQuery(query).getResultList();
		
		LOG.debug("#Performance#Repository#Total time took for find(BigDecimal id) operation is - " + timer.getTime());
		
		if (models == null || models.isEmpty()) {
			return null;
		} else if (models.size() > 1) {
			throw new NonUniqueResultException();
		}
		return models.get(0);
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
