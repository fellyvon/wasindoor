package com.waspring.wasservice.net.busii;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.UserDao;
import com.waspring.wasservice.net.model.CommonRepMessage;
import com.waspring.wasservice.net.model.ModifyUserHeadReqMessage;
@Requestable(serverName = "MODIFY_USERHEAD_REQ")
public class ModifyUserHeadHandler implements IHandler {
	private UserDao dao = new UserDao();

	/**
	 * felly
	 */
	public Response handle(JsonElement data) throws Exception {
		ModifyUserHeadReqMessage model = GsonFactory.getGsonInstance()
				.fromJson(data, ModifyUserHeadReqMessage.class);
		dao.modifyUserHead(model.MESSAGE.USER_NO, model.MESSAGE.USER_HEAD);

		CommonRepMessage rm = new CommonRepMessage();
		rm.RTN_FLAG = "1";
		rm.RTN_MSG = "²Ù×÷³É¹¦£¡";

		return new Response(Status.ok, rm.RTN_MSG, rm.toJson());

	}

}
