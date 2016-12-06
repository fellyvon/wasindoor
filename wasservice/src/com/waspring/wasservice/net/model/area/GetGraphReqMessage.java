package com.waspring.wasservice.net.model.area;

import com.waspring.wasservice.net.model.BaseObject;

public class GetGraphReqMessage extends BaseObject {
  public Message MESSAGE=new Message();
	
	public   static class Message {
		public String MAP_NO;
	}
}
