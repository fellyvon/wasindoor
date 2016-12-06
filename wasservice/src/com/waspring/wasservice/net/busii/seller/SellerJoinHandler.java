package com.waspring.wasservice.net.busii.seller;

import java.sql.ResultSet;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.sell.SellerDao;
import com.waspring.wasservice.net.model.seller.SellerJoinRepMessage;
import com.waspring.wasservice.net.model.seller.SellerJoinReqMessage;
@Requestable(serverName = "SJ_JOIN_REQ")
public class SellerJoinHandler implements IHandler {
	private SellerDao dao = new SellerDao();

	/**
	 * 
	 */
	public Response handle(JsonElement data) throws Exception {
		Response res;

		SellerJoinReqMessage model = GsonFactory.getGsonInstance().fromJson(
				data, SellerJoinReqMessage.class);

		SellerJoinRepMessage rep = new SellerJoinRepMessage();

		if (StringUtils.isNullOrBank(model.MESSAGE.USER_NO)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "商家负责人必须传入！";

			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}

		if (StringUtils.isNullOrBank(model.MESSAGE.SJ_NAME)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "商家名称必须传入！";

			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}

		if (StringUtils.isNullOrBank(model.MESSAGE.DESCRIPTION)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "商家描述必须传入！";

			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}

		if (StringUtils.isNullOrBank(model.MESSAGE.KEY)) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "商家关键字必须传入（用于被检索）！";

			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		String sjNo = "";
		ResultSet rs = dao.getSeller(model.MESSAGE.SJ_NO, null);

		if (rs != null && rs.next()) {
			if (rs.getString("ZZ_NO").equalsIgnoreCase(model.MESSAGE.USER_NO)) {
				rep.RTN_FLAG = "0";
				rep.RTN_MSG = "权限验证失败！";

				return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
			}

			sjNo = dao.editSeller(model);
		} else {
			sjNo = dao.saveSeller(model);
		}
		rep.SJ_NO = sjNo;
		rep.RTN_FLAG = "1";
		rep.RTN_MSG = "操作成功！";
		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
	}
}
