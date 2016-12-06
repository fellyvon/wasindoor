package com.waspring.wasservice.net.model.msg;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.CommonRepMessage;

public class GetGroupMsgNumRepMessage extends CommonRepMessage {

	public List<Msg> MSG_LIST = new ArrayList<Msg>();

	public static class Msg {
		public String GROUP_ID, MSG_NUM;
	}

}
