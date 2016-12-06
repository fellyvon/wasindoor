package com.waspring.wasservice.net.model.area;

import com.waspring.wasservice.net.model.BaseObject;

public class SetDoorReqMessage extends BaseObject {
	public Message MESSAGE = new Message();

	public static class Message {
		public String AREA_NO, BUILD_NO, FLOOR_NO, DOOR_NO;
		public String BUSINESSID, OBJECTID, X, Y,SHOPENGLIS;

	}
}
