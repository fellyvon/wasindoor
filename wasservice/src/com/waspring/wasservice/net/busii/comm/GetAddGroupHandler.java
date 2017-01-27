package com.waspring.wasservice.net.busii.comm;

import java.util.ArrayList;
import java.util.List;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasdb.comp.CachedRowSet;
import com.waspring.wasservice.net.dao.comm.GroupDao;
import com.waspring.wasservice.net.model.GetUserReqMessage;
import com.waspring.wasservice.net.model.comm.GetAddGroupRepMessage;
@Requestable(serverName = "GET_ADDGROUP_REQ")
public class GetAddGroupHandler implements IHandler {
	private GroupDao dao = new GroupDao();

	public Response handle(JsonElement data) throws Exception {
		GetUserReqMessage model = GsonFactory.getGsonInstance().fromJson(data,
				GetUserReqMessage.class);
		GetAddGroupRepMessage rep = new GetAddGroupRepMessage();
		List<GetAddGroupRepMessage.AddGroup> ADDGROUPLIST = new ArrayList<GetAddGroupRepMessage.AddGroup>();
		CachedRowSet cs = dao.getAddGroup(model.MESSAGE.USER_NO);
		while (cs.next()) {
			GetAddGroupRepMessage.AddGroup g = new GetAddGroupRepMessage.AddGroup();
			g.REQ_NO = cs.getString("ID");
			g.GROUP_ID = cs.getString("GROUP_ID");
			g.USER_NO = cs.getString("send_user_no");
			g.RCV_NO = cs.getString("rcv_no");
			g.CONTENT_MSG = cs.getString("content_msg");
			g.REQ_TIME = cs.getString("req_time");
			g.STATUS = cs.getString("status");
			g.ADD_SORT = cs.getString("add_sort");
			ADDGROUPLIST.add(g);

		}
		if (cs.getRowCount() > 0) {
			rep.ADDGROUPLIST = ADDGROUPLIST;
			rep.RTN_FLAG = "1";
			rep.RTN_MSG = "��ѯ�ɹ���";
			return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
		} else {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "��ѯʧ�ܣ�������";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}

	}
}
