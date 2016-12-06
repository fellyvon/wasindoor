 
package com.aiyc.server.standalone.core;

import com.aiyc.server.standalone.db.IEntity;

/**
 * @see com.aiyc.base.core.Map
 * @author felly
 *
 */
public class Map extends com.aiyc.base.core.Map implements IEntity<Integer> {
	
	private Integer id;

	/**
	 * @return the database id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * @param id
	 * 			the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	


}
