package com.waspring.wasservice.net.model.seller;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.BaseObject;

public class SellerJoinReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static class Message {
		public String SJ_NO, USER_NO, SJ_NAME, KEY, JY_SORT, YY_SDATE,
				YY_EDATE, DESCRIPTION, AREA_NO, BUILD_NO, FLOOR_NO, DOOR_NO;

	}
}
