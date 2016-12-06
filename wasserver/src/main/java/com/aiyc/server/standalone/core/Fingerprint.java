package com.aiyc.server.standalone.core;

import com.aiyc.server.standalone.db.IEntity;

/**
 * @see com.aiyc.base.core.Fingerprint
 * @author felly
 * 
 */
public class Fingerprint extends com.aiyc.base.core.Fingerprint implements
		IEntity<Integer> {

	private Integer id;

	public Fingerprint(Location location, Measurement measurement) {
		super(location, measurement);
	}

	public Fingerprint() {
		super(new Location(), new Measurement());
	}

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
