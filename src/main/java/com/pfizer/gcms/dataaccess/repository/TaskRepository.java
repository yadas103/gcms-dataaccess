package com.pfizer.gcms.dataaccess.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pfizer.gcms.dataaccess.dto.PagingSearchResultDTO;
import com.pfizer.gcms.dataaccess.dto.TaskSearchDTO;
import com.pfizer.gcms.dataaccess.model.BusinessProfileModel;
import com.pfizer.gcms.dataaccess.model.ConsentAnnexModel;
import com.pfizer.gcms.dataaccess.model.ConsentLovModel;
import com.pfizer.gcms.dataaccess.model.CountryModel;
import com.pfizer.gcms.dataaccess.model.TaskModel;
import com.pfizer.gcms.dataaccess.model.UserModelNew;

/**
 * @author khans129 The Task Repository will provide methods for accessing,
 *         creating and updating the values stored in the database.
 */
public class TaskRepository extends AbstractRepository<TaskModel> {

	private static final Log LOG = LogFactory.getLog(AbstractRepository.class);

	/**
	 * Creates a new instance of the Task Repository and configures the abstract
	 * class with the correct models.
	 * 
	 * @param country
	 *            (Locale) – The country by which to limit the result list
	 */
	public TaskRepository(Locale country) {
		setModelType(TaskModel.class);
	}

	
	public TaskRepository() {
		setModelType(TaskModel.class);
	}
	/**
	 * This method gets all task id from the task table Returns the collection
	 * of all the ids
	 * 
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
		query = query.select(root.<BigDecimal> get(TaskModel.FIELD_TASK_ID));
		TypedQuery<BigDecimal> typedQuery = entityManager.createQuery(query);
		List<BigDecimal> ids = (List<BigDecimal>) typedQuery.getResultList();
		LOG.debug("#Performance#Repository#Total time took for find() operation is - " + timer.getTime());
		return ids;
	}



	/**
	 * This method for delete tempalte
	 */

	public List<TaskModel> findTemplate(BigDecimal id) throws Exception {
		LOG.debug("Inside method findTemplate()");
		StopWatch timer = new StopWatch();
		timer.start();
		EntityManager entityManager = getEntityManager();
		List<TaskModel> models = new ArrayList<TaskModel>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TaskModel> query = builder.createQuery(getModelType());
		Root<TaskModel> root = query.from(getModelType());
		Predicate wherePredicate = buildPredicate(builder, query, root, id);
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		if (deleteFilter != null) {
			query.where(deleteFilter);
		}
		query.where(wherePredicate);
		query = query.select(root);
		models = entityManager.createQuery(query).getResultList();

		LOG.debug("#Performance#Repository#Total time took for find() operation is - " + timer.getTime());
		return models;
	}

	public Predicate buildPredicate(CriteriaBuilder builder, CriteriaQuery<TaskModel> query,
			Root<TaskModel> rootModelType, BigDecimal id) {
		LOG.debug("Inside method buildSearchCriteria");
		StopWatch timer = new StopWatch();
		timer.start();
		List<Predicate> andPredicates = new ArrayList<Predicate>();

		Path<TaskModel> pathTaskModel = rootModelType.get(TaskModel.FIELD_CONS);
		Subquery<TaskModel> subqueryTask = query.subquery(TaskModel.class);
		Root<ConsentAnnexModel> rootConsentAnnex = subqueryTask.from(ConsentAnnexModel.class);
		Predicate templatepredicate = builder.equal(rootConsentAnnex.get(ConsentAnnexModel.FIELD_TEMPLATEID), id);
		subqueryTask.where(templatepredicate);
		subqueryTask = subqueryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
		Predicate predicate = builder.in(pathTaskModel).value(subqueryTask);
		andPredicates.add(predicate);

		Predicate taskstatusPredicate = builder.equal(rootModelType.get(TaskModel.FIELD_TASKSTATUS), "INCOMPLETE");
		andPredicates.add(taskstatusPredicate);

		Predicate andPredicate = null;
		if (andPredicates != null && !andPredicates.isEmpty()) {
			andPredicate = builder.and(buildPredicatesArray(andPredicates));
		}
		LOG.debug("Time took for search criteria execution - " + timer.getTime());
		return andPredicate;

	}

	/**
	 * Updates the current instance of Model and persists the changes to the
	 * database.
	 * 
	 * @param item
	 *            (ModelType)TaskModel – The representation containing the data
	 *            that was just updated
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
		LOG.debug(
				"#Performance#Repository#Total time took for update(ModelType item) operation is - " + timer.getTime());
		return result;
	}

	/**
	 * Get All task according from task table according to search criteria
	 * 
	 * @param searchDTO
	 *            (TaskSearchDTO) - The Search Criteria provided by the user.
	 * @return the PagingSearchResultDTO<TaskModel>
	 * @throws Exception
	 *             If finding the recipient based on search criteria fails.
	 */
	public PagingSearchResultDTO<TaskModel> find(TaskSearchDTO searchDTO) throws Exception {
		LOG.debug("Inside method find");
		StopWatch timer = new StopWatch();
		timer.start();
		String pageNo = null;
		String pageSz = null;
		if (searchDTO.getPageNumber() != null) {
			pageNo = searchDTO.getPageNumber();
		} else {
			pageNo = "1";
		}
		if (searchDTO.getPageSize() != null) {
			pageSz = searchDTO.getPageSize();
		} else {
			pageSz = "6";
		}
		int pageNumber = Integer.parseInt(pageNo);
		int pageSize = Integer.parseInt(pageSz);
		int firstIndex = pageSize * (pageNumber - 1);
		Long count = null;
		PagingSearchResultDTO<TaskModel> pagingResult = new PagingSearchResultDTO<TaskModel>();
		List<TaskModel> models = new ArrayList<TaskModel>();
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TaskModel> query = builder.createQuery(getModelType());
		Root<TaskModel> root = query.from(getModelType());
		Predicate wherePredicate = buildSearchCriteriaPredicate(builder, query, root, searchDTO);
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		query.where(wherePredicate);
		if (deleteFilter != null) {
			query.where(deleteFilter);
		}
		/*
		 * String sortByField = sortByField(); if (sortByField != null){
		 * applySorting(builder, query, sortByField, sortDescending(), root); }
		 */
		// doJoins(root.getJoins(), root);
		root.fetch(TaskModel.FIELD_CONS, JoinType.INNER);
		root.fetch(TaskModel.FIELD_ASSIGNED_TO, JoinType.INNER);

		CriteriaQuery<TaskModel> select = query.select(root);
		TypedQuery<TaskModel> typedQuery = entityManager.createQuery(select);
		typedQuery.setFirstResult(firstIndex);
		typedQuery.setMaxResults(pageSize);
		models = typedQuery.getResultList();
		CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
		Root<TaskModel> countRoot = countQuery.from(query.getResultType());
		countQuery.where(wherePredicate);
		// doJoins(root.getJoins(), countRoot);
		// doJoinsOnFetches(root.getFetches(), countRoot);
		countQuery.select(builder.countDistinct(countRoot));
		// countQuery.distinct(true);
		TypedQuery<Long> typedCountQuery = entityManager.createQuery(countQuery);
		count = typedCountQuery.getSingleResult();

		LOG.debug(" Total Count" + count);
		Collection<TaskModel> result = new LinkedHashSet<TaskModel>(models);
		List<TaskModel> resultList = new ArrayList<TaskModel>();
		resultList.addAll(result);
		pagingResult.setTotalRecordsCount(count);
		pagingResult.setCurrentPageData(resultList);
		LOG.debug("Time took for all task data - " + timer.getTime());
		return pagingResult;
	}

