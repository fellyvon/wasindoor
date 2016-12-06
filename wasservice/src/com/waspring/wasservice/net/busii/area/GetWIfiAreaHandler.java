package com.waspring.wasservice.net.busii.area;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.ResultToObject;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.area.GraphDao;
import com.waspring.wasservice.net.dao.comm.CommDao;
import com.waspring.wasservice.net.model.area.GetAreaRepMessage;
import com.waspring.wasservice.net.model.area.GetBuildRepMessage;
import com.waspring.wasservice.net.model.area.GetWifiAreaReqMessage;

@Requestable(serverName = "GET_WIFIAREA_REQ")
public class GetWIfiAreaHandler implements IHandler {
	private GraphDao dao = new GraphDao();
	private CommDao cdao = new CommDao();

	public Response handle(JsonElement data) throws Exception {
		GetWifiAreaReqMessage model = GsonFactory.getGsonInstance().fromJson(
				data, GetWifiAreaReqMessage.class);
		GetAreaRepMessage rep = new GetAreaRepMessage();
		Iterator<GetWifiAreaReqMessage.Message.Wifi> it = model.MESSAGE.AP_INFO
				.iterator();
		String mapNo = "";
		while (it.hasNext()) {
			GetWifiAreaReqMessage.Message.Wifi ap = it.next();
			String temp = cdao.getMapNo(ap.AP_MAC);
			if (temp == null || "".equals(temp)) {
				continue;
			}

			mapNo = temp;
			break;
		}
		if ("".equals(mapNo)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "定位区域失败！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		ResultSet rs = null;
		String areaNo = "";
		rs = dao.queryArea(mapNo);
		if (rs.next()) {
			areaNo = rs.getString("AREA_NO");
		} else {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "请先维护区域信息！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}

		rs = dao.getAreaInfo(areaNo);
		int index = 0;

		while (rs.next()) {

			rep.AREA_NO = rs.getString("AREA_NO");
			rep.AREA_NAME = rs.getString("AREA_NAME");
			rep.AREA_ADDR = rs.getString("AREA_ADDR");
			rep.AREA_TYPE = rs.getString("AREA_TYPE");
			rep.P_AREA_NO = rs.getString("P_AREA_NO");
			rep.BUILD_NUM = rs.getString("BUILD_NUM");
			rep.IMG_URL = rs.getString("IMG_URL");

			index++;
		}

		if (index > 0) {
			Map map = new HashMap();
			map.put("FLOORS_LIST", "x");
			map.put("RTN_FLAG", "x");
			map.put("RTN_MSG", "x");
			rep.BUILDS_LIST = ResultToObject.resultToBase(
					GetBuildRepMessage.class, dao.getBuildInfo(areaNo, null),
					map);

			/**	for (int i = 0; i < rep.BUILDS_LIST.size(); i++) {
				GetBuildRepMessage next = (GetBuildRepMessage) rep.BUILDS_LIST
						.get(i);
				map = new HashMap();
				map.put("DOOR_LIST", "x");

				map.put("MAP_LIST", "x");
				map.put("RTN_FLAG", "x");
				map.put("RTN_MSG", "x");
				next.FLOORS_LIST = ResultToObject.resultToBase(
						GetFloorRepMessage.class, dao.getFloorInfo(rep.AREA_NO,
								next.BUILD_NO, null), map);
				for (int j = 0; j < next.FLOORS_LIST.size(); j++) {
					GetFloorRepMessage p = (GetFloorRepMessage) next.FLOORS_LIST
							.get(j);

					map = new HashMap();
					map.put("SJ_LIST", "x");
					map.put("RTN_FLAG", "x");
					map.put("RTN_MSG", "x");
					p.DOOR_LIST = ResultToObject.resultToBase(
							GetDoorRepMessage.class, dao.getDoorInfo(
									rep.AREA_NO, next.BUILD_NO, p.FLOOR_NO,
									null), map);

					rs = dao.getGraph(p.MAP_NO);
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
					p.MAP_LIST = list;

				}

			}
**/
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
