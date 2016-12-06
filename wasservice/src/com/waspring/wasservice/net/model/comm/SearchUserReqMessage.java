package com.waspring.wasservice.net.model.comm;

import com.waspring.wasservice.net.model.BaseObject;

public class SearchUserReqMessage extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2064862604911203616L;

	public Message MESSAGE = new Message();

	public static class Message {
		public String USER_NO, USER_NAME, MP_NO, R, JD, WD,SEX;

	}

}
