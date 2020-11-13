package com.pfizer.gcms.dataaccess.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import com.pfizer.gcms.dataaccess.common.HibernateFactory;
import com.pfizer.gcms.dataaccess.common.exception.GCMSBadDataException;
import com.pfizer.gcms.dataaccess.dto.BusinessProfileDisplayDTO;
import com.pfizer.gcms.dataaccess.model.BusinessProfileModel;

/**
 * @author VENKAD09 The Business Profile Repository will provide methods for
 *         accessing data stored in the database.
 */

public class BusinessProfileRepository extends AbstractRepository<BusinessProfileModel> {

	private static final Log LOG = LogFactory.getLog(AbstractRepository.class);
	private BigDecimal regionId;

	/**
	 * Creates a new instance of the BusinessProfileRepository and configures
	 * the abstract class with the correct models.
	 * 
	 */

	public BusinessProfileRepository() {
		LOG.debug("Inside method BusinessProfileRepository()");
		setModelType(BusinessProfileModel.class);
	}

	/**
	 * @author VENKAD09
	 * @param String name, String type, String lastName, String city,String firstName, String address
	 * @return List of Business Profile Models
	 * @throws Exception
	 * @description findByCountry method to search for Business Profiles by Reporting Country
	 */

	@SuppressWarnings("unchecked")
	@Override
	public  List<BusinessProfileDisplayDTO> findByCountry(String name,String type,String lastName, String city,String firstName,String address,String speciality,String uniqueTypeCode,String uniqueTypeId,BigDecimal regionId) throws Exception {
		LOG.debug("Inside method List<BusinessProfileModel> findByCountry(String name,String type,String lastName, String city,String firstName,String address,String speciality,String uniqueTypeCode,BigDecimal uniqueTypeId, BigDecimal regionId)" );
		BigDecimal zero = BigDecimal.ZERO;
			if (name == null || name.trim().isEmpty()) {
				String message = "Invalid Country Name";
				LOG.warn(message);
				throw new GCMSBadDataException(message);
			}
			
			EntityManager entityManager = getEntityManager();
			LOG.debug("entityManager"+entityManager);
			List<BusinessProfileDisplayDTO> models = null;
				
			try {
				
				Session session = entityManager.unwrap(org.hibernate.Session.class);
				SessionFactory sessionFactory = session.getSessionFactory();
				SessionFactoryImplementor impl = (SessionFactoryImplementor)sessionFactory;
				ConnectionProvider cp = impl.getConnectionProvider();
				Connection conn = cp.getConnection();
				String sqlString = "query in here";
				conn.setAutoCommit(false);

				// create the statement object  
				Statement stmt=conn.createStatement(); 		
				
				/**
				 * @author ARUNKV
				 * R2.0 Starts
				 * Profile creation
				 */
				String genericQueryHead = "select 'N' as TEMP_PROFILE, BP_ID, PROFILE_TYPE_ID, FIRST_NAME, LAST_NAME,ORGANISATION_NAME,CITY, ADDR_LN_1_TXT,SPECIALITY,STATUS,UNIQUE_TYPE_CODE,UNQ_ID_VAL from GCMS_ODS.GCMS_BUS_PROFILE_MVIEW_NEW" ;
				String genericQueryTail = "  where COUNTRY= '"+name.trim()+"' and PROFILE_TYPE_ID= '"+type.trim()+"' ";
				
				String unionHead = "select * from (";
				String unionTail = ") where 1=1 ";
				
				String colombiaQueryHead = "select 'Y' as TEMP_PROFILE, TEMP_BP_ID as BP_ID, TO_CHAR(PROFILE_TYPE_ID), FIRST_NAME, LAST_NAME,ORGANISATION_NAME,CITY, ADDRESS as ADDR_LN_1_TXT,SPECILITY as SPECIALITY, NULL, NULL, NULL from GCMS_ODS.GCMS_PROFILE_REQUEST";
				String colombiaQueryTail = "and REG_ID= 5 and BP_ID IS NULL";
				
				String searchBPQuery = (name.trim().equalsIgnoreCase("COLOMBIA")) 
						? unionHead.concat(genericQueryHead)
								.concat(genericQueryTail.concat((regionId != null) ? " and UNIQUE_TYPE_CODE = 'TR-ID' and REG_ID LIKE ('"+regionId+"')" : ""))
								.concat(" UNION ")
								.concat(colombiaQueryHead)
								.concat(genericQueryTail)
								.concat(colombiaQueryTail)
								.concat(unionTail)
						: genericQueryHead.concat(genericQueryTail);
				/**R2.0 changes ends**/
				
				if(lastName != null && !lastName.equals("lastName")){
				lastName = '%'+lastName+'%';	
				searchBPQuery = searchBPQuery + " and( " + 
						"  upper(LAST_NAME) like upper('"+lastName.trim()+"') " + 
						"	  or upper(ORGANISATION_NAME) like upper('"+lastName.trim()+"') " + 
						"	 )";			
				}
				if(city != null && !city.equals("city")){
					city = '%'+city+'%';
					searchBPQuery = searchBPQuery + " and UPPER(CITY) LIKE UPPER('"+city.trim()+"')";
				
				}
				if(firstName != null && !firstName.equals("firstName")){
					firstName = '%'+firstName+'%';
					searchBPQuery = searchBPQuery + " and UPPER(FIRST_NAME) LIKE UPPER('"+firstName.trim()+"')";
				
				}
				if(address != null && !address.equals("address")){
					address = '%'+address+'%';
					searchBPQuery = searchBPQuery + " and UPPER(ADDR_LN_1_TXT) LIKE UPPER('"+address.trim()+"')";
				
				}
				if(speciality != null && !speciality.equals("speciality")){
					speciality = '%'+speciality+'%';
					searchBPQuery = searchBPQuery + " and UPPER(SPECIALITY) LIKE UPPER('"+speciality.trim()+"')";
				
				}
			
			  	if(uniqueTypeCode != null && !uniqueTypeId.equals("uniqueTypeId")){
				  uniqueTypeCode = '%'+uniqueTypeCode+'%';
				  searchBPQuery = searchBPQuery + " and bp_id in \r\n" + 
				  		"(select master_bp_id from GCMS_ODS.GCMS_BUS_PROFILE_MVIEW_NEW\r\n" + 
				  		"where UPPER(UNIQUE_TYPE_CODE) LIKE UPPER('"+uniqueTypeCode.trim()+"') and UNQ_ID_VAL LIKE ('"+uniqueTypeId+"'))";
				}
				if(regionId.intValue() == 5 && uniqueTypeId.equals("uniqueTypeId")){
					  searchBPQuery = searchBPQuery+" and STATUS = 'ACTIVE' ";
				}
			 
				PreparedStatement pStmt = conn.prepareStatement(searchBPQuery);
				
							
				LOG.info("Before time execute" + new Date().toString());
				LOG.info("Query executed " + searchBPQuery);
				
				// execute query  
				ResultSet resultSet = pStmt.executeQuery(); 
				resultSet.setFetchSize(10000);
				LOG.info("After query execute" + new Date().toString() );
				models = new ArrayList<BusinessProfileDisplayDTO>();
				while(resultSet.next()) {
					// Populate Business Profile DTO to be send to service layer
					BusinessProfileDisplayDTO bp = new BusinessProfileDisplayDTO();
					bp.setId(resultSet.getBigDecimal("BP_ID"));
					bp.setProfileType(resultSet.getString("PROFILE_TYPE_ID"));
					bp.setFirstName(resultSet.getString("FIRST_NAME"));
					bp.setLastName(resultSet.getString("LAST_NAME"));
					bp.setOrganisationName(resultSet.getString("ORGANISATION_NAME"));
					bp.setCity(resultSet.getString("CITY"));
					bp.setAddress(resultSet.getString("ADDR_LN_1_TXT"));
					bp.setSpeciality(resultSet.getString("SPECIALITY"));
					// R2.0 - arunkv
					bp.setIsTempProfile(resultSet.getString("TEMP_PROFILE"));
					models.add(bp);
				}
				
				// close the connection object 
				resultSet.close();
				pStmt.close();
				conn.close();  
				LOG.info("After closing connection" + new Date().toString() );
				
			} catch (Exception e) {
				LOG.debug(e.getStackTrace());
			}
						
			LOG.debug("models" +models);
			if (models == null || models.isEmpty()) {
				return null;
			}
			return models;
		}
	/**
	 * @author KHANS129
	 * @param BigDecimal[] id
	 * @return List of Business Profile Models
	 * @throws Exception
	 * @description findById method to search for Business Profiles by array of id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BusinessProfileModel> findById(BigDecimal[] id) throws Exception {
		LOG.debug("Inside method List<BusinessProfileModel> findById(BigDecimal[] id )");
		if (id == null) {
			String message = "Invalid selection";
			LOG.warn(message);
			throw new GCMSBadDataException(message);
		}
		EntityManager entityManager = getEntityManager();
		List<BusinessProfileModel> models = null;
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<BusinessProfileModel> query = criteria.createQuery(getModelType());
		Root<BusinessProfileModel> root = query.from(getModelType());
		Expression<BigDecimal[]> idExp = root.get(BusinessProfileModel.FIELD_BP_ID);
		List<BigDecimal> ids = Arrays.asList(id);
		Query typedQuery = entityManager.createQuery("FROM com.pfizer.gcms.dataaccess.model.BusinessProfileModel WHERE id IN (:ids) AND regionId = (:regionId)");
		typedQuery.setParameter("ids", ids);
		typedQuery.setParameter("regionId", regionId);
		models = typedQuery.getResultList();
		LOG.debug("models" + models);
		if (models == null || models.isEmpty()) {
			return null;
		}
		return models;
	}

	/**
	 * @Kaswas
	 * @param bpid
	 * @return profile of that particular bpid
	 * @throws Exception
	 * @description this method is used to get data for one particular Bpid from
	 *              BusinessProfile table for validation in Profile Review
	 */
	public List<BusinessProfileModel> validationfindById(BigDecimal id,BigDecimal regionId) throws Exception {
		LOG.debug("Inside method List<BusinessProfileModel> validationfindById(BigDecimal id )");
		if (id == null) {
			String message = "Invalid selection";
			LOG.warn(message);
			throw new GCMSBadDataException(message);
		}

		EntityManager entityManager = getEntityManager();		
		List<BusinessProfileModel> models = null;
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();		
		CriteriaQuery<BusinessProfileModel> query = criteria.createQuery(getModelType());
		Root<BusinessProfileModel> root = query.from(getModelType());
		Expression<BigDecimal> idExp = root.get(BusinessProfileModel.FIELD_BP_ID);
		List<BigDecimal> ids = Arrays.asList(id);
		Query typedQuery = entityManager
				.createQuery("FROM com.pfizer.gcms.dataaccess.model.BusinessProfileModel WHERE id IN (:ids) AND regionId = (:regionId)");
		typedQuery.setParameter("ids", ids);
		typedQuery.setParameter("regionId", regionId);
		models = typedQuery.getResultList();
		LOG.debug("models" + models);
		if (models == null || models.isEmpty()) {
			return null;
		}
		return models;
	}
	
