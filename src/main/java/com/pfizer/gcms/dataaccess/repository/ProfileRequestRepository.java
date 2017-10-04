package com.pfizer.gcms.dataaccess.repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.time.StopWatch;

import com.pfizer.gcms.dataaccess.common.exception.GCMSBadDataException;
import com.pfizer.gcms.dataaccess.model.ProfileRequestModel;

/**
 * @author Kaswas
 * The ProfileRequests Repository will provide methods for accessing ProfileRequests stored in the database.
 */
public class ProfileRequestRepository extends AbstractRepository<ProfileRequestModel>  {
	private static final Log LOG = LogFactory.getLog(AbstractRepository.class);
	/**
	 * Creates a new instance of the ProfileRequest Repository and configures the abstract class with the correct models.
	 * 
	 * @param country
	 *            (Locale) – The country by which to limit the result list
	 */
	public ProfileRequestRepository(Locale country) {
		super(country);
		setModelType(ProfileRequestModel.class);
	}
	
	
	/**
	 * Updates the current instance of Model and persists the changes to the database.
	 * @param item
	 *            (ModelType) – The representation containing the data that was just updated
	 * @return ModelType
	 * @throws Exception
	 *             if save is unsuccessful
	 */
	public ProfileRequestModel update(ProfileRequestModel item) throws Exception {
		LOG.debug("Inside update(ProfileRequestModel item)");
		
		StopWatch timer = new StopWatch();
        timer.start();
		
		EntityManager entityManager = getEntityManager();
		
		StopWatch timer1 = new StopWatch();
        timer1.start();
        
        ProfileRequestModel result = entityManager.merge(item);
		
		LOG.debug("#Performance#Repository#Total time took only for merge during update is - " + timer1.getTime());
		
		LOG.debug("#Performance#Repository#Total time took for update(ModelType item) operation is - " + timer.getTime());
		
		return result;
	}
	public  List<ProfileRequestModel> findReviewerRecord(String userNTID ) throws Exception {
		LOG.debug("Inside method List<ProfileRequestModel> findReviewerRecord(String userNTID )" +userNTID );
			if (userNTID == null || userNTID.trim().isEmpty()) {
				String message = "Invalid userNTID";
				LOG.warn(message);
				throw new GCMSBadDataException(message);
			}
			
			EntityManager entityManager = getEntityManager();
			LOG.debug("entityManager"+entityManager);
			List<ProfileRequestModel> models = null;
			CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
			LOG.debug("criteria"+criteria);
			
			Query typedQuery;
			userNTID = '%'+userNTID+'%';
			typedQuery=entityManager.createQuery("select name FROM com.pfizer.gcms.dataaccess.model.CountryModel where id IN (select countries.id FROM com.pfizer.gcms.dataaccess.model.CountryReviewerModel WHERE UPPER(cntryReviewer) Like UPPER((:userNTID)))");
			typedQuery.setParameter("userNTID", userNTID.trim());
			LOG.debug("typedQuery"+typedQuery);
			List<String> country = typedQuery.getResultList();
			
			 typedQuery = entityManager.createQuery("FROM com.pfizer.gcms.dataaccess.model.ProfileRequestModel WHERE country IN (:country)");
			 typedQuery.setParameter("country", country);
			LOG.debug("typedQuery"+typedQuery);
			models = typedQuery.getResultList();
			LOG.debug("models" +models);
			if (models == null || models.isEmpty()) {
				return null;
			}
			return models;
		}
}
