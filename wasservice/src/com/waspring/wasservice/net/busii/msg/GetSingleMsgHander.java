package com.waspring.wasservice.net.busii.msg;

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
import com.waspring.wasservice.net.dao.msg.MsgDao;
import com.waspring.wasservice.net.model.msg.GetSingleMsgRepMessage;
import com.waspring.wasservice.net.model.msg.SengSingleMsgReqMessage;

/**
 * 5.1.7.2.7 个人消息接收请求 服务名称 GET_SINGLEMSG_REQ/个人消息接受请求
 * 
 * 
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "GET_SINGLEMSG_REQ")
public class GetSingleMsgHander implements IHandler {
	private MsgDao dao = new MsgDao();

	public Response handle(JsonElement data) throws Exception {
		SengSingleMsgReqMessage model = GsonFactory.getGsonInstance().fromJson(
				data, SengSingleMsgReqMessage.class);
		GetSingleMsgRepMessage rm = new GetSingleMsgRepMessage();
		if (StringUtils.isNullOrBank(model.MESSAGE.RCVER_NO)) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "接收人必须传入！";
			return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
		}
		if (StringUtils.isNullOrBank(model.MESSAGE.SENDER_NO)) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "发送人必须传入！";
			return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
		}
		CachedRowSet rs = (com.aiyc.framework.component.CachedRowSet) dao
				.getMsg(model.MESSAGE.SENDER_NO, model.MESSAGE.RCVER_NO);

		if (rs.getRowCount() == 0) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "无消息！";

			return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
		}
		List<GetSingleMsgRepMessage.Msg> MSG_LIST = ResultToObject
				.resultToBase(GetSingleMsgRepMessage.Msg.class, dao.getMsg(
						model.MESSAGE.SENDER_NO, model.MESSAGE.RCVER_NO));

		rm.SENDER_NO = model.MESSAGE.SENDER_NO;
		rm.MSG_LIST = MSG_LIST;
		rm.RTN_FLAG = "1";
		rm.RTN_MSG = "接收成功！";
////归档
		dao.arcMsg(model.MESSAGE.SENDER_NO, model.MESSAGE.RCVER_NO);
		return new Response(Status.ok, rm.RTN_MSG, rm.toJson());

	}

}
