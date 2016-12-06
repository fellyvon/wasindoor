package com.waspring.wasservice.net.model.loc;

import com.waspring.wasservice.net.model.BaseObject;

public class ClearModelReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {
		public String MAP_NO;
	}

}
