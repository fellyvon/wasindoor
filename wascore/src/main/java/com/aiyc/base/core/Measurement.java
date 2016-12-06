package com.aiyc.base.core;

import java.util.Vector;

import com.aiyc.base.core.measure.BluetoothReading;
import com.aiyc.base.core.measure.GSMReading;
import com.aiyc.base.core.measure.WiFiReading;

/**
 * 设备抽象，具有采集gsm信号，wifi信号，蓝牙信号能力
 * 
 * @author felly
 */
public abstract class Measurement {

	/* time of measurment */
	protected long timestamp = 0;

	/* set of GSM readings that where taken during the measurement */
	/*
	 * cant use generic type because of compability to j2me which does not have
	 * an generic vector type
	 */
	protected Vector gsmReadings;

	/* set of WiFi readings that where taken during the measurement */
	protected Vector wifiReadings;

	/* set of Bluetooth readings that where taken during the measurement */
	protected Vector bluetoothReadings;

	/* constructor */
	public Measurement() {
		timestamp = System.currentTimeMillis();
		gsmReadings = new Vector();
		wifiReadings = new Vector();
		bluetoothReadings = new Vector();

	}

	public Measurement(Vector gsmReadings, Vector wifiReadings,
			Vector bluetoothReadings) {
		timestamp = System.currentTimeMillis();
		this.gsmReadings = gsmReadings;
		this.wifiReadings = wifiReadings;
		this.bluetoothReadings = bluetoothReadings;
	}

	/* ************ Getter and Setter Methods ************ */

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the gsm readings
	 */
	public Vector getGsmReadings() {
		return gsmReadings;
	}

	public void addGSMReading(GSMReading gsmReading) {
		this.gsmReadings.addElement(gsmReading);
	}

	/**
	 * @return the wifi readings
	 */
	public Vector getWiFiReadings() {
		return wifiReadings;
	}

	public void addWiFiReading(WiFiReading wiFiReading) {
		this.wifiReadings.addElement(wiFiReading);
	}

	/**
	 * @return the bluetooth readings
	 */
	public Vector getBluetoothReadings() {
		return bluetoothReadings;
	}

	public void addBluetoothReading(BluetoothReading bluetoothReading) {
		this.bluetoothReadings.addElement(bluetoothReading);
	}

}
