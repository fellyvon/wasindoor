package com.waspring.wasservice.net.busii;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.LoginDao;
import com.waspring.wasservice.net.dao.RegDao;
import com.waspring.wasservice.net.dao.UserDao;
import com.waspring.wasservice.net.model.RegRepMessage;
import com.waspring.wasservice.net.model.RegReqMessage;
@Requestable(serverName = "REG_REQ")
public class RegHandler implements IHandler {
	private LoginDao ldao = new LoginDao();
	private RegDao dao = new RegDao();
	private UserDao uerdao = new UserDao();

	public Response handle(JsonElement data) throws Exception {
		Response res;
		RegRepMessage rm = new RegRepMessage();
		RegReqMessage loc = GsonFactory.getGsonInstance().fromJson(data,
				RegReqMessage.class);
		if (loc.MESSAGE.USER_PWD == null
				|| !loc.MESSAGE.USER_PWD.equals(loc.MESSAGE.CONFIRM_USER_PWD)) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "注册失败，密码输入不一致";
			res = new Response(Status.failed, rm.RTN_MSG, rm.toJson());
			return res;
		}
		if (ldao.queryUserByName(loc.MESSAGE.USER_NO).next()) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "注册失败，用户名已经被注册";
			res = new Response(Status.failed, rm.RTN_MSG, rm.toJson());
			return res;
		}
		int result = dao.addUser(loc);
		if (!"".equals(StringUtils.nullToEmpty(loc.MESSAGE.USER_HEAD))) {
			uerdao.modifyUserHead(loc.MESSAGE.USER_NO, loc.MESSAGE.USER_HEAD);
		}

		rm.RTN_FLAG = "1";
		rm.RTN_MSG = "注册成功";
		rm.USER_NAME=StringUtils.isNullOrBank(loc.MESSAGE.USER_NAME)?loc.MESSAGE.USER_NO:
			loc.MESSAGE.USER_NAME;
		res = new Response(Status.ok, rm.RTN_MSG, rm.toJson());

		return res;
	}

}
