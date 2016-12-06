package com.waspring.wasservice.net.model;


public class GetCodeReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {
		public String CODE_TYPE,CODE_NAME,CODE_VALUE;
	 

	}

 
}
