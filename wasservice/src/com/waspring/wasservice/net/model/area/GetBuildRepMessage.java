package com.waspring.wasservice.net.model.area;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.CommonRepMessage;

public class GetBuildRepMessage extends CommonRepMessage {
	public String AREA_NO, BUILD_NO, BUILDING_NAME, BUILDING_DESC, FLOORS, JD,
			WD, IMG_URL,FLOOR_PIX;

	public List<GetFloorRepMessage> FLOORS_LIST = new ArrayList<GetFloorRepMessage>();
}