	/**
	 * Builds the search criteria predicate.
	 * 
	 * @param builder
	 *            (CriteriaBuilder) - The builder for type TaskModel
	 *            representation
	 * @param query
	 *            (CriteriaQuery<TaskModel>) - The query of type TaskModel
	 *            representation
	 * @param rootModelType
	 *            (Root<TaskModel>) - The root of type TaskModel representation
	 * @param searchDTO
	 *            (TaskSearchDTO) - The representation of type TaskModel
	 *            representation
	 * @param sortOrders
	 *            (List<Order>) - The representation sorting order type of
	 *            TaskModel representation
	 */

	public Predicate buildSearchCriteriaPredicate(CriteriaBuilder builder, CriteriaQuery<TaskModel> query,
			Root<TaskModel> rootModelType, TaskSearchDTO searchDTO) {
		LOG.debug("Inside method buildSearchCriteria");
		StopWatch timer = new StopWatch();
		timer.start();
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		List<Order> sortOrders = new ArrayList<Order>();
		// server side searching
		
		// search by taskstatus
		if (searchDTO.getTaskStatus() != null) {
            Path<TaskModel> pathTaskModel = rootModelType.get(TaskModel.FIELD_CONS);
            Subquery<TaskModel> subquryTask = query.subquery(TaskModel.class);
            Root<ConsentAnnexModel> rootConsentAnnex = subquryTask.from(ConsentAnnexModel.class);
            Predicate predicateRegionId = builder.equal(rootConsentAnnex.get(ConsentAnnexModel.FIELD_REGIONID), searchDTO.getRegionId());
            
            //R2.0 arunkv - temprofile [dont show tempprofile task]
			Predicate predicateTempProfile = builder.equal(rootConsentAnnex.get(ConsentAnnexModel.FIELD_TEMP_PROFILE), "N");
			Predicate newPredicate = builder.and(predicateRegionId,predicateTempProfile);
            subquryTask.where(newPredicate);
            
            subquryTask = subquryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
            Predicate predicate = builder.in(pathTaskModel).value(subquryTask);    
            Predicate taskstatusPredicate = builder.equal(rootModelType.get(TaskModel.FIELD_TASKSTATUS),
                    searchDTO.getTaskStatus());
            Predicate predicate1 = builder.and(predicate,taskstatusPredicate);
            //Predicate taskstatusPredicate2 = builder.equal(rootModelType.get(TaskModel.FIELD_CONS).get(ConsentAnnexModel.FIELD_REGIONID),searchDTO.getRegionId());
            andPredicates.add(predicate1);
        } else {            
                
            Path<TaskModel> pathTaskModel = rootModelType.get(TaskModel.FIELD_CONS);
            Subquery<TaskModel> subquryTask = query.subquery(TaskModel.class);
            Root<ConsentAnnexModel> rootConsentAnnex = subquryTask.from(ConsentAnnexModel.class);
            Predicate predicateRegionId = builder.equal(rootConsentAnnex.get(ConsentAnnexModel.FIELD_REGIONID), searchDTO.getRegionId());
            
            //R2.0 arunkv - temprofile [dont show tempprofile task]
			Predicate predicateTempProfile = builder.equal(rootConsentAnnex.get(ConsentAnnexModel.FIELD_TEMP_PROFILE), "N");
			Predicate newPredicate = builder.and(predicateRegionId,predicateTempProfile);
            subquryTask.where(newPredicate);
            
            subquryTask = subquryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
            Predicate predicate = builder.in(pathTaskModel).value(subquryTask);    
            Predicate taskstatusPredicate = builder.equal(rootModelType.get(TaskModel.FIELD_TASKSTATUS),
                    "INCOMPLETE");
            Predicate predicate1 = builder.and(predicate,taskstatusPredicate);
            andPredicates.add(predicate1);

 

        }
		
		// search by payer country
		if (searchDTO.getPayercountry() != null) {
			String country = searchDTO.getPayercountry();
			Path<TaskModel> pathTaskModel = rootModelType.get(TaskModel.FIELD_CONS);
			Subquery<TaskModel> subquryTask = query.subquery(TaskModel.class);
			Root<ConsentAnnexModel> rootConsentAnnex = subquryTask.from(ConsentAnnexModel.class);
			Path<ConsentAnnexModel> pathConsentAnnexModel = rootConsentAnnex.get(ConsentAnnexModel.FIELD_PAYERCOUNTRY);
			Subquery<ConsentAnnexModel> subqueryConsentAnnex = query.subquery(ConsentAnnexModel.class);
			Root<CountryModel> rootCountry = subqueryConsentAnnex.from(CountryModel.class);
			Predicate predicateCountryName = builder.like(
					builder.lower(rootCountry.get(CountryModel.FIELD_COUNTRY_NAME)), "%" + country.toLowerCase() + "%");
			//R2.0 arunkv - temprofile [dont show tempprofile task]
			Predicate predicateTempProfile = builder.equal(rootConsentAnnex.get(ConsentAnnexModel.FIELD_TEMP_PROFILE), searchDTO.getTempProfile());
			Predicate newPredicate = builder.and(predicateCountryName,predicateTempProfile);
			subqueryConsentAnnex.where(newPredicate);
			subqueryConsentAnnex = subqueryConsentAnnex.select(rootCountry.get(CountryModel.FIELD_COUNTRY_ID));
			Predicate predicateConsentID = builder.in(pathConsentAnnexModel).value(subqueryConsentAnnex);
			subquryTask.where(predicateConsentID);
			subquryTask = subquryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
			Predicate predicate = builder.in(pathTaskModel).value(subquryTask);
			andPredicates.add(predicate);
		}
		// search by last name
		if (searchDTO.getLastName() != null) {
			String lastname = searchDTO.getLastName();
			Path<TaskModel> pathTaskModel = rootModelType.get(TaskModel.FIELD_CONS);
			Subquery<TaskModel> subquryTask = query.subquery(TaskModel.class);
			Root<ConsentAnnexModel> rootConsentAnnex = subquryTask.from(ConsentAnnexModel.class);
			Path<ConsentAnnexModel> pathConsentAnnexModel = rootConsentAnnex.get(ConsentAnnexModel.FIELD_BPID);
			Subquery<ConsentAnnexModel> subqueryConsentAnnex = query.subquery(ConsentAnnexModel.class);
			Root<BusinessProfileModel> rootBusinessProfile = subqueryConsentAnnex.from(BusinessProfileModel.class);
			Predicate predicateLastName = builder.like(
					builder.lower(rootBusinessProfile.get(BusinessProfileModel.FIELD_LAST_NAME)),
					"%" + lastname.toLowerCase() + "%");
			Predicate predicateLastName2 = builder.like(
					builder.lower(rootBusinessProfile.get(BusinessProfileModel.FIELD_ORGANISATION_NAME)),
					"%" + lastname.toLowerCase() + "%");
			Predicate predicateRegionId = builder.equal(rootBusinessProfile.get(BusinessProfileModel.FIELD_REGION_ID), searchDTO.getRegionId());
			Predicate newpredicate = builder.or(predicateLastName, predicateLastName2);
			Predicate newPredicate2 = builder.and(newpredicate,predicateRegionId);
			subqueryConsentAnnex.where(newPredicate2);
			subqueryConsentAnnex = subqueryConsentAnnex.select(rootBusinessProfile.get(BusinessProfileModel.FIELD_BP_ID));

			Predicate predicateBusinessID = builder.in(pathConsentAnnexModel).value(subqueryConsentAnnex);
			subquryTask.where(predicateBusinessID);
			subquryTask = subquryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
			Predicate predicate = builder.in(pathTaskModel).value(subquryTask);
			andPredicates.add(predicate);
		}
		// search by first name
		if (searchDTO.getFirstName() != null) {
			String firstname = searchDTO.getFirstName();
			Path<TaskModel> pathTaskModel = rootModelType.get(TaskModel.FIELD_CONS);
			Subquery<TaskModel> subquryTask = query.subquery(TaskModel.class);
			Root<ConsentAnnexModel> rootConsentAnnex = subquryTask.from(ConsentAnnexModel.class);
			Path<ConsentAnnexModel> pathConsentAnnexModel = rootConsentAnnex.get(ConsentAnnexModel.FIELD_BPID);
			Subquery<ConsentAnnexModel> subqueryConsentAnnex = query.subquery(ConsentAnnexModel.class);
			Root<BusinessProfileModel> rootBusinessProfile = subqueryConsentAnnex.from(BusinessProfileModel.class);
			Predicate predicateFirstName = builder.like(
					builder.lower(rootBusinessProfile.get(BusinessProfileModel.FIELD_FIRST_NAME)),
					"%" + firstname.toLowerCase() + "%");
			Predicate predicateRegionId = builder.equal(rootBusinessProfile.get(BusinessProfileModel.FIELD_REGION_ID), searchDTO.getRegionId());
			Predicate newPredicate = builder.and(predicateFirstName,predicateRegionId);
			subqueryConsentAnnex.where(newPredicate);
			subqueryConsentAnnex = subqueryConsentAnnex
					.select(rootBusinessProfile.get(BusinessProfileModel.FIELD_BP_ID));
			Predicate predicateBusinessID = builder.in(pathConsentAnnexModel).value(subqueryConsentAnnex);
			subquryTask.where(predicateBusinessID);
			subquryTask = subquryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
			Predicate predicate = builder.in(pathTaskModel).value(subquryTask);
			andPredicates.add(predicate);
		}

		// search by profile country
		if (searchDTO.getProfilecountry() != null) {
			String country = searchDTO.getProfilecountry();
			Path<TaskModel> pathTaskModel = rootModelType.get(TaskModel.FIELD_CONS);
			Subquery<TaskModel> subquryTask = query.subquery(TaskModel.class);
			Root<ConsentAnnexModel> rootConsentAnnex = subquryTask.from(ConsentAnnexModel.class);
			Path<ConsentAnnexModel> pathConsentAnnexModel = rootConsentAnnex
					.get(ConsentAnnexModel.FIELD_PROFILECOUNTRY);
			Subquery<ConsentAnnexModel> subqueryConsentAnnex = query.subquery(ConsentAnnexModel.class);
			Root<CountryModel> rootCountry = subqueryConsentAnnex.from(CountryModel.class);
			Predicate predicateCountryName = builder.like(
					builder.lower(rootCountry.get(CountryModel.FIELD_COUNTRY_NAME)), "%" + country.toLowerCase() + "%");
			subqueryConsentAnnex.where(predicateCountryName);
			subqueryConsentAnnex = subqueryConsentAnnex.select(rootCountry.get(CountryModel.FIELD_COUNTRY_ID));
			Predicate predicateConsentID = builder.in(pathConsentAnnexModel).value(subqueryConsentAnnex);
			subquryTask.where(predicateConsentID);
			subquryTask = subquryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
			Predicate predicate = builder.in(pathTaskModel).value(subquryTask);
			andPredicates.add(predicate);
		}
		// search by consent status name
		if (searchDTO.getConsentStatus() != null) {
			String consentstatus = searchDTO.getConsentStatus();
			Path<TaskModel> pathTaskModel = rootModelType.get(TaskModel.FIELD_CONS);
			Subquery<TaskModel> subquryTask = query.subquery(TaskModel.class);
			Root<ConsentAnnexModel> rootConsentAnnex = subquryTask.from(ConsentAnnexModel.class);
			Path<ConsentAnnexModel> pathConsentAnnexModel = rootConsentAnnex.get(ConsentAnnexModel.FIELD_CONSENT);
			Subquery<ConsentAnnexModel> subqueryConsentAnnex = query.subquery(ConsentAnnexModel.class);
			Root<ConsentLovModel> rootConsentLov = subqueryConsentAnnex.from(ConsentLovModel.class);
			Predicate predicateConsentName = builder.like(
					builder.lower(rootConsentLov.get(ConsentLovModel.FIELD_CONSENT_NAME)),
					"%" + consentstatus.toLowerCase() + "%");
			subqueryConsentAnnex.where(predicateConsentName);
			subqueryConsentAnnex = subqueryConsentAnnex
					.select(rootConsentLov.get(ConsentLovModel.FIELD_CONSENT_STS_ID));
			Predicate predicateConsentID = builder.in(pathConsentAnnexModel).value(subqueryConsentAnnex);
			subquryTask.where(predicateConsentID);
			subquryTask = subquryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
			Predicate predicate = builder.in(pathTaskModel).value(subquryTask);
			andPredicates.add(predicate);
		}
		// search event name from task page
		if (searchDTO.getEventName() != null) {
			String eventname = searchDTO.getEventName();
			Path<TaskModel> pathTaskModel = rootModelType.get(TaskModel.FIELD_CONS);
			Subquery<TaskModel> subqueryTask = query.subquery(TaskModel.class);
			Root<ConsentAnnexModel> rootConsentAnnex = subqueryTask.from(ConsentAnnexModel.class);
			Predicate eventpredicate = builder.like(
					builder.lower(rootConsentAnnex.get(ConsentAnnexModel.FIELD_EVENTNAME)),
					"%" + eventname.toLowerCase() + "%");
			subqueryTask.where(eventpredicate);
			subqueryTask = subqueryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
			Predicate predicate = builder.in(pathTaskModel).value(subqueryTask);
			andPredicates.add(predicate);
		}
		/*
		 * //search initiated by name from task page
		 * if(searchDTO.getInitiatedBy() != null){ String
		 * initiatedBy=searchDTO.getInitiatedBy(); Predicate initiatedPredicate
		 * = builder.like(builder.lower(rootModelType.get(TaskModel.
		 * FIELD_CREATED_BY)), initiatedBy.toLowerCase()+"%");
		 * andPredicates.add(initiatedPredicate); }
		 */
		// search initiated by name from task page
		if (searchDTO.getInitiatedBy() != null) {
			String initiatedBy = searchDTO.getInitiatedBy();
			List<Predicate> orPredicates = new ArrayList<Predicate>();
			Path<TaskModel> pathTaskModel = rootModelType.get(TaskModel.FIELD_ASSIGNED_TO);
			Subquery<TaskModel> subqueryTask = query.subquery(TaskModel.class);
			Root<UserModelNew> rootConsentAnnex = subqueryTask.from(UserModelNew.class);
			Predicate inipredicate1 = builder.like(builder.lower(rootConsentAnnex.get(UserModelNew.FIELD_FIRSTNAME)),
					"%" + initiatedBy.toLowerCase() + "%");
			Predicate inipredicate2 = builder.like(builder.lower(rootConsentAnnex.get(UserModelNew.FIELD_USERNAME)),
					"%" + initiatedBy.toLowerCase() + "%");
			Predicate inipredicate3 = builder.like(builder.lower(rootConsentAnnex.get(UserModelNew.FIELD_LASTNAME)),
					"%" + initiatedBy.toLowerCase() + "%");
			Predicate newpredicate = builder.or(inipredicate1, inipredicate2, inipredicate3);
			subqueryTask.where(newpredicate);
			subqueryTask = subqueryTask.select(rootConsentAnnex.get(UserModelNew.FIELD_USERNAME));
			Predicate predicate = builder.in(pathTaskModel).value(subqueryTask);
			andPredicates.add(predicate);
		}
		// search updated date from task page
		if (searchDTO.getUpdateddate() != null) {
			String date = searchDTO.getUpdateddate();

			Path<Date> path = rootModelType.get(TaskModel.FIELD_UPDATED_DATE);
			Expression<String> dateStringExpr = builder.function("TO_CHAR", String.class, path,
					builder.literal("dd-MM-YYYY"));
			Predicate datePredicate = builder.like(dateStringExpr, "%" + date + "%");
			/*
			 * Expression<String> idkey=
			 * rootModelType.get(TaskModel.FIELD_UPDATED_DATE).as(String.class);
			 * Predicate datePredicate = builder.like(idkey,date+"%");
			 */
			andPredicates.add(datePredicate);
		}

		// Server side sorting

		// sort by updatedDate (default sort)
		if (searchDTO.getSortBy() == null || searchDTO.getSortBy().equalsIgnoreCase("updateddate")) {
			applySorting(builder, query, TaskModel.FIELD_UPDATED_DATE, searchDTO.isSortDescending(), rootModelType);
		}
		// sort by payer country name
		if (searchDTO.getSortBy() != null && searchDTO.getSortBy().equalsIgnoreCase("payercountry")) {
			if (CountryModel.FIELD_COUNTRY_NAME != null) {
				Join<TaskModel, ConsentAnnexModel> conAnnexIdJoin1 = rootModelType.join(TaskModel.FIELD_CONS);
				Join<ConsentAnnexModel, CountryModel> cntryJoin1 = conAnnexIdJoin1
						.join(ConsentAnnexModel.FIELD_PAYERCOUNTRY);
				buildSortingOrder(builder, cntryJoin1, CountryModel.FIELD_COUNTRY_NAME, searchDTO.isSortDescending(),
						sortOrders);
				applySorting(builder, query, sortOrders);
			}

		}

		// sort by last name
		if (searchDTO.getSortBy() != null && searchDTO.getSortBy().equalsIgnoreCase("lastname")) {

			if (BusinessProfileModel.FIELD_LAST_NAME != null) {
				Join<TaskModel, ConsentAnnexModel> conAnnexIdJoin1 = rootModelType.join(TaskModel.FIELD_CONS);
				Join<ConsentAnnexModel, BusinessProfileModel> bpIdJoin1 = conAnnexIdJoin1
						.join(ConsentAnnexModel.FIELD_BPID);
				buildSortingOrder(builder, bpIdJoin1, BusinessProfileModel.FIELD_LAST_NAME,
						searchDTO.isSortDescending(), sortOrders);
				applySorting(builder, query, sortOrders);
			}

		}
		// sort first name
		if (searchDTO.getSortBy() != null && searchDTO.getSortBy().equalsIgnoreCase("firstname")) {
			if (BusinessProfileModel.FIELD_FIRST_NAME != null) {
				Join<TaskModel, ConsentAnnexModel> conAnnexIdJoin1 = rootModelType.join(TaskModel.FIELD_CONS);
				Join<ConsentAnnexModel, BusinessProfileModel> bpIdJoin1 = conAnnexIdJoin1
						.join(ConsentAnnexModel.FIELD_BPID);
				buildSortingOrder(builder, bpIdJoin1, BusinessProfileModel.FIELD_FIRST_NAME,
						searchDTO.isSortDescending(), sortOrders);
				applySorting(builder, query, sortOrders);
			}
		}

		// sort by profile country name
		if (searchDTO.getSortBy() != null && searchDTO.getSortBy().equalsIgnoreCase("profilecountry")) {
			if (CountryModel.FIELD_COUNTRY_NAME != null) {
				Join<TaskModel, ConsentAnnexModel> conAnnexIdJoin1 = rootModelType.join(TaskModel.FIELD_CONS);
				Join<ConsentAnnexModel, CountryModel> cntryJoin1 = conAnnexIdJoin1
						.join(ConsentAnnexModel.FIELD_PROFILECOUNTRY);
				buildSortingOrder(builder, cntryJoin1, CountryModel.FIELD_COUNTRY_NAME, searchDTO.isSortDescending(),
						sortOrders);
				applySorting(builder, query, sortOrders);
			}

		}

		// sort consent name
		if (searchDTO.getSortBy() != null && searchDTO.getSortBy().equalsIgnoreCase("consentstaus")) {

			if (ConsentLovModel.FIELD_CONSENT_NAME != null) {
				Join<TaskModel, ConsentAnnexModel> conAnnexIdJoin1 = rootModelType.join(TaskModel.FIELD_CONS);
				Join<ConsentAnnexModel, ConsentLovModel> cncntJoin1 = conAnnexIdJoin1
						.join(ConsentAnnexModel.FIELD_CONSENT);
				buildSortingOrder(builder, cncntJoin1, ConsentLovModel.FIELD_CONSENT_NAME, searchDTO.isSortDescending(),
						sortOrders);
				applySorting(builder, query, sortOrders);
			}
		}

		// sort initiated by name
		if (searchDTO.getSortBy() != null && searchDTO.getSortBy().equalsIgnoreCase("initiatedby")) {
			if (UserModelNew.FIELD_FIRSTNAME != null) {
				Join<TaskModel, UserModelNew> conAnnexIdJoin = rootModelType.join(TaskModel.FIELD_ASSIGNED_TO);
				buildSortingOrder(builder, conAnnexIdJoin, UserModelNew.FIELD_FIRSTNAME, searchDTO.isSortDescending(),
						sortOrders);
				applySorting(builder, query, sortOrders);
			}

		}
		/*
		 * 
		 * if (searchDTO.getSortBy() != null &&
		 * searchDTO.getSortBy().equalsIgnoreCase("initiatedby")){
		 * applySorting(builder, query, TaskModel.FIELD_CREATED_BY,
		 * searchDTO.isSortDescending(), rootModelType); }
		 */

		// sort event name
		if (searchDTO.getSortBy() != null && searchDTO.getSortBy().equalsIgnoreCase("eventname")) {

			if (ConsentAnnexModel.FIELD_EVENTNAME != null) {
				Join<TaskModel, ConsentAnnexModel> conAnnexIdJoin = rootModelType.join(TaskModel.FIELD_CONS);
				buildSortingOrder(builder, conAnnexIdJoin, ConsentAnnexModel.FIELD_EVENTNAME,
						searchDTO.isSortDescending(), sortOrders);
				applySorting(builder, query, sortOrders);
			}

		}

		// search task id from task page

		/*
		 * if(searchDTO.getTaskId() != null){ String
		 * taskID=searchDTO.getTaskId(); Expression<String> idkey=
		 * rootModelType.get(TaskModel.FIELD_TASK_ID).as(String.class);
		 * Predicate idPredicate = builder.like(idkey,taskID+"%");
		 * andPredicates.add(idPredicate); } //sort task id if
		 * (searchDTO.getSortBy() != null &&
		 * searchDTO.getSortBy().equalsIgnoreCase("taskid")){
		 * applySorting(builder, query, TaskModel.FIELD_TASK_ID,
		 * searchDTO.isSortDescending(), rootModelType); }
		 */
		// search trid from task page
		/*
		 * if(searchDTO.getTrId() != null){ String trid=searchDTO.getTrId();
		 * Path<TaskModel>
		 * pathTaskModel=rootModelType.get(TaskModel.FIELD_CONS);
		 * Subquery<TaskModel> subquryTask=query.subquery(TaskModel.class);
		 * Root<ConsentAnnexModel>
		 * rootConsentAnnex=subquryTask.from(ConsentAnnexModel.class);
		 * Path<ConsentAnnexModel>
		 * pathConsentAnnexModel=rootConsentAnnex.get(ConsentAnnexModel.
		 * FIELD_BPID); Subquery<ConsentAnnexModel>
		 * subqueryConsentAnnex=query.subquery(ConsentAnnexModel.class);
		 * Root<BusinessProfileModel>
		 * rootBusinessProfile=subqueryConsentAnnex.from(BusinessProfileModel.
		 * class); Expression<String> idkey=
		 * rootBusinessProfile.get(BusinessProfileModel.FIELD_BP_ID).as(String.
		 * class); Predicate predicateBpId = builder.like(idkey, trid+"%");
		 * subqueryConsentAnnex.where(predicateBpId); subqueryConsentAnnex =
		 * subqueryConsentAnnex.select(rootBusinessProfile.get(
		 * BusinessProfileModel.FIELD_BP_ID)); Predicate
		 * predicateBusinessID=builder.in(pathConsentAnnexModel).value(
		 * subqueryConsentAnnex); subquryTask.where(predicateBusinessID);
		 * subquryTask =
		 * subquryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
		 * Predicate predicate=builder.in(pathTaskModel).value(subquryTask);
		 * andPredicates.add(predicate); } //sort tr id if
		 * (searchDTO.getSortBy() != null &&
		 * searchDTO.getSortBy().equalsIgnoreCase("trid")){
		 * 
		 * applySorting(builder, query, BusinessProfileModel.FIELD_BP_ID,
		 * searchDTO.isSortDescending(), rootModelType); }
		 */

		Predicate andPredicate = null;
		if (andPredicates != null && !andPredicates.isEmpty()) {
			andPredicate = builder.and(buildPredicatesArray(andPredicates));
		}
		LOG.debug("Time took for search criteria execution - " + timer.getTime());
		return andPredicate;
	}

