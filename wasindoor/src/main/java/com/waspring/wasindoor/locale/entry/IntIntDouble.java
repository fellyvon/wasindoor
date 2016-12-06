package com.waspring.wasindoor.locale.entry;


public class IntIntDouble{
	
	private int int1;
	private int int2;
	private double doubleValue;
	
	public IntIntDouble(int int_value1, int int_value2, double double_value)
	{
		int1 = int_value1;
		int2 = int_value2;
		doubleValue = double_value;
	}

	public int getInt1() {
		return int1;
	}

	public void setInt1(int int1) {
		this.int1 = int1;
	}

	public int getInt2() {
		return int2;
	}

	public void setInt2(int int2) {
		this.int2 = int2;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}

}
