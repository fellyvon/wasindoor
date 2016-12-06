package com.waspring.wasservice.net.busii;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.CodeDao;
import com.waspring.wasservice.net.model.GetCodeRepMessage;
import com.waspring.wasservice.net.model.GetCodeReqMessage;
@Requestable(serverName = "GET_CODE_REQ")
public class GetCodeHandler implements IHandler {
	private CodeDao dao = new CodeDao();

	public Response handle(JsonElement data) throws Exception {
		GetCodeReqMessage model = GsonFactory.getGsonInstance().fromJson(data,
				GetCodeReqMessage.class);
	 
		GetCodeRepMessage rm = dao.getCode(model);
		return new Response(Status.ok, rm.RTN_MSG, rm.toJson());
	}

}