	/**
	 * Get task according assign id for global and local user from task table
	 * and according to search criteria provided by user
	 * 
	 * @param searchDTO
	 *            , countryId (TaskSearchDTO) - The Search Criteria provided by
	 *            the user. (BigDecimal) - Country ID
	 * @return the PagingSearchResultDTO<TaskModel>
	 * @throws Exception
	 *             If finding the recipient based on search criteria fails.
	 */

	public PagingSearchResultDTO<TaskModel> findUserSpecificTask(String assign, TaskSearchDTO searchDTO)
			throws Exception {
		LOG.debug("Inside method findUserSpecificTask");
		StopWatch timer = new StopWatch();
		timer.start();
		String pageNo = null;
		String pageSz = null;
		if (searchDTO.getPageNumber() != null) {
			pageNo = searchDTO.getPageNumber();
		} else {
			pageNo = "1";
		}
		if (searchDTO.getPageSize() != null) {
			pageSz = searchDTO.getPageSize();
		} else {
			pageSz = "6";
		}
		int pageNumber = Integer.parseInt(pageNo);
		int pageSize = Integer.parseInt(pageSz);
		int firstIndex = pageSize * (pageNumber - 1);
		Long count = null;
		PagingSearchResultDTO<TaskModel> pagingResult = new PagingSearchResultDTO<TaskModel>();
		List<TaskModel> models = new ArrayList<TaskModel>();
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TaskModel> query = builder.createQuery(getModelType());
		Root<TaskModel> root = query.from(getModelType());
		Predicate wherePredicate = buildUserSpecificSearchCriteriaPredicate(builder, query, root, searchDTO, assign);
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		query.where(wherePredicate);
		if (deleteFilter != null) {
			query.where(deleteFilter);
		}
		/*
		 * String sortByField = sortByField(); if (sortByField != null){
		 * applySorting(builder, query, sortByField, sortDescending(), root); }
		 */
		root.fetch(TaskModel.FIELD_CONS, JoinType.INNER);
		root.fetch(TaskModel.FIELD_ASSIGNED_TO, JoinType.INNER);
		CriteriaQuery<TaskModel> select = query.select(root);
		TypedQuery<TaskModel> typedQuery = entityManager.createQuery(select);
		typedQuery.setFirstResult(firstIndex);
		typedQuery.setMaxResults(pageSize);
		models = typedQuery.getResultList();
		CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
		Root<TaskModel> countRoot = countQuery.from(query.getResultType());
		countQuery.where(wherePredicate);
		// doJoins(root.getJoins(), countRoot);
		// doJoinsOnFetches(root.getFetches(), countRoot);
		countQuery.select(builder.countDistinct(countRoot));
		// countQuery.distinct(true);
		TypedQuery<Long> typedCountQuery = entityManager.createQuery(countQuery);
		count = typedCountQuery.getSingleResult();

		LOG.debug(" Total Count" + count);
		Collection<TaskModel> result = new LinkedHashSet<TaskModel>(models);
		List<TaskModel> resultList = new ArrayList<TaskModel>();
		resultList.addAll(result);
		pagingResult.setTotalRecordsCount(count);
		pagingResult.setCurrentPageData(resultList);
		LOG.debug("Time took for User Specific task data - " + timer.getTime());
		return pagingResult;
	}

