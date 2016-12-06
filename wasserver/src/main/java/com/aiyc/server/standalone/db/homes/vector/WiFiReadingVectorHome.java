 
package com.aiyc.server.standalone.db.homes.vector;

import com.aiyc.server.standalone.core.measure.WiFiReading;
import com.aiyc.server.standalone.db.HomeFactory;
import com.aiyc.server.standalone.db.homes.EntityHome;
 
public class WiFiReadingVectorHome extends VectorHome<WiFiReading> {

	private static final String className = WiFiReading.class.getSimpleName();
	
	/**
	 * @see VectorHome#getContainedObjectClassName()
	 */
	@Override
	public String getContainedObjectClassName() {
		return className;
	}

	/**
	 * @see VectorHome#getObjectHome()
	 */
	@Override
	public EntityHome<WiFiReading> getObjectHome() {
		return HomeFactory.getWiFiReadingHome();
	}
	
}
