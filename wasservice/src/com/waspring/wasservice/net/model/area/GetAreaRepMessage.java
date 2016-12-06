package com.waspring.wasservice.net.model.area;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.CommonRepMessage;

public class GetAreaRepMessage extends CommonRepMessage {
	public String AREA_NO, AREA_TYPE, P_AREA_NO, AREA_NAME, AREA_ADDR, IMG_URL,
			BUILD_NUM;

	public List<GetBuildRepMessage> BUILDS_LIST = new ArrayList<GetBuildRepMessage>();
}
