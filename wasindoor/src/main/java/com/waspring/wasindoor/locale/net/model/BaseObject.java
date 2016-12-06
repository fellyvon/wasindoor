package com.waspring.wasindoor.locale.net.model;

/**
 * 
 * @author felly
 *
 */
public  class BaseObject extends Base {
	public  Head HEAD = new Head();

	public static class Head {
		private String CMD, VERSION, FLOWID, CHKSUM, LENGTH, CLIENTNO;

		public String getCMD() {
			return CMD;
		}

		public void setCMD(String cmd) {
			CMD = cmd;
		}

		public String getVERSION() {
			return VERSION;
		}

		public void setVERSION(String version) {
			VERSION = version;
		}

		public String getFLOWID() {
			return FLOWID;
		}

		public void setFLOWID(String flowid) {
			FLOWID = flowid;
		}

		public String getCHKSUM() {
			return CHKSUM;
		}

		public void setCHKSUM(String chksum) {
			CHKSUM = chksum;
		}

		public String getLENGTH() {
			return LENGTH;
		}

		public void setLENGTH(String length) {
			LENGTH = length;
		}

		public String getCLIENTNO() {
			return CLIENTNO;
		}

		public void setCLIENTNO(String clientno) {
			CLIENTNO = clientno;
		}

	}

}
