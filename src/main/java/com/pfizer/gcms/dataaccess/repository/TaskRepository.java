package com.pfizer.gcms.dataaccess.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pfizer.gcms.dataaccess.model.TaskModel;

/**
 * @author khans129
 * The Task Repository will provide methods for accessing, creating and updating the values 
 * stored in the database. 
 */
public class TaskRepository extends AbstractRepository<TaskModel> {
	
	private static final Log LOG = LogFactory.getLog(AbstractRepository.class);
	/**
	 * Creates a new instance of the Task Repository and configures the abstract class with the 
	 * correct models.
	 * 
	 * @param country
	 *            (Locale) – The country by which to limit the result list
	 */
	public TaskRepository(Locale country) {
		setModelType(TaskModel.class);
	}
	
	
	
	/**
	 * This method gets all task id from the task table 
	 * Returns the collection of all the ids  
	 * @return ArrayList<ids>
	 */	
		public List<BigDecimal> findID() throws Exception {
			LOG.debug("Inside method findID()");
			StopWatch timer = new StopWatch();
	        timer.start();
			EntityManager entityManager = getEntityManager();	
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<BigDecimal> query = builder.createQuery(BigDecimal.class);
			Root<TaskModel> root = query.from(getModelType());
			query = query.select(root.<BigDecimal>get(TaskModel.FIELD_TASK_ID));	
			TypedQuery<BigDecimal> typedQuery = entityManager.createQuery(query);
			List<BigDecimal> ids=(List<BigDecimal>)typedQuery.getResultList();			
			LOG.debug("#Performance#Repository#Total time took for find() operation is - " + timer.getTime());
			return ids;
		}
	
	
	/**
	 * Updates the current instance of Model and persists the changes to the database.
	 * @param item
	 *            (ModelType)TaskModel – The representation containing the data that was just updated
	 * @return ModelType
	 * @throws Exception
	 *             if save is unsuccessful
	 */
	public TaskModel update(TaskModel item) throws Exception {
		LOG.debug("Inside update(ModelType item)");		
		StopWatch timer = new StopWatch();
        timer.start();		
		EntityManager entityManager = getEntityManager();       
        TaskModel result = entityManager.merge(item);		
		LOG.debug("#Performance#Repository#Total time took for update(ModelType item) operation is - " + timer.getTime());		
		return result;
	}
	
	
		
		/**
		 * This method gets all task according to decending order of updation date when rold id is sytem admin
		 * Returns the collection of all the instances of type ModelType an empty partial model is generated 
		 * @return ArrayList<ModelType>
		 */
		  public List<TaskModel> find() {
			LOG.debug("Inside method find()");
			StopWatch timer = new StopWatch();
	        timer.start();	        
			EntityManager entityManager = getEntityManager();
			List<TaskModel> models = null;
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<TaskModel> query = builder.createQuery(getModelType());
			Root<TaskModel> root = query.from(getModelType());
			Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
			if (deleteFilter != null) {
				query.where(deleteFilter);
			}
			String sortByField = sortByField();
			if (sortByField != null){
				applySorting(builder, query, sortByField, sortDescending(), root);
			}
			query.select(root);
			models = entityManager.createQuery(query).getResultList();			
			LOG.debug("#Performance#Repository#Total time took for find() operation is - " + timer.getTime());
			return models;
		}
		
		  /**
			 * This method gets task according to assigned id when role id is local and global user
			 * Returns the collection of all the instances of type ModelType an empty partial model is generated 
			 * @return ArrayList<ModelType>
			 */
		  public List<TaskModel> findUserSpecificTask(String s) {
			LOG.debug("Inside method findUserSpecificTask(String s)");
			StopWatch timer = new StopWatch();
	        timer.start();        
			EntityManager entityManager = getEntityManager();
			List<TaskModel> models = null;
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<TaskModel> query = builder.createQuery(getModelType());
			Root<TaskModel> root = query.from(getModelType());
			Predicate namePredicate = builder.equal(root.get(TaskModel.FIELD_ASSIGNED_TO), s);
			Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
			if (deleteFilter != null) {
				query.where(deleteFilter);
			}
			Predicate predicate = deleteFilter == null 
					? namePredicate : builder.and(namePredicate, deleteFilter);
			query.where(predicate);
			query = query.select(root);
			List<TaskModel> models1 = entityManager.createQuery(query).getResultList();
			
			LOG.debug("#Performance#Repository#Total time took for findUserSpecificTask(String s) operation is - " + timer.getTime());
			
			if (models1 == null || models1.isEmpty()) {
				return null;
			} 
			return models1;
		}
		
		  /**
			 * This method gets task according to country when role id is EFPIA lead and data stewerd
			 * Returns the collection of all the instances of type ModelType an empty partial model is generated 
			 * @return ArrayList<ModelType>
			 */
			public List<TaskModel> findCountryTask(BigDecimal country) throws Exception  {
				LOG.debug("Inside method findCountryTask(BigDecimal country)");
				StopWatch timer = new StopWatch();
		        timer.start();		        
				EntityManager entityManager = getEntityManager();
				CriteriaBuilder builder = entityManager.getCriteriaBuilder();
				List<TaskModel> models = null;
				Query typedQuery;
				typedQuery=entityManager.createQuery("select e FROM com.pfizer.gcms.dataaccess.model.TaskModel e "
						+ "where consannexid IN (select id FROM com.pfizer.gcms.dataaccess.model.ConsentAnnexModel"
						+ " WHERE profilecountry.id = :country)");
				typedQuery.setParameter("country", country);
				LOG.debug("typedQuery"+typedQuery);
				models = typedQuery.getResultList();
				LOG.debug("models" +models);
				if (models == null || models.isEmpty()) {
					return null;
				}
				return models;
				

			}
		

	/**
	 * Soft deletes the quick link model.
	 * @param item
	 * 		(AnnouncementsModel) - The item representation
	 * @throws Exception
	 * 		If delete is unsuccessful
	 */
	
	public void delete(TaskModel item) throws Exception {
		TaskModel model = find(item.getId());
		if (model != null) {
			//model.setDeleted('Y');
			update(model);
		}		
	}
	
	/**
	 * Informs whether sort by field need to be applied on which column.
	 * If no sorting needed then returns null otherwise overrides the particular column.
	 * @return the sort by field value
	 */
	public String sortByField() {
		
		return TaskModel.FIELD_UPDATED_DATE;
		//return null;
		
	}
	/**
	 * @return If true return decending order
	 */
	public Boolean sortDescending() {
		return true;
	}
	
	/**
	 * Returns a flag indicating it supports soft delete.
	 * @return the soft delete enabled Flag
	 */
	
	public boolean isSoftDeleteEnabled() {
		return true;
	}

	@Override
	public Predicate prepareSoftDeletePredicate(CriteriaBuilder builder, Root<TaskModel> rootModelType) {
		return null;
	}	
}
