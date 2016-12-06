package com.waspring.wasindoor.locale;

public class GeomagneticEntity {
	private Integer buildingId;   	//建筑物编号
	private Integer floor; 			//第几层 
	
	private double xAngle;			//x维度角度
	private double yAngle;			//y维度角度
	private double zAngle;			//z维度角度
	
	private double xMagnetic; 		//x维度磁强
	private double yMagnetic;		//y维度磁强
	private double zMagnetic; 		//z维度磁强	
	
	
	private String timeStr; 		//数据采集时间
	private Integer targetLable;		//已有的标签，没有标签的为null
	
	
	public GeomagneticEntity(){}
	
	public GeomagneticEntity(Integer buildingId, Integer floor, double xAngle, double yAngle, 
			double zAngle, double xMagnetic, double yMagnetic, double zMagnetic, String timeStr, Integer targetLable)
	{
		this.buildingId = buildingId;
		this.floor = floor;
		this.xAngle = xAngle;
		this.yAngle = yAngle;
		this.zAngle = zAngle;
		this.xMagnetic = xMagnetic;
		this.yMagnetic = yMagnetic;
		this.zMagnetic = zMagnetic;
		this.timeStr = timeStr;		
		this.targetLable = targetLable;
	}


	public Integer getBuildingId() {
		return buildingId;
	}


	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}


	public Integer getFloor() {
		return floor;
	}


	public void setFloor(Integer floor) {
		this.floor = floor;
	}


	public double getxAngle() {
		return xAngle;
	}


	public void setxAngle(double xAngle) {
		this.xAngle = xAngle;
	}


	public double getyAngle() {
		return yAngle;
	}


	public void setyAngle(double yAngle) {
		this.yAngle = yAngle;
	}


	public double getzAngle() {
		return zAngle;
	}


	public void setzAngle(double zAngle) {
		this.zAngle = zAngle;
	}


	public double getxMagnetic() {
		return xMagnetic;
	}


	public void setxMagnetic(double xMagnetic) {
		this.xMagnetic = xMagnetic;
	}


	public double getyMagnetic() {
		return yMagnetic;
	}


	public void setyMagnetic(double yMagnetic) {
		this.yMagnetic = yMagnetic;
	}


	public double getzMagnetic() {
		return zMagnetic;
	}


	public void setzMagnetic(double zMagnetic) {
		this.zMagnetic = zMagnetic;
	}


	public String getTimeStr() {
		return timeStr;
	}


	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}


	public Integer getTargetLable() {
		return targetLable;
	}


	public void setTargetLable(Integer targetLable) {
		this.targetLable = targetLable;
	}	

}