	/**
	 * Builds the search criteria predicate for user specific task.
	 * 
	 * @param builder
	 *            (CriteriaBuilder) - The builder for type TaskModel
	 *            representation
	 * @param query
	 *            (CriteriaQuery<TaskModel>) - The query of type TaskModel
	 *            representation
	 * @param rootModelType
	 *            (Root<TaskModel>) - The root of type TaskModel representation
	 * @param searchDTO
	 *            (TaskSearchDTO) - The representation of type TaskModel
	 *            representation
	 * @param sortOrders
	 *            (List<Order>) - The representation sorting order type of
	 *            TaskModel representation
	 */

	public Predicate buildUserSpecificSearchCriteriaPredicate(CriteriaBuilder builder, CriteriaQuery<TaskModel> query,
			Root<TaskModel> rootModelType, TaskSearchDTO searchDTO, String assign) {
		List<Predicate> andPredicates = new ArrayList<Predicate>();

		// search assigned user specific task
		/*
		 * if(assign !=null){ Predicate assignPredicate =
		 * builder.equal(builder.lower(rootModelType.get(TaskModel.
		 * FIELD_ASSIGNED_TO)), assign.toLowerCase());
		 * andPredicates.add(assignPredicate); }
		 */
		if (assign != null) {
			Path<TaskModel> pathTaskModel = rootModelType.get(TaskModel.FIELD_ASSIGNED_TO);
			Subquery<TaskModel> subqueryTask = query.subquery(TaskModel.class);
			Root<UserModelNew> rootUserModel = subqueryTask.from(UserModelNew.class);
			Predicate eventpredicate = builder.equal(builder.lower(rootUserModel.get(UserModelNew.FIELD_USERNAME)),
					assign.toLowerCase());
			subqueryTask.where(eventpredicate);
			subqueryTask = subqueryTask.select(rootUserModel.get(UserModelNew.FIELD_USERNAME));
			Predicate predicate = builder.in(pathTaskModel).value(subqueryTask);
			andPredicates.add(predicate);
		}

		// search user provided field
		Predicate otherPredicate = buildSearchCriteriaPredicate(builder, query, rootModelType, searchDTO);
		andPredicates.add(otherPredicate);

		Predicate andPredicate = null;
		if (andPredicates != null && !andPredicates.isEmpty()) {
			andPredicate = builder.and(buildPredicatesArray(andPredicates));
		}
		return andPredicate;
	}

