package com.waspring.wasservice.net.model.seller;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.BaseObject;

public class AddFreeReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public class Message {
		public String SJ_NO;

		public List<GetSellerFreeRepMessage> FREE_LIST = new ArrayList<GetSellerFreeRepMessage>();

	}

}
