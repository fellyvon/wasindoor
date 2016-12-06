package com.waspring.wasindoor.locale.net.model;

public class ClearGeoModelReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {
		public String BUILD_NO, FLOOR_NO;
		public String POS_NO;
		public double X=Double.MIN_VALUE,Y=Double.MIN_VALUE;
	}

}
