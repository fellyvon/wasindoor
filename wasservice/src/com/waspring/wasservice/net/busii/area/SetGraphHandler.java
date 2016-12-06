package com.waspring.wasservice.net.busii.area;

import java.sql.ResultSet;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.area.GraphDao;
import com.waspring.wasservice.net.model.CommonRepMessage;
import com.waspring.wasservice.net.model.area.SetGraphReqMessage;

@Requestable(serverName = "SET_GRAPH_REQ")
public class SetGraphHandler implements IHandler {
	private GraphDao dao = new GraphDao();

	public Response handle(JsonElement data) throws Exception {

		SetGraphReqMessage model = GsonFactory.getGsonInstance().fromJson(data,
				SetGraphReqMessage.class);
		CommonRepMessage rep = new CommonRepMessage();
		if (StringUtils.isNullOrBank(model.MESSAGE.MAP_NO)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "地图编号必须传入！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}

		if (StringUtils.isNullOrBank(model.MESSAGE.MAP_URL)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "地图URL必须传入！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		if (StringUtils.isNullOrBank(model.MESSAGE.MAP_BOUNDS)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "地图范围必须传入！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		
		if (StringUtils.isNullOrBank(model.MESSAGE.MAP_CENTER)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "地图中心坐标必须传入！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		
		if (model.MESSAGE.MAP_BOUNDS.split(",").length!=4) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "地图范围坐标有错误！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		if (model.MESSAGE.MAP_CENTER.split(",").length!=4) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "地图中心坐标有错误！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		ResultSet rs = dao.getGraph(model.MESSAGE.MAP_NO);
		if (rs.next()) {
			dao.delGraph(model.MESSAGE.MAP_NO);
		}

		dao.saveGraph(model);
		rep.RTN_FLAG = "1";
		rep.RTN_MSG = "操作成功！";
		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());

	}

}
