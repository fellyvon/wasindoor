package com.waspring.wasservice.net.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class GetSecRepMessage extends Base {

	public String USER_NO,RTN_FLAG, RTN_MSG;

	public List<SetList> SET_LIST = new ArrayList<SetList>();

 
	public  static  class SetList {
		public String CONFIG_TYPE, CONFIG_NAME,CONFIG_VALUE;
	}
}