	/**
	 * Get task according country for dataStewatrd and EFPIA lead from task
	 * table and according to search criteria provided by user
	 * 
	 * @param searchDTO
	 *            , countryId (TaskSearchDTO) - The Search Criteria provided by
	 *            the user. (BigDecimal) - Country ID
	 * @return the PagingSearchResultDTO<TaskModel>
	 * @throws Exception
	 *             If finding the recipient based on search criteria fails.
	 */
	public PagingSearchResultDTO<TaskModel> findCountryTask(BigDecimal countryId, TaskSearchDTO searchDTO)
			throws Exception {
		LOG.debug("Inside method findCountry Specific task");
		StopWatch timer = new StopWatch();
		timer.start();
		String pageNo = null;
		String pageSz = null;
		if (searchDTO.getPageNumber() != null) {
			pageNo = searchDTO.getPageNumber();
		} else {
			pageNo = "1";
		}
		if (searchDTO.getPageSize() != null) {
			pageSz = searchDTO.getPageSize();
		} else {
			pageSz = "6";
		}
		int pageNumber = Integer.parseInt(pageNo);
		int pageSize = Integer.parseInt(pageSz);
		int firstIndex = pageSize * (pageNumber - 1);
		Long count = null;
		PagingSearchResultDTO<TaskModel> pagingResult = new PagingSearchResultDTO<TaskModel>();
		List<TaskModel> models = new ArrayList<TaskModel>();
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TaskModel> query = builder.createQuery(getModelType());
		Root<TaskModel> root = query.from(getModelType());
		Predicate wherePredicate = buildCountrySearchCriteriaPredicate(builder, query, root, searchDTO, countryId);
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		query.where(wherePredicate);
		if (deleteFilter != null) {
			query.where(deleteFilter);
		}
		/*
		 * String sortByField = sortByField(); if (sortByField != null){
		 * applySorting(builder, query, sortByField, sortDescending(), root); }
		 */
		root.fetch(TaskModel.FIELD_CONS, JoinType.INNER);
		root.fetch(TaskModel.FIELD_ASSIGNED_TO, JoinType.INNER);
		CriteriaQuery<TaskModel> select = query.select(root);
		TypedQuery<TaskModel> typedQuery = entityManager.createQuery(select);
		typedQuery.setFirstResult(firstIndex);
		typedQuery.setMaxResults(pageSize);
		models = typedQuery.getResultList();
		CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
		Root<TaskModel> countRoot = countQuery.from(query.getResultType());
		countQuery.where(wherePredicate);
		// doJoins(root.getJoins(), countRoot);
		// doJoinsOnFetches(root.getFetches(), countRoot);
		countQuery.select(builder.countDistinct(countRoot));
		// countQuery.distinct(true);
		TypedQuery<Long> typedCountQuery = entityManager.createQuery(countQuery);
		count = typedCountQuery.getSingleResult();

		LOG.debug(" Total Count" + count);
		Collection<TaskModel> result = new LinkedHashSet<TaskModel>(models);
		List<TaskModel> resultList = new ArrayList<TaskModel>();
		resultList.addAll(result);
		pagingResult.setTotalRecordsCount(count);
		pagingResult.setCurrentPageData(resultList);
		LOG.debug("Time took for country specific task data - " + timer.getTime());
		return pagingResult;
	}

