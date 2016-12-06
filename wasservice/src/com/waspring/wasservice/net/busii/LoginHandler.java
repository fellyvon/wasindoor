package com.waspring.wasservice.net.busii;

import java.math.BigInteger;
import java.sql.ResultSet;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.LoginDao;
import com.waspring.wasservice.net.model.LoginRepMessage;
import com.waspring.wasservice.net.model.LoginReqMessage;

/**
 * 登录
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "LOGIN_REQ")
public class LoginHandler implements IHandler {

	public static String md5(String userPass) throws Exception {

		java.security.MessageDigest md5 = java.security.MessageDigest
				.getInstance("MD5");
		md5.update(userPass.getBytes());

		byte b[] = md5.digest();

		return new BigInteger(b).toString(16);
	}

 

	private LoginDao dao = new LoginDao();

	public Response handle(JsonElement data) throws Exception {
		Response res;

		LoginReqMessage loc = GsonFactory.getGsonInstance().fromJson(data,
				LoginReqMessage.class);
		String clientNo = "";
		ResultSet rs = dao.queryUser(loc.MESSAGE.USER_NO, loc.MESSAGE.USER_PWD);
		if (rs.next()) {
			clientNo = rs.getString("user_id");
		}

		LoginRepMessage rm = new LoginRepMessage();
		rm.CLIENTNO = clientNo;
		if (clientNo == null || "".equals(clientNo)) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG="登录失败，用户名或密码不正确！";
			res = new Response(Status.failed, "登录失败，用户名或密码不正确！", null);
		} else {
			rm.RTN_FLAG = "1";
           rm.RTN_MSG="操作成功！";
			res = new Response(Status.ok, "登录成功！", rm.toJson());
		}

		return res;
	}

}
