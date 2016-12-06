package com.waspring.wasindoor.locale.net.model;

import java.util.ArrayList;
import java.util.List;

public class CreateGenModelReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {

		public List<Pos> POS_INFO = new ArrayList<Pos>();

	}

	public static class Geo {

		public String xAngle; // x维度角度
		public String yAngle; // y维度角度
		public String zAngle; // z维度角度

		public String xMagnetic; // x维度磁强
		public String yMagnetic; // y维度磁强
		public String zMagnetic; // z维度磁强

	}

	public static class Pos {
		public String BUILD_NO, FLOOR_NO;
		public double X=Double.MIN_VALUE, Y=Double.MIN_VALUE;
		public String  POS_NO;
		public List<Geo> GEO_INFO = new ArrayList<Geo>();
	}

}
