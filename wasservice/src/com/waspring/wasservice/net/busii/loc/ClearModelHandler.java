package com.waspring.wasservice.net.busii.loc;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.loc.ClearModelDao;
import com.waspring.wasservice.net.model.CommonRepMessage;
import com.waspring.wasservice.net.model.loc.ClearModelReqMessage;
@Requestable(serverName = "CLEAR_MODEL_REQ")
public class ClearModelHandler implements IHandler {

	public Response handle(JsonElement data) throws Exception {
		ClearModelReqMessage model=GsonFactory.getGsonInstance().fromJson(data,
				ClearModelReqMessage.class);
		String mapNo=model.MESSAGE.MAP_NO;
		CommonRepMessage rep=new CommonRepMessage();
		if(StringUtils.nullToEmpty(mapNo).equals("")){
			rep.RTN_FLAG="0";
			rep.RTN_MSG="±ÿ–Î ‰»ÎµÿÕº±‡∫≈£°";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		
		ClearModelDao dao=new ClearModelDao();
		dao.clearModel(mapNo);
		
		rep.RTN_FLAG="1";
		rep.RTN_MSG="";
		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
	}

}
