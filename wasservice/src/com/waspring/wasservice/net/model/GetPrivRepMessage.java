package com.waspring.wasservice.net.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class GetPrivRepMessage extends Base {

	public String USER_NO,RTN_FLAG, RTN_MSG;

	public List<PrivList> PRIV_LIST = new ArrayList<PrivList>();

 
	public  static  class PrivList {
		public String PRIV_NO, PRIV_NAME;
	}
}
