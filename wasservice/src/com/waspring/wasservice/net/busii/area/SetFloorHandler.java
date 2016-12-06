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
import com.waspring.wasservice.net.model.area.SetFloorReqMessage;

@Requestable(serverName = "SET_FLOOR_REQ")
public class SetFloorHandler implements IHandler {
	private GraphDao dao = new GraphDao();

	public Response handle(JsonElement data) throws Exception {
		SetFloorReqMessage model = GsonFactory.getGsonInstance().fromJson(data,
				SetFloorReqMessage.class);

		CommonRepMessage rep = new CommonRepMessage();
		if (StringUtils.isNullOrBank(model.MESSAGE.AREA_NO)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "ÇøÓò±àºÅÎª¿Õ";
			return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
		}
		if (StringUtils.isNullOrBank(model.MESSAGE.BUILD_NO)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "Â¥Óî±àºÅÎª¿Õ";
			return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
		}
		if (StringUtils.isNullOrBank(model.MESSAGE.FLOOR_NO)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "Â¥²ãºÅÎª¿Õ";
			return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
		}
		if (StringUtils.isNullOrBank(model.MESSAGE.MAP_NO)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "µØÍ¼±àºÅÎª¿Õ";
			return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
		}
		ResultSet rs = dao.getFloorInfo(model.MESSAGE.AREA_NO,
				model.MESSAGE.BUILD_NO, model.MESSAGE.FLOOR_NO);
		if (rs.next()) {
			dao.delFloor(model.MESSAGE.AREA_NO, model.MESSAGE.BUILD_NO,
					model.MESSAGE.FLOOR_NO, false);
		}
		dao.saveFloorInfo(model);

		rep.RTN_FLAG = "1";
		rep.RTN_MSG = "²Ù×÷³É¹¦£¡";
		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());

	}
}
