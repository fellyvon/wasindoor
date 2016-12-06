package com.waspring.wasservice.net.busii.comm;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.comm.CommDao;
import com.waspring.wasservice.net.model.GetUserReqMessage;
import com.waspring.wasservice.net.model.comm.GetAddFriendRepMessage;

/**
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "GET_ADDFREND_REQ")
public class GetAddFriendHandler implements IHandler {
	private CommDao dao = new CommDao();

	/**
	 * 
	 */
	public Response handle(JsonElement data) throws Exception {
		GetUserReqMessage model = GsonFactory.getGsonInstance().fromJson(data,
				GetUserReqMessage.class);

		ResultSet rs = dao.queryAddAddFriendList(model.MESSAGE.USER_NO);
		GetAddFriendRepMessage reps = new GetAddFriendRepMessage();
		List<GetAddFriendRepMessage.AddList> list = new ArrayList<GetAddFriendRepMessage.AddList>();
		while (rs.next()) {
			GetAddFriendRepMessage.AddList rep = new GetAddFriendRepMessage.AddList();
			rep.REQ_NO = rs.getString("ID");
			rep.SEND_USER_NO = rs.getString("SEND_USER_NO");
			rep.RCVER_NO = rs.getString("RCVER_NO");
			rep.CONTENT_MSG = rs.getString("CONTENT_MSG");
			rep.REQ_TIME = rs.getString("REQ_TIME");
			rep.STATUS = rs.getString("STATUS");
			list.add(rep);
		}

		if (list.size() > 0) {
			reps.ADDFRENDLIST=list;
			reps.RTN_FLAG = "1";
			reps.RTN_MSG = "操作成功！";
			return new Response(Status.ok, reps.RTN_MSG, reps.toJson());
		} else {
			reps.RTN_FLAG = "0";
			reps.RTN_MSG = "无查询结果";
			return new Response(Status.failed, reps.RTN_MSG, reps.toJson());
		}
	}

}