	/**
	 * Builds the search criteria predicate for country specific task.
	 * 
	 * @param builder
	 *            (CriteriaBuilder) - The builder for type TaskModel
	 *            representation
	 * @param query
	 *            (CriteriaQuery<TaskModel>) - The query of type TaskModel
	 *            representation
	 * @param rootModelType
	 *            (Root<TaskModel>) - The root of type TaskModel representation
	 * @param searchDTO
	 *            (TaskSearchDTO) - The representation of type TaskModel
	 *            representation
	 * @param sortOrders
	 *            (List<Order>) - The representation sorting order type of
	 *            TaskModel representation
	 */

	public Predicate buildCountrySearchCriteriaPredicate(CriteriaBuilder builder, CriteriaQuery<TaskModel> query,
			Root<TaskModel> rootModelType, TaskSearchDTO searchDTO, BigDecimal countryId) {
		List<Predicate> andPredicates = new ArrayList<Predicate>();

		// search country specific task
		if (countryId != null) {
			Path<TaskModel> path = rootModelType.get(TaskModel.FIELD_CONS);
			Subquery<TaskModel> sq = query.subquery(TaskModel.class);
			Root<ConsentAnnexModel> rt = sq.from(ConsentAnnexModel.class);

			Predicate predicate = builder.equal(rt.get(ConsentAnnexModel.FIELD_PROFILECOUNTRY), countryId);
			//R2.0 arunkv - temprofile [dont show tempprofile task]
			Predicate predicateTempProfile = builder.equal(rt.get(ConsentAnnexModel.FIELD_TEMP_PROFILE), "N");
			Predicate newPredicate = builder.and(predicate,predicateTempProfile);
			sq.where(newPredicate);
			sq = sq.select(rt.get(ConsentAnnexModel.FIELD_ID));
			LOG.debug("Icando" + sq);

			Predicate countrypredicate1 = builder.in(path).value(sq);
			andPredicates.add(countrypredicate1);
		}
		// search user provided field
		Predicate otherPredicate = buildSearchCriteriaPredicate(builder, query, rootModelType, searchDTO);
		andPredicates.add(otherPredicate);

		Predicate andPredicate = null;
		if (andPredicates != null && !andPredicates.isEmpty()) {
			andPredicate = builder.and(buildPredicatesArray(andPredicates));
		}
		return andPredicate;
	}

