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
import com.waspring.wasservice.net.model.comm.ApplyAddGroupReqMessage;

/**
 * 申请入群
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "APPLY_GROUP_REP")
public class ApplyAddGroupHandler implements IHandler {
	private GroupDao dao = new GroupDao();

	public Response handle(JsonElement data) throws Exception {
		ApplyAddGroupReqMessage model = GsonFactory.getGsonInstance().fromJson(
				data, ApplyAddGroupReqMessage.class);

		CommonRepMessage rep = new CommonRepMessage();
		if ("".equals(StringUtils.nullToEmpty(model.MESSAGE.USER_NO))) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "申请人必须传入！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		if ("".equals(StringUtils.nullToEmpty(model.MESSAGE.GROUP_ID))) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "群组编号必须传入！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		dao.applyAddGroup(model.MESSAGE.GROUP_ID, model.MESSAGE.USER_NO,
				model.MESSAGE.CONTENT_MSG);

		rep.RTN_FLAG = "1";
		rep.RTN_MSG = "申请成功！";

		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());

	}

}
