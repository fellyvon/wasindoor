package com.waspring.wasservice.net.busii;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.UserDao;
import com.waspring.wasservice.net.model.CommonRepMessage;
import com.waspring.wasservice.net.model.SetUserReqMessage;

/**
 * 修改获取用户信息
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "MODIFY_USERINFO_REQ")
public class SetUserHandler implements IHandler {
	private UserDao dao = new UserDao();

	public Response handle(JsonElement data) throws Exception {
		Response res;

		SetUserReqMessage loc = GsonFactory.getGsonInstance().fromJson(data,
				SetUserReqMessage.class);
		int index = dao.modifyUser(loc);
		
		CommonRepMessage rm = new CommonRepMessage();
		if(index==-100){
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "必须传入需要修改的属性！";
		return    new Response(Status.ok, rm.RTN_MSG, rm.toJson());
		}
		if (!"".equals(StringUtils.nullToEmpty(loc.MESSAGE.USER_HEAD))) {
			dao.modifyUserHead(loc.MESSAGE.USER_NO, loc.MESSAGE.USER_HEAD);
		}


		rm.RTN_FLAG = "1";
		rm.RTN_MSG = "";
		res = new Response(Status.ok, rm.RTN_MSG, rm.toJson());

		return res;
	}

}
