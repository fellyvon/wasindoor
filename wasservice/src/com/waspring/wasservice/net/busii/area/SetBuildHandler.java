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
import com.waspring.wasservice.net.model.area.SetBuildReqMessage;

@Requestable(serverName = "SET_BUILDING_REQ")
public class SetBuildHandler implements IHandler {
	private GraphDao dao = new GraphDao();

	public Response handle(JsonElement data) throws Exception {
		SetBuildReqMessage model = GsonFactory.getGsonInstance().fromJson(data,
				SetBuildReqMessage.class);

		CommonRepMessage rep = new CommonRepMessage();
		if (StringUtils.isNullOrBank(model.MESSAGE.AREA_NO)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "区域编号为空";
			return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
		}
		if (StringUtils.isNullOrBank(model.MESSAGE.BUILD_NO)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "楼宇编号为空";
			return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
		}
		if (StringUtils.isNullOrBank(model.MESSAGE.JD)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "经度为空";
			return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
		}
		if (StringUtils.isNullOrBank(model.MESSAGE.WD)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "纬度为空";
			return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
		}
		ResultSet rs = dao.getBuildInfo(model.MESSAGE.AREA_NO,
				model.MESSAGE.BUILD_NO);
		if (rs.next()) {
			dao.delBuild(model.MESSAGE.AREA_NO, model.MESSAGE.BUILD_NO, false);
		}
		dao.saveBuildInfo(model);

		rep.RTN_FLAG = "1";
		rep.RTN_MSG = "操作成功！";
		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());

	}
}
