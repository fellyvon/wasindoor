 
package com.aiyc.server.standalone.db;

import com.aiyc.server.standalone.db.homes.BluetoothReadingHome;
import com.aiyc.server.standalone.db.homes.EntityHome;
import com.aiyc.server.standalone.db.homes.FingerprintHome;
import com.aiyc.server.standalone.db.homes.GSMReadingHome;
import com.aiyc.server.standalone.db.homes.LocationHome;
import com.aiyc.server.standalone.db.homes.MapHome;
import com.aiyc.server.standalone.db.homes.MeasurementHome;
import com.aiyc.server.standalone.db.homes.ReadingInMeasurementHome;
import com.aiyc.server.standalone.db.homes.WiFiReadingHome;
import com.aiyc.server.standalone.db.homes.vector.BluetoothReadingVectorHome;
import com.aiyc.server.standalone.db.homes.vector.GSMReadingVectorHome;
import com.aiyc.server.standalone.db.homes.vector.WiFiReadingVectorHome;

/**
 * Factory for all {@link EntityHome}s
 * 
 * @author felly
 */
public class HomeFactory {

	private static MapHome mapHome = null;

	public synchronized static MapHome getMapHome() {
		if (mapHome == null) {
			mapHome = new MapHome();
		}
		return mapHome;
	}

	private static LocationHome locHome = null;

	public synchronized static LocationHome getLocationHome() {
		if (locHome == null) {
			locHome = new LocationHome();
		}
		return locHome;
	}
 

	private static FingerprintHome fpHome = null;

	public synchronized static FingerprintHome getFingerprintHome() {
		if (fpHome == null) {
			fpHome = new FingerprintHome();
		}
		return fpHome;
	}

	private static MeasurementHome mHome = null;

	public synchronized static MeasurementHome getMeasurementHome() {
		if (mHome == null) {
			mHome = new MeasurementHome();
		}
		return mHome;
	}

	private static ReadingInMeasurementHome rinmHome = null;

	public synchronized static ReadingInMeasurementHome getReadingInMeasurementHome() {
		if (rinmHome == null) {
			rinmHome = new ReadingInMeasurementHome();
		}
		return rinmHome;
	}

	private static WiFiReadingHome wrHome = null;

	public synchronized static WiFiReadingHome getWiFiReadingHome() {
		if (wrHome == null) {
			wrHome = new WiFiReadingHome();
		}
		return wrHome;
	}

	private static WiFiReadingVectorHome wrvHome = null;

	public synchronized static WiFiReadingVectorHome getWiFiReadingVectorHome() {
		if (wrvHome == null) {
			wrvHome = new WiFiReadingVectorHome();
		}
		return wrvHome;
	}

	private static GSMReadingHome grHome = null;

	public synchronized static GSMReadingHome getGSMReadingHome() {
		if (grHome == null) {
			grHome = new GSMReadingHome();
		}
		return grHome;
	}

	private static GSMReadingVectorHome grvHome = null;

	public synchronized static GSMReadingVectorHome getGSMReadingVectorHome() {
		if (grvHome == null) {
			grvHome = new GSMReadingVectorHome();
		}
		return grvHome;
	}

	private static BluetoothReadingHome brHome = null;

	public synchronized static BluetoothReadingHome getBluetoothReadingHome() {
		if (brHome == null) {
			brHome = new BluetoothReadingHome();
		}
		return brHome;
	}

	private static BluetoothReadingVectorHome brvHome = null;

	public synchronized static BluetoothReadingVectorHome getBluetoothReadingVectorHome() {
		if (brvHome == null) {
			brvHome = new BluetoothReadingVectorHome();
		}
		return brvHome;
	}
}
