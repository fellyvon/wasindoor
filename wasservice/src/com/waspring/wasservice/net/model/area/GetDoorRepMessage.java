package com.waspring.wasservice.net.model.area;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.CommonRepMessage;
import com.waspring.wasservice.net.model.seller.GetSellerRepMessage;

public class GetDoorRepMessage extends CommonRepMessage {
	public String AREA_NO, BUILD_NO, FLOOR_NO, DOOR_NO, BUSINESSID, SHOPLOGO,
			SHOPENGLIS, HOTLINE, BUSSI_NAME, BUSSI_SORT, OBJECTID, X, Y;

	//public List<GetSellerRepMessage> SJ_LIST = new ArrayList<GetSellerRepMessage>();
}
