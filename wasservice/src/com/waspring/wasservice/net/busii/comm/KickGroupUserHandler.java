package com.waspring.wasservice.net.busii.comm;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.comm.GroupDao;
import com.waspring.wasservice.net.model.CommonRepMessage;
import com.waspring.wasservice.net.model.comm.KickGroupUserReqMessage;

/**
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "KICK_USER_REQ")
public class KickGroupUserHandler implements IHandler {
	private GroupDao dao = new GroupDao();

	public Response handle(JsonElement data) throws Exception {
		KickGroupUserReqMessage model = GsonFactory.getGsonInstance().fromJson(
				data, KickGroupUserReqMessage.class);

		CommonRepMessage rep = new CommonRepMessage();
		if ("".equals(StringUtils.nullToEmpty(model.MESSAGE.GROUP_ID))) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "群组必须传入！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}

		if ("".equals(StringUtils.nullToEmpty(model.MESSAGE.USER_NO))) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "操作人必须传入！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		if ("".equals(StringUtils.nullToEmpty(model.MESSAGE.KICK_NO))) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "被剔除人必须传入！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		if (!dao.haveDel(model.MESSAGE.GROUP_ID, model.MESSAGE.USER_NO)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "无剔除权限！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		if (!dao.isInGroup(model.MESSAGE.GROUP_ID, model.MESSAGE.KICK_NO)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "被剔除人不在本群内！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}

		dao.kickGroupUser(model.MESSAGE.GROUP_ID, model.MESSAGE.USER_NO,
				model.MESSAGE.KICK_NO, model.MESSAGE.KICK_REASON);

		rep.RTN_FLAG = "1";
		rep.RTN_MSG = "操作成功！";

		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());

	}

}
