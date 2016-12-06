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
 * 多方登录
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "MUTI_LOGIN_REQ")
public class MutiLoginHandler implements IHandler {

	public static final String STATUS_LINK = "1";
	public static final String STATUS_UNLINK = "0";

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
		ResultSet rs = dao.queryUserByOpenID(loc.MESSAGE.OPEN_ID);
		LoginRepMessage rm = new LoginRepMessage();
		if (rs.next()) {
			clientNo = rs.getString("user_id");
			rm.STATUS = STATUS_LINK;
		} else {
			rm.STATUS = STATUS_UNLINK;
		}

		rm.CLIENTNO = clientNo;

		rm.RTN_FLAG = "1";
		rm.RTN_MSG = "操作成功！";
		res = new Response(Status.ok, "操作成功！", rm.toJson());

		return res;
	}

}
