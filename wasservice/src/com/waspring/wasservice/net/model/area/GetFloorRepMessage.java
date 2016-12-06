package com.waspring.wasservice.net.model.area;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.CommonRepMessage;

public class GetFloorRepMessage extends CommonRepMessage {
	public String AREA_NO, BUILD_NO, FLOOR_NO, IMG_URL, DOORS, MAP_NO;

	public List<GetDoorRepMessage> DOOR_LIST = new ArrayList<GetDoorRepMessage>();

	public List<GetGraphRepMessage> MAP_LIST = new ArrayList<GetGraphRepMessage>();
}
