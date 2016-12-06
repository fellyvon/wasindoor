package com.waspring.wasservice.net.busii.comm;

import java.util.List;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.component.CachedRowSet;
import com.aiyc.framework.utils.ResultToObject;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.comm.GroupDao;
import com.waspring.wasservice.net.model.comm.GetGroupRepMessage;
import com.waspring.wasservice.net.model.comm.SearchGroupReqMessage;

/**
 * 群组搜索
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "SEARCH_GROUP_REQ")
public class SearchGroupHandler implements IHandler {

	private GroupDao dao = new GroupDao();

	/**
	 * 
	 */
	public Response handle(JsonElement data) throws Exception {
		SearchGroupReqMessage model = GsonFactory.getGsonInstance().fromJson(
				data, SearchGroupReqMessage.class);

		com.aiyc.framework.component.CachedRowSet set = (CachedRowSet) dao
				.searchGroup(model.MESSAGE.GROUP_ID, model.MESSAGE.GROUP_NAME,
						model.MESSAGE.GROUP_FUN);

		GetGroupRepMessage rep = new GetGroupRepMessage();

		if (set != null && set.getRowCount() > 0) {
			List<GetGroupRepMessage.Groups> gs = ResultToObject.resultToBase(
					GetGroupRepMessage.Groups.class, set);
			rep.GROUP_LIST = gs;
			rep.RTN_FLAG = "1";
			rep.RTN_MSG = "操作成功！";
			return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
		} else {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "无群组！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
	}

}
