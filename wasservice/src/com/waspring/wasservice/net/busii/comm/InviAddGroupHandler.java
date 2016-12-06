package com.waspring.wasservice.net.busii.comm;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.comm.GroupDao;
import com.waspring.wasservice.net.model.CommonRepMessage;
import com.waspring.wasservice.net.model.comm.InviAddGroupReqMessage;

/**
 * 邀请入群
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "INVIADD_GROUP_REQ")
public class InviAddGroupHandler implements IHandler {
	private GroupDao dao = new GroupDao();

	public Response handle(JsonElement data) throws Exception {
		CommonRepMessage rep = new CommonRepMessage();

		InviAddGroupReqMessage model = GsonFactory.getGsonInstance().fromJson(
				data, InviAddGroupReqMessage.class);

		if ("".equals(model.MESSAGE.USER_NO) || model.MESSAGE.USER_NO == null) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "邀请人必须传入！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		if (model.MESSAGE.INVI_LIST.size() == 0) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "邀请明细必须传入！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		if (model.MESSAGE.INVI_LIST.size() > 50) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "一次邀请数量不能超过50！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		dao.inviAddGroup(model);

		rep.RTN_FLAG = "1";
		rep.RTN_MSG = "操作成功！";

		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
	}

}
