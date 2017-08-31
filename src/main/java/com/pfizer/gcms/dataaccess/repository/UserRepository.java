package com.pfizer.gcms.dataaccess.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.pfizer.gcms.dataaccess.dto.ISearchDTO;
import com.pfizer.gcms.dataaccess.model.AbstractCountryModel;
import com.pfizer.gcms.dataaccess.model.CountryModel;
import com.pfizer.gcms.dataaccess.model.UserModel;
import com.pfizer.gcms.dataaccess.model.UserProfileModel;

/**
 * @author rtalapaneni 
 * The User Repository will provide methods for accessing, creating and updating the Users stored in
 * the database.
 */
public class UserRepository extends AbstractRepository<UserModel> {
	Map<Class<?>, List<BigDecimal>> childEntitiesToBeDeleted = null;
	
	/**
	 * Creates a new instance of the User Repository and configures the abstract class with the correct models.
	 * @param country
	 *            (Locale) – The country by which to limit the result list
	 */
	public UserRepository(Locale country) {
		setModelType(UserModel.class);
	}
	
	/**
	 * Finds the users based on user name.
	 * @param userName
	 * 			(String) - The user name
	 * @return UserModel
	 */
	public UserModel find(String userName)  {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<UserModel> query = builder.createQuery(getModelType());
		Root<UserModel> root = query.from(getModelType());
		Expression<String> userNameExp = root.get(UserModel.FIELD_USERNAME);
		Map<String, String> criteriaParameters = new HashMap<String, String>();
		Predicate userNamePredicate = buildCaseSensitiveEqualPredicate(builder, userNameExp, userName, 
				criteriaParameters);
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		if (deleteFilter != null) {
			query.where(deleteFilter);
		}
		Predicate predicate = deleteFilter == null 
				? userNamePredicate : builder.and(userNamePredicate, deleteFilter);
		query.where(predicate);
		query = query.select(root);
		TypedQuery<UserModel> typedQuery = getEntityManager().createQuery(query);
		applyParameterExpressionValues(typedQuery, criteriaParameters);
		List<UserModel> models = typedQuery.getResultList();
		if (models == null || models.isEmpty()) {
			return null;
		} else if (models.size() > 1) {
			throw new NonUniqueResultException();
		}
		return models.get(0);
	}
	/**
	 * Finds the users based on country locale.
	 * @param isearchDto
	 * 			(ISearchDTO<UserModel>) - The search item representation
	 * @return List<UserModel>
	 */
	@Override
	public List<UserModel> find(ISearchDTO<UserModel> isearchDto)  {
		EntityManager entityManager = getEntityManager();
		List<UserModel> models = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserModel> query = builder.createQuery(getModelType());
		Root<UserModel> root = query.from(getModelType());
		Map<String, String> criteriaParameters = new HashMap<String, String>();
		query.where(buildUserProfileCriteriaPredicate(builder, query, root, isearchDto, criteriaParameters));
		query = query.select(root);
		TypedQuery<UserModel> typedQuery = entityManager.createQuery(query);
		applyParameterExpressionValues(typedQuery, criteriaParameters);
		models = typedQuery.getResultList();
		return models;
	}
	/**
	 * Builds the user profile criteria predicate.
	 * @param builder
	 * 			(CriteriaBuilder) - The builder for applying the predicate
	 * @param query 
	 * 		(CriteriaQuery<UserModel>) - The query for applying the predicate
	 * @param rootModelType
	 * 			(CriteriaQuery<UserModel>) - The query for applying the representation
	 * @param searchDTO
	 * 			(ISearchDTO<UserModel>) - The search item representation
	 * @param criteriaParameters 
	 * 		(Map<String, String>) - The criteria parameters for applying the predicate
	 * @return the predicate
	 */
	public Predicate buildUserProfileCriteriaPredicate(CriteriaBuilder builder, CriteriaQuery<UserModel> query, 
		Root<UserModel> rootModelType, ISearchDTO<UserModel> searchDTO, Map<String, String> criteriaParameters){
		List<Predicate> predicates = new ArrayList<Predicate>();
		Join<UserModel, UserProfileModel> userProfileJoin = rootModelType.join(UserModel.FIELD_USERPROFILES);
		Expression<String> isoCodeExp = userProfileJoin.get
				(AbstractCountryModel.FIELD_COUNTRY).get(CountryModel.FIELD_ISO_CODE);
		Predicate predicate = buildCaseSensitiveEqualPredicate(builder, isoCodeExp, searchDTO.getCountryCode(), 
				criteriaParameters);
		predicates.add(predicate);
		return predicate;
	}
	/* (non-Javadoc)
	 * @see com.pfizer.pgrd.gts.dataaccess.repository.AbstractRepository
	 * #update(com.pfizer.pgrd.gts.dataaccess.model.AbstractModel)
	 */
	@Override
	public UserModel update(UserModel item) throws Exception {
		if (item != null && item.getUserProfiles() != null && !item.getUserProfiles().isEmpty()) {
			for (UserProfileModel profile : item.getUserProfiles()) {
				profile.setUser(item);
			}
			updateChildReferences(item);
		}
		return super.update(item);
	}
	
	/**
	 * Gives the child entity mappings to be deleted.
	 * @param model
	 * 		(ModelType) - The representation
	 * @return Map<Class<?>, List<BigDecimal>>
	 */
	protected Map<Class<?>, List<BigDecimal>> getChildEntitiesToBeDeleted(UserModel model) {
		return childEntitiesToBeDeleted;
	}
	/**
	 * Updates the child data with parent references.
	 * @param userModel
	 * 			(UserModel) - The user model
	 * @return Map<Class<?>, List<BigDecimal>>
	 */
	private Map<Class<?>, List<BigDecimal>> updateChildReferences(UserModel userModel) {
		childEntitiesToBeDeleted = userModel.updateChildReferences();
		/*if (userModel != null && userModel.getUserProfiles() != null 
				&& !userModel.getUserProfiles().isEmpty()) {*/
		List<BigDecimal> ids = new ArrayList<BigDecimal>();
		Iterator<UserProfileModel> iterator = userModel.getUserProfiles().iterator();
		while (iterator.hasNext()) {
			UserProfileModel model = iterator.next();
			if (model.isDeleteRecord()) {
				ids.add(model.getId());
				iterator.remove();
			} /*else {
					model.setUser(userModel);
				}*/
			//}
			childEntitiesToBeDeleted.put(UserProfileModel.class, ids);
		}
		return childEntitiesToBeDeleted;
	}
	/**
	 * Soft deletes the User model.
	 * @throws Exception
	 * 		If the deletion is unsuccessful
	 */
	@Override
	public void delete(UserModel item) throws Exception {
		UserModel model = find(item.getUserName());
		if (model != null) {
			//model.setDeleted('Y');
			update(model);
		}		
	}
	
	/**
	 * Returns a flag indicating it supports soft delete.
	 *//*
	@Override
	public boolean isSoftDeleteEnabled() {
		return true;
	}*/
}
