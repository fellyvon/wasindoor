package com.waspring.wasservice.net.busii.seller;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.waspring.wasservice.net.dao.sell.SearchDao;
import com.waspring.wasservice.net.model.seller.SearchSjRepMessage;
import com.waspring.wasservice.net.model.seller.SearchSjReqMessage;

/**
 * �����̼���Ϣ
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

			res = new Response(Status.failed, "�̻���š���γ�ȡ������ű�����һ����", reponse
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

		CachedRowSet cs = (CachedRowSet) dao
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
			reponse.RTN_MSG="��¼Ϊ��!";
			res = new Response(Status.failed, "��ѯʧ��", reponse.toJson());
		}

		else {
			reponse.RTN_FLAG="1";
			reponse.RTN_MSG="��ѯ�ɹ�!";
			res = new Response(Status.ok
					, "��ѯ�ɹ���", reponse.toJson());
		}

		return res;
	}

}
