package com.waspring.wasservice.net.model.msg;

import com.waspring.wasservice.net.model.BaseObject;

public class SendGroupMsgReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {
		public String SENDER_NO, GROUP_ID, MSG_SORT_CODE, CONTENT_MSG;

	}
}
