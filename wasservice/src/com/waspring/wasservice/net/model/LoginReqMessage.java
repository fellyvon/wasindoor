package com.waspring.wasservice.net.model;


public class LoginReqMessage extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2064862604911203616L;

	public Message MESSAGE = new Message();

	public static class Message {
		public String USER_NO, USER_PWD,OPEN_ID;

		public String getUSER_NO() {
			return USER_NO;
		}

		public String getOPEN_ID() {
			return OPEN_ID;
		}

		public void setOPEN_ID(String open_id) {
			OPEN_ID = open_id;
		}

		public void setUSER_NO(String user_no) {
			USER_NO = user_no;
		}

		public String getUSER_PWD() {
			return USER_PWD;
		}

		public void setUSER_PWD(String user_pwd) {
			USER_PWD = user_pwd;
		}
	}

}
