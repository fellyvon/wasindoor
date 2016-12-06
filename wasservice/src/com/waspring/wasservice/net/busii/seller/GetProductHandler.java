package com.waspring.wasservice.net.busii.seller;

import java.util.List;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.ResultToObject;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.sell.SellerDao;
import com.waspring.wasservice.net.model.seller.GetProductListRepMessage;
import com.waspring.wasservice.net.model.seller.GetProductRepMessage;
import com.waspring.wasservice.net.model.seller.GetProductReqMessage;

/**
 * 获取商家产品信息
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "GET_PRODUCT_REQ")
public class GetProductHandler implements IHandler {
	private SellerDao dao = new SellerDao();

	public Response handle(JsonElement data) throws Exception {
		Response res;

		GetProductReqMessage model = GsonFactory.getGsonInstance().fromJson(
				data, GetProductReqMessage.class);
		GetProductListRepMessage rm = new GetProductListRepMessage();
		if (StringUtils.isNullOrBank(model.MESSAGE.SJ_NO)) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = " 商家编号必须输入！";
			return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
		}

		List<GetProductRepMessage> rst = ResultToObject.resultToBase(
				GetProductRepMessage.class, dao.getProduct(model.MESSAGE.SJ_NO,
						model.MESSAGE.KEY, model.MESSAGE.CP_NO));

		if (rst.size() > 0) {
			rm.CP_LIST = rst;
			rm.RTN_FLAG = "1";
			rm.RTN_MSG = "查询成功！";
			return new Response(Status.ok, rm.RTN_MSG, rm.toJson());
		} else {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "查询失败,产品不存在！";
			return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
		}

	}
}
