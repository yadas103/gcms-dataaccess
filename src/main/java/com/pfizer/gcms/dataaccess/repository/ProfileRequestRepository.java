package com.pfizer.gcms.dataaccess.repository;

import java.util.Locale;

import javax.persistence.EntityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.time.StopWatch;

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
}
