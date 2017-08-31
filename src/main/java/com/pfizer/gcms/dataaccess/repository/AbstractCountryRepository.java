/**
 * 
 */
package com.pfizer.gcms.dataaccess.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.pfizer.gcms.dataaccess.common.exception.GCMSBadDataException;
import com.pfizer.gcms.dataaccess.dto.ISearchDTO;
import com.pfizer.gcms.dataaccess.model.AbstractCountryModel;
import com.pfizer.gcms.dataaccess.model.CountryModel;

/**
 * @author rtalapaneni
 * The Abstract Country Repository will provide methods for accessing, creating and updating the Models
 * stored in the database. All other Repositories will inherit from it.
 * @param <ModelType> the model type used by repository Country implementation
 */
public abstract class AbstractCountryRepository<ModelType extends AbstractCountryModel>
extends AbstractRepository<ModelType> {
	private static final Log LOG = LogFactory.getLog(AbstractCountryRepository.class);
	
	/**
	 * Default constructor.
	 * @param country
	 * 			(Locale) - To enable country Locale for the specific country.
	 */
	public AbstractCountryRepository(Locale country) {
		super(country);
	}
	
	/* (non-Javadoc)
	 * @see com.pfizer.pgrd.gts.dataaccess.repository.AbstractRepository
	 * #find(com.pfizer.pgrd.gts.dataaccess.dto.ISearchDTO)
	 */
	@Override
	public List<ModelType> find(ISearchDTO<ModelType> searchDTO) throws Exception {
		if (searchDTO == null || searchDTO.getCountryCode() == null) {
			String message = "Invalid Search DTO";
			LOG.warn(message);
			throw new GCMSBadDataException(message);
		}
		
		EntityManager entityManager = getEntityManager();
		List<ModelType> models = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ModelType> query = builder.createQuery(getModelType());
		Root<ModelType> root = query.from(getModelType());
		
		Predicate countryPredicate = prepareCountryPredicate(root, searchDTO.getCountryCode());
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (deleteFilter == null){
			predicates.add(countryPredicate);
		} else {
			predicates.add(deleteFilter);
			predicates.add(countryPredicate);
		}
		query.where(buildPredicatesArray(predicates));
		String sortByField = sortByField();
		if (sortByField != null){
			applySorting(builder, query, sortByField, sortDescending(), root);
		}
		query = query.select(root);
		models = entityManager.createQuery(query).getResultList();
		return models;
	}
	
	/**
	 * Prepares the country predicate.
	 * @param rootModelType
	 * 		(Root<ModelType>) - The root model type
	 * @param isoCode
	 * 		(String) - The isoCode of the country
	 * @return the predicate
	 */
	public Predicate prepareCountryPredicate(Root<ModelType> rootModelType, String isoCode) {
		return rootModelType.get
		(AbstractCountryModel.FIELD_COUNTRY).get(CountryModel.FIELD_ISO_CODE)
		.in(isoCode);
	}
}
