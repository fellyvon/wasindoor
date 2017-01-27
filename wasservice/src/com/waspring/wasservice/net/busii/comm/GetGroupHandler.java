package com.waspring.wasservice.net.busii.comm;

import java.util.List;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.ResultToObject;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasdb.comp.CachedRowSet;
import com.waspring.wasservice.net.dao.comm.GroupDao;
import com.waspring.wasservice.net.model.GetUserReqMessage;
import com.waspring.wasservice.net.model.comm.GetGroupRepMessage;
@Requestable(serverName = "GET_GROUP_REQ")
public class GetGroupHandler implements IHandler {

	private GroupDao dao = new GroupDao();

	/**
	 * 
	 */
	public Response handle(JsonElement data) throws Exception {
		GetUserReqMessage model = GsonFactory.getGsonInstance().fromJson(data,
				GetUserReqMessage.class);
		String userNo = model.MESSAGE.USER_NO;

	 CachedRowSet set = (CachedRowSet) dao
				.queryGroup(userNo);

		GetGroupRepMessage rep = new GetGroupRepMessage();

		if (set.getRowCount() > 0) {
			List<GetGroupRepMessage.Groups> gs = ResultToObject.resultToBase(
					GetGroupRepMessage.Groups.class, set);
			rep.GROUP_LIST = gs;
			rep.RTN_FLAG = "1";
			rep.RTN_MSG = "�����ɹ���";
			return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
		} else {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "��Ⱥ�飡";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
	}

}
