 
package com.aiyc.base.core.measure;


import com.aiyc.base.core.Types;

 /**
  * 蓝牙信号定义
  * @author felly
  *
  */
public class BluetoothReading {


	/* attributes */
	protected String friendlyName = "";
	protected String bluetoothAddress = "";
	protected String majorDeviceClass = ""; // see
	// http://www.jasonlam604.com/articles_introduction_to_bluetooth_and_j2me_part2.php
	protected String minorDeviceClass = "";

	/* **************** Getter and Setter Methods **************** */

	/**
	 * @return the friendlyName
	 */
	public String getFriendlyName() {
		return friendlyName;
	}

	/**
	 * @param friendlyName
	 *            the friendlyName to set
	 */
	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	/**
	 * @return the bluetoothAddress
	 */
	public String getBluetoothAddress() {
		return bluetoothAddress;
	}

	/**
	 * @param bluetoothAddress
	 *            the bluetoothAddress to set
	 */
	public void setBluetoothAddress(String bluetoothAddress) {
		this.bluetoothAddress = bluetoothAddress;
	}

	/**
	 * @return the majorDeviceClass
	 */
	public String getMajorDeviceClass() {
		return majorDeviceClass;
	}

	/**
	 * @param majorDeviceClass
	 *            the majorDeviceClass to set
	 */
	public void setMajorDeviceClass(String majorDeviceClass) {
		this.majorDeviceClass = majorDeviceClass;
	}

	/**
	 * @return the minorDeviceClass
	 */
	public String getMinorDeviceClass() {
		return minorDeviceClass;
	}

	/**
	 * @param minorDeviceClass
	 *            the minorDeviceClass to set
	 */
	public void setMinorDeviceClass(String minorDeviceClass) {
		this.minorDeviceClass = minorDeviceClass;
	}

	public String getType() {
		return Types.BLUETOOTH;
	}
	
	/*
	 * removed due to conflicts: what is id needed for?
	 *
	public String getId() {
		return bluetoothAddress;
	}
	*/
	
	
	/**
	 * Returns Bluetooth Friendly Name
	 */
	public String getHumanReadableName() {
		return friendlyName;
	}

	public String toString() {
		return super.toString() + ": " + Types.FRIENDLY_NAME + "=" + friendlyName
				+ "|" + Types.BLUETOOTH_ADDRESS + "=" + bluetoothAddress + "|"
				+ Types.MAJOR_DEVICE_CLASS + "=" + majorDeviceClass + "|"
				+ Types.MINOR_DEVICE_CLASS + "=" + minorDeviceClass;
	}

	
}
