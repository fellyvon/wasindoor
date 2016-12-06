package com.waspring.wasservice.net.busii.area;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.ResultToObject;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.area.GraphDao;
import com.waspring.wasservice.net.model.area.GetAreaReqMessage;
import com.waspring.wasservice.net.model.area.GetDoorRepMessage;
import com.waspring.wasservice.net.model.area.GetFloorRepMessage;
import com.waspring.wasservice.net.model.area.GetGraphRepMessage;

@Requestable(serverName = "GET_FLOOR_REQ")
public class GetFloorHandler implements IHandler {
	private GraphDao dao = new GraphDao();

	public Response handle(JsonElement data) throws Exception {
		GetAreaReqMessage model = GsonFactory.getGsonInstance().fromJson(data,
				GetAreaReqMessage.class);
		GetFloorRepMessage rep = new GetFloorRepMessage();

		ResultSet rs = dao.getFloorInfo(model.MESSAGE.AREA_NO,
				model.MESSAGE.BUILD_NO, model.MESSAGE.FLOOR_NO);
		int index = 0;

		while (rs.next()) {

			rep.AREA_NO = rs.getString("AREA_NO");
			rep.BUILD_NO = rs.getString("BUILD_NO");
			rep.DOORS = rs.getString("DOORS");
			rep.FLOOR_NO = rs.getString("FLOOR_NO");
			rep.IMG_URL = rs.getString("IMG_URL");
			rep.MAP_NO = rs.getString("MAP_NO");
			rep.IMG_URL = rs.getString("IMG_URL");

			index++;
		}

		if (index > 0) {
			Map map = new HashMap();
			map.put("DOOR_LIST", "x");

			map.put("MAP_LIST", "x");
			map.put("RTN_FLAG", "x");
			map.put("RTN_MSG", "x");
			rep.DOOR_LIST = ResultToObject.resultToBase(
					GetDoorRepMessage.class, dao.getDoorInfo(rep.AREA_NO,
							rep.BUILD_NO, rep.FLOOR_NO, null),map);
			rs = dao.getGraph(rep.MAP_NO);
			List list = new ArrayList();
			while (rs.next()) {
				GetGraphRepMessage repp = new GetGraphRepMessage();
				repp.MAP_NAME = rs.getString("MAP_NAME");
				repp.MAP_NO = rs.getString("MAP_NO");
				repp.MAP_URL = rs.getString("MAP_URL");
				repp.MAX_LEVEL = rs.getString("maxlevel");
				repp.MIN_LEVEL = rs.getString("minlevel");
				repp.MAP_BOUNDS = rs.getString("mapx0") + ","
						+ rs.getString("mapy0") + ","
						+ rs.getString("mapx1") + ","
						+ rs.getString("mapy1");
				repp.MAP_CENTER = rs.getString("centerx") + ","
						+ rs.getString("centery") + ","
						+ rs.getString("maplevel");
				repp.MAP_LEVEL = rs.getString("maplevel");
				list.add(repp);
			}
			rep.MAP_LIST = list;

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
