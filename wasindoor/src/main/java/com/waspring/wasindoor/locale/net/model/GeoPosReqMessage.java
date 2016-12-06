package com.waspring.wasindoor.locale.net.model;

public class GeoPosReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {
		public String BUILD_NO, FLOOR_NO;
		public String xAngle; // x维度角度
		public String yAngle; // y维度角度
		public String zAngle; // z维度角度

		public String xMagnetic; // x维度磁强
		public String yMagnetic; // y维度磁强
		public String zMagnetic; // z维度磁强
	}

}
