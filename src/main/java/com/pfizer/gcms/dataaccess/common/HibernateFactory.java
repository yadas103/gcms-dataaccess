/**
 * 
 */
package com.pfizer.gcms.dataaccess.common;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author rtalapaneni
 * This class provides base hibernate configurations for getting entity manager.
 */
public class HibernateFactory {
	private static EntityManagerFactory entityManagerFactory;

	/**
	 * Default constructor.
	 */
	private HibernateFactory() {}

	/**
	 * Creates the Entity manager factory from the data base manager.
	 * @return the entityManagerFactory
	 */
	public static EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("GTSDBManager");
		}
		return entityManagerFactory;
	}

	/**
	 * Creates the entity manager from factory class.
	 * @return the EntityManager 
	 */
	public static EntityManager createEntityManager() {
		return getEntityManagerFactory().createEntityManager();
	}
}
