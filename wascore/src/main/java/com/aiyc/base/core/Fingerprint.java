package com.aiyc.base.core;

import com.aiyc.base.core.Location;
import com.aiyc.base.core.Measurement;

/**
 * 指纹库定义，用于关联设备和位置信息
 * 
 * @author felly
 */
public class Fingerprint {

	/* the measurement that and the location which are associated hereby */
	protected Location location;
	protected Measurement measurement;

	/* **************** Constructors **************** */

	public Fingerprint(Location location, Measurement measurement) {
		this.location = location;
		this.measurement = measurement;
	}

	/* ************ Getter / Setter Methods ************ */

	/**
	 * @return the reading
	 */
	public Measurement getMeasurement() {
		return measurement;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @param measurement
	 *            the measurement to set
	 */
	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

}
