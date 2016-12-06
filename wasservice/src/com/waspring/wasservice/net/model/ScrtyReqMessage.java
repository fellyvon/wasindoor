package com.waspring.wasservice.net.model;

import java.util.ArrayList;
import java.util.List;

public class ScrtyReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {
		public String USER_NO;
		public List<scrList> SERCTY_LIST = new ArrayList<scrList>();

	}

	public static class scrList {
		public String SERCTY_TYPE;
		public String SERCTY_VALUE;
	}
}
