package com.waspring.wasservice.net.model.comm;

import com.waspring.wasservice.net.model.BaseObject;

public class DelFriendReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {
		public String SEND_USER_NO, DEL_USER_NO, DEL_REASON;

	}
}
