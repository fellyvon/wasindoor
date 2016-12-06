package com.waspring.wasservice.net.model.area;

import com.waspring.wasservice.net.model.BaseObject;

public class SetBuildReqMessage extends BaseObject {
	public Message MESSAGE = new Message();

	public static class Message {
		public String AREA_NO, BUILD_NO, BUILDING_NAME, BUILDING_DESC, 
				JD, WD, IMG_URL,FLOOR_PIX;
		
		  public int     FLOORS;
	}
}
