package com.waspring.wasservice.net.model.comm;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.BaseObject;

/**
 * 
 * @author felly
 * 
 */
public class InviAddGroupReqMessage extends BaseObject {
	public Message MESSAGE = new Message();

	public static class Message {

		public  List<Invi> INVI_LIST = new ArrayList<Invi>();

		public String USER_NO;

	}

	public static class Invi {
		public String GROUP_ID, RCV_NO, CONTENT_MSG;
	}
}
