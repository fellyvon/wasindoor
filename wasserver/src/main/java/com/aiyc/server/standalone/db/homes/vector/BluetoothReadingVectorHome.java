 
package com.aiyc.server.standalone.db.homes.vector;

import com.aiyc.server.standalone.core.measure.BluetoothReading;
import com.aiyc.server.standalone.db.HomeFactory;
import com.aiyc.server.standalone.db.homes.EntityHome;

 
public class BluetoothReadingVectorHome extends VectorHome<BluetoothReading> {

	private static final String className = BluetoothReading.class.getSimpleName();
	
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
	public EntityHome<BluetoothReading> getObjectHome() {
		return HomeFactory.getBluetoothReadingHome();
	}

	
}
