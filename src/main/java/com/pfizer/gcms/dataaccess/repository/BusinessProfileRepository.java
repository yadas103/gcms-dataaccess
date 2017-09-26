package com.pfizer.gcms.dataaccess.repository;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pfizer.gcms.dataaccess.common.exception.GCMSBadDataException;
import com.pfizer.gcms.dataaccess.model.BusinessProfileModel;


public class BusinessProfileRepository extends AbstractRepository<BusinessProfileModel>{

	private static final Log LOG = LogFactory.getLog(AbstractRepository.class);
	public BusinessProfileRepository() {
		LOG.debug("Inside method BusinessProfileRepository()");		
		setModelType(BusinessProfileModel.class);
	}	
	
@SuppressWarnings("unchecked")
@Override	
public  List<BusinessProfileModel> findByCountry(String name,String type,String lastName, String city ) throws Exception {
	LOG.debug("Inside method List<BusinessProfileModel> findByCountry(String name,String type,String lastName )" +name );
		if (name == null || name.trim().isEmpty()) {
			String message = "Invalid Country Name";
			LOG.warn(message);
			throw new GCMSBadDataException(message);
		}
		
		EntityManager entityManager = getEntityManager();
		LOG.debug("entityManager"+entityManager);
		List<BusinessProfileModel> models = null;
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		LOG.debug("criteria"+criteria);
		CriteriaQuery<BusinessProfileModel> query = criteria.createQuery(getModelType());		
		Query typedQuery;
		lastName = '%'+lastName+'%';
		if(city != null){
		city = '%'+city+'%';
		 typedQuery = entityManager.createQuery("FROM com.pfizer.gcms.dataaccess.model.BusinessProfileModel WHERE country = (:country) and (UPPER(lastName) LIKE UPPER((:lastName)) or UPPER(organisationName) LIKE UPPER((:organisationName))) and profileType = (:profileType) and UPPER(city) LIKE UPPER((:city))");		
		typedQuery.setParameter("country", name.trim());
		typedQuery.setParameter("lastName", lastName.trim());
		typedQuery.setParameter("organisationName", lastName.trim());
		typedQuery.setParameter("profileType", type.trim());
		typedQuery.setParameter("city", city.trim());	
	
		LOG.debug("typedQuery"+typedQuery);
			
		}				
		else 
		{
			 typedQuery = entityManager.createQuery("FROM com.pfizer.gcms.dataaccess.model.BusinessProfileModel WHERE country = (:country) and (UPPER(lastName) LIKE UPPER((:lastName)) or UPPER(organisationName) LIKE UPPER((:organisationName))) and profileType = (:profileType) ");			
				typedQuery.setParameter("country", name.trim());
				typedQuery.setParameter("lastName", lastName.trim());
				typedQuery.setParameter("profileType", type.trim());
				typedQuery.setParameter("organisationName", lastName.trim());
		}
		models = typedQuery.getResultList();
		LOG.debug("models" +models);
		if (models == null || models.isEmpty()) {
			return null;
		}
		return models;
	}
	
@SuppressWarnings("unchecked")
@Override	
public  List<BusinessProfileModel> findById(BigDecimal[] id) throws Exception {
	LOG.debug("Inside method List<BusinessProfileModel> findById(BigDecimal[] id )" );
		if (id == null) {
			String message = "Invalid selection";
			LOG.warn(message);
			throw new GCMSBadDataException(message);
		}
		
		EntityManager entityManager = getEntityManager();
		LOG.debug("entityManager"+entityManager);
		List<BusinessProfileModel> models = null;
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		LOG.debug("criteria"+criteria);
		CriteriaQuery<BusinessProfileModel> query = criteria.createQuery(getModelType());
		LOG.debug("query:getModelType()"+query +" "+ getModelType());
		Root<BusinessProfileModel> root = query.from(getModelType());
		LOG.debug("root"+root);
		Expression<BigDecimal[]> idExp = root.get(BusinessProfileModel.FIELD_BP_ID);
		LOG.debug("countryNameExp"+idExp);		
		List<BigDecimal> ids = Arrays.asList(id);								
		LOG.debug("query"+query);
		Query typedQuery = entityManager.createQuery("FROM com.pfizer.gcms.dataaccess.model.BusinessProfileModel WHERE id IN (:ids)");
		typedQuery.setParameter("ids", ids);
		LOG.debug("typedQuery"+typedQuery);
		models = typedQuery.getResultList();
		LOG.debug("models" +models);
		if (models == null || models.isEmpty()) {
			return null;
		}
		return models;
	}
public  List<BusinessProfileModel> ValidationfindById(BigDecimal id) throws Exception {
	LOG.debug("Inside method List<BusinessProfileModel> ValidationfindById(BigDecimal id )" );
		if (id == null) {
			String message = "Invalid selection";
			LOG.warn(message);
			throw new GCMSBadDataException(message);
		}
		
		EntityManager entityManager = getEntityManager();
		LOG.debug("entityManager"+entityManager);
		List<BusinessProfileModel> models = null;
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		LOG.debug("criteria"+criteria);
		CriteriaQuery<BusinessProfileModel> query = criteria.createQuery(getModelType());
		LOG.debug("query:getModelType()"+query +" "+ getModelType());
		Root<BusinessProfileModel> root = query.from(getModelType());
		LOG.debug("root"+root);
		Expression<BigDecimal> idExp = root.get(BusinessProfileModel.FIELD_BP_ID);
		LOG.debug("countryNameExp"+idExp);		
		List<BigDecimal> ids = Arrays.asList(id);								
		LOG.debug("query"+query);
		Query typedQuery = entityManager.createQuery("FROM com.pfizer.gcms.dataaccess.model.BusinessProfileModel WHERE id IN (:ids)");
		typedQuery.setParameter("ids", ids);
		LOG.debug("typedQuery"+typedQuery);
		models = typedQuery.getResultList();
		LOG.debug("models" +models);
		if (models == null || models.isEmpty()) {
			return null;
		}
		return models;
	}
}
