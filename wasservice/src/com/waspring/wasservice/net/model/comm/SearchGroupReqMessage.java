package com.waspring.wasservice.net.model.comm;

import com.waspring.wasservice.net.model.BaseObject;

public class SearchGroupReqMessage extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2064862604911203616L;

	public Message MESSAGE = new Message();

	public static class Message {
		public String GROUP_ID, GROUP_NAME, GROUP_FUN;

	}

}