	public List<BusinessProfileModel> validationfindByCode(BigDecimal id,BigDecimal regionId) throws Exception {
		LOG.debug("Inside method List<BusinessProfileModel> validationfindById(BigDecimal id )");
		if (id == null) {
			String message = "Invalid selection";
			LOG.warn(message);
			throw new GCMSBadDataException(message);
		}

		List<BusinessProfileModel> models = null;
		EntityManager entityManager = HibernateFactory.createEntityManager();		
		
		try {

			Session session = entityManager.unwrap(org.hibernate.Session.class);
			SessionFactory sessionFactory = session.getSessionFactory();
			SessionFactoryImplementor impl = (SessionFactoryImplementor) sessionFactory;
			ConnectionProvider cp = impl.getConnectionProvider();
			Connection conn = cp.getConnection();
			String sqlString = "query in here";
			conn.setAutoCommit(false);

			Statement stmt = conn.createStatement();

			String searchBPQuery = "select BP_ID, MASTER_BP_ID, UNQ_ID_VAL, PROFILE_TYPE_ID, FIRST_NAME, LAST_NAME, ORGANISATION_NAME, CITY, ADDR_LN_1_TXT,"
					+ " SPECIALITY, STATUS, UNIQUE_TYPE_CODE, COUNTRY, REG_ID from GCMS_ODS.GCMS_BUS_PROFILE_MVIEW_NEW  where UNQ_ID_VAL = '"
					+ id + "' and REG_ID = '" + regionId + "' and UNIQUE_TYPE_CODE IN ('NIT','CCID')";

			PreparedStatement pStmt = conn.prepareStatement(searchBPQuery);

			LOG.info("Before time execute" + new Date().toString());
			LOG.info("Query executed " + searchBPQuery);

			// execute query
			ResultSet resultSet = pStmt.executeQuery();
			System.out.println("Size of reslt set : " + resultSet.getFetchSize());
			LOG.info("After query execute" + new Date().toString());
			models = new ArrayList<BusinessProfileModel>();
			while (resultSet.next()) {
				// Populate Business Profile DTO to be send to service layer
				BusinessProfileModel bp = new BusinessProfileModel();
				bp.setId(resultSet.getBigDecimal("BP_ID"));
				bp.setMasterId(resultSet.getBigDecimal("MASTER_BP_ID"));
				bp.setProfileType(resultSet.getString("PROFILE_TYPE_ID"));
				bp.setFirstName(resultSet.getString("FIRST_NAME"));
				bp.setLastName(resultSet.getString("LAST_NAME"));
				bp.setOrganisationName(resultSet.getString("ORGANISATION_NAME"));
				bp.setCity(resultSet.getString("CITY"));
				bp.setAddress(resultSet.getString("ADDR_LN_1_TXT"));
				bp.setSpeciality(resultSet.getString("SPECIALITY"));
				bp.setUniqueTypeId(resultSet.getString("UNQ_ID_VAL"));
				bp.setCountry(resultSet.getString("COUNTRY"));
				models.add(bp);
			}
			//closing connection
			resultSet.close();
			pStmt.close();
			conn.close();
			session.disconnect();

		} catch (Exception e) {
			LOG.debug(e.getStackTrace());
		} 
		return models;
	}
	
}
