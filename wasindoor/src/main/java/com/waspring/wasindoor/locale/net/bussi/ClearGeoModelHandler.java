package com.waspring.wasindoor.locale.net.bussi;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasindoor.locale.net.dao.ModelDao;
import com.waspring.wasindoor.locale.net.model.ClearGeoModelReqMessage;
import com.waspring.wasindoor.locale.net.model.CommonRepMessage;

 
@Requestable(serverName = "CLEAR_GEO_MODEL_REQ")
public class ClearGeoModelHandler implements IHandler {

	public Response handle(JsonElement data) throws Exception {
		ClearGeoModelReqMessage model = GsonFactory.getGsonInstance().fromJson(
				data, ClearGeoModelReqMessage.class);
		String buildNo = model.MESSAGE.BUILD_NO;
		String floorNo = model.MESSAGE.FLOOR_NO;
		CommonRepMessage rep = new CommonRepMessage();
		if (StringUtils.nullToEmpty(buildNo).equals("")) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "楼宇编号必须输入！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}

		ModelDao dao = new ModelDao();
		dao.clearModel(buildNo, floorNo, model.MESSAGE.POS_NO, model.MESSAGE.X,
				model.MESSAGE.Y);

		rep.RTN_FLAG = "1";
		rep.RTN_MSG = "";
		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
	}

}
