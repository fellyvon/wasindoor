package com.waspring.wasservice.net.busii.area;

import java.sql.ResultSet;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.area.GraphDao;
import com.waspring.wasservice.net.model.area.GetAreaReqMessage;
import com.waspring.wasservice.net.model.area.GetDoorRepMessage;

@Requestable(serverName = "GET_DOOR_REQ")
public class GetDoorHandler implements IHandler {
	private GraphDao dao = new GraphDao();

	public Response handle(JsonElement data) throws Exception {
		GetAreaReqMessage model = GsonFactory.getGsonInstance().fromJson(data,
				GetAreaReqMessage.class);
		GetDoorRepMessage rep = new GetDoorRepMessage();

		ResultSet rs = dao.getDoorInfo(model.MESSAGE.AREA_NO,
				model.MESSAGE.BUILD_NO, model.MESSAGE.FLOOR_NO,
				model.MESSAGE.DOOR_NO);
		int index = 0;

		while (rs.next()) {

			rep.AREA_NO = rs.getString("AREA_NO");
			rep.BUILD_NO = rs.getString("BUILD_NO");

			rep.FLOOR_NO = rs.getString("FLOOR_NO");
			rep.DOOR_NO = rs.getString("DOOR_NO");

			index++;
		}

		if (index > 0) {

//			rep.SJ_LIST = ResultToObject.resultToBase(
//					GetSellerRepMessage.class, (new SellerDao()).getSeller(
//							model.MESSAGE.AREA_NO, model.MESSAGE.BUILD_NO,
//							model.MESSAGE.FLOOR_NO, model.MESSAGE.DOOR_NO));

			rep.RTN_FLAG = "1";
			rep.RTN_MSG = "操作成功！";
			return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
		} else {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "无查询结果！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
	}

}
