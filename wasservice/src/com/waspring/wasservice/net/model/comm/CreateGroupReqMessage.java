package com.waspring.wasservice.net.model.comm;

import com.waspring.wasservice.net.model.BaseObject;

public class CreateGroupReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {
		public String GROUP_NAME, GROUP_NO, GROUP_FUN, GROUP_REMARK;

	}
}
