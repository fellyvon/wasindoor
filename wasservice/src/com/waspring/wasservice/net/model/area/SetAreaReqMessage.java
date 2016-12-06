package com.waspring.wasservice.net.model.area;

import com.waspring.wasservice.net.model.BaseObject;

public class SetAreaReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {
		public String AREA_NO, AREA_TYPE, P_AREA_NO, AREA_NAME, AREA_ADDR,
				IMG_URL ;
		  public int     BUILD_NUM;
	}
}