	/**
	 * Soft deletes the quick link model.
	 * 
	 * @param item
	 *            (AnnouncementsModel) - The item representation
	 * @throws Exception
	 *             If delete is unsuccessful
	 */

	public void delete(TaskModel item) throws Exception {
		TaskModel model = find(item.getId());
		if (model != null) {
			// model.setDeleted('Y');
			update(model);
		}
	}

	/**
	 * Informs whether sort by field need to be applied on which column. If no
	 * sorting needed then returns null otherwise overrides the particular
	 * column.
	 * 
	 * @return the sort by field value
	 */
	public String sortByField() {

		return TaskModel.FIELD_UPDATED_DATE;
		// return null;

	}

	/**
	 * @return If true return decending order
	 */
	public Boolean sortDescending() {
		return true;
	}

	/**
	 * Configures the sort property.
	 * 
	 * @param builder
	 *            (CriteriaBuilder) - The builder criteria of type TaskModel
	 *            representation
	 * @param join
	 *            (Join<?, ?>) - The join criteria of type TaskModel
	 *            representation
	 * @param sortProperty
	 *            (String) - The sort property criteria of type TaskModel
	 *            representation
	 * @param sortDescending
	 *            (Boolean) - The sorting order criteria of type TaskModel
	 *            representation
	 * @param sortOrders
	 *            (List<Order>) - The sorting order criteria of type TaskModel
	 *            representation
	 */

