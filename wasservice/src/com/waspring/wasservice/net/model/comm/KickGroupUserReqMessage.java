package com.waspring.wasservice.net.model.comm;

import com.waspring.wasservice.net.model.BaseObject;

public class KickGroupUserReqMessage extends BaseObject {

	public    Message MESSAGE=new Message();
	public static class Message {
		public String GROUP_ID, USER_NO, KICK_NO, KICK_REASON;

	}
}
