package com.waspring.wasservice.net.busii;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.UserDao;
import com.waspring.wasservice.net.model.CommonRepMessage;
import com.waspring.wasservice.net.model.SetSecReqMessage;
@Requestable(serverName = "SET_SEC_REQ")
public class SetSecHandler implements IHandler {
     private UserDao dao=new UserDao();
	public Response handle(JsonElement data) throws Exception {
		
		SetSecReqMessage model=GsonFactory.getGsonInstance().fromJson(data,
				SetSecReqMessage.class);
     
		dao.addSec(model);
		CommonRepMessage rm = new CommonRepMessage();
		rm.RTN_FLAG = "1";
		rm.RTN_MSG = "²Ù×÷³É¹¦£¡";
		Response res = new Response(Status.ok, rm.RTN_MSG, rm.toJson());

		return res;
	}

}
