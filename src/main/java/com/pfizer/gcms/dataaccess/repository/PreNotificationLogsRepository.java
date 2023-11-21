package com.pfizer.gcms.dataaccess.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pfizer.gcms.dataaccess.model.PreConsentTemplateModel;
import com.pfizer.gcms.dataaccess.model.PreNotificationLogsModel;

public class PreNotificationLogsRepository extends AbstractRepository<PreNotificationLogsModel> {
	private static final Log LOG = LogFactory.getLog(AbstractRepository.class);

	public PreNotificationLogsRepository() {
		setModelType(PreNotificationLogsModel.class);
	}
	public PreNotificationLogsModel findCode(String code) throws Exception {
		LOG.debug("Inside method find(String code)");
		StopWatch timer = new StopWatch();
		timer.start();

		EntityManager entityManager = getEntityManager();

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<PreNotificationLogsModel> query = builder.createQuery(getModelType());
		Root<PreNotificationLogsModel> root = query.from(getModelType());
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		if (deleteFilter != null) {
			query.where(deleteFilter);
		}
		query = query.select(root);
		List<PreNotificationLogsModel> models = entityManager.createQuery(query).getResultList();

		LOG.debug("#Performance#Repository#Total time took for find(BigDecimal id) operation is - " + timer.getTime());

		if (models == null || models.isEmpty()) {
			return null;
		} else if (models.size() > 1) {
			throw new NonUniqueResultException();
		}
		return models.get(0);
	}
	
}