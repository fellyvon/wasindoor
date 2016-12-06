package com.waspring.wasservice.net.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ²âÁ¿½×¶Î
 * 
 * @author felly
 * 
 */
public class SetModelReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {
		public String MAP_NO;
		public List<AP_INFO> AP_INFO = new ArrayList<AP_INFO>();
		public LOCATION LOCATION = new LOCATION();

		public static class AP_INFO {
			public String AP_NAME, AP_VALUE, AP_MAC, WEPENABLED,
					ISINFRASTRUCTURE;

		}

		public static class LOCATION {
			public String X, Y;

		}

	}

}
