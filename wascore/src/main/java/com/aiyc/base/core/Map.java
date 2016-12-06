package com.aiyc.base.core;

/**
 * 位置点位置信息定义
 * 
 * @author felly
 */
public class Map {

	/*
	 * unique identifier, commonly the name of this map e.g. 'IFW floor A'
	 */
	protected String mapName = "";

	/*
	 * the URL of the corresponding map (image) where this location resides 
	 
	 */
	protected String mapURL = "";

	/* **************** Constructors **************** */

	public Map() {
		mapName = "";
		mapURL = "";
	}

	public Map(String mapName, String mapURL) {
		super();
		this.mapName = mapName;
		this.mapURL = mapURL;
	}

	/* **************** Getter and Setter Methods **************** */

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getMapURL() {
		return mapURL;
	}

	public void setMapURL(String mapURL) {
		this.mapURL = mapURL;
	}

	public String toString() {
		return super.toString() + ": " + mapName + "; mapURL = " + mapURL + ";";
	}
}