package com.aiyc.base.core.measure;

import com.aiyc.base.core.Types;
import com.aiyc.base.util.FixedPointLong;
import com.aiyc.base.util.FixedPointLongException;

/**
 * 手机GSM信号抽象定义
 * 
 * @author felly
 * 
 */
public class GSMReading {

	/* attributes */
	protected String cellId = "";
	protected String areaId = "";
	protected String signalStrength = "";
	protected String MCC = "";
	protected String MNC = "";
	protected String networkName = "";

	/* **************** Getter and Setter Methods **************** */

	/**
	 * @return the cellId
	 */
	public String getCellId() {
		return cellId;
	}

	/**
	 * @param cellId
	 *            the cellId to set
	 */
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	/**
	 * @return the areaId
	 */
	public String getAreaId() {
		return areaId;
	}

	/**
	 * @param areaId
	 *            the areaId to set
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	/**
	 * @return the signalStrength
	 */
	public String getSignalStrength() {
		return signalStrength;
	}

	/**
	 * @param signalStrength
	 *            the signalStrength to set
	 */
	public void setSignalStrength(String signalStrength) {
		this.signalStrength = signalStrength;
	}

	/**
	 * @return the mCC
	 */
	public String getMCC() {
		return MCC;
	}

	/**
	 * @param mcc
	 *            the mCC to set
	 */
	public void setMCC(String mcc) {
		MCC = mcc;
	}

	/**
	 * @return the mNC
	 */
	public String getMNC() {
		return MNC;
	}

	/**
	 * @param mnc
	 *            the mNC to set
	 */
	public void setMNC(String mnc) {
		MNC = mnc;
	}

	/**
	 * @return the networkName
	 */
	public String getNetworkName() {
		return networkName;
	}

	/**
	 * @param networkName
	 *            the networkName to set
	 */
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	/* **************** Methods for Reading **************** */

	public String getType() {
		return Types.GSM;
	}

	/*
	 * removed due to conflicts: what is id needed for?
	 * 
	 * public String getId() { return MCC + ":" + MNC + ":" + cellId + ":" +
	 * areaId; }
	 */

	public int getNormalizedSignalStrength() {
		// TODO test and rewrite (taken from placelab)

		// This is an approximate algorithm
		// All the #'s should come in positive form, changing them up top

		try {
			int nativeSignal = Integer.parseInt(signalStrength);
			if (nativeSignal > 0)
				nativeSignal *= -1;

			long a, b;
			try {
				a = FixedPointLong.stringToFlong("1.6164");
				b = FixedPointLong.stringToFlong("182.3836");
			} catch (FixedPointLongException fple) {
				throw new RuntimeException("Cannot get FPLs for statics!");
			}

			long signal = nativeSignal * a + b;
			int value = FixedPointLong.intValue(signal);
			if (value > 100)
				value = 100;

			return value;
		} catch (NumberFormatException nfe) {
			// LogService.error(this, nfe.getMessage(), nfe);
			return 0;
		}
	}

	public String getHumanReadableName() {
		return "" + networkName + ":" + areaId + ":" + cellId;
	}

	public String toString() {
		return super.toString() + ": " + Types.HUMANREADABLENAME + "="
				+ networkName + "|" + Types.CELLID + "=" + cellId + "|"
				+ Types.AREAID + "=" + areaId + "|" + Types.MCC + "=" + MCC
				+ "|" + Types.MNC + "=" + MNC + "|" + Types.SIGNAL + "="
				+ signalStrength + "|" + Types.PERCENTAGE + "="
				+ getNormalizedSignalStrength();
	}

}
