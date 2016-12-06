package com.aiyc.base.core;

import com.aiyc.base.core.Map;

/**
 * 位置点抽象定义
 * 
 * @author felly
 */
public class Location {

	/*
	 * unique identifier, commonly the name of this location e.g. 'IFW D47.2'
	 */
	protected String symbolicID = "";

	/*
	 * the Map where this location resides. includes path to image and a name
	 */
	protected Map map;

	/*
	 * X and Y coordinates of the location in the image referenced by fileName
	 * in pixel format
	 */
	protected String mapXcord = "";
	protected String mapYcord = "";

	/*
	 * StaticResources.LOCATION_UNKNOWN = location totally unknown
	 * StaticResources.LOCATION_KNOWN = location known Numbers in between define
	 * level of accuracy
	 */
	protected int accuracy = 0;

	/* **************** Constructors **************** */

	public Location() {
		this("", new Map(), "", "", 0, -1);
	}

	public Location(String symbolicId, Map map, String mapXcord,
			String mapYcord, int accuracy, int reflocationId) {
		this.symbolicID = symbolicId;
		this.map = map;
		this.mapXcord = mapXcord;
		this.mapYcord = mapYcord;
		this.accuracy = accuracy;
	}

	/* **************** Getter and Setter Methods **************** */

	/**
	 * @return accuracy
	 */
	public int getAccuracy() {
		return accuracy;
	}

	/**
	 * @param accuracy
	 */
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * @return the symbolicID
	 */
	public String getSymbolicID() {
		return symbolicID;
	}

	/**
	 * @param symbolicID
	 *            the symbolicID to set
	 */
	public void setSymbolicID(String symbolicID) {
		this.symbolicID = symbolicID;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public String getMapXcord() {
		return mapXcord;
	}

	public void setMapXcord(String mapXcord) {
		this.mapXcord = mapXcord;
	}

	public String getMapYcord() {
		return mapYcord;
	}

	public void setMapYcord(String mapYcord) {
		this.mapYcord = mapYcord;
	}

}