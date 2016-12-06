package com.waspring.wasservice.net.busii;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.UserDao;
import com.waspring.wasservice.net.model.GetUserHeadRepMessage;
import com.waspring.wasservice.net.model.GetUserHeadReqMessage;
/**
 * 获取用户头像
 * @author felly
 *
 */@Requestable(serverName = "GET_USERHEAD_REQ")
public class GetUserHeadHandler implements IHandler {
	private UserDao dao = new UserDao();

	public Response handle(JsonElement data) throws Exception {

		GetUserHeadReqMessage loc = GsonFactory.getGsonInstance().fromJson(
				data, GetUserHeadReqMessage.class);

		String head = dao.getUserHead(loc.MESSAGE.USER_NO);

		GetUserHeadRepMessage rm = new GetUserHeadRepMessage();
		if (head != null && !"".equals(head)) {
			rm.RTN_FLAG = "1";
			rm.RTN_MSG = "操作成功！";
			rm.USER_HEAD = head;
		} else {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "头像数据不存在！";
		}
		return new Response(Status.ok, rm.RTN_MSG, rm.toJson());
	}

}
