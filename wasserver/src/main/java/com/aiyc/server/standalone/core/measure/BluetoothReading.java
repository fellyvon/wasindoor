package com.aiyc.server.standalone.core.measure;

import com.aiyc.server.standalone.db.IEntity;

/**
 * @see com.aiyc.base.core.measure.BluetoothReading
 * @author felly
 * 
 */
public class BluetoothReading extends
		com.aiyc.base.core.measure.BluetoothReading implements IEntity<Integer> {

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
