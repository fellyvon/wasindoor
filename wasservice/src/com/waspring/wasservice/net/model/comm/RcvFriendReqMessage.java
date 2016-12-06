package com.waspring.wasservice.net.model.comm;

import com.waspring.wasservice.net.model.BaseObject;

public class RcvFriendReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {
		public String REQ_NO, CONFIRM_RSLT, CONFU_RESON;

	}
}
