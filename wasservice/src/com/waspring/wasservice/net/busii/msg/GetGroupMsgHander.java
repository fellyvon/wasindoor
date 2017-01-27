package com.waspring.wasservice.net.busii.msg;

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
import com.waspring.wasservice.net.dao.msg.MsgDao;
import com.waspring.wasservice.net.model.msg.GetGroupMsgRepMessage;
import com.waspring.wasservice.net.model.msg.GetGroupMsgReqMessage;

/**
 * 5.1.7.2.11 Ⱥ����Ϣ�������� ������� GET_GROUPMSG_REQ/Ⱥ����Ϣ��������
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "GET_GROUPMSG_REQ")
public class GetGroupMsgHander implements IHandler {
	private MsgDao dao = new MsgDao();

	public Response handle(JsonElement data) throws Exception {
		GetGroupMsgReqMessage model = GsonFactory.getGsonInstance().fromJson(
				data, GetGroupMsgReqMessage.class);
		GetGroupMsgRepMessage rm = new GetGroupMsgRepMessage();
		if (StringUtils.isNullOrBank(model.MESSAGE.RCVER_NO)) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "�����˱��봫�룡";
			return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
		}

		if (StringUtils.isNullOrBank(model.MESSAGE.GROUP_ID)) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "Ⱥ���ű��봫�룡";
			return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
		}
		CachedRowSet rs = (CachedRowSet) dao
				.getGroupMsg(model.MESSAGE.GROUP_ID, model.MESSAGE.RCVER_NO);

		if (rs.getRowCount() == 0) {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "����Ϣ��";

			return new Response(Status.failed, rm.RTN_MSG, rm.toJson());
		}
		List<GetGroupMsgRepMessage.Message> MSG_LIST =

		ResultToObject.resultToBase(GetGroupMsgRepMessage.Message.class, dao
				.getGroupMsg(model.MESSAGE.GROUP_ID, model.MESSAGE.RCVER_NO));
		rm.GROUP_ID = model.MESSAGE.GROUP_ID;
		rm.MSG_LIST = MSG_LIST;
		rm.RTN_FLAG = "1";
		rm.RTN_MSG = "���ճɹ���";

		
		///////�鵵��Ϣ
		dao.arcGroupMsg(model);
		return new Response(Status.ok, rm.RTN_MSG, rm.toJson());

	}
}
