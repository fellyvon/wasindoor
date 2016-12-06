 
package com.aiyc.server.standalone.core.measure;

import com.aiyc.server.standalone.db.IEntity;

/**
 * @see com.aiyc.base.core.measure.GSMReading
 * @author felly
 * 
 */
public class GSMReading extends com.aiyc.base.core.measure.GSMReading
		implements IEntity<Integer> {

	private Integer id;

	/**
	 * @return the database id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

}
