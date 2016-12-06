package com.waspring.wasservice.net.model.comm;

import com.waspring.wasservice.net.model.BaseObject;

public class ApplyAddGroupReqMessage extends BaseObject {
	public Message MESSAGE = new Message();

	public static class Message {
		public String GROUP_ID, USER_NO, CONTENT_MSG;

	}
}
