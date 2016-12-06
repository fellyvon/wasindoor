package com.waspring.wasindoor.locale.net.bussi;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasindoor.locale.IndoorTrainTask;
import com.waspring.wasindoor.locale.net.model.GeoPosRepMessage;
import com.waspring.wasindoor.locale.net.model.GeoPosReqMessage;

 

@Requestable(serverName = "BUILD_GEO_POS_REQ")
public class BuildGeoPosHandler implements IHandler {

	public Response handle(JsonElement data) throws Exception {
 
		GeoPosReqMessage model = GsonFactory.getGsonInstance().fromJson(data,
				GeoPosReqMessage.class);

		GeoPosRepMessage rep = new GeoPosRepMessage();

		String buildNo = model.MESSAGE.BUILD_NO;
		String floorNo = model.MESSAGE.FLOOR_NO;

		if (StringUtils.nullToEmpty(buildNo).equals("")) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "创建失败，楼宇编号必须输入！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}

		IndoorTrainTask task = new IndoorTrainTask();

		if (!StringUtils.nullToEmpty(floorNo).equals("")) {
			task.buildBuildFloor(buildNo, floorNo);
		} else {

			task.buildBuild(buildNo);

		}

		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
	}

}
