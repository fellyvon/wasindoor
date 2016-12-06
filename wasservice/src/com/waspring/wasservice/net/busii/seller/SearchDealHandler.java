package com.waspring.wasservice.net.busii.seller;

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
import com.waspring.wasservice.net.model.seller.SearchDealRepMessage;
import com.waspring.wasservice.net.model.seller.SearchDealReqMessage;

/**
 * 搜素商家团购信息
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "SEARCH_DEAL_REQ")
public class SearchDealHandler implements IHandler {

	public Response handle(JsonElement data) throws Exception {
		SearchDealReqMessage model = (SearchDealReqMessage) GsonFactory
				.getGsonInstance().fromJson(data, SearchDealReqMessage.class);
		Response res = null;
		SearchDealRepMessage reponse = new SearchDealRepMessage();

		if (StringUtils.isNullOrBank(model.MESSAGE.latitude)
				&& StringUtils.isNullOrBank(model.MESSAGE.longitude)
				&& StringUtils.isNullOrBank(model.MESSAGE.area_no)
				&& (StringUtils.isNullOrBank(model.MESSAGE.deal_id) || "0"
						.equals(model.MESSAGE.deal_id))) {

			res = new Response(Status.failed, "团购编号、经纬度、区域编号必输其一！！", reponse
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
				.queryDeal(model);

		List<SearchDealRepMessage.DataList> reps = ResultToObject.resultToBase(
				SearchDealRepMessage.DataList.class, cs);

		reponse.DATA_LIST = reps;

		if (cs.getRowCount() == 0) {
			reponse.RTN_FLAG = "0";
			reponse.RTN_MSG = "记录为空!";
			res = new Response(Status.failed, "查询失败", reponse.toJson());
		}

		else {
			reponse.RTN_FLAG = "1";
			reponse.RTN_MSG = "查询成功!";
			res = new Response(Status.ok, "查询成功！", reponse.toJson());
		}

		return res;
	}

}