	private void buildSortingOrder(CriteriaBuilder builder, Join<?, ?> join, String sortProperty,
			Boolean sortDescending, List<Order> sortOrders) {
		Expression<String> sortPropertyExp = join.<String> get(sortProperty);
		if (sortDescending) {
			sortOrders.add(builder.desc(builder.upper(sortPropertyExp)));
		} else {
			sortOrders.add(builder.asc(builder.upper(sortPropertyExp)));
		}
	}

	/**
	 * Applies sorting for all the orders.
	 * 
	 * @param builder
	 *            (CriteriaBuilder) - The builder criteria of type TaskModel
	 *            representation
	 * @param query
	 *            (CriteriaQuery<TaskModel>) - The query criteria of type
	 *            TaskModel representation
	 * @param sortOrders
	 *            (List<Order>) - The order list criteria of type TaskModel
	 *            representation
	 */
	private void applySorting(CriteriaBuilder builder, CriteriaQuery<TaskModel> query, List<Order> sortOrders) {
		if (sortOrders != null && !sortOrders.isEmpty()) {
			query.orderBy(sortOrders);
		}
	}

	/**
	 * Returns a flag indicating it supports soft delete.
	 * 
	 * @return the soft delete enabled Flag
	 */

	public boolean isSoftDeleteEnabled() {
		return true;
	}

	@Override
	public Predicate prepareSoftDeletePredicate(CriteriaBuilder builder, Root<TaskModel> rootModelType) {
		return null;
	}
	
	/**
	 * Get task for reminders
	 * @param searchDTO , countryId
	 * 		(TaskSearchDTO) - The Search Criteria provided by the user.
	 * 		 (BigDecimal) - Country ID
	 * @return the PagingSearchResultDTO<TaskModel>
	 * @throws Exception
	 * 		If finding the recipient based on search criteria fails.
	 */
	public PagingSearchResultDTO<TaskModel> findTaskForReminder(String daysOffset) throws Exception{
		LOG.debug("Inside method findTaskForReminder");
		StopWatch timer = new StopWatch();
        timer.start();

        Long count = null;
        PagingSearchResultDTO<TaskModel> pagingResult = new PagingSearchResultDTO<TaskModel>();
        List<TaskModel> models = new ArrayList<TaskModel>();	        
		EntityManager entityManager = getEntityManager();			
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();				
		CriteriaQuery<TaskModel> query = builder.createQuery(getModelType());
		Root<TaskModel> root = query.from(getModelType());
		
		Predicate wherePredicate = buildReminderSearchCriteriaPredicate(builder,
				query, root,daysOffset);
		
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		query.where(wherePredicate);
		if (deleteFilter != null) {
			query.where(deleteFilter);
		}
		
		root.fetch(TaskModel.FIELD_CONS,JoinType.INNER);
		root.fetch(TaskModel.FIELD_ASSIGNED_TO,JoinType.INNER);
		CriteriaQuery<TaskModel> select = query.select(root);
		TypedQuery<TaskModel> typedQuery = entityManager.createQuery(select);

		models = typedQuery.getResultList();							        

		Collection<TaskModel> result = new LinkedHashSet<TaskModel>(models);
	    List<TaskModel> resultList = new ArrayList<TaskModel>();
	    resultList.addAll(result);
		pagingResult.setTotalRecordsCount(count);
		pagingResult.setCurrentPageData(resultList);			
		LOG.debug("Time took for reminder task data - " + timer.getTime());
		return pagingResult;
	}
	/**
	 * Builds the search criteria predicate for country specific task.
	 * @param builder
	 * 		(CriteriaBuilder) - The builder for type TaskModel representation
	 * @param query
	 * 		(CriteriaQuery<TaskModel>) - The query of type TaskModel representation
	 * @param rootModelType
	 * 		(Root<TaskModel>) - The root of type TaskModel representation
	 */
	
	public Predicate buildReminderSearchCriteriaPredicate(CriteriaBuilder builder,
			CriteriaQuery<TaskModel> query, Root<TaskModel> rootModelType, String daysOffset) {
		
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		
		Predicate taskstatusPredicate =  builder.equal(rootModelType.get(TaskModel.FIELD_TASKSTATUS), "INCOMPLETE");					 
		andPredicates.add(taskstatusPredicate);
	    
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -Integer.parseInt(daysOffset));
		
		Predicate createdDatePredicate =  builder.lessThan(rootModelType.get(TaskModel.FIELD_CREATED_DATE), cal.getTime());					 
		andPredicates.add(createdDatePredicate);
		
		Predicate andPredicate = null;
		if (andPredicates != null && !andPredicates.isEmpty()) {
			andPredicate = builder.and(buildPredicatesArray(andPredicates));
		}
		return andPredicate;
	}

}
