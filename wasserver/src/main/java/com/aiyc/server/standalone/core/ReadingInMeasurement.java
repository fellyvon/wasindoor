 
package com.aiyc.server.standalone.core;

import com.aiyc.server.standalone.db.IEntity;

public class ReadingInMeasurement implements IEntity<Integer> {

	private Integer id = -1;
	private int measurementId = -1;
	private int readingId = -1;
	private String readingClassName = "";
	
	public ReadingInMeasurement() {}
	
	public ReadingInMeasurement(int measurementId, int readingId, String readingClassName) {
		this.setMeasurementId(measurementId);
		this.setReadingId(readingId);
		this.setReadingClassName(readingClassName);		
	}
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

	public void setMeasurementId(int measurementId) {
		this.measurementId = measurementId;
	}

	public int getMeasurementId() {
		return measurementId;
	}

	public void setReadingId(int readingId) {
		this.readingId = readingId;
	}

	public int getReadingId() {
		return readingId;
	}

	public void setReadingClassName(String readingClassName) {
		this.readingClassName = readingClassName;
	}

	public String getReadingClassName() {
		return readingClassName;
	}

}
