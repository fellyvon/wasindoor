 
package com.aiyc.server.standalone.core;

import java.util.Vector;

import com.aiyc.base.core.IMeasurement;
import com.aiyc.server.standalone.core.measure.BluetoothReading;
import com.aiyc.server.standalone.core.measure.GSMReading;
import com.aiyc.server.standalone.core.measure.WiFiReading;
import com.aiyc.server.standalone.db.IEntity;
import com.aiyc.server.standalone.locator.LocatorHome;


/**
 * @see com.aiyc.base.core.Measurement
 * @author felly
 */
public class Measurement extends com.aiyc.base.core.Measurement implements IMeasurement, IEntity<Integer> {
	
	
	public Measurement() {
		super(new Vector<GSMReading>(), new Vector<WiFiReading>(), new Vector<BluetoothReading>());
		
	}

	public Measurement(Vector<GSMReading> gsmReadings, Vector<WiFiReading> wifiReadings, Vector<BluetoothReading> bluetoothReadings) {
		super(gsmReadings, wifiReadings, bluetoothReadings);
	}

	private Integer id;

	/**
	 * @return the database id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * @param id
	 * 			the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/*
	protected Vector<GSMReading> gsmReadings;

	
	protected Vector<WiFiReading> wifiReadings;

	
	protected Vector<BluetoothReading> bluetoothReadings;
	*/
	
	/**
	 * @return Bluetooth readings vector
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Vector<BluetoothReading> getBluetoothReadings() {
		return (Vector<BluetoothReading>) super.getBluetoothReadings();
	}

	/**
	 * @return GSM readings vector
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Vector<GSMReading> getGsmReadings() {
		return (Vector<GSMReading>) super.getGsmReadings();
	}

	/**
	 * @return WiFi readings vector
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Vector<WiFiReading> getWiFiReadings() {
		return (Vector<WiFiReading>) super.getWiFiReadings();
	}
	
	/**
	 * 
	 * @param br Bluetooth readings vector
	 */
	public void setBluetoothReadings(Vector<BluetoothReading> br) {
		bluetoothReadings = br;
	}
	
	/**
	 * 
	 * @param wr WiFi readings vector
	 */
	public void setWiFiReadings(Vector<WiFiReading> wr) {
		wifiReadings = wr;
	}
	
	/**
	 * 
	 * @param gr GSMreadings vector
	 */
	public void setGSMReadings(Vector<GSMReading> gr) {
		gsmReadings = gr;
	}

	 
	public boolean isSimilar(com.aiyc.base.core.Measurement m) {
		return LocatorHome.getLocator().measurmentAreSimilar(this, m);
	}

	 
	public int similarityLevel(com.aiyc.base.core.Measurement m) {
		return LocatorHome.getLocator().measurementSimilarityLevel(this, m);
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) || (this.getTimestamp() == ((Measurement)obj).getTimestamp());
	}
	
	

}
