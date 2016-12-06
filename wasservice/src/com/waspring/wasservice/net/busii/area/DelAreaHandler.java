package com.waspring.wasservice.net.busii.area;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.area.GraphDao;
import com.waspring.wasservice.net.model.CommonRepMessage;
import com.waspring.wasservice.net.model.area.GetAreaReqMessage;

@Requestable(serverName = "DEL_AREA_REQ")
public class DelAreaHandler implements IHandler {
	private GraphDao dao = new GraphDao();

	public Response handle(JsonElement data) throws Exception {
		GetAreaReqMessage model = GsonFactory.getGsonInstance().fromJson(data,
				GetAreaReqMessage.class);
		CommonRepMessage rep = new CommonRepMessage();
        if(dao.isSeller(model.MESSAGE.AREA_NO, model.MESSAGE.BUILD_NO, 
        		model.MESSAGE.FLOOR_NO, model.MESSAGE.DOOR_NO)){
        	rep.RTN_FLAG = "0";
    		rep.RTN_MSG = "区域存在商家信息，禁止删除！";
    		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
        }
		dao.delArea(model.MESSAGE.AREA_NO, true);
		rep.RTN_FLAG = "1";
		rep.RTN_MSG = "操作成功！";
		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());

	}

}
