package com.pfizer.gcms.dataaccess.repository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.time.StopWatch;

import com.pfizer.gcms.dataaccess.common.exception.GCMSBadDataException;
import com.pfizer.gcms.dataaccess.model.ProfileRequestModel;
import com.pfizer.gcms.dataaccess.model.TaskModel;

/**
 * @author Kaswas The ProfileRequests Repository will provide methods for
 *         accessing ProfileRequests stored in the database.
 */
public class ProfileRequestRepository extends AbstractRepository<ProfileRequestModel> {
	private static final Log LOG = LogFactory.getLog(AbstractRepository.class);

	/**
	 * Creates a new instance of the ProfileRequest Repository and configures
	 * the abstract class with the correct models.
	 * 
	 * @param country
	 *            (Locale) – The country by which to limit the result list
	 */
	public ProfileRequestRepository(Locale country) {
		super(country);
		setModelType(ProfileRequestModel.class);
	}

	public ProfileRequestRepository() {
		setModelType(ProfileRequestModel.class);
	}

	/**
	 * @description Updates the current instance of Model and persists the
	 *              changes to the database.
	 * @param item
	 *            (ProfileRequestModel) – The representation containing the data
	 *            that was just updated
	 * @return ProfileRequestModel
	 * @throws Exception
	 *             if save is unsuccessful
	 */
	public ProfileRequestModel update(ProfileRequestModel item) throws Exception {
		LOG.debug("Inside update(ProfileRequestModel item)");

		StopWatch profilerequesttimer = new StopWatch();
		profilerequesttimer.start();
		EntityManager entityManager = getEntityManager();
		StopWatch timer1 = new StopWatch();
		timer1.start();
		ProfileRequestModel result = entityManager.merge(item);
		LOG.debug("#Performance#Repository#Total time took only for merge during update is - "
				+ profilerequesttimer.getTime());
		LOG.debug("#Performance#Repository#Total time took for update(ModelType item) operation is - "
				+ profilerequesttimer.getTime());
		return result;
	}

