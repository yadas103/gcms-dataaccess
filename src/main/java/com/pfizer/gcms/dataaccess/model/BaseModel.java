package com.pfizer.gcms.dataaccess.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * @author prayudu
 * BaseModel is  a POJO classes,annotated with hibernate mappings, 
 * they are responsible for holding instances of data objects.
 */
@MappedSuperclass
public interface BaseModel extends Serializable{
}
