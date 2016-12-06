package com.waspring.wasservice.net.model.comm;

import com.waspring.wasservice.net.model.BaseObject;

public class AddFriendReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {
		public String SEND_USER_NO, RCVER_NO, CONTENT_MSG;

	}
}
