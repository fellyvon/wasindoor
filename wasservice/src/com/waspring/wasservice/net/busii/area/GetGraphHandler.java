package com.waspring.wasservice.net.busii.area;

import java.sql.ResultSet;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.area.GraphDao;
import com.waspring.wasservice.net.model.area.GetGraphRepMessage;
import com.waspring.wasservice.net.model.area.GetGraphReqMessage;

@Requestable(serverName = "GET_GRAPH_REQ")
public class GetGraphHandler implements IHandler {
	private GraphDao dao = new GraphDao();

	public Response handle(JsonElement data) throws Exception {

		GetGraphReqMessage model = GsonFactory.getGsonInstance().fromJson(data,
				GetGraphReqMessage.class);

		String mapNo = model.MESSAGE.MAP_NO;
		ResultSet rs = dao.getGraph(mapNo);
		GetGraphRepMessage rep = new GetGraphRepMessage();

		if (rs.next()) {
			rep.MAP_NAME = rs.getString("MAP_NAME");
			rep.MAP_NO = rs.getString("MAP_NO");
			rep.MAP_URL = rs.getString("MAP_URL");
			rep.MAX_LEVEL = rs.getString("maxlevel");
			rep.MIN_LEVEL = rs.getString("minlevel");
			rep.MAP_BOUNDS = rs.getString("mapx0") + ","
					+ rs.getString("mapy0") + "," + rs.getString("mapx1") + ","
					+ rs.getString("mapy1");
			rep.MAP_CENTER = rs.getString("centerx") + ","
					+ rs.getString("centery") + "," + rs.getString("maplevel");
			rep.MAP_LEVEL=rs.getString("maplevel");
			rep.RTN_FLAG = "1";
			rep.RTN_MSG = "操作成功！";
			return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
		} else {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "无地图！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
	}

}
