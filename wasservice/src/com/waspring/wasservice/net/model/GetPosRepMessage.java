package com.waspring.wasservice.net.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取位置请求
 * 
 * @author felly
 * 
 */
public class GetPosRepMessage extends BaseObject {

 

	public Message MESSAGE = new Message();

	public static class Message {
		public String MAP_NO;
		public long timestamp = System.currentTimeMillis();
		public List<AP_INFO> AP_INFO = new ArrayList<AP_INFO>();

		public static class AP_INFO {
			public String AP_NAME, AP_VALUE, AP_MAC, WEPENABLED,
					ISINFRASTRUCTURE;

		}

	}

 
}