	/**
	 * @description find ReviewerRecord in ProfileRequest table according to
	 *              logged in userNTID database.
	 * @param item
	 *            (ProfileRequestModel) – The representation containing the data
	 *            that was just updated
	 * @return ProfileRequestModel
	 * @throws Exception
	 *             if save is unsuccessful
	 */
@SuppressWarnings("rawtypes")	public List<ProfileRequestModel> findReviewerRecord(String userNTID,String status) throws Exception {
		LOG.debug("Inside method List<ProfileRequestModel> findReviewerRecord(String userNTID )" + userNTID);
		if (null == userNTID || userNTID.trim().isEmpty()) {
			String message = "Invalid userNTID";
			LOG.warn(message);
			throw new GCMSBadDataException(message);
		}

		EntityManager entityManager = getEntityManager();
		LOG.debug("entityManager" + entityManager);
		List<ProfileRequestModel> models = null;
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		LOG.debug("criteria" + criteria);

		Query typedQuery = null;
		userNTID = '%' + userNTID + '%';
		typedQuery = entityManager.createQuery(
				"select name FROM com.pfizer.gcms.dataaccess.model.CountryModel where id IN (select countries.id FROM com.pfizer.gcms.dataaccess.model.CountryReviewerModel WHERE UPPER(cntryReviewer) Like UPPER((:userNTID)))");
		typedQuery.setParameter("userNTID", userNTID.trim());
		LOG.debug("typedQuery" + typedQuery);
		List<String> country = typedQuery.getResultList();

		// Get Profile Review data from GCMS_PROFILE_REQUEST table and Created user information from GCMS_USERS table.
		try {
			if (null == status || status.trim().isEmpty()) {
			typedQuery = entityManager.createQuery(
					"SELECT pr.id,pr.profileTypeId,pr.firstName,pr.lastName,pr.organizationName,pr.country,pr.address,pr.city,"
					+ "pr.specility,pr.notes,pr.status,pr.bpid, user.firstName, user.lastName, user.userName, pr.regionId, pr.tempBpid, pr.uniqueTypeCodeForCCID, pr.uniqueTypeCodeForNIT FROM com.pfizer.gcms.dataaccess.model.ProfileRequestModel pr, "
					+ "com.pfizer.gcms.dataaccess.model.UserModelNew user WHERE  UPPER(pr.createdBy) = UPPER(user.userName) and pr.status= 'Pending'and  pr.country IN (:country) order by pr.createdDate DESC");
			typedQuery.setParameter("country", country);
			
			}else{
				
				typedQuery = entityManager.createQuery(
						"SELECT pr.id,pr.profileTypeId,pr.firstName,pr.lastName,pr.organizationName,pr.country,pr.address,pr.city,"
						+ "pr.specility,pr.notes,pr.status,pr.bpid, user.firstName, user.lastName, user.userName, pr.regionId, pr.tempBpid, pr.uniqueTypeCodeForCCID, pr.uniqueTypeCodeForNIT FROM com.pfizer.gcms.dataaccess.model.ProfileRequestModel pr, "
						+ "com.pfizer.gcms.dataaccess.model.UserModelNew user WHERE UPPER(pr.createdBy) = UPPER(user.userName) and  pr.status IN (:status) and pr.country IN (:country) order by pr.createdDate DESC");
				typedQuery.setParameter("country", country);
				typedQuery.setParameter("status", status);
			}
			models = new ArrayList<ProfileRequestModel>();
			List newModels = null;
			newModels = typedQuery.getResultList();
			for (Iterator iterator = newModels.iterator(); iterator.hasNext();) {
				 Object[] values = (Object[]) iterator.next();
				ProfileRequestModel model = new ProfileRequestModel();
				model.setId((BigDecimal)values[0]);
				model.setProfileTypeId((String)values[1]);
				model.setFirstName((String)values[2]);
				model.setLastName((String)values[3]);
				model.setOrganizationName((String)values[4]);
				model.setCountry((String)values[5]);
				model.setAddress((String)values[6]);
				model.setCity((String)values[7]);
				model.setSpecility((String)values[8]);
				model.setNotes((String)values[9]);
				model.setStatus((String)values[10]);
				model.setBpid((BigDecimal)values[11]); 
				model.setCreatedBy((String)values[12] + " " + (String)values[13] + "(" + (String)values[14] + ")");
				model.setRegionId((BigDecimal)values[15]); 
				model.setTempBpid((BigDecimal)values[16]);
				model.setUniqueTypeCodeForCCID((BigDecimal)values[17]);
				model.setUniqueTypeCodeForNIT((BigDecimal)values[18]);
				models.add(model);
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
LOG.debug("models" + models);
		if (models == null || models.isEmpty()) {
			return null;
		}
		return models;
	}	
	public List<ProfileRequestModel> findPendingRequestForReminder(String daysOffset) throws Exception {
		LOG.debug("Inside method List<ProfileRequestModel> findPendingRequestForReminder(String daysOffset)" + daysOffset);
		if (null == daysOffset || daysOffset.trim().isEmpty()) {
			String message = "Invalid userNTID";
			LOG.warn(message);
			throw new GCMSBadDataException(message);
		}

		EntityManager entityManager = getEntityManager();
		LOG.debug("entityManager" + entityManager);
		List<ProfileRequestModel> models = null;
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		LOG.debug("criteria" + criteria);

		Query typedQuery = null;
		//userNTID = '%' + userNTID + '%';
		/*typedQuery = entityManager.createQuery(
				"select name FROM com.pfizer.gcms.dataaccess.model.CountryModel where id IN (select countries.id FROM com.pfizer.gcms.dataaccess.model.CountryReviewerModel WHERE UPPER(cntryReviewer) Like UPPER((:userNTID)))");
		typedQuery.setParameter("userNTID", userNTID.trim());
		LOG.debug("typedQuery" + typedQuery);
		List<String> country = typedQuery.getResultList();*/

		typedQuery = entityManager.createQuery(
				"FROM com.pfizer.gcms.dataaccess.model.ProfileRequestModel where STATUS='Pending' order by createdDate DESC");
	
		//typedQuery.setParameter("country", country);
		LOG.debug("typedQuery" + typedQuery);
		models = typedQuery.getResultList();
		LOG.debug("models" + models);
		if (models == null || models.isEmpty()) {
			return null;
		}
		return models;
	}
	public List<ProfileRequestModel> findAllRequest(String status) throws Exception {
		LOG.debug("Inside method List<ProfileRequestModel> findAllRequest(String userNTID )" );
		EntityManager entityManager = getEntityManager();
		LOG.debug("entityManager" + entityManager);
		List<ProfileRequestModel> models = null;
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		LOG.debug("criteria" + criteria);

		Query typedQuery = null;
		
		// Get Profile Review data from GCMS_PROFILE_REQUEST table and Created user information from GCMS_USERS table.
		try {
			if (null == status || status.trim().isEmpty()) {
			typedQuery = entityManager.createQuery(
					"SELECT pr.id,pr.profileTypeId,pr.firstName,pr.lastName,pr.organizationName,pr.country,pr.address,pr.city,"
					+ "pr.specility,pr.notes,pr.status,pr.bpid,user.firstName, user.lastName, user.userName,pr.regionId, pr.tempBpid, pr.uniqueTypeCodeForCCID, pr.uniqueTypeCodeForNIT FROM com.pfizer.gcms.dataaccess.model.ProfileRequestModel pr, "
					+ "com.pfizer.gcms.dataaccess.model.UserModelNew user WHERE UPPER(pr.createdBy) = UPPER(user.userName) and pr.status= 'Pending'  order by pr.createdDate DESC");
		
			}
			else{
				typedQuery = entityManager.createQuery(
						"SELECT pr.id,pr.profileTypeId,pr.firstName,pr.lastName,pr.organizationName,pr.country,pr.address,pr.city,"
						+ "pr.specility,pr.notes,pr.status,pr.bpid, user.firstName, user.lastName, user.userName,pr.regionId, pr.tempBpid, pr.uniqueTypeCodeForCCID, pr.uniqueTypeCodeForNIT FROM com.pfizer.gcms.dataaccess.model.ProfileRequestModel pr, "
						+ "com.pfizer.gcms.dataaccess.model.UserModelNew user WHERE UPPER(pr.createdBy) = UPPER(user.userName) and pr.status IN (:status) order by pr.createdDate DESC");
				typedQuery.setParameter("status", status);
			}
			models = new ArrayList<ProfileRequestModel>();
			List newModels = null;
			newModels = typedQuery.getResultList();
			for (Iterator iterator = newModels.iterator(); iterator.hasNext();) {
				 Object[] values = (Object[]) iterator.next();
				ProfileRequestModel model = new ProfileRequestModel();
				model.setId((BigDecimal)values[0]);
				model.setProfileTypeId((String)values[1]);
				model.setFirstName((String)values[2]);
				model.setLastName((String)values[3]);
				model.setOrganizationName((String)values[4]);
				model.setCountry((String)values[5]);
				model.setAddress((String)values[6]);
				model.setCity((String)values[7]);
				model.setSpecility((String)values[8]);
				model.setNotes((String)values[9]);
				model.setStatus((String)values[10]);
				model.setBpid((BigDecimal)values[11]); 
				model.setCreatedBy((String)values[12] + " " + (String)values[13] + "(" + (String)values[14] + ")");
				model.setRegionId((BigDecimal)values[15]);
				model.setTempBpid((BigDecimal)values[16]);
				model.setUniqueTypeCodeForCCID((BigDecimal)values[17]);
				model.setUniqueTypeCodeForNIT((BigDecimal)values[18]);
				models.add(model);
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
LOG.debug("models" + models);
		if (models == null || models.isEmpty()) {
			return null;
		}
		return models;
	}
}
