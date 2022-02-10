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
import com.pfizer.gcms.dataaccess.model.LocalTextModel;
/**
 *  The LocalText Repository will provide methods for accessing, creating and updating the Local Texts stored in
 *  the database.
 */
public class LocalTextRepository extends AbstractRepository<LocalTextModel> {
	
	/**
	 * Creates a new instance of the User Profile and configures the abstract class with 
	 * the correct models.
	 * @param country
	 *            (Locale) – The country by which to limit the result list
	 */
	public LocalTextRepository(Locale country) {
		super(country);
		setModelType(LocalTextModel.class);
	}
	
	/**
	 * Finds the user profiles based on user name.
	 * @param userName
	 * 			(String) - The user name
	 * @return List<UserProfileModel>
	 */
	public List<LocalTextModel> find(String userName)  {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<LocalTextModel> query = builder.createQuery(getModelType());
		Root<LocalTextModel> root = query.from(getModelType());
		Expression<String> userNameExp = root.get(LocalTextModel.LOCAL_LANG_TEXT);
		Map<String, String> criteriaParameters = new HashMap<String, String>();
		Predicate userNamePredicate = buildCaseSensitiveEqualPredicate(builder, userNameExp, userName, 
				criteriaParameters);
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		
		Predicate predicate = deleteFilter == null 
				? userNamePredicate : builder.and(userNamePredicate, deleteFilter);
		
		//Predicate countryPredicate = prepareCountryPredicate(root, getCountry().getCountry());
		//Predicate finalPredicate = builder.and(predicate, countryPredicate);
		
		query.where(predicate);
		query = query.select(root);
		TypedQuery<LocalTextModel> typedQuery = getEntityManager().createQuery(query);
		applyParameterExpressionValues(typedQuery, criteriaParameters);
		List<LocalTextModel> models = typedQuery.getResultList();
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
	public LocalTextModel filterProfilesUsersByUserNameAndCountry(LocalTextModel item){
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<LocalTextModel> query = builder.createQuery(getModelType());
		Root<LocalTextModel> root = query.from(getModelType());
		Expression<String> userNameExp = root.get(		LocalTextModel.LOCAL_LANG_TEXT);
		Map<String, String> criteriaParameters = new HashMap<String, String>();
		//Predicate userNamePredicate = buildCaseSensitiveEqualPredicate(builder, userNameExp, 
			//	item.getUserName(), criteriaParameters);
		//Predicate countryPredicate = builder.equal(root.get(AbstractCountryModel.FIELD_COUNTRY_ID),
			//	item.getCountryId());
		//Predicate finalPredicate = builder.and(userNamePredicate, countryPredicate);
		//query.where(finalPredicate);
		query = query.select(root);
		LocalTextModel model = null;
		try {
			TypedQuery<LocalTextModel> typedQuery = getEntityManager().createQuery(query);
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
	public LocalTextModel create(LocalTextModel item) throws Exception {
		LocalTextModel model = filterProfilesUsersByUserNameAndCountry(item);
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
		LocalTextModel model = find(profileKey);
		if (model != null) {
			model.setDeleted('Y');
			super.update(model);
		}		
	}
}
