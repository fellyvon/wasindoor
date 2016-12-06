package com.waspring.wasservice.net.busii;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.UserDao;
import com.waspring.wasservice.net.model.GetPrivRepMessage;
import com.waspring.wasservice.net.model.GetUserReqMessage;

/**
 * 获取用户信息
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "GET_PRIV_REQ")
public class GetPrivHandler implements IHandler {
	private UserDao dao = new UserDao();

	public Response handle(JsonElement data) throws Exception {
		Response res;

		GetUserReqMessage loc = GsonFactory.getGsonInstance().fromJson(data,
				GetUserReqMessage.class);
		GetPrivRepMessage rm = dao.getPriv(loc.MESSAGE.USER_NO);

		res = new Response(Status.ok, rm.RTN_MSG, rm.toJson());

		return res;
	}
	
	

}
