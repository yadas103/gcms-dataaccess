package com.pfizer.gcms.dataaccess.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pfizer.gcms.dataaccess.common.NumericUtil;
import com.pfizer.gcms.dataaccess.common.exception.GCMSBadDataException;
import com.pfizer.gcms.dataaccess.model.AbstractModel;
import com.pfizer.gcms.dataaccess.model.ConsentAnnexModel;

/**
 * @author khans129
 * The ConsentAnnex Repository will provide methods for accessing data stored in the database.
 */
public class ConsentAnnexRepository extends AbstractRepository<ConsentAnnexModel> {
	/**
	 * Creates a new instance of the ConsentAnnex Repository and configures the abstract class with the correct models.
	 * 
	 * @param country
	 *            (Locale) – The country by which to limit the result list
	 */
	public ConsentAnnexRepository(Locale country) {
		super(country);
		setModelType(ConsentAnnexModel.class);
	}
	private static final Log LOG = LogFactory.getLog(AbstractRepository.class);

	/**selim
	 * Returns the representation of the current Model.
	 * @param id
	 *            (ModelType id) – The representation containing the data that was just updated
	 * @return ModelType
	 * @throws Exception
	 * 		If id is invalid and able to found
	 */
	public List<ConsentAnnexModel> findBPid(BigDecimal id) throws Exception {
		LOG.debug("Inside method find(BigDecimal id)");
		StopWatch timer = new StopWatch();
        timer.start();
        
		if (NumericUtil.isNullOrLessThanOrEqualToZero(id)) {
			throw new GCMSBadDataException("Invalid ID");
		}
		
		EntityManager entityManager = getEntityManager();
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<ConsentAnnexModel> query = builder.createQuery(getModelType());
		Root<ConsentAnnexModel> root = query.from(getModelType());
		Predicate idPredicate = builder.equal(root.get(ConsentAnnexModel.FIELD_BPID), id);
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		if (deleteFilter != null) {
			query.where(deleteFilter);
		}
		Predicate predicate = deleteFilter == null 
				? idPredicate : builder.and(idPredicate, deleteFilter);
		query.where(predicate);
		query = query.select(root);
		List<ConsentAnnexModel> models1 = entityManager.createQuery(query).getResultList();
		
		LOG.debug("#Performance#Repository#Total time took for find(BigDecimal id) operation is - " + timer.getTime());
		
		if (models1 == null || models1.isEmpty()) {
			return null;
		} 
		return models1;
	}
}
