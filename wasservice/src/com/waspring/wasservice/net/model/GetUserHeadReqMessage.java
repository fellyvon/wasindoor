package com.waspring.wasservice.net.model;


public class GetUserHeadReqMessage extends BaseObject {

	public Message MESSAGE = new Message();
	public static class Message {
		public String USER_NO;
	}
}
