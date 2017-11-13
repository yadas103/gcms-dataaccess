/**
 * 
 */
package com.pfizer.gcms.dataaccess.repository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pfizer.gcms.dataaccess.common.NumericUtil;
import com.pfizer.gcms.dataaccess.common.exception.GCMSBadDataException;
import com.pfizer.gcms.dataaccess.common.exception.GCMSInternalServerError;
import com.pfizer.gcms.dataaccess.dto.ISearchDTO;
import com.pfizer.gcms.dataaccess.model.AbstractModel;
import com.pfizer.gcms.dataaccess.model.BaseModel;
import com.pfizer.gcms.dataaccess.model.BusinessProfileModel;
import com.pfizer.gcms.dataaccess.model.ConsentTemplateModel;
import com.pfizer.gcms.dataaccess.model.error.ErrorModel;




/**
 * The Abstract Repository will provide methods for accessing, creating and updating the Models
 * stored in the database. All other Repositories will inherit from it.
 * @param <ModelType> the model type used by repository implementation
 */
public abstract class AbstractRepository<ModelType extends BaseModel> implements IRepository<ModelType>{

	private static final Log LOG = LogFactory.getLog(AbstractRepository.class);
	
	private Class<ModelType> modelType;
	private Locale country;
	private EntityManager entityManager;
	
	/**
	 * Default constructor.
	 */
	public AbstractRepository() { }
	
	/**
	 * Constructor to set locale.
	 * @param country
	 * 			(Locale) - To enable country Locale for the specific country.
	 */
	public AbstractRepository(Locale country) {
		if (country == null) {
			throw new IllegalArgumentException("CountryModel value should not be null");
		}
		setCountry(country);
	}
	
	/**
	 * Configures the sort property.
	 * @param builder
	 * 		(CriteriaBuilder) - The CriteriaBuilder
	 * @param query
	 * 			(CriteriaQuery<ModelType>) - The query
	 * @param sortProperty
	 * 		(String) - The sort by property
	 * @param sortDescending
	 * 		(Boolean) - The sortDescending
	 * @param root
	 * 		(Root<ModelType>) - The root model type
	 */
	public void applySorting(CriteriaBuilder builder,
			CriteriaQuery<ModelType> query, String sortProperty, Boolean sortDescending,
			Root<ModelType> root) {
		if (sortDescending) {
			query.orderBy(builder.desc(root.get(sortProperty)));
		} else {
			query.orderBy(builder.asc(root.get(sortProperty)));
		}
	}
	
