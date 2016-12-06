package com.waspring.wasservice.net.model.comm;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.Base;

public class SearchUserRepMessage extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String RTN_FLAG, RTN_MSG;

	public List<Friends> USER_LIST = new ArrayList<Friends>();

	public static class Friends {
		public String USER_NO, USER_NAME, STATUS, AREA_NO, MP_NO, MAP_X, MAP_Y,
				LAST_HD, DESCRIPTION, USER_IMG;
	}
}
