package com.waspring.wasindoor.locale.libsvm;

public class LocationEntity {
	private int No; // 地点编号
	private double x; //x坐标
	private double y; //y坐标
	
	
	public LocationEntity(int locNo, double locX, double locY)
	{
		No = locNo;
		x = locX;
		y = locY;
	}
	
	
	public int getNo() {
		return No;
	}
	public void setNo(int no) {
		No = no;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	
}
