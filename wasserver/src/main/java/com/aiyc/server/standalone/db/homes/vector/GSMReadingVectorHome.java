 
package com.aiyc.server.standalone.db.homes.vector;

import com.aiyc.server.standalone.core.measure.GSMReading;
import com.aiyc.server.standalone.db.HomeFactory;
import com.aiyc.server.standalone.db.homes.EntityHome;

 
public class GSMReadingVectorHome extends VectorHome<GSMReading> {
	
	private static final String className = GSMReading.class.getSimpleName();	

	@Override
	public String getContainedObjectClassName() {
		return className;
	}
	
	/**
	 * @see VectorHome#getObjectHome()
	 */
	@Override
	public EntityHome<GSMReading> getObjectHome() {
		return HomeFactory.getGSMReadingHome();
	}

	
	
}
