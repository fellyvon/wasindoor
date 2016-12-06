package com.waspring.wasservice.net.model.msg;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.CommonRepMessage;

public class GetGroupMsgRepMessage extends CommonRepMessage {
	public String GROUP_ID;
	public List<Message> MSG_LIST = new ArrayList<Message>();

	public static class Message {
		public String SENDER_NO, MSG_SORT_CODE, CONTENT_MSG, SEND_TIME;

	}
}
