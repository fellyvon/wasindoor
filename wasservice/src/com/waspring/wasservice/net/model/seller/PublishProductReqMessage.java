package com.waspring.wasservice.net.model.seller;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.BaseObject;

public class PublishProductReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static  class Message {
		public String SJ_NO;

		public List<GetProductRepMessage> CP_LIST = new ArrayList<GetProductRepMessage>();

	}

}
