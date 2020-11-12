package com.pfizer.gcms.dataaccess.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pfizer.gcms.dataaccess.common.NumericUtil;
import com.pfizer.gcms.dataaccess.common.exception.GCMSBadDataException;
import com.pfizer.gcms.dataaccess.model.AbstractModel;
import com.pfizer.gcms.dataaccess.model.ConsentAnnexModel;
import com.pfizer.gcms.dataaccess.model.TaskModel;

/**
 * @author khans129
 * The ConsentAnnex Repository will provide methods for accessing data stored in the database.
 */
public class ConsentAnnexRepository extends AbstractRepository<ConsentAnnexModel> {
	/**
	 * Creates a new instance of the ConsentAnnex Repository and configures the abstract class with the correct models.
	 * 
	 * @param country
	 *            (Locale) – The country by which to limit the result list
	 */
	public ConsentAnnexRepository(Locale country) {
		super(country);
		setModelType(ConsentAnnexModel.class);
	}
	private static final Log LOG = LogFactory.getLog(AbstractRepository.class);

	/**
	 * @author khans129
	 * Returns the representation of the current Model.
	 * @param id
	 *            (ModelType id) – The representation containing the data that was just updated
	 * @return ModelType
	 * @throws Exception
	 * 		If id is invalid and able to found
	 */
	public List<ConsentAnnexModel> findBPid(BigDecimal id, BigDecimal regionId) throws Exception {
		LOG.debug("Inside method find(BigDecimal id)");
		StopWatch timer = new StopWatch();
        timer.start();
        List<ConsentAnnexModel> models1=new ArrayList<ConsentAnnexModel>();
		if (NumericUtil.isNullOrLessThanOrEqualToZero(id)) {
			throw new GCMSBadDataException("Invalid ID");
		}
		EntityManager entityManager = getEntityManager();		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<TaskModel> query = builder.createQuery(TaskModel.class);
		Root<TaskModel> root = query.from(TaskModel.class);
		Predicate wherePredicate = buildPredicate(builder,
				query, root,id,regionId);
		//Predicate idPredicate = builder.equal(root.get(ConsentAnnexModel.FIELD_BPID), id);
		Predicate deleteFilter = prepareDeletePredicate(builder, root);
		query.where(wherePredicate);
		if (deleteFilter != null) {
			query.where(deleteFilter);
		}
		query = query.select(root);
		List<TaskModel> models = entityManager.createQuery(query).getResultList();
		
		for (TaskModel model : models) {
			models1.add(model.getConsannexid());
		}
		
		/*EntityManager entityManager = getEntityManager();		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();		
		CriteriaQuery<ConsentAnnexModel> query = builder.createQuery(getModelType());
		Root<ConsentAnnexModel> root = query.from(getModelType());
		Predicate idPredicate = builder.equal(root.get(ConsentAnnexModel.FIELD_BPID), id);
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		if (deleteFilter != null) {
			query.where(deleteFilter);
		}
		Predicate predicate = deleteFilter == null 
				? idPredicate : builder.and(idPredicate, deleteFilter);
		query.where(predicate);
		query = query.select(root);
		List<ConsentAnnexModel> models1 = entityManager.createQuery(query).getResultList();*/
		
		LOG.debug("#Performance#Repository#Total time took for find(BigDecimal id) operation is - " + timer.getTime());
		
		if (models1 == null || models1.isEmpty()) {
			return null;
		} 
		return models1;
	}
	
	
	public Predicate buildPredicate(CriteriaBuilder builder,
			CriteriaQuery<TaskModel> query, Root<TaskModel> rootModelType,
			BigDecimal id, BigDecimal regionId) {
	  	LOG.debug("Inside method buildPredicate");
		StopWatch timer = new StopWatch();
        timer.start();
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		
		//Start: Release 2.0 :  Added by nandap02 for Addition of predicate to include pending task in Consent History pop-up
		Predicate taskstatusPredicateForComplete =  builder.equal(rootModelType.get(TaskModel.FIELD_TASKSTATUS), "COMPLETED");	
		Predicate taskstatusPredicateForIncomplete = builder.equal(rootModelType.get(TaskModel.FIELD_TASKSTATUS), "INCOMPLETE");
		Predicate taskstatusPredicate = builder.or(taskstatusPredicateForComplete,taskstatusPredicateForIncomplete);
		andPredicates.add(taskstatusPredicate);
		//End
		
		Path<TaskModel> pathTaskModel=rootModelType.get(TaskModel.FIELD_CONS);
		Subquery<TaskModel> subqueryTask=query.subquery(TaskModel.class);
		Root<ConsentAnnexModel> rootConsentAnnex=subqueryTask.from(ConsentAnnexModel.class);	
						
		Predicate idpredicate1 = builder.equal(rootConsentAnnex.get(ConsentAnnexModel.FIELD_BPID), id);
		Predicate idpredicate2 = builder.equal(rootConsentAnnex.get(ConsentAnnexModel.FIELD_REGIONID), regionId);
		Predicate idpredicate = builder.and(idpredicate1,idpredicate2);
		subqueryTask.where(idpredicate);
		subqueryTask = subqueryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
		Predicate predicate=builder.in(pathTaskModel).value(subqueryTask);
		andPredicates.add(predicate);
		
		
		
		Predicate andPredicate = null;
		if (andPredicates != null && !andPredicates.isEmpty()) {
			andPredicate = builder.and(buildPredicatesArray(andPredicates));
		}
		LOG.debug("Time took for search criteria execution - " + timer.getTime());
		return andPredicate;
		
	}
	
	public Predicate prepareDeletePredicate(CriteriaBuilder builder, Root<TaskModel> rootModelType) {
		if (isSoftDeleteEnabled()) {
			Predicate nullPredicate = builder.isNull(rootModelType.get(AbstractModel.FIELD_DELETED));
			Predicate notDeletedPredicate = builder.equal(
					rootModelType.get(AbstractModel.FIELD_DELETED), 'N');
			
			return builder.or(nullPredicate, notDeletedPredicate);			
		}
		return null;
	}
	
	
}
