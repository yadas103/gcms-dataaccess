package com.pfizer.gcms.dataaccess.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pfizer.gcms.dataaccess.common.NumericUtil;
import com.pfizer.gcms.dataaccess.common.exception.GCMSBadDataException;
import com.pfizer.gcms.dataaccess.common.exception.GCMSInternalServerError;
import com.pfizer.gcms.dataaccess.dto.ISearchDTO;
import com.pfizer.gcms.dataaccess.model.AbstractModel;

/**
 * @author rtalapaneni 
 * The Abstract Repository will provide methods for accessing, creating and updating the Models stored in 
 * the database. All other Repositories will inherit from it.
 * @param <ModelType> the model type used by repository implementation
 */
public abstract class AbstractChangeRepository<ModelType> {

	private static final Log LOG = LogFactory.getLog(AbstractChangeRepository.class);
	
	private Class<ModelType> modelType;
	private Locale country;
	private EntityManager entityManager;
	
	/**
	 * Default constructor.
	 */
	public AbstractChangeRepository() { }
	
	/**
	 * Constructor to set locale.
	 * @param country  - Locale of the logged country.
	 */
	public AbstractChangeRepository(Locale country) {
		if (country == null) {
			throw new IllegalArgumentException("CountryModel value should not be null");
		}
		setCountry(country);
	}
	
	/**
	 * Configures the sort property.
	 * @param builder
	 * 		(CriteriaBuilder) - The criteria builder
	 * @param query
	 * 		(CriteriaQuery) - The criteria query
	 * @param sortProperty
	 * 		(String) - The sort property
	 * @param sortDescending
	 * 		(Boolean) - The sort descending
	 * @param root
	 * 		(Root<ModelType>) - The root
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
		return models;
	}

	/**
	 * Returns the collection of all the instances of type ModelType limited by the partial model 
	 * as search criteria.
	 * @param item
	 *            (ModelType item) – Partial model containing the search criteria
	 * @return ArrayList<ModelType>
	 * @throws Exception
	 * 		If finding the item was unsuccessful
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
	 * 		(ModelType) - The model representation
	 * @param id
	 * 		(BigDecimal) - The ID of the model
	 * @throws Exception
	 * 		If setting the ID is unsuccessful
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
	 * 		(ModelType) - The model representation
	 * @return the ID value
	 * @throws Exception
	 * 		If getting the item was unsuccessful
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
	 * For getting the change History details based on parameters.
	 * @param item
	 * 		(ModelType) - The model representation
	 * @param pageIndex
	 * 		(Integer) - The page index
	 * @param numberOfRecords
	 * 		(Integer) - The number of records
	 * @return the models
	 */
	public List<ModelType> find(ModelType item, int pageIndex, int numberOfRecords) {
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
		return models;
	}
	
	/**
	 * Returns the representation of the current Model.
	 * @param id
	 * 			(ModelType id) – The representation containing the data that was just updated
	 * @return the model based on the criteria
	 * @throws Exception
	 * 		If finding the ID was unsuccessful
	 */
	public ModelType find(BigDecimal id) throws Exception {
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
		EntityManager entityManger = getEntityManager();
		ModelType result = entityManger.merge(item);
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
		EntityManager entityManager = getEntityManager();
		
		Map<Class<?>, List<BigDecimal>> childEntitiesToRemove = getChildEntitiesToBeDeleted(item);
		delete(childEntitiesToRemove);
		
		ModelType result = entityManager.merge(item);
		return result;
	}
	
	/**
	 * Deletes the current instance of ModelType.
	 * @param item
	 *            (ModelType) – The representation containing the data that needs to be deleted
	 * @throws Exception
	 *             if delete is unsuccessful
	 */
	public void delete(ModelType item) throws Exception {
		EntityManager entityManager = getEntityManager();
		entityManager.remove(item);
	}
	
