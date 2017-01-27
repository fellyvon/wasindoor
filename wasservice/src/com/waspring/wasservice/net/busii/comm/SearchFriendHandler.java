package com.waspring.wasservice.net.busii.comm;

import java.util.List;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.ResultToObject;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasdb.comp.CachedRowSet;
import com.waspring.wasservice.net.dao.comm.CommDao;
import com.waspring.wasservice.net.model.comm.GetFriendRepMessage;
import com.waspring.wasservice.net.model.comm.SearchUserReqMessage;

/**
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "SEARCH_FRIEND_REQ")
public class SearchFriendHandler implements IHandler {
	private CommDao dao = new CommDao();

	/**
	 * 
	 */
	public Response handle(JsonElement data) throws Exception {
		SearchUserReqMessage model = GsonFactory.getGsonInstance().fromJson(
				data, SearchUserReqMessage.class);

		GetFriendRepMessage rep = new GetFriendRepMessage();
		CachedRowSet as = null, bs = null, cs = null;
		if ("".equals(StringUtils.nullToEmpty(model.MESSAGE.USER_NO))
				&& "".equals(StringUtils.nullToEmpty(model.MESSAGE.USER_NAME))
				&& "".equals(StringUtils.nullToEmpty(model.MESSAGE.MP_NO))

		) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "���������ѯ������";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		if (!"".equals(StringUtils.nullToEmpty(model.MESSAGE.USER_NO)))
			as = (CachedRowSet) dao.queryFriendList(model.MESSAGE.USER_NO);

		if (!"".equals(StringUtils.nullToEmpty(model.MESSAGE.USER_NAME))) {
			bs = (CachedRowSet) dao.queryFriendByName(model.MESSAGE.USER_NAME);
		}
		if (!"".equals(StringUtils.nullToEmpty(model.MESSAGE.MP_NO))) {
			cs = (CachedRowSet) dao.queryFriendByMap(model.MESSAGE.MP_NO);
		}

		CachedRowSet rs = null;
		int a = 0, b = 0, c = 0;
		if (as != null) {
			rs = as;
			a = 1;
		} else if (bs != null) {
			rs = bs;
			b = 1;
		} else if (cs != null) {
			rs = cs;
			c = 1;
		}

		if (rs == null) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "�޲�ѯ���";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}

		if (a == 1 && b == 1 && c == 1) {
			rs.union(bs);
			rs.union(cs);
		} else if (a == 0 && b == 1 && c == 1) {

			rs.union(cs);
		} else if (a == 1 && b == 0 && c == 1) {

			rs.union(cs);
		} else if (a == 1 && b == 1 && c == 0) {

			rs.union(bs);
		}

		List<GetFriendRepMessage.Friends> friends = ResultToObject
				.resultToBase(GetFriendRepMessage.Friends.class, rs);

		if (friends.size() > 0) {
			rep.FRIEND_LIST = friends;
			rep.RTN_FLAG = "1";
			rep.RTN_MSG = "��ѯ�ɹ���";
			return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
		} else {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "��ѯʧ�ܣ��޽��";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
	}

}
