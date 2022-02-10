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
import com.pfizer.gcms.dataaccess.model.CountryModel;
import com.pfizer.gcms.dataaccess.model.LanguageModel;
import com.pfizer.gcms.dataaccess.model.UserProfileModel;
/**
 *  The Language Repository will provide methods for accessing, creating and updating the Languages stored in
 *  the database.
 */
public class LanguageRepository extends AbstractRepository<LanguageModel> {
	
	/**
	 * Creates a new instance of the User Profile and configures the abstract class with 
	 * the correct models.
	 * @param country
	 *            (Locale) – The country by which to limit the result list
	 */
	public LanguageRepository(Locale country) {
		super(country);
		setModelType(LanguageModel.class);
	}
	
	/**
	 * Finds the user profiles based on user name.
	 * @param userName
	 * 			(String) - The user name
	 * @return List<UserProfileModel>
	 */
	public List<LanguageModel> find(String userName)  {System.out.println("octob lang 1");
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<LanguageModel> query = builder.createQuery(getModelType());
		Root<LanguageModel> root = query.from(getModelType());
		Expression<String> langNameExp = root.get(LanguageModel.LOCAL_LANG_NM);
		Map<String, String> criteriaParameters = new HashMap<String, String>();
		Predicate userNamePredicate = buildCaseSensitiveEqualPredicate(builder, langNameExp, userName, 
				criteriaParameters);
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		
		
		
		Predicate predicate = deleteFilter == null 
				? userNamePredicate : builder.and(userNamePredicate, deleteFilter);
		
//		Predicate countryPredicate = prepareCountryPredicate(root, getCountry().getCountry());
//		Predicate finalPredicate = builder.and(predicate, countryPredicate);

		query.where(predicate);
		query = query.select(root);
		TypedQuery<LanguageModel> typedQuery = getEntityManager().createQuery(query);
		applyParameterExpressionValues(typedQuery, criteriaParameters);
		List<LanguageModel> models = typedQuery.getResultList();
		if (models == null || models.isEmpty()) {
			return null;
		} 
		return models;
	}
	
	/**
	 * Filters the users based on provided user name and country.
	 * @param item
	 * 			(LanguageModel) - The item representation
	 * @return LanguageModel
	 */
	public LanguageModel filterProfilesUsersByUserNameAndCountry(LanguageModel item){
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<LanguageModel> query = builder.createQuery(getModelType());
		Root<LanguageModel> root = query.from(getModelType());
		Expression<String> userNameExp = root.get(		LanguageModel.LOCAL_LANG_NM);
		Map<String, String> criteriaParameters = new HashMap<String, String>();
		//Predicate userNamePredicate = buildCaseSensitiveEqualPredicate(builder, userNameExp, 
			//	item.getUserName(), criteriaParameters);
		//Predicate countryPredicate = builder.equal(root.get(AbstractCountryModel.FIELD_COUNTRY_ID),
			//	item.getCountryId());
		//Predicate finalPredicate = builder.and(userNamePredicate, countryPredicate);
		//query.where(finalPredicate);
		query = query.select(root);
		LanguageModel model = null;
		try {
			TypedQuery<LanguageModel> typedQuery = getEntityManager().createQuery(query);
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
	 * 			(LanguageModel) - The item representation
	 * @return LanguageModel
	 * @throws Exception
	 * 		If creating the object is unsuccessful
	 */
	@Override
	public LanguageModel create(LanguageModel item) throws Exception {
		LanguageModel model = filterProfilesUsersByUserNameAndCountry(item);
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
		LanguageModel model = find(profileKey);
		if (model != null) {
			model.setDeleted('Y');
			super.update(model);
		}		
	}
	
}
