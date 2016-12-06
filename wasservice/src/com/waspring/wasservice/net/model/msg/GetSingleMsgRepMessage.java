package com.waspring.wasservice.net.model.msg;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.CommonRepMessage;

public class GetSingleMsgRepMessage extends CommonRepMessage {
	public String SENDER_NO;
	public List<Msg> MSG_LIST = new ArrayList<Msg>();

	public static class Msg {
		public String MSG_SORT_CODE, CONTENT_MSG, SEND_TIME;

	}
}
