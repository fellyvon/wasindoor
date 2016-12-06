package com.waspring.wasservice.net.busii.comm;

import java.sql.ResultSet;
import java.util.List;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.ResultToObject;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.comm.CommDao;
import com.waspring.wasservice.net.model.GetUserReqMessage;
import com.waspring.wasservice.net.model.comm.GetFriendRepMessage;

/**
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "GET_FRIEND_REQ")
public class GetFriendHandler implements IHandler {
	private CommDao dao = new CommDao();
 
	/**
	 * 
	 */
	public Response handle(JsonElement data) throws Exception {
		GetUserReqMessage model = GsonFactory.getGsonInstance().fromJson(data,
				GetUserReqMessage.class);

		GetFriendRepMessage rep = new GetFriendRepMessage();

		ResultSet rs = dao.queryFriendList(model.MESSAGE.USER_NO);

		List<GetFriendRepMessage.Friends> friends = ResultToObject
				.resultToBase(GetFriendRepMessage.Friends.class, rs);

		if (friends.size() > 0) {
			rep.FRIEND_LIST = friends;
			rep.RTN_FLAG = "1";
			rep.RTN_MSG = "查询成功！";
			return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
		} else {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "查询失败，无好友！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
	}

}
