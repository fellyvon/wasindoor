package com.waspring.wasservice.net.model.msg;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.CommonRepMessage;

public class GetSingleMsgNumRepMessage extends CommonRepMessage {

	
	public List<Msg> MSG_LIST=new ArrayList<Msg>();
	
	public static class Msg{
	public String SENDER_NO, MSG_NUM;
	}

}
