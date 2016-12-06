package com.aiyc.server.standalone.locator;

import java.util.Comparator;

import com.aiyc.server.standalone.core.Measurement;

/**
 * Comparator for different {@link Measurement}. On creation, there must be set
 * an basis {@link Measurement} based on which the comparisation is performed.
 * 
 * @author felly
 * 
 */
public class MeasurementComparator implements Comparator<Measurement> {

	Measurement basisMeasurement;

	/**
	 * Creates a {@link MeasurementComparator} whith basis {@link Measurement}
	 * 
	 * @see MeasurementComparator
	 * @param m
	 *            Basis Basis {@link Measurement}
	 */
	public MeasurementComparator(Measurement m) {
		basisMeasurement = m;
	}

	/**
	 * Gets the Basis {@link Measurement} to which the others are compared
	 * 
	 * @return {@link Measurement}
	 */
	public Measurement getBasisMeasurement() {
		return basisMeasurement;
	}

	/**
	 * Sets the Basis {@link Measurement} to which the others are compared
	 * 
	 * @param basisMeasurement
	 *            {@link Measurement}
	 */
	public void setBasisMeasurement(Measurement basisMeasurement) {
		this.basisMeasurement = basisMeasurement;
	}

	/**
	 * Compares two measurement to the basisMeasurement and returns which one is
	 * more similar to the basisMeasurement
	 * 
	 * @param arg0
	 *            Measurement 1
	 * @param arg1
	 *            Measurement 2
	 * @return -1 if arg0 is more more similar to basisMeasurement than arg1 <br />
	 *         0 if arg0 and arg1 are equal similar to basisMeasuremet <br />
	 *         1 if arg1 is more similar to basisMeasurement than arg0
	 */

	public int compare(Measurement arg0, Measurement arg1) {
		int a1 = basisMeasurement.similarityLevel(arg0);
		int a2 = basisMeasurement.similarityLevel(arg1);

		if (a1 == a2) {
			long t1 = arg0.getTimestamp();
			long t2 = arg1.getTimestamp();
			if (t1 == t2) {
				return 0;
			} else {
				if (t1 < t2) {
					return 1;
				} else {
					return -1;
				}
			}
		} else {
			if (a1 < a2) {
				return 1;
			} else {
				return -1;
			}
		}

	}

}
