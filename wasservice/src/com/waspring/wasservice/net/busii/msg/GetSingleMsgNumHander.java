package com.waspring.wasservice.net.busii.msg;

import java.util.ArrayList;
import java.util.List;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasdb.comp.CachedRowSet;
import com.waspring.wasservice.net.dao.msg.MsgDao;
import com.waspring.wasservice.net.model.msg.GetSingleMsgNumRepMessage;
import com.waspring.wasservice.net.model.msg.SengSingleMsgReqMessage;

/**
 * 5.1.7.2.5 ������Ϣ�������� ������� GET_SINGLEMSGNUM_REQ/������Ϣ������������
 * 
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "GET_SINGLEMSGNUM_REQ")
public class GetSingleMsgNumHander implements IHandler {
	private MsgDao dao = new MsgDao();

	public Response handle(JsonElement data) throws Exception {
		SengSingleMsgReqMessage model = GsonFactory.getGsonInstance().fromJson(
				data, SengSingleMsgReqMessage.class);
		GetSingleMsgNumRepMessage rm = new GetSingleMsgNumRepMessage();
		if (StringUtils.isNullOrBank(model.MESSAGE.RCVER_NO)) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "�����˱��봫�룡";
			return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
		}
		CachedRowSet rs = (CachedRowSet) dao
				.getMsgNum(model.MESSAGE.RCVER_NO);

		if (rs.getRowCount() == 0) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "����Ϣ��";

			return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
		}
		List<GetSingleMsgNumRepMessage.Msg> MSG_LIST = new ArrayList<GetSingleMsgNumRepMessage.Msg>();
		while (rs.next()) {
			GetSingleMsgNumRepMessage.Msg msg = new GetSingleMsgNumRepMessage.Msg();
			msg.MSG_NUM = rs.getString("num");
			msg.SENDER_NO = rs.getString("SENDER_NO");

			MSG_LIST.add(msg);
		}

		rm.MSG_LIST = MSG_LIST;
		rm.RTN_FLAG = "1";
		rm.RTN_MSG = "���ճɹ���";

		return new Response(Status.ok, rm.RTN_MSG, rm.toJson());

	}

}
