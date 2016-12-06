package com.waspring.wasservice.net.model;



/**
 * 
 * @author felly
 * 
 */
public class ModifyUserHeadReqMessage extends BaseObject {
	public Message MESSAGE = new Message();
	public static class Message {
		public String USER_NO, USER_HEAD;
	}
}
