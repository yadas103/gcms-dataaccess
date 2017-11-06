package com.pfizer.gcms.dataaccess.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
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
	 * Get All task according from task table according to search criteria
	 * @param searchDTO
	 * 		(TaskSearchDTO) - The Search Criteria provided by the user.
	 * @return the PagingSearchResultDTO<TaskModel>
	 * @throws Exception
	 * 		If finding the recipient based on search criteria fails.
	 */
	  public PagingSearchResultDTO<TaskModel> find(TaskSearchDTO searchDTO) throws Exception {
		LOG.debug("Inside method find");
		StopWatch timer = new StopWatch();
        timer.start();
        String pageNo=null;
        String pageSz=null;
        if(searchDTO.getPageNumber()!=null){
        	pageNo=searchDTO.getPageNumber();
        }else{
        	pageNo="1";
        }
        if(searchDTO.getPageSize()!=null){
        	pageSz=searchDTO.getPageSize();
        }else{
        	pageSz="6";	
        }
        int pageNumber=Integer.parseInt(pageNo);
        int pageSize=Integer.parseInt(pageSz);
        int firstIndex=pageSize*(pageNumber - 1);
        Long count = null;
        PagingSearchResultDTO<TaskModel> pagingResult = new PagingSearchResultDTO<TaskModel>();
        List<TaskModel> models = new ArrayList<TaskModel>();	        
		EntityManager entityManager = getEntityManager();			
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();				
		CriteriaQuery<TaskModel> query = builder.createQuery(getModelType());
		Root<TaskModel> root = query.from(getModelType());
		Predicate wherePredicate = buildSearchCriteriaPredicate(builder,
				query, root,searchDTO );
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		query.where(wherePredicate);
		if (deleteFilter != null) {
			query.where(deleteFilter);
		}
		String sortByField = sortByField();
		if (sortByField != null){
			applySorting(builder, query, sortByField, sortDescending(), root);
		}
		CriteriaQuery<TaskModel> select = query.select(root);
		TypedQuery<TaskModel> typedQuery = entityManager.createQuery(select);
		typedQuery.setFirstResult(firstIndex);
		typedQuery.setMaxResults(pageSize);					
		models = typedQuery.getResultList();							        
		CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
		Root<TaskModel> countRoot = countQuery.from(query.getResultType());
		countQuery.where(wherePredicate);
		doJoins(root.getJoins(), countRoot);
		doJoinsOnFetches(root.getFetches(), countRoot);
		countQuery.select(builder.countDistinct(countRoot));			
		//countQuery.distinct(true);
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
		 * @param builder
		 * 		(CriteriaBuilder) - The builder for type TaskModel representation
		 * @param query
		 * 		(CriteriaQuery<TaskModel>) - The query of type TaskModel representation
		 * @param rootModelType
		 * 		(Root<TaskModel>) - The root of type TaskModel representation
		 * @param searchDTO
		 * 		(TaskSearchDTO) - The representation of type TaskModel representation
		 * @param sortOrders
		 * 		(List<Order>) - The representation sorting order type of TaskModel representation
		 */
	
	  public Predicate buildSearchCriteriaPredicate(CriteriaBuilder builder,
				CriteriaQuery<TaskModel> query, Root<TaskModel> rootModelType,
				TaskSearchDTO searchDTO) {
		  	LOG.debug("Inside method buildSearchCriteria");
			StopWatch timer = new StopWatch();
	        timer.start();
			List<Predicate> andPredicates = new ArrayList<Predicate>();
			
			//search task status  from task page				
			if(searchDTO.getTaskStatus() != null){					
				Predicate taskstatusPredicate =  builder.equal(rootModelType.get(TaskModel.FIELD_TASKSTATUS), searchDTO.getTaskStatus());					 
				andPredicates.add(taskstatusPredicate);
			}else{
				Predicate taskstatusPredicate =  builder.equal(rootModelType.get(TaskModel.FIELD_TASKSTATUS), "INCOMPLETE");					 
				andPredicates.add(taskstatusPredicate);
				
			}
			//search task id  from task page
			
			if(searchDTO.getTaskId() != null){
				String taskID=searchDTO.getTaskId();					
				Expression<String> idkey= rootModelType.get(TaskModel.FIELD_TASK_ID).as(String.class);
				Predicate idPredicate =  builder.like(idkey,taskID+"%");					 
				andPredicates.add(idPredicate);
			}
			//search trid  from task page
			if(searchDTO.getTrId() != null){
				String trid=searchDTO.getTrId();
				Path<TaskModel> pathTaskModel=rootModelType.get(TaskModel.FIELD_CONS);
				Subquery<TaskModel> subquryTask=query.subquery(TaskModel.class);
				Root<ConsentAnnexModel> rootConsentAnnex=subquryTask.from(ConsentAnnexModel.class);					
				Path<ConsentAnnexModel> pathConsentAnnexModel=rootConsentAnnex.get(ConsentAnnexModel.FIELD_BPID);
				Subquery<ConsentAnnexModel> subqueryConsentAnnex=query.subquery(ConsentAnnexModel.class);
				Root<BusinessProfileModel> rootBusinessProfile=subqueryConsentAnnex.from(BusinessProfileModel.class);
				Expression<String> idkey= rootBusinessProfile.get(BusinessProfileModel.FIELD_BP_ID).as(String.class);
				Predicate predicateBpId = builder.like(idkey, trid+"%");	
				subqueryConsentAnnex.where(predicateBpId);
				subqueryConsentAnnex = subqueryConsentAnnex.select(rootBusinessProfile.get(BusinessProfileModel.FIELD_BP_ID));					
				Predicate predicateBusinessID=builder.in(pathConsentAnnexModel).value(subqueryConsentAnnex);					
				subquryTask.where(predicateBusinessID);
				subquryTask = subquryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
				Predicate predicate=builder.in(pathTaskModel).value(subquryTask);
				andPredicates.add(predicate);
			}
			//search last initiated by name  from task page
			if(searchDTO.getLastName() != null){						
				String lastname=searchDTO.getLastName();
				Path<TaskModel> pathTaskModel=rootModelType.get(TaskModel.FIELD_CONS);
				Subquery<TaskModel> subquryTask=query.subquery(TaskModel.class);
				Root<ConsentAnnexModel> rootConsentAnnex=subquryTask.from(ConsentAnnexModel.class);					
				Path<ConsentAnnexModel> pathConsentAnnexModel=rootConsentAnnex.get(ConsentAnnexModel.FIELD_BPID);
				Subquery<ConsentAnnexModel> subqueryConsentAnnex=query.subquery(ConsentAnnexModel.class);
				Root<BusinessProfileModel> rootBusinessProfile=subqueryConsentAnnex.from(BusinessProfileModel.class);
				Predicate predicateLastName = builder.like(builder.lower(rootBusinessProfile.get(BusinessProfileModel.FIELD_LAST_NAME)), lastname.toLowerCase()+"%");	
				subqueryConsentAnnex.where(predicateLastName);
				subqueryConsentAnnex = subqueryConsentAnnex.select(rootBusinessProfile.get(BusinessProfileModel.FIELD_BP_ID));					
				Predicate predicateBusinessID=builder.in(pathConsentAnnexModel).value(subqueryConsentAnnex);					
				subquryTask.where(predicateBusinessID);
				subquryTask = subquryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
				Predicate predicate=builder.in(pathTaskModel).value(subquryTask);
				andPredicates.add(predicate);
			}
			//search first name  from task page
			if(searchDTO.getFirstName() != null){						
				String firstname=searchDTO.getFirstName();
				Path<TaskModel> pathTaskModel=rootModelType.get(TaskModel.FIELD_CONS);
				Subquery<TaskModel> subquryTask=query.subquery(TaskModel.class);
				Root<ConsentAnnexModel> rootConsentAnnex=subquryTask.from(ConsentAnnexModel.class);					
				Path<ConsentAnnexModel> pathConsentAnnexModel=rootConsentAnnex.get(ConsentAnnexModel.FIELD_BPID);
				Subquery<ConsentAnnexModel> subqueryConsentAnnex=query.subquery(ConsentAnnexModel.class);
				Root<BusinessProfileModel> rootBusinessProfile=subqueryConsentAnnex.from(BusinessProfileModel.class);
				Predicate predicateFirstName = builder.like(builder.lower(rootBusinessProfile.get(BusinessProfileModel.FIELD_FIRST_NAME)), firstname.toLowerCase()+"%");	
				subqueryConsentAnnex.where(predicateFirstName);
				subqueryConsentAnnex = subqueryConsentAnnex.select(rootBusinessProfile.get(BusinessProfileModel.FIELD_BP_ID));					
				Predicate predicateBusinessID=builder.in(pathConsentAnnexModel).value(subqueryConsentAnnex);					
				subquryTask.where(predicateBusinessID);
				subquryTask = subquryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
				Predicate predicate=builder.in(pathTaskModel).value(subquryTask);
				andPredicates.add(predicate);
			}
			//search country name from task page
			if(searchDTO.getCountry() != null){						
				String country=searchDTO.getCountry();
				Path<TaskModel> pathTaskModel=rootModelType.get(TaskModel.FIELD_CONS);
				Subquery<TaskModel> subquryTask=query.subquery(TaskModel.class);
				Root<ConsentAnnexModel> rootConsentAnnex=subquryTask.from(ConsentAnnexModel.class);					
				Path<ConsentAnnexModel> pathConsentAnnexModel=rootConsentAnnex.get(ConsentAnnexModel.FIELD_PROFILECOUNTRY);
				Subquery<ConsentAnnexModel> subqueryConsentAnnex=query.subquery(ConsentAnnexModel.class);
				Root<CountryModel> rootCountry=subqueryConsentAnnex.from(CountryModel.class);
				Predicate predicateCountryName = builder.like(builder.lower(rootCountry.get(CountryModel.FIELD_COUNTRY_NAME)), country.toLowerCase()+"%");	
				subqueryConsentAnnex.where(predicateCountryName);
				subqueryConsentAnnex = subqueryConsentAnnex.select(rootCountry.get(CountryModel.FIELD_COUNTRY_ID));					
				Predicate predicateConsentID=builder.in(pathConsentAnnexModel).value(subqueryConsentAnnex);					
				subquryTask.where(predicateConsentID);
				subquryTask = subquryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
				Predicate predicate=builder.in(pathTaskModel).value(subquryTask);
				andPredicates.add(predicate);
			}
			//search consent status name  from task page
			if(searchDTO.getConsentStatus() != null){						
				String consentstatus=searchDTO.getConsentStatus();
				Path<TaskModel> pathTaskModel=rootModelType.get(TaskModel.FIELD_CONS);
				Subquery<TaskModel> subquryTask=query.subquery(TaskModel.class);
				Root<ConsentAnnexModel> rootConsentAnnex=subquryTask.from(ConsentAnnexModel.class);					
				Path<ConsentAnnexModel> pathConsentAnnexModel=rootConsentAnnex.get(ConsentAnnexModel.FIELD_CONSENT);
				Subquery<ConsentAnnexModel> subqueryConsentAnnex=query.subquery(ConsentAnnexModel.class);
				Root<ConsentLovModel> rootConsentLov=subqueryConsentAnnex.from(ConsentLovModel.class);
				Predicate predicateConsentName = builder.like(builder.lower(rootConsentLov.get(ConsentLovModel.FIELD_CONSENT_NAME)), consentstatus.toLowerCase()+"%");	
				subqueryConsentAnnex.where(predicateConsentName);
				subqueryConsentAnnex = subqueryConsentAnnex.select(rootConsentLov.get(ConsentLovModel.FIELD_CONSENT_STS_ID));					
				Predicate predicateConsentID=builder.in(pathConsentAnnexModel).value(subqueryConsentAnnex);					
				subquryTask.where(predicateConsentID);
				subquryTask = subquryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
				Predicate predicate=builder.in(pathTaskModel).value(subquryTask);
				andPredicates.add(predicate);
			}
			//search initiated by name  from task page
			if(searchDTO.getInitiatedBy() != null){
				String initiatedBy=searchDTO.getInitiatedBy();
				Predicate initiatedPredicate =  builder.like(builder.lower(rootModelType.get(TaskModel.FIELD_CREATED_BY)), initiatedBy.toLowerCase()+"%");					 
				andPredicates.add(initiatedPredicate);
			}
			//search event name from task page
			if(searchDTO.getEventName() !=null){
				String eventname=searchDTO.getEventName();
				Path<TaskModel> pathTaskModel=rootModelType.get(TaskModel.FIELD_CONS);
				Subquery<TaskModel> subqueryTask=query.subquery(TaskModel.class);
				Root<ConsentAnnexModel> rootConsentAnnex=subqueryTask.from(ConsentAnnexModel.class);					
				Predicate eventpredicate = builder.like(builder.lower(rootConsentAnnex.get(ConsentAnnexModel.FIELD_EVENTNAME)), eventname.toLowerCase()+"%");					
				subqueryTask.where(eventpredicate);
				subqueryTask = subqueryTask.select(rootConsentAnnex.get(ConsentAnnexModel.FIELD_ID));
				Predicate predicate=builder.in(pathTaskModel).value(subqueryTask);
				andPredicates.add(predicate);
				}
			
			Predicate andPredicate = null;
			if (andPredicates != null && !andPredicates.isEmpty()) {
				andPredicate = builder.and(buildPredicatesArray(andPredicates));
			}
			LOG.debug("Time took for search criteria execution - " + timer.getTime());
			return andPredicate;
	  }
		
	  /**
		 * Get task according assign id for global and local user from task table and according to search criteria provided by user
		 * @param searchDTO , countryId
		 * 		(TaskSearchDTO) - The Search Criteria provided by the user.
		 * 		 (BigDecimal) - Country ID
		 * @return the PagingSearchResultDTO<TaskModel>
		 * @throws Exception
		 * 		If finding the recipient based on search criteria fails.
		 */
	  
	  public PagingSearchResultDTO<TaskModel> findUserSpecificTask(String assign,TaskSearchDTO searchDTO) throws Exception {
			LOG.debug("Inside method findUserSpecificTask");
			StopWatch timer = new StopWatch();
	        timer.start();
	        String pageNo=searchDTO.getPageNumber();
	        String pageSz=searchDTO.getPageSize();
	        int pageNumber=Integer.parseInt(pageNo);
	        int pageSize=Integer.parseInt(pageSz);
	        int firstIndex=pageSize*(pageNumber - 1);
	        Long count = null;
	        PagingSearchResultDTO<TaskModel> pagingResult = new PagingSearchResultDTO<TaskModel>();
	        List<TaskModel> models = new ArrayList<TaskModel>();	        
			EntityManager entityManager = getEntityManager();			
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();				
			CriteriaQuery<TaskModel> query = builder.createQuery(getModelType());
			Root<TaskModel> root = query.from(getModelType());
			Predicate wherePredicate = buildUserSpecificSearchCriteriaPredicate(builder,
					query, root,searchDTO,assign );
			Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
			query.where(wherePredicate);
			if (deleteFilter != null) {
				query.where(deleteFilter);
			}
			String sortByField = sortByField();
			if (sortByField != null){
				applySorting(builder, query, sortByField, sortDescending(), root);
			}
			CriteriaQuery<TaskModel> select = query.select(root);
			TypedQuery<TaskModel> typedQuery = entityManager.createQuery(select);
			typedQuery.setFirstResult(firstIndex);
			typedQuery.setMaxResults(pageSize);					
			models = typedQuery.getResultList();							        
			CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
			Root<TaskModel> countRoot = countQuery.from(query.getResultType());
			countQuery.where(wherePredicate);
			doJoins(root.getJoins(), countRoot);
			doJoinsOnFetches(root.getFetches(), countRoot);
			countQuery.select(builder.countDistinct(countRoot));			
			//countQuery.distinct(true);
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
		 * @param builder
		 * 		(CriteriaBuilder) - The builder for type TaskModel representation
		 * @param query
		 * 		(CriteriaQuery<TaskModel>) - The query of type TaskModel representation
		 * @param rootModelType
		 * 		(Root<TaskModel>) - The root of type TaskModel representation
		 * @param searchDTO
		 * 		(TaskSearchDTO) - The representation of type TaskModel representation
		 * @param sortOrders
		 * 		(List<Order>) - The representation sorting order type of TaskModel representation
		 */
		
		public Predicate buildUserSpecificSearchCriteriaPredicate(CriteriaBuilder builder,
				CriteriaQuery<TaskModel> query, Root<TaskModel> rootModelType,TaskSearchDTO searchDTO,String assign) {
			List<Predicate> andPredicates = new ArrayList<Predicate>();
			
			//search assigned user specific task 
			if(assign !=null){							
				Predicate assignPredicate = builder.equal(builder.lower(rootModelType.get(TaskModel.FIELD_ASSIGNED_TO)), assign.toLowerCase());
				andPredicates.add(assignPredicate);
			}
			
			//search user provided field
			Predicate otherPredicate= buildSearchCriteriaPredicate(builder,
					query, rootModelType,searchDTO);
			andPredicates.add(otherPredicate);
		    
			
			Predicate andPredicate = null;
			if (andPredicates != null && !andPredicates.isEmpty()) {
				andPredicate = builder.and(buildPredicatesArray(andPredicates));
			}
			return andPredicate;
		}
		/**
		 * Get task according country for dataStewatrd and EFPIA lead from task table and according to search criteria provided by user
		 * @param searchDTO , countryId
		 * 		(TaskSearchDTO) - The Search Criteria provided by the user.
		 * 		 (BigDecimal) - Country ID
		 * @return the PagingSearchResultDTO<TaskModel>
		 * @throws Exception
		 * 		If finding the recipient based on search criteria fails.
		 */
		public PagingSearchResultDTO<TaskModel> findCountryTask(BigDecimal countryId,TaskSearchDTO searchDTO) throws Exception{
			LOG.debug("Inside method findCountry Specific task");
			StopWatch timer = new StopWatch();
	        timer.start();
	        String pageNo=searchDTO.getPageNumber();
	        String pageSz=searchDTO.getPageSize();
	        int pageNumber=Integer.parseInt(pageNo);
	        int pageSize=Integer.parseInt(pageSz);
	        int firstIndex=pageSize*(pageNumber - 1);
	        Long count = null;
	        PagingSearchResultDTO<TaskModel> pagingResult = new PagingSearchResultDTO<TaskModel>();
	        List<TaskModel> models = new ArrayList<TaskModel>();	        
			EntityManager entityManager = getEntityManager();			
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();				
			CriteriaQuery<TaskModel> query = builder.createQuery(getModelType());
			Root<TaskModel> root = query.from(getModelType());
			Predicate wherePredicate = buildCountrySearchCriteriaPredicate(builder,
					query, root,searchDTO,countryId );
			Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
			query.where(wherePredicate);
			if (deleteFilter != null) {
				query.where(deleteFilter);
			}
			String sortByField = sortByField();
			if (sortByField != null){
				applySorting(builder, query, sortByField, sortDescending(), root);
			}
			CriteriaQuery<TaskModel> select = query.select(root);
			TypedQuery<TaskModel> typedQuery = entityManager.createQuery(select);
			typedQuery.setFirstResult(firstIndex);
			typedQuery.setMaxResults(pageSize);					
			models = typedQuery.getResultList();							        
			CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
			Root<TaskModel> countRoot = countQuery.from(query.getResultType());
			countQuery.where(wherePredicate);
			doJoins(root.getJoins(), countRoot);
			doJoinsOnFetches(root.getFetches(), countRoot);
			countQuery.select(builder.countDistinct(countRoot));			
			//countQuery.distinct(true);
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
		 * @param builder
		 * 		(CriteriaBuilder) - The builder for type TaskModel representation
		 * @param query
		 * 		(CriteriaQuery<TaskModel>) - The query of type TaskModel representation
		 * @param rootModelType
		 * 		(Root<TaskModel>) - The root of type TaskModel representation
		 * @param searchDTO
		 * 		(TaskSearchDTO) - The representation of type TaskModel representation
		 * @param sortOrders
		 * 		(List<Order>) - The representation sorting order type of TaskModel representation
		 */
		
		public Predicate buildCountrySearchCriteriaPredicate(CriteriaBuilder builder,
				CriteriaQuery<TaskModel> query, Root<TaskModel> rootModelType,TaskSearchDTO searchDTO,BigDecimal countryId) {
			List<Predicate> andPredicates = new ArrayList<Predicate>();
			
			//search country specific task 
			if(countryId !=null){			
			Path<TaskModel> path=rootModelType.get(TaskModel.FIELD_CONS);
			Subquery<TaskModel> sq=query.subquery(TaskModel.class);
			Root<ConsentAnnexModel> rt=sq.from(ConsentAnnexModel.class);
			
			Predicate predicate = builder.equal(rt.get(ConsentAnnexModel.FIELD_PROFILECOUNTRY), countryId);	
			sq.where(predicate);
			sq = sq.select(rt.get(ConsentAnnexModel.FIELD_ID));
			LOG.debug("Icando"+sq);
			
			Predicate countrypredicate1=builder.in(path).value(sq);
			andPredicates.add(countrypredicate1);
			}
			//search user provided field
			Predicate otherPredicate= buildSearchCriteriaPredicate(builder,
					query, rootModelType,searchDTO);
			andPredicates.add(otherPredicate);
		    
			
			Predicate andPredicate = null;
			if (andPredicates != null && !andPredicates.isEmpty()) {
				andPredicate = builder.and(buildPredicatesArray(andPredicates));
			}
			return andPredicate;
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
