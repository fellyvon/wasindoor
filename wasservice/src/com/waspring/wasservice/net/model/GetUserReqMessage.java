package com.waspring.wasservice.net.model;


public class GetUserReqMessage extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2064862604911203616L;

	public Message MESSAGE = new Message();

	public static class Message {
		public String USER_NO;
	}

	 

}
