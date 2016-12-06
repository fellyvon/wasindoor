package com.waspring.wasservice.net.model.area;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.BaseObject;

public class GetWifiAreaReqMessage extends BaseObject {
	public Message MESSAGE = new Message();

	public static class Message {

		public List<Wifi> AP_INFO = new ArrayList<Wifi>();

		public static  class Wifi {
			 
			public String AP_NAME, AP_VALUE, AP_MAC, WEPENABLED,
					ISINFRASTRUCTURE;

		}
	}
}
