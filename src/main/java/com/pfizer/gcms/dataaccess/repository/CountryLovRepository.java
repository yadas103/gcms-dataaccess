package com.pfizer.gcms.dataaccess.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.pfizer.gcms.dataaccess.common.exception.GCMSBadDataException;
import com.pfizer.gcms.dataaccess.model.CountryModel;

/**
 * @author prayadu
 * The CountryLovRepository  will provide methods for accessing, creating and updating the CountryLov 
 * stored in the database. This CountryLovRepository is used as the lookup for the country Lov.
 */
public class CountryLovRepository extends AbstractRepository<CountryModel> {
	private static final Log LOG = LogFactory.getLog(CountryLovRepository.class);
	/**
	 * Creates a new instance of the CountryLov Repository and configures the
	 * abstract class with the correct models.
	 * 
	 * @param country
	 *            (Locale) – The country by which to limit the result list
	 */
	public CountryLovRepository(Locale country) {
		setModelType(CountryModel.class);
	}
	
	/**
	 * Finds the country model based on passed in ISO code.
	 * @param isoCode
	 * 			(String) p - The isoCOde of the country provided
	 * @return the country model
	 * @throws Exception 
	 * 		If find By isoCode search criteria is unsuccessful
	 */
	public CountryModel findByISOCode(String isoCode) throws Exception {
		
		if (isoCode == null || isoCode.trim().isEmpty()) {
			String message = "Invalid ISO Code";
			LOG.warn(message);
			throw new GCMSBadDataException(message);
		}
		
		EntityManager entityManager = getEntityManager();
		List<CountryModel> models = null;
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<CountryModel> query = criteria.createQuery(getModelType());
		Root<CountryModel> root = query.from(getModelType());
		Expression<String> isoCodeExp = root.get(CountryModel.FIELD_ISO_CODE);
		Map<String, String> criteriaParameters = new HashMap<String, String>();
		Predicate predicate = buildCaseSensitiveEqualPredicate(criteria, isoCodeExp, isoCode, criteriaParameters);
		query.where(predicate);
		query = query.select(root);
		TypedQuery<CountryModel> typedQuery = entityManager.createQuery(query);
		applyParameterExpressionValues(typedQuery, criteriaParameters);
		models = typedQuery.getResultList();
		if (models == null || models.isEmpty()) {
			return null;
		} else if (models.size() > 1) {
			throw new NonUniqueResultException();
		}
		return models.get(0);
	}

	/**
	 * Overrides the method for applying sorting on country name.
	 * @return String
	 */
	@Override
	public String sortByField() {
		return CountryModel.FIELD_COUNTRY_NAME;
	}
}
