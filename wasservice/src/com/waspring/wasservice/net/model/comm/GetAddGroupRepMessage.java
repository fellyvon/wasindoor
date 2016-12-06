package com.waspring.wasservice.net.model.comm;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.CommonRepMessage;

public class GetAddGroupRepMessage extends CommonRepMessage {

	public List<AddGroup> ADDGROUPLIST = new ArrayList<AddGroup>();

	public static class AddGroup {
		public String REQ_NO, GROUP_ID, USER_NO, RCV_NO, CONTENT_MSG, REQ_TIME,
				STATUS,ADD_SORT;

	}
}
