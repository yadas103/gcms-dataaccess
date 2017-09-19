package com.pfizer.gcms.dataaccess.repository;

import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.time.StopWatch;

import com.pfizer.gcms.dataaccess.model.AbstractModel;
import com.pfizer.gcms.dataaccess.model.TaskModel;


public class TaskRepository extends AbstractRepository<TaskModel> {
	private static final Log LOG = LogFactory.getLog(AbstractRepository.class);
	public TaskRepository(Locale country) {
		setModelType(TaskModel.class);
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
		//Map<Class<?>, List<BigDecimal>> childEntitiesToRemove = getChildEntitiesToBeDeleted(item);
		//delete(childEntitiesToRemove, item);
		//LOG.debug("#Performance#Repository#Total time took deleting child objects during update is - " + timer.getTime());
		
		StopWatch timer1 = new StopWatch();
        timer1.start();
        
        TaskModel result = entityManager.merge(item);
		
		LOG.debug("#Performance#Repository#Total time took only for merge during update is - " + timer1.getTime());
		
		LOG.debug("#Performance#Repository#Total time took for update(ModelType item) operation is - " + timer.getTime());
		
		return result;
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
			model.setDeleted('Y');
			update(model);
		}		
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
