package com.pfizer.gcms.dataaccess.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pfizer.gcms.dataaccess.model.UserModelNew;

public class UserDetailRepository extends AbstractRepository<UserModelNew> {
	private static final Log LOG = LogFactory.getLog(UserDetailRepository.class);
	public UserDetailRepository(Locale country) {
		setModelType(UserModelNew.class);
	}
	public UserModelNew find(String userName)  {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<UserModelNew> query = builder.createQuery(getModelType());
		Root<UserModelNew> root = query.from(getModelType());
		Expression<String> userNameExp = root.get(UserModelNew.FIELD_USERNAME);
		Map<String, String> criteriaParameters = new HashMap<String, String>();
		Predicate userNamePredicate = buildCaseSensitiveEqualPredicate(builder, userNameExp, userName, 
				criteriaParameters);
		Predicate deleteFilter = prepareSoftDeletePredicate(builder, root);
		if (deleteFilter != null) {
			query.where(deleteFilter);
		}
		Predicate predicate = deleteFilter == null 
				? userNamePredicate : builder.and(userNamePredicate, deleteFilter);
		query.where(predicate);
		query = query.select(root);
		TypedQuery<UserModelNew> typedQuery = getEntityManager().createQuery(query);
		applyParameterExpressionValues(typedQuery, criteriaParameters);
		List<UserModelNew> models = typedQuery.getResultList();
		if (models == null || models.isEmpty()) {
			return null;
		} else if (models.size() > 1) {
			throw new NonUniqueResultException();
		}
		return models.get(0);
	}
}
