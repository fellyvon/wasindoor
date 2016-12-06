package com.waspring.wasservice.net.model.comm;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.Base;

public class GetGroupRepMessage extends Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String RTN_FLAG, RTN_MSG;

	public List<Groups> GROUP_LIST = new ArrayList<Groups>();

	public static class Groups {
		public String GROUP_ID, GROUP_NAME, GROUP_NO, GROUP_DATE, GROUP_FUN,
				GROUP_NUM, MAX_NUM, GROUP_REMARK;
	}

	public static void main(String fsf[]) throws Exception {
		GetGroupRepMessage f = new GetGroupRepMessage();
		f.RTN_FLAG = "1";
		List<GetGroupRepMessage.Groups> friends = new ArrayList<GetGroupRepMessage.Groups>();
		GetGroupRepMessage.Groups fff = new GetGroupRepMessage.Groups();
		fff.GROUP_NAME = "11";
		friends.add(fff);
		f.GROUP_LIST = friends;

		System.out.println(f.toJson());

	}
}
