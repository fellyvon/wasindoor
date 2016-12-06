package com.waspring.wasservice.net.busii;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.ScrtyDao;
import com.waspring.wasservice.net.model.CommonRepMessage;
import com.waspring.wasservice.net.model.ScrtyReqMessage;

/**
 * 密保功能
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "SERCTY_REQ")
public class ScrtyHandler implements IHandler {
	private ScrtyDao dao = new ScrtyDao();

	public Response handle(JsonElement data) throws Exception {
		Response res;

		ScrtyReqMessage loc = GsonFactory.getGsonInstance().fromJson(data,
				ScrtyReqMessage.class);

		dao.setScrty(loc);
		CommonRepMessage rm = new CommonRepMessage();

		rm.RTN_FLAG = "1";
rm.RTN_MSG="操作成功！";
		res = new Response(Status.ok, rm.RTN_MSG, rm.toJson());

		return res;
	}

}
