 
package com.aiyc.server.standalone.db;

import java.io.Serializable;

/**
 * Generic interface for a database entity
 * 
 * @author felly
 *
 * @param <ID> Primary key type
 */
public interface IEntity<ID extends Serializable> {
	/**
	 * gets the primary key
	 * 
	 * @return primary key 
	 */
	public ID getId();
	
	/**
	 * sets the primary key
	 * 
	 * @param id primary key
	 */
	public void setId(ID id);
}
