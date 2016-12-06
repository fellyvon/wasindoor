package com.waspring.wasservice.net.busii.seller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.component.CachedRowSet;
import com.aiyc.framework.utils.ResultToObject;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.sell.SearchDao;
import com.waspring.wasservice.net.model.seller.SearchSjRepMessage;
import com.waspring.wasservice.net.model.seller.SearchSjReqMessage;

/**
 * 搜素商家信息
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "SEARCH_SJ_REQ")
public class SearchSjHandler implements IHandler {

	public Response handle(JsonElement data) throws Exception {
		SearchSjReqMessage model = (SearchSjReqMessage) GsonFactory
				.getGsonInstance().fromJson(data, SearchSjReqMessage.class);
		Response res = null;
		SearchSjRepMessage reponse = new SearchSjRepMessage();

		if (StringUtils.isNullOrBank(model.MESSAGE.latitude)
				&& StringUtils.isNullOrBank(model.MESSAGE.longitude)
				&& StringUtils.isNullOrBank(model.MESSAGE.area_no)
				&&(StringUtils.isNullOrBank(model.MESSAGE.business_id)
						|| "0".equals(model.MESSAGE.business_id))) {

			res = new Response(Status.failed, "商户编号、经纬度、区域编号必输其一！！", reponse
					.toJson());

			return res;
		}

		if (StringUtils.isNullOrBank(model.MESSAGE.radius)
				|| "0".equals(model.MESSAGE.radius)) {
			model.MESSAGE.radius = "1000";
		}

		if (StringUtils.isNullOrBank(model.MESSAGE.limit)
				|| "0".equals(model.MESSAGE.limit)) {
			model.MESSAGE.limit = "20";
		}
		if (StringUtils.isNullOrBank(model.MESSAGE.page)
				|| "0".equals(model.MESSAGE.page)) {
			model.MESSAGE.page = "1";
		}

		SearchDao dao = new SearchDao();

		com.aiyc.framework.component.CachedRowSet cs = (CachedRowSet) dao
				.querySJ(model);

		List<SearchSjRepMessage.DataList> reps = ResultToObject.resultToBase(
				SearchSjRepMessage.DataList.class, cs);
		Iterator<SearchSjRepMessage.DataList> rt = reps.iterator();

		List<SearchSjRepMessage.DataList> result = new ArrayList<SearchSjRepMessage.DataList>();
		while (rt.hasNext()) {
			SearchSjRepMessage.DataList next = rt.next();

			next.deals = ResultToObject.resultToBase(
					SearchSjRepMessage.DataList.Deal.class, dao
							.queryDeals(next.business_id));
			result.add(next);
		}

		reponse.DATA_LIST = reps;

		if (cs.getRowCount() == 0) {
			reponse.RTN_FLAG="0";
			reponse.RTN_MSG="记录为空!";
			res = new Response(Status.failed, "查询失败", reponse.toJson());
		}

		else {
			reponse.RTN_FLAG="1";
			reponse.RTN_MSG="查询成功!";
			res = new Response(Status.ok
					, "查询成功！", reponse.toJson());
		}

		return res;
	}

}
