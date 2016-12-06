package com.waspring.wasservice.net.model.area;

import com.waspring.wasservice.net.model.BaseObject;

public class SetGraphReqMessage extends BaseObject {
	public Message MESSAGE = new Message();

	public static class Message {
		public String MAP_NO, MAP_NAME, MAP_URL, MAP_BOUNDS, MIN_LEVEL,
				MAX_LEVEL, MAP_CENTER,MAP_LEVEL;
	}
}
