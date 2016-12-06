package com.waspring.wasservice.net.model.comm;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.Base;

public class GetAddFriendRepMessage extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public String   RTN_FLAG, RTN_MSG;
	public List<AddList> ADDFRENDLIST = new ArrayList<AddList>();

	public static class AddList {
		public String REQ_NO, SEND_USER_NO, RCVER_NO, CONTENT_MSG, REQ_TIME,
				STATUS;
	}

}
