package com.waspring.wasservice.net.model;


public class RegReqMessage extends BaseObject {
	public Message MESSAGE = new Message();

	public static class Message {
		public String USER_NO,USER_NAME, USER_PWD, CONFIRM_USER_PWD, IS_MAIL, IS_PHONE,
		SEX,ACT_NAME,USER_HEAD,OPEN_ID;

	}
}
