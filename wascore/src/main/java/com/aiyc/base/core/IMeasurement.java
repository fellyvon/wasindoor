package com.aiyc.base.core;

import com.aiyc.base.core.Measurement;

/**
 * 设备抽象定义
 * 
 * @author felly
 * 
 */
public interface IMeasurement {

	/**
	 * computes the achieved accuracy level of a measurement compared to the
	 * current one
	 * 
	 * @param m
	 *            Measurement
	 * @return computed similarity level
	 */
	public int similarityLevel(Measurement m);

	/**
	 * returns a boolean whether a measurement is considered to be similar or
	 * not to the current measurement
	 * 
	 * @param m
	 *            Measurement
	 * @return true if considered similar
	 */
	public boolean isSimilar(Measurement m);
}
