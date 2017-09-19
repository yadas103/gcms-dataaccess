package com.pfizer.gcms.dataaccess.repository;

import java.util.Locale;

import javax.persistence.EntityManager;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




import com.pfizer.gcms.dataaccess.model.CountryReviewerModel;


public class CountryReviewerRepository extends AbstractRepository<CountryReviewerModel> {
	private static final Log LOG = LogFactory.getLog(CountryReviewerRepository.class);

	
	public CountryReviewerRepository(Locale country) {
		setModelType(CountryReviewerModel.class);
	}
	
	/** update function for edit countries details*/
	
	public CountryReviewerModel update(CountryReviewerModel item) throws Exception {
		LOG.debug("Inside update(ModelType item)");
		
		StopWatch timer = new StopWatch();
        timer.start();
		
		EntityManager entityManager = getEntityManager();
		
		
		StopWatch timer1 = new StopWatch();
        timer1.start();
        
        CountryReviewerModel result = entityManager.merge(item);
		
		LOG.debug("#Performance#Repository#Total time took only for merge during update is - " + timer1.getTime());
		
		LOG.debug("#Performance#Repository#Total time took for update(ModelType item) operation is - " + timer.getTime());
		
		return result;
		
	}
	

}
