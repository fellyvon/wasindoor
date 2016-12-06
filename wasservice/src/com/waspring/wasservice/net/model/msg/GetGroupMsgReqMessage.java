package com.waspring.wasservice.net.model.msg;

import com.waspring.wasservice.net.model.BaseObject;

public class GetGroupMsgReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {
		public String GROUP_ID, RCVER_NO;
	}
}
