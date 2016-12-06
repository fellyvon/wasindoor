package com.waspring.wasservice.net.busii;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.UserDao;
import com.waspring.wasservice.net.model.GetSecRepMessage;
import com.waspring.wasservice.net.model.GetUserReqMessage;
@Requestable(serverName = "GET_SEC_REQ")
public class GetSecHandler implements IHandler {
	private UserDao dao = new UserDao();

	public Response handle(JsonElement data) throws Exception {
		GetUserReqMessage model = GsonFactory.getGsonInstance().fromJson(data,
				GetUserReqMessage.class);
		
		GetSecRepMessage rm = dao.getSec(model.MESSAGE.USER_NO);
		return new Response(Status.ok, rm.RTN_MSG, rm.toJson());
	}

}
