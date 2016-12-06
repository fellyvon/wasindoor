package com.waspring.wasservice.net.model;

public class SetUserReqMessage extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2064862604911203616L;

	public Message MESSAGE = new Message();

	public static class Message {
		public String USER_NO, USER_NAME, MAIL, PHONE, SEX, ACT_NAME,USER_HEAD;

	}

}
