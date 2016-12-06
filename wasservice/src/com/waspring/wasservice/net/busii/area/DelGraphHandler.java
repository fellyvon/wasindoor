package com.waspring.wasservice.net.busii.area;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.area.GraphDao;
import com.waspring.wasservice.net.model.CommonRepMessage;
import com.waspring.wasservice.net.model.area.GetGraphReqMessage;

@Requestable(serverName = "DEL_GRAPH_REQ")
public class DelGraphHandler implements IHandler {
	private GraphDao dao = new GraphDao();

	public Response handle(JsonElement data) throws Exception {
		GetGraphReqMessage model = GsonFactory.getGsonInstance().fromJson(data,
				GetGraphReqMessage.class);
		CommonRepMessage rep = new CommonRepMessage();

		if(dao.ismapUse(model.MESSAGE.MAP_NO)){
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "地图使用中，禁止删除！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		dao.delGraph(model.MESSAGE.MAP_NO);
		rep.RTN_FLAG = "1";
		rep.RTN_MSG = "操作成功！";
		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());

	}

}
