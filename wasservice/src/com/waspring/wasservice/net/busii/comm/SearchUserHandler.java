package com.waspring.wasservice.net.busii.comm;

import java.sql.ResultSet;
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
import com.waspring.wasservice.net.model.comm.SearchUserRepMessage;
import com.waspring.wasservice.net.model.comm.SearchUserReqMessage;

/**
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "SEARCH_USER_REQ")
public class SearchUserHandler implements IHandler {
	private CommDao dao = new CommDao();

	/**
	 * 
	 */
	public Response handle(JsonElement data) throws Exception {
		SearchUserReqMessage model = GsonFactory.getGsonInstance().fromJson(
				data, SearchUserReqMessage.class);

		SearchUserRepMessage rep = new SearchUserRepMessage();
		CachedRowSet as = null, bs = null, cs = null;
		if ("".equals(StringUtils.nullToEmpty(model.MESSAGE.USER_NO))
				&& "".equals(StringUtils.nullToEmpty(model.MESSAGE.USER_NAME))
				&& "".equals(StringUtils.nullToEmpty(model.MESSAGE.MP_NO))
				&& ("".equals(StringUtils.nullToEmpty(model.MESSAGE.R))
						|| "".equals(StringUtils.nullToEmpty(model.MESSAGE.JD)) || ""
						.equals(StringUtils.nullToEmpty(model.MESSAGE.WD))))

		{
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "���������ѯ������";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}

		ResultSet rs = dao.queryUserList(model);

		List<SearchUserRepMessage.Friends> friends = ResultToObject
				.resultToBase(SearchUserRepMessage.Friends.class, rs);

		if (friends.size() > 0) {
			rep.USER_LIST = friends;
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
