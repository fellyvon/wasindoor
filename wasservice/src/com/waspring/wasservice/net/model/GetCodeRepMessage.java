package com.waspring.wasservice.net.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class GetCodeRepMessage extends Base {

	public String RTN_FLAG, RTN_MSG;

	public List<CodeList> CODE_LIST = new ArrayList<CodeList>();

	public static class CodeList {
		public String CODE_TYPE, CODE_NAME, CODE_VALUE, P_CODE_VALUE, CONTENT1,
				CONTENT2, CONTENT3, CONTENT4, CONTENT5;
	}
}
