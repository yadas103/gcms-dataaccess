package com.pfizer.gcms.dataaccess.repository;

import java.math.BigDecimal;
import java.util.List;

import com.pfizer.gcms.dataaccess.dto.ISearchDTO;
import com.pfizer.gcms.dataaccess.model.BaseModel;

/**
 * @author rtalapaneni
 * This class represents the all the declared methods.
 * @param <ModelType>
 * 		(ModelType) - The representation 
 */
public interface IRepository<ModelType extends BaseModel> {
	/**
	 * Returns the collection of all the instances of type ModelType an empty partial model is generated 
	 * and the Find overload is called.
	 * 
	 * @return ArrayList<ModelType>
	 * @throws Exception
	 * 		If find is unsuccessful
	 */
	public List<ModelType> find() throws Exception;

	/**
	 * Returns the collection of all the instances of type ModelType limited by the partial model 
	 * as search criteria.
	 * 
	 * @param item
	 *            (ModelType) – Partial model containing the search criteria
	 * @return ArrayList<ModelType>
	 * @throws Exception
	 * 		If find is unsuccessful
	 */
	public List<ModelType> find(ModelType item) throws Exception;
	
	/**
	 * Returns the collection of all the instances of type ModelType limited by the partial model 
	 * as search criteria.
	 * 
	 * @param item
	 *            (ModelType) – Partial model containing the search criteria
	 * @param pageIndex
	 * 			  (Integer)	- index from which records needs to be fetched
	 * @param numberOfRecords
	 * 			  (Integer) - max number of records to be fetched           
	 * @return ArrayList<ModelType>
	 * @throws Exception
	 * 		If find is unsuccessful
	 */
	public List<ModelType> find(ModelType item, int pageIndex, int numberOfRecords) throws Exception;
	
	/**
	 * Returns the representation of the current Model.
	 * 
	 * @param id
	 *            (ModelType id) – The representation containing the data that was just updated
	 * @return ModelType
	 * @throws Exception
	 * 		If find is unsuccessful
	 */
	public ModelType find(BigDecimal id) throws Exception;

	/**
	 * Creates a new ModelType.
	 * 
	 * @param item
	 *            (ModelType) – The representation containing the data that was added
	 * @return the model type
	 * @throws Exception
	 *             if save is unsuccessful
	 */
	public ModelType create(ModelType item) throws Exception;

	/**
	 * Updates the current instance of Model and persists the changes to the database.
	 * 
	 * @param item
	 *            (ModelType) – The representation containing the data that was just updated
	 * @return the model type
	 * @throws Exception
	 *             if save is unsuccessful
	 */
	public ModelType update(ModelType item) throws Exception;

	/**
	 * Deletes the current instance of ModelType.
	 * 
	 * @param item
	 *            (ModelType) – The representation containing the data that needs to be deleted
	 * @throws Exception
	 *             if delete is unsuccessful
	 */
	public void delete(ModelType item) throws Exception;
	
	/**
	 * Deletes the current instance of ModelType.
	 * 
	 * @param id
	 *             – The representation containing the data that needs to be deleted
	 * @throws Exception
	 *             if delete is unsuccessful
	 */
	public void delete(BigDecimal id) throws Exception;
	
	/**
	 * @param item
	 * 		(ModelType) -  The item type of model
	 * @return the model type list
	 * @throws Exception
	 * 		If search is unsuccessful
	 */
	public List<ModelType> search(ModelType item) throws Exception;
	
	/**
	 * Gives the model type associated with the repository.
	 * @return the model type class
	 */
	public Class<ModelType> getModelType();
	
	/**
	 * Returns the collection of all the instances of type ModelType limited by the DTO 
	 * as search criteria.
	 * @param isearchDto
	 * 		(ISearchDTO<ModelType>) - The search criteria
	 * @return the model type list
	 * @throws Exception
	 * 		If delete is unsuccessful
	 */
	public List<ModelType> find(ISearchDTO<ModelType> isearchDto) throws Exception;

	/**
	 * Deactivates existing errors.
	 * @throws Exception
	 * 		If deactivating existing errors is unsuccessful
	 */
	public void deactivateExistingErrors() throws Exception;
}
