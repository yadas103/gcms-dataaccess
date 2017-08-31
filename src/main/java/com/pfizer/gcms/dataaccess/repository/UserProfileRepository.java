package com.pfizer.gcms.dataaccess.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.pfizer.gcms.dataaccess.model.AbstractCountryModel;
import com.pfizer.gcms.dataaccess.model.UserProfileModel;
/**
 * @author rnarne
 *  The User Profile Repository will provide methods for accessing, creating and updating the User Profiles stored in
 *  the database.
 */
public class UserProfileRepository extends AbstractCountryRepository<UserProfileModel> {
	
	/**
	 * Creates a new instance of the User Profile and configures the abstract class with 
	 * the correct models.
	 * @param country
	 *            (Locale) – The country by which to limit the result list
	 */
	public UserProfileRepository(Locale country) {
		super(country);
		setModelType(UserProfileModel.class);
	}
	
	/**
	 * Finds the user profiles based on user name.
	 * @param userName
	 * 			(String) - The user name
	 * @return List<UserProfileModel>
	 */
	public List<UserProfileModel> find(String userName)  {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<UserProfileModel> query = builder.createQuery(getModelType());
		Root<UserProfileModel> root = query.from(getModelType());
		Expression<String> userNameExp = root.get(UserProfileModel.FIELD_USERNAME);
		Map<String, String> criteriaParameters = new HashMap<String, String>();
		Predicate userNamePredicate = buildCaseSensitiveEqualPredicate(builder, userNameExp, userName, 
				criteriaParameters);
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		
		Predicate predicate = deleteFilter == null 
				? userNamePredicate : builder.and(userNamePredicate, deleteFilter);
		
		Predicate countryPredicate = prepareCountryPredicate(root, getCountry().getCountry());
		Predicate finalPredicate = builder.and(predicate, countryPredicate);
		
		query.where(finalPredicate);
		query = query.select(root);
		TypedQuery<UserProfileModel> typedQuery = getEntityManager().createQuery(query);
		applyParameterExpressionValues(typedQuery, criteriaParameters);
		List<UserProfileModel> models = typedQuery.getResultList();
		if (models == null || models.isEmpty()) {
			return null;
		} 
		return models;
	}
	
	/**
	 * Filters the users based on provided user name and country.
	 * @param item
	 * 			(UserProfileModel) - The item representation
	 * @return UserProfileModel
	 */
	public UserProfileModel filterProfilesUsersByUserNameAndCountry(UserProfileModel item){
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<UserProfileModel> query = builder.createQuery(getModelType());
		Root<UserProfileModel> root = query.from(getModelType());
		Expression<String> userNameExp = root.get(UserProfileModel.FIELD_USERNAME);
		Map<String, String> criteriaParameters = new HashMap<String, String>();
		Predicate userNamePredicate = buildCaseSensitiveEqualPredicate(builder, userNameExp, 
				item.getUserName(), criteriaParameters);
		Predicate countryPredicate = builder.equal(root.get(AbstractCountryModel.FIELD_COUNTRY_ID),
				item.getCountryId());
		Predicate finalPredicate = builder.and(userNamePredicate, countryPredicate);
		query.where(finalPredicate);
		query = query.select(root);
		UserProfileModel model = null;
		try {
			TypedQuery<UserProfileModel> typedQuery = getEntityManager().createQuery(query);
			applyParameterExpressionValues(typedQuery, criteriaParameters);
			model = typedQuery.getSingleResult();	
		} catch (javax.persistence.NoResultException ex){
			model = null;
		}
		return model;
	}
	
	/**
	 * Creates the user profile objects with parent reference for persisting and creates.
	 * @param item
	 * 			(UserProfileModel) - The item representation
	 * @return UserProfileModel
	 * @throws Exception
	 * 		If creating the object is unsuccessful
	 */
	@Override
	public UserProfileModel create(UserProfileModel item) throws Exception {
		UserProfileModel model = filterProfilesUsersByUserNameAndCountry(item);
		if (model == null) {
			return super.create(item);
		} else {
			item.setDeleted('N');
			item.setId(model.getId());
			return super.update(item);
		}
	}
	/**
	 * Soft deletes the User profile model.
	 * @param profileKey
	 * 			(BigDecimal) - The profile key
	 * @throws Exception
	 * 		If deleting the object unsuccessful
	 */
	@Override
	public void delete(BigDecimal  profileKey) throws Exception {
		UserProfileModel model = find(profileKey);
		if (model != null) {
			model.setDeleted('Y');
			super.update(model);
		}		
	}
}