	/**
	 * Deletes the current instance of ModelType.
	 * @param id
	 *            (ModelType) – The representation containing the data that needs to be deleted
	 * @throws Exception
	 *             if delete is unsuccessful
	 */
	public void delete(BigDecimal id) throws Exception {
		List<BigDecimal> ids = new ArrayList<BigDecimal>();
		ids.add(id);
		delete(getModelType(), ids);
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
	 * 		(Map<Class<?>, List<BigDecimal>>) - The pEntityData
	 */
	public void delete(Map<Class<?>, List<BigDecimal>> pEntityData) {
		if (pEntityData != null && !pEntityData.keySet().isEmpty()) {
			for (Class<?> entityType : pEntityData.keySet()) {
				List<BigDecimal> data = pEntityData.get(entityType);
				if (data != null && !data.isEmpty()) {
					delete(entityType, data);
				}
				
			}
		}
	}
	
	/**
	 * Deletes list of id's matching to the provided entity type id.
	 * @param pEntityType
	 * 		(Class<?>) - The pEntityData
	 * @param ids
	 * 		(List<BigDecimal>) - The ID's
	 */
	public void delete(Class<?> pEntityType, List<BigDecimal> ids) {
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
	
	
	/**
	 * @param item
	 * 		(ModelTYpe) - The item
	 * @return the models
	 * @throws Exception
	 * 		if the search is unsuccessful
	 */
	@Deprecated
	public List<ModelType> search(ModelType item) throws Exception {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		//EntityManager entityManager = getEntityManager();
		List<ModelType> models = null;
		/*String link = "url";
		//Query query = entityManager.createQuery(" FROM QuickLinksModel "
			//	+ "covre where covre.sourceLastName like :lName");
		//query.setParameter("lName", lName);
		//List<QuickLinksModel> models = query.getResultList();
		//criteriaBuilder
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<CoveredRecipientModel> from = criteriaQuery.from(CoveredRecipientModel.class);
		CriteriaQuery<Object> select = criteriaQuery.select(from);
		Expression<String> literal = criteriaBuilder.literal((String) link);
		Predicate predicate = criteriaBuilder.like(literal, "u%");
		 
		//Predicate predicate = criteriaBuilder.like(criteriaBuilder.upper(from.get("linkUri")), "gov%");
		criteriaQuery.where(predicate);
			//Predicate predicate = criteriaBuilder.like(criteriaBuilder
		//.upper(from.get("pstring")), literal);
		TypedQuery<Object> typedQuery = getEntityManager().createQuery(select);
		
			List<Object> resultList = typedQuery.getResultList();
			System.out.println(resultList.size());*/
		return models;
	}

	/**
	 * @return the modelType
	 */
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
	 * 		If finding the search based on the creteria was unsuccessful
	 */
	public List<ModelType> find(ISearchDTO<ModelType> searchDTO) throws Exception {
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
		return models;
	}
	
	/**
	 * Prepares the predicate for soft delete.
	 * @param builder
	 * 		(CriteriaBuilder) - The builder
	 * @param rootModelType
	 * 		(Root<ModelType>) - The rootModelType
	 * @return the predicate
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
	 * 		(List<Predicate>) - The predicates list
	 * @return the predicate array
	 */
	protected Predicate[] buildPredicatesArray(List<Predicate> predicates) {
		if (predicates == null || predicates.isEmpty()) {
			return null;
		}
		Predicate[] predicatesArray = new Predicate[predicates.size()];
		predicates.toArray(predicatesArray);
		return predicatesArray;
	}
  	
	/**
	 * Informs whether soft delete is enabled for the model.
	 * @return the soft Delete enabled flag
	 */
	public boolean isSoftDeleteEnabled() {
		return false;
	}
	
	/**
	 * Appends the wild card characters to given string.
	 * @param pActualData
	 * 		(String) - The actual data
	 * @return the actual data with wild cards
	 */
	protected String appendWildCards(String pActualData) {
		if (pActualData == null || pActualData.trim().isEmpty()) {
			return "%";
		} else {
			return "%" + pActualData.trim() + "%";
		}
	}
	
	/**
	 * Builds case sensitive like predicate based on the given search string.
	 * @param builder
	 * 		(CriteriaBuilder) - The builder representation
	 * @param searchExp
	 * 		(Expression<String>) - The expression of the representation
	 * @param searchData
	 * 		(String) - The search data provided
	 * @return the predicate
	 */
	protected Predicate buildCaseSensitiveLikePredicate(CriteriaBuilder builder, 
			Expression<String> searchExp, String searchData) {
		Predicate predicate = builder.like(
				builder.upper(searchExp), appendWildCards(searchData.toUpperCase()));
		return predicate;
	}
	/**
	 * Builds case sensitive equal predicate based on the given search string.
	 * @param builder
	 * 		(CriteriaBuilder) - The builder representation
	 * @param searchExp
	 * 		(Expression<String>) - The expression of the representation
	 * @param searchData
	 * 		(String) - The search data provided
	 * @return the predicate
	 */
	protected Predicate buildCaseSensitiveEqualPredicate(CriteriaBuilder builder, 
			Expression<String> searchExp, String searchData) {
		Predicate predicate = builder.equal(
				builder.upper(searchExp), searchData.toUpperCase());
		return predicate;
	}
	
	/**
	 * Informs whether sort by field need to be applied on which column.
	 * If no sorting needed then returns null otherwise overrides the particular column.
	 * @return the sort by field
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
	
}
