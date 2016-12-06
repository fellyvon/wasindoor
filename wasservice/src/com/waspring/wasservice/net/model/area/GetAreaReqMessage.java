package com.waspring.wasservice.net.model.area;

import com.waspring.wasservice.net.model.BaseObject;

public class GetAreaReqMessage extends BaseObject {
	public Message MESSAGE = new Message();

	public static class Message {
		public String AREA_NO, BUILD_NO, FLOOR_NO, DOOR_NO;
	}
}
