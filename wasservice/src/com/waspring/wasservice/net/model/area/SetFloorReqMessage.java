package com.waspring.wasservice.net.model.area;

import com.waspring.wasservice.net.model.BaseObject;

public class SetFloorReqMessage extends BaseObject {
	public Message MESSAGE = new Message();

	public static class Message {
		public String AREA_NO, BUILD_NO, FLOOR_NO, IMG_URL, MAP_NO;
  public int     DOORS;
	}
}
