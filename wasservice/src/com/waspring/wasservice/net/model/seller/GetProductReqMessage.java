package com.waspring.wasservice.net.model.seller;

import com.waspring.wasservice.net.model.BaseObject;

public class GetProductReqMessage extends BaseObject {
	
	public Message MESSAGE=new    Message();
	public static class Message
	{	public String SJ_NO, KEY,CP_NO;
	
	}
}
