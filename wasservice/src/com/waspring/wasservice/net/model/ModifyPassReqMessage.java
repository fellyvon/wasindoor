package com.waspring.wasservice.net.model;


public class ModifyPassReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {
		public String USER_NO;
		public String OLD_PWD;
		public String NEW_PWD;
		public String NEW_PWD_CONFIRM;
	}

}
