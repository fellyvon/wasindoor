package com.aiyc.server.standalone.locator;

import com.aiyc.server.standalone.core.Location;
import com.aiyc.server.standalone.core.Measurement;

/**
 * Interface for a locator algorithm
 * 
 * @author felly
 * 
 */
public interface ILocator {
	/**
	 * Tries to find a location which fingerprint measurement matches the
	 * {@link Measurement} m
	 * 
	 * @param m
	 *            {@link Measurement}
	 * @return {@link Location} or null if no location could be found
	 */
	public Location locate(Measurement m);

	/**
	 * Returns a similarity level between to measurement. This function is
	 * called by
	 * {@link Measurement#similarityLevel(org.icscn.base.core.Measurement)}
	 * 
	 * @see Measurement#similarityLevel(org.icscn.base.core.Measurement)
	 * @param t
	 *            {@link org.icscn.base.core.Measurement}
	 * @param o
	 *            {@link org.icscn.base.core.Measurement}
	 * @return Similarity level
	 */
	public int measurementSimilarityLevel(com.aiyc.base.core.Measurement t,
			com.aiyc.base.core.Measurement o);

	/**
	 * Decides whether to measurements are similar. This function is called by
	 * {@link Measurement#isSimilar(org.icscn.base.core.Measurement)}
	 * 
	 * @see Measurement#isSimilar(org.icscn.base.core.Measurement)
	 * @param t
	 *            {@link org.icscn.base.core.Measurement}
	 * @param o
	 *            {@link org.icscn.base.core.Measurement}
	 * @return Similarity level
	 */
	public Boolean measurmentAreSimilar(com.aiyc.base.core.Measurement t,
			com.aiyc.base.core.Measurement o);

}
