package com.waspring.wasservice.net.model.msg;

import com.waspring.wasservice.net.model.BaseObject;

public class SengSingleMsgReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {
		public String SENDER_NO, RCVER_NO, MSG_SORT_CODE, CONTENT_MSG,
				SEND_TIME;

	}
}
