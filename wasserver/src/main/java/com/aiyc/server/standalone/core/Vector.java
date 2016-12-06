package com.aiyc.server.standalone.core;

import com.aiyc.server.standalone.db.IEntity;

/**
 * @see java.util.Vector
 * @author felly
 * 
 * @param <E>
 *            contained objects
 */
public class Vector<E> extends java.util.Vector<E> implements IEntity<Integer> {

	private static final long serialVersionUID = 5314230691061190546L;

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
