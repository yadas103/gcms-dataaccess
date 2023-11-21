package com.pfizer.gcms.dataaccess.repository;

import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pfizer.gcms.dataaccess.model.ConsentTemplateModel;

public class ConsentTemplateRepository extends AbstractRepository<ConsentTemplateModel> {
	private static final Log LOG = LogFactory.getLog(AbstractRepository.class);

	public ConsentTemplateRepository(Locale country) {
		System.out.println("In respository:"+ country);
		setModelType(ConsentTemplateModel.class);
	}
	

	/**
	 * Customized find method
	 * @param 
	 * @return ModelType
	 * @throws Exception
	 * 		
	 */
	
	public List<ConsentTemplateModel> find() {
		LOG.debug("Inside method find()");
		StopWatch timer = new StopWatch();
        timer.start();
        
		EntityManager entityManager = getEntityManager();
		List<ConsentTemplateModel> models = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ConsentTemplateModel> query = builder.createQuery(getModelType());
		Root<ConsentTemplateModel> root = query.from(getModelType());
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		if (deleteFilter != null) {
			query.where(deleteFilter);
		}	
/*		applySorting(builder, query, ConsentTemplateModel.FIELD_TMPL_STAT,
				false, root);*/
		String sortByField = sortByField();
		if (sortByField != null){
			applySorting(builder, query, sortByField, sortDescending(), root);
		}
		query.select(root);
		models = entityManager.createQuery(query).getResultList();
		System.out.println("Model in Consent Repo:"+models.toString());
		
		//LOG.debug("#Performance#Repository#Total time took for find() operation is - " + timer.getTime());
		return models;
	}


	/**
	 * Informs whether sort by field need to be applied on which column.
	 * If no sorting needed then returns null otherwise overrides the particular column.
	 * @return the sort by field value
	 */
	public String sortByField() {
		
		return ConsentTemplateModel.FIELD_UPDATED_DATE;
		//return null;
		
	}
	/**
	 * @return If true return decending order
	 */
	public Boolean sortDescending() {
		return true;
	}
	
	

	
	/**
	 * Applies sorting for all the orders.
	 * @param builder
	 * 		(CriteriaBuilder) - The builder criteria of type TaskModel representation
	 * @param query
	 * 	(CriteriaQuery<TaskModel>) - The query criteria of type TaskModel representation
	 * @param sortOrders
	 * 		(List<Order>) - The order list criteria of type TaskModel representation
	 */
  private void applySorting(CriteriaBuilder builder,
			CriteriaQuery<ConsentTemplateModel> query, List<Order> sortOrders) {
		if (sortOrders != null && !sortOrders.isEmpty()) {
			query.orderBy(sortOrders);
		}
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
