package com.waspring.wasindoor.locale.entry;

public class IntDoublePair {
	private double doubleValue;
	private int intValue;
	
	public IntDoublePair(int int_value, double double_value)
	{
		doubleValue = double_value;
		intValue = int_value;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
	
	
}