	/**
	 * Returns the collection of all the instances of type ModelType an empty partial model is generated 
	 * and the Find overload is called.
	 * @return ArrayList<ModelType>
	 */
	public List<ModelType> find() {
		LOG.debug("Inside method find()");
		StopWatch timer = new StopWatch();
        timer.start();
        
		EntityManager entityManager = getEntityManager();
		List<ModelType> models = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ModelType> query = builder.createQuery(getModelType());
		Root<ModelType> root = query.from(getModelType());
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
	 * Returns the collection of all the instances of type ModelType limited by the partial model 
	 * as search criteria.
	 * @param item
	 *            (ModelType) – Partial model containing the search criteria
	 * @return ArrayList<ModelType>
	 */
	public List<ModelType> find(ModelType item) throws Exception {
		List<ModelType> models = new ArrayList<ModelType>();
		models.add(find(getIDValue(item)));
		//TODO(rtalapaneni): Replace getIDValue to generic id method
		return models;
	}
	
	/**
	 * Sets the id value to given model.
	 * @param model
	 * 		(ModelType) - The model type of the the representation
	 * @param id
	 * 		(CriteriaQuery<ModelType>) - The query
	 * @throws Exception
	 * 		If setting ID is unsuccessful
	 */
	public void setIDValue(ModelType model, BigDecimal id) throws Exception{
		try {
			BeanUtils.setProperty(model, "id", id);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			throw new GCMSInternalServerError("Failed to map id property of the model");
		}
	}
	
	/**
	 * Gets the id value from given model.
	 * @param model
	 * 		(ModelType) - The model type of the the representation
	 * @return the ID value
	 * @throws Exception
	 * 		If getting ID value is unsuccessful
	 */
	public BigDecimal getIDValue(ModelType model) throws Exception {
		try {
			String value = BeanUtils.getProperty(model, "id");
			if (value == null) {
				return null;
			}
			return new BigDecimal(value);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			throw new GCMSInternalServerError("Failed to fetch id property from model");
		}
	}

	/**
	 * For getting the records based on parameters.
	 * @param item
	 * 		(ModelType) - The model type of the the representation
	 * @param pageIndex
	 * 		(Integer) - The page index
	 * @param numberOfRecords
	 * 		(Integer) - The numberOfRecords
	 * @return  the model list
	 */
	@Override
	public List<ModelType> find(ModelType item, int pageIndex, int numberOfRecords) {
		LOG.debug("Inside method find(ModelType item, int pageIndex, int numberOfRecords)");
		StopWatch timer = new StopWatch();
        timer.start();
        
		EntityManager entityManager = getEntityManager();
		List<ModelType> models = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ModelType> query = builder.createQuery(getModelType());
		Root<ModelType> root = query.from(getModelType());
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		if (deleteFilter != null) {
			query.where(deleteFilter);
		}
		query.select(root);
		models = entityManager.createQuery(query)
				.setMaxResults(numberOfRecords)
				.setFirstResult(pageIndex * numberOfRecords).getResultList();
		
		LOG.debug("#Performance#Repository#Total time took for find(ModelType item"
				+ ", int pageIndex, int numberOfRecords) operation is - " + timer.getTime());
		
		return models;
	}
	
	/**
	 * Returns the representation of the current Model.
	 * @param id
	 *            (ModelType id) – The representation containing the data that was just updated
	 * @return ModelType
	 * @throws Exception
	 * 		If id is invalid and able to found
	 */
	public ModelType find(BigDecimal id) throws Exception {
		LOG.debug("Inside method find(BigDecimal id)");
		StopWatch timer = new StopWatch();
        timer.start();
        
		if (NumericUtil.isNullOrLessThanOrEqualToZero(id)) {
			throw new GCMSBadDataException("Invalid ID");
		}
		
		EntityManager entityManager = getEntityManager();
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<ModelType> query = builder.createQuery(getModelType());
		Root<ModelType> root = query.from(getModelType());
		Predicate idPredicate = builder.equal(root.get(AbstractModel.FIELD_ID), id);
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		if (deleteFilter != null) {
			query.where(deleteFilter);
		}
		Predicate predicate = deleteFilter == null 
				? idPredicate : builder.and(idPredicate, deleteFilter);
		query.where(predicate);
		query = query.select(root);
		List<ModelType> models = entityManager.createQuery(query).getResultList();
		
		LOG.debug("#Performance#Repository#Total time took for find(BigDecimal id) operation is - " + timer.getTime());
		
		if (models == null || models.isEmpty()) {
			return null;
		} else if (models.size() > 1) {
			throw new NonUniqueResultException();
		}
		return models.get(0);
	}
	
	
	/**
	 * Creates a new ModelType.
	 * @param item
	 *            (ModelType) – The representation containing the data that was added
	 * @return ModelType
	 * @throws Exception
	 *             if save is unsuccessful
	 */
	public ModelType create(ModelType item) throws Exception {
		LOG.debug("Inside create(ModelType item)");
		StopWatch timer = new StopWatch();
        timer.start();
        
		EntityManager entityManger = getEntityManager();
		ModelType result = entityManger.merge(item);
		
		LOG.debug("#Performance#Repository#Total time took for create(ModelType item) operation is - " + timer.getTime());
		return result;
	}

	/**
	 * Updates the current instance of Model and persists the changes to the database.
	 * @param item
	 *            (ModelType) – The representation containing the data that was just updated
	 * @return ModelType
	 * @throws Exception
	 *             if save is unsuccessful
	 */
	public ModelType update(ModelType item) throws Exception {
		LOG.debug("Inside update(ModelType item)");
		
		StopWatch timer = new StopWatch();
        timer.start();
		
		EntityManager entityManager = getEntityManager();
		Map<Class<?>, List<BigDecimal>> childEntitiesToRemove = getChildEntitiesToBeDeleted(item);
		delete(childEntitiesToRemove, item);
		LOG.debug("#Performance#Repository#Total time took deleting child objects during update is - " + timer.getTime());
		
		StopWatch timer1 = new StopWatch();
        timer1.start();
        
		ModelType result = entityManager.merge(item);
		
		LOG.debug("#Performance#Repository#Total time took only for merge during update is - " + timer1.getTime());
		
		LOG.debug("#Performance#Repository#Total time took for update(ModelType item) operation is - " + timer.getTime());
		
		return result;
	}
	
	/**
	 * Deactivates all the existing error records for provided business party or value exchange transaction.
	 * @throws Exception
	 * 		If calling stored procedure failed
	 */
	@Override
	public void deactivateExistingErrors() throws Exception {
    	 StoredProcedureQuery query = getEntityManager().createStoredProcedureQuery("sp_deact_incmplt_errors");
   		 query.execute();
	}
	
	/**
	 * Deletes the current instance of ModelType.
	 * @param item
	 *            (ModelType) – The representation containing the data that needs to be deleted
	 * @throws Exception
	 *             if delete is unsuccessful
	 */
	public void delete(ModelType item) throws Exception {
		LOG.debug("Inside method delete(ModelType item)");
		StopWatch timer = new StopWatch();
        timer.start();
        
		EntityManager entityManager = getEntityManager();
		entityManager.remove(item);
		
		LOG.debug("#Performance#Repository#Total time took for delete(ModelType item) operation is - " + timer.getTime());
	}
	
	/**
	 * Deletes the current instance of ModelType.
	 * @param id
	 *            (ModelType) – The representation containing the data that needs to be deleted
	 * @throws Exception
	 *             if delete is unsuccessful
	 */
	public void delete(BigDecimal id) throws Exception {
		LOG.debug("Inside method delete(BigDecimal id)");
		StopWatch timer = new StopWatch();
        timer.start();
        
		List<BigDecimal> ids = new ArrayList<BigDecimal>();
		ids.add(id);
		delete(getModelType(), ids, null);
		
		LOG.debug("#Performance#Repository#Total time took for delete(BigDecimal id) operation is - " + timer.getTime());
	}
	
	/**
	 * Gives the child entity mappings to be deleted.
	 * @param model
	 * 		(ModelType) - The representation
	 * @return the value
	 */
	protected Map<Class<?>, List<BigDecimal>> getChildEntitiesToBeDeleted(ModelType model) {
		return null;
	}
	
	
	/**
	 * Deletes list of id's matching to the provided entity type id.
	 * @param pEntityData
	 *          (Map<Class<?>, List<BigDecimal>>) – The pEntityData containing the data that was added
	 * @param item
	 *          (ModelType) – The representation containing the data that was added
	 */
	public void delete(Map<Class<?>, List<BigDecimal>> pEntityData, ModelType item) {
		LOG.debug("Inside delete(Map<Class<?>, List<BigDecimal>> pEntityData, ModelType item)");
		StopWatch timer = new StopWatch();
        timer.start();
        
		if (pEntityData != null && !pEntityData.keySet().isEmpty()) {
			for (Class<?> entityType : pEntityData.keySet()) {
				List<BigDecimal> data = pEntityData.get(entityType);
				if (data != null && !data.isEmpty()) {
					delete(entityType, data, item);
				}
				
			}
		}
		LOG.debug("#Performance#Repository# Method completion time for deleting child objects - " + timer);
	}
	
	/**
	 * Deletes list of id's matching to the provided entity type id.
	 * @param pEntityType
	 *           (ModelType) – The pEntityType containing the data that was added
	 * @param ids
	 *           (List<BigDecimal>) – The representation containing the list of ID's that was added
	 * @param item
	 *           (ModelType) – The representation containing the data that was added
	 */
	public void delete(Class<?> pEntityType, List<BigDecimal> ids, ModelType item) {
		if (ids != null && !ids.isEmpty()) {
			if (isSoftDeleteEnabled()) {
				Query query = getEntityManager().createQuery("update " + pEntityType.getSimpleName() 
						+ " SET  deleted = 'Y' " + " where id = :id");
							for (BigDecimal id : ids) {
								query.setParameter("id", id);
								query.executeUpdate();
							}
			} else {
				Query query = getEntityManager().createQuery("delete from " + 
						pEntityType.getSimpleName() + " where id = :id");
				for (BigDecimal id : ids) {
					query.setParameter("id", id);
					query.executeUpdate();
				}
			
			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.pfizer.pgrd.gts.dataaccess.repository.IRepository#search
	 * (com.pfizer.pgrd.gts.dataaccess.model.BaseModel)
	 */
	@Deprecated
	public List<ModelType> search(ModelType item) throws Exception {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		//EntityManager entityManager = getEntityManager();
		List<ModelType> models = null;
		String link = "url";
		//Query query = entityManager.createQuery(" FROM QuickLinksModel "
			//	+ "covre where covre.sourceLastName like :lName");
		//query.setParameter("lName", lName);
		//List<QuickLinksModel> models = query.getResultList();
		//criteriaBuilder
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		//Root<CoveredRecipientModel> from = criteriaQuery.from(CoveredRecipientModel.class);
		CriteriaQuery<Object> select = null;//criteriaQuery.select(from);
		Expression<String> literal = criteriaBuilder.literal((String) link);
		Predicate predicate = criteriaBuilder.like(literal, "u%");
		 
		//Predicate predicate = criteriaBuilder.like(criteriaBuilder.upper(from.get("linkUri")), "gov%");
		criteriaQuery.where(predicate);
			//Predicate predicate = criteriaBuilder.like(criteriaBuilder
		//.upper(from.get("pstring")), literal);
		TypedQuery<Object> typedQuery = getEntityManager().createQuery(select);
		
			List<Object> resultList = typedQuery.getResultList();
			System.out.println(resultList.size());
		return models;
	}

	/**
	 * @return the modelType
	 */
	@Override
	public Class<ModelType> getModelType() {
		return modelType;
	}

	/**
	 * @param modelType
	 *            the modelType to set
	 */
	public void setModelType(Class<ModelType> modelType) {
		this.modelType = modelType;
	}

	/**
	 * @return the country
	 */
	public Locale getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(Locale country) {
		this.country = country;
	}

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param entityManager
	 *            the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Returns the collection of all the instances of type ModelType limited by the search DTO 
	 * as search criteria.
	 * @param searchDTO
	 * 			  (ISearchDTO<ModelType>) – DTO containing the search criteria
	 * @return ArrayList<ModelType>
	 * @throws Exception
	 * 		If finding results based on search criteria is unsuccessful
	 */
	@Override
	public List<ModelType> find(ISearchDTO<ModelType> searchDTO) throws Exception {
		LOG.debug("Inside method find(ISearchDTO<ModelType> searchDTO)");
		StopWatch timer = new StopWatch();
        timer.start();
        
		if (searchDTO == null) {
			throw new GCMSBadDataException("Search DTO is null");
		}
		
		if (searchDTO.isEnablePaging()) {
			if (searchDTO.getPageIndex() < 0) {
				throw new GCMSBadDataException("Invalid Page Index");
			}
			
			if (searchDTO.getMaxResults() <= 0) {
				throw new GCMSBadDataException("Invalid Max Results");
			}
		}
		
		List<ModelType> models = new ArrayList<ModelType>();
		
		if (searchDTO.getModel() == null) {
			EntityManager entityManager = getEntityManager();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<ModelType> query = builder.createQuery(getModelType());
			Root<ModelType> root = query.from(getModelType());
			Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
			if (deleteFilter != null) {
				query.where(deleteFilter);
			}
			query = query.select(root);
			
			TypedQuery<ModelType> typedQuery = entityManager.createQuery(query);
			
			if (searchDTO.isEnablePaging()) {
				typedQuery = typedQuery.setMaxResults(searchDTO.getMaxResults())
					.setFirstResult(searchDTO.getPageIndex() * searchDTO.getMaxResults());
			}
			
			models = typedQuery.getResultList();
		}
		
		LOG.debug("#Performance#Repository#Total time took for find(ISearchDTO<ModelType> searchDTO) "
				+ "operation is - " + timer.getTime());
		return models;
	}
	
	/**
	 * Prepares the predicate for soft delete.
	 * @param builder
	 *           (CriteriaBuilder) – The builder containing the predicate that was added
	 * @param rootModelType
	 *           (Root<ModelType>) – The rootModelType containing the model type that was added
	 * @return the predicate for soft delete
	 */
	public Predicate prepareSoftDeletePredicate(CriteriaBuilder builder, Root<ModelType> rootModelType) {
		if (isSoftDeleteEnabled()) {
			Predicate nullPredicate = builder.isNull(rootModelType.get(AbstractModel.FIELD_DELETED));
			Predicate notDeletedPredicate = builder.equal(
					rootModelType.get(AbstractModel.FIELD_DELETED), 'N');
			
			return builder.or(nullPredicate, notDeletedPredicate);			
		}
		return null;
	}
	
	/**
	 * Builds the predicate array.
	 * @param predicates
	 *          (List<Predicate>) – The predicates list containing the individual predicate that was added
	 * @return the predicate array
	 */
	public Predicate[] buildPredicatesArray(List<Predicate> predicates) {
		if (predicates == null || predicates.isEmpty()) {
			return null;
		}
		Predicate[] predicatesArray = new Predicate[predicates.size()];
		predicates.toArray(predicatesArray);
		return predicatesArray;
	}
  	
	/**
	 * Informs whether soft delete is enabled for the model.
	 * @return the soft delete enabled flag
	 */
	public boolean isSoftDeleteEnabled() {
		return false;
	}
	
	/**
	 * Appends the wild card characters to given string.
	 * @param pActualData
	 * 		(String) - The actual data
	 * @return the actual data value
	 */
	protected String appendWildCards(String pActualData) {
		if (pActualData == null || pActualData.trim().isEmpty()) {
			return "%";
		} else {
			return  pActualData.trim() + "%";
		}
	}
	
	/**
	 * Appends the wild card characters to given string to search the given string any where.
	 * @param pActualData
	 * 		(String) - The actual data
	 * @return the actual data value
	 */
	protected String appendAnyWhereWildCards(String pActualData) {
		if (pActualData == null || pActualData.trim().isEmpty()) {
			return "%";
		} else {
			return  "%" + pActualData.trim() + "%";
		}
	}
	
	/**
	 * Builds case sensitive like predicate based on the given search string.
	 * @param builder
	 *           (CriteriaBuilder) – The builder containing the predicate that was added
	 * @param searchExp
	 *           (Expression<String>) – The searchExp containing the expression for the predicate that was added
	 * @param searchData
	 *           (String) – The search Data containing the predicate that was added
	 * @param parametersData
	 *           (Map<String, String>) – The parametersData containing the predicate that was added
	 * @return the predicate
	 */
	public Predicate buildCaseSensitiveLikePredicate(CriteriaBuilder builder, 
			Expression<String> searchExp, String searchData, Map<String, String> parametersData) {
		String uniqueParameterName = generateGUID(parametersData);
		ParameterExpression<String> valueExpression = builder.parameter(String.class, uniqueParameterName);
		Predicate predicate = builder.like(builder.upper(searchExp), builder.upper(valueExpression));
		parametersData.put(uniqueParameterName, appendWildCards(searchData));
		/*Predicate predicate = builder.like(
				builder.upper(searchExp), appendWildCards(searchData.toUpperCase()));*/
		return predicate;
	}
	/**
	 * Builds case sensitive not like predicate based on the given search string.
	 * @param builder
	 *           (CriteriaBuilder) – The builder containing the predicate that was added
	 * @param searchExp
	 *           (Expression<String>) – The searchExp containing the expression for the predicate that was added
	 * @param searchData
	 *           (String) – The search Data containing the predicate that was added
	 * @param parametersData
	 *           (Map<String, String>) – The parametersData containing the predicate that was added
	 * @return the predicate
	 */
	public Predicate buildCaseSensitiveNotLikePredicate(CriteriaBuilder builder, 
			Expression<String> searchExp, String searchData, Map<String, String> parametersData) {
		String uniqueParameterName = generateGUID(parametersData);
		ParameterExpression<String> valueExpression = builder.parameter(String.class, uniqueParameterName);
		Predicate predicate = builder.notLike(builder.upper(searchExp), builder.upper(valueExpression));
		parametersData.put(uniqueParameterName, appendWildCards(searchData));
		return predicate;
	}
	/**
	 * Builds case sensitive like predicate based on the given search string.
	 * @param builder
	 *           (CriteriaBuilder) – The builder containing the predicate that was added
	 * @param searchExp
	 *           (Expression<String>) – The searchExp containing the expression for the predicate that was added
	 * @param searchData
	 *           (String) – The search Data containing the predicate that was added
	 * @param parametersData
	 *           (Map<String, String>) – The parametersData containing the predicate that was added
	 * @return the predicate
	 */
	public Predicate buildCaseSensitiveLikeAnyWherePredicate(CriteriaBuilder builder, 
			Expression<String> searchExp, String searchData, Map<String, String> parametersData) {
		String uniqueParameterName = generateGUID(parametersData);
		ParameterExpression<String> valueExpression = builder.parameter(String.class, uniqueParameterName);
		Predicate predicate = builder.like(builder.upper(searchExp), builder.upper(valueExpression));
		parametersData.put(uniqueParameterName, appendAnyWhereWildCards(searchData));
		return predicate;
	}
	
	/**
	 * Builds case sensitive equal predicate based on the given search string.
	 * @param builder
	 *           (CriteriaBuilder) – The builder containing the predicate that was added
	 * @param searchExp
	 *           (Expression<String>) – The searchExp containing the predicate that was added
	 * @param searchData
	 *           (String) – The searchData containing the predicate that was added
	 * @param parametersData
	 *           (Map<String, String>) – The parametersData containing the predicate that was added
	 * @return the predicate
	 */
	public Predicate buildCaseSensitiveEqualPredicate(CriteriaBuilder builder, 
			Expression<String> searchExp, String searchData, Map<String, String> parametersData) {
		String uniqueParameterName = generateGUID(parametersData);
		ParameterExpression<String> valueExpression = builder.parameter(String.class, uniqueParameterName);
		Predicate predicate = builder.equal(builder.upper(searchExp), builder.upper(valueExpression));
		parametersData.put(uniqueParameterName, searchData);
		/*Predicate predicate = builder.equal(
				builder.upper(searchExp), searchData.toUpperCase());*/
		return predicate;
	}
	
	/**
	 * Applies the parameters to query.
	 * @param typedQuery
	 *           (TypedQuery<ModelType>) – The typedQuery containing the predicates that were added
	 * @param parameters
	 *           (Map<String, String>) – The parameters containing the predicates that were added
	 */
	public void applyParameterExpressionValues(TypedQuery<ModelType> typedQuery, Map<String, String> parameters) {
		if (typedQuery != null && parameters != null && !parameters.isEmpty()) {
			LOG.info("Query parameters - " + parameters);
			for (String parameterName : parameters.keySet()) {
				typedQuery.setParameter(parameterName, parameters.get(parameterName));
			}
		}
	}
	
	/**
	 * Creates the error audit sequence.
	 * @return
	 */
	protected BigDecimal createErrorAuditSequence() throws Exception{
		LOG.debug("Inside createErrorAuditSequence()");
		Query query = getEntityManager().createNativeQuery("SELECT SEQ_SRC_RECID.NEXTVAL FROM DUAL");
		BigDecimal batchId = (BigDecimal) query.getSingleResult();
		return batchId;
	}
	
	/**
	 * 
	 * @param errorModel
	 * @param errorSequenceID
	 */
	protected <ErrorType extends ErrorModel> void updateErrorAuditSequence(List<ErrorType> errorModels, BigDecimal errorSequenceID) {
		if (errorModels != null && !errorModels.isEmpty()) {
			for (ErrorType errorModel : errorModels) {
				errorModel.setFileID(AbstractModel.CONSTANT_DEFAULT_FILE_ID);
				errorModel.setSourceRecordID(errorSequenceID);
			}
		}
	}
	
	/**
	 * Generates a random UUID.
	 * @param parametersData
	 * 		(String) - The parameters data
	 * @return the parameter name
	 */
	private String generateGUID(Map<String, String> parametersData) {
		String paramName = "param_" + (parametersData.size() + 1);
		return paramName;
	}
	
	/**
	 * Informs whether sort by field need to be applied on which column.
	 * If no sorting needed then returns null otherwise overrides the particular column.
	 * @return the sort by field value
	 */
	public String sortByField() {
		return null;
	}
	/**
	 * Returns SortByAscending value to true by default.
	 * @return Boolean
	 */
	public Boolean sortDescending() {
		return false;
	}
	/**
	 * For getting joins of query on fetches.
	 * @param joins
	 * 		(Set<? extends Fetch<?, ?>>) - The set of fetches applied on joins
	 * @param root
	 * 		(Root<?>) - The root of representation
	 */
	@SuppressWarnings("unchecked")
	public void doJoinsOnFetches(Set<? extends Fetch<?, ?>> joins, Root<?> root) {
	    doJoins((Set<? extends Join<?, ?>>) joins, root);
	}

	/**
	 * For getting joins of query.
	 * @param joins
	 * 		(Set<? extends Join<?, ?>>) - The set of fetches applied on joins
	 * @param root
	 * 		(Root<?>) - The root of representation
	 */
	public void doJoins(Set<? extends Join<?, ?>> joins, Root<?> root) {
	    for (Join<?, ?> join : joins) {
	        Join<?, ?> joined = root.join(join.getAttribute().getName(), join.getJoinType());
	        joined.alias(join.getAlias());
	        doJoins(join.getJoins(), joined);
	    }
	}

	/**
	 * For getting joins of query on fetches.
	 * @param joins
	 * 		(Set<? extends Join<?, ?>>) - The set of fetches applied on joins
	 * @param root
	 * 		(Join<?, ?>) - The join of root
	 */
	public void doJoins(Set<? extends Join<?, ?>> joins, Join<?, ?> root) {
	    for (Join<?, ?> join : joins) {
	        Join<?, ?> joined = root.join(join.getAttribute().getName(), join.getJoinType());
	        joined.alias(join.getAlias());
	        doJoins(join.getJoins(), joined);
	    }
	}
		
	public List<BusinessProfileModel> findById(BigDecimal[] id) throws Exception {			
		return null;
	}

	public List<ConsentTemplateModel> fetchTemplate(BigDecimal id) throws Exception {
		return null;
	}

	public List<BusinessProfileModel> findByCountry(String name,String type,String lastName, String city,String firstName,String address,String speciality)
			throws Exception {
		return null;
	}
	public List<BusinessProfileModel> validationfindById(BigDecimal id) throws Exception {			
		return null;
	}
}
