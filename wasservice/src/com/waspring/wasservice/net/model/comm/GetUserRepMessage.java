package com.waspring.wasservice.net.model.comm;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.Base;

public class GetUserRepMessage extends Base {

	public String RTN_FLAG, RTN_MSG;

	public List<Friends> ADDFRENDLIST = new ArrayList<Friends>();

	public static class Friends {
		public String REQ_NO, SEND_USER_NO, RCVER_NO, CONTENT_MSG;
	}
}
