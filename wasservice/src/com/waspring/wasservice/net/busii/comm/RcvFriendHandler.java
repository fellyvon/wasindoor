package com.waspring.wasservice.net.busii.comm;

import java.sql.ResultSet;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.dao.comm.CommDao;
import com.waspring.wasservice.net.model.CommonRepMessage;
import com.waspring.wasservice.net.model.comm.AddFriendReqMessage;
import com.waspring.wasservice.net.model.comm.RcvFriendReqMessage;

/**
 * 
 * @author felly
 * 
 *
 */
@Requestable(serverName = "RCV_FREND_REQ")
public class RcvFriendHandler implements IHandler {
	private CommDao dao = new CommDao();

	public Response handle(JsonElement data) throws Exception {
		RcvFriendReqMessage model = GsonFactory.getGsonInstance().fromJson(
				data, RcvFriendReqMessage.class);
		CommonRepMessage rep = new CommonRepMessage();
		String reqNo = model.MESSAGE.REQ_NO;
		String rlt = model.MESSAGE.CONFIRM_RSLT;
		if ("".equals(StringUtils.nullToEmpty(reqNo))) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "请求编号为空！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}
		if ("".equals(StringUtils.nullToEmpty(rlt))) {
			rep.RTN_FLAG = "0";
			rep.RTN_MSG = "处理结果为空！";
			return new Response(Status.failed, rep.RTN_MSG, rep.toJson());
		}

		// ////处理结果保存
		dao.rcvFriend(reqNo, rlt, model.MESSAGE.CONFU_RESON);
		// 02忽略，03同意，04，拒绝,05,同意并添加对方
		ResultSet rs = dao.queryAddFriendReq(reqNo);
		// //处理结果处理
		if ("05".equals(rlt)) {

			while (rs.next()) {
				AddFriendReqMessage add = new AddFriendReqMessage();
				add.MESSAGE.CONTENT_MSG = "";
				add.MESSAGE.RCVER_NO = rs.getString("SEND_USER_NO");
				add.MESSAGE.SEND_USER_NO = rs.getString("RCVER_NO");
				dao.addFriend(add);
			}
		}

		if ("05".equals(rlt) || "03".equals(rlt)) {
			rs.beforeFirst();
			while (rs.next()) {
				dao.addFreind(rs.getString("SEND_USER_NO"), rs
						.getString("RCVER_NO"));

				dao.addSysMsg(rs.getString("SEND_USER_NO"), rs
						.getString("RCVER_NO"), "添加好友"
						+ rs.getString("RCV_NAME") + "成功！");
			}

		}
		if ("04".equals(rlt)) {
			rs.beforeFirst();
			while (rs.next()) {
			dao.addSysMsg(rs.getString("SEND_USER_NO"), rs
					.getString("RCVER_NO"), "用户" + rs.getString("RCV_NAME")
					+ "拒绝，拒绝原因:" + rs.getString("CONFU_RESON"));
			}
		}

		rep.RTN_FLAG = "1";
		rep.RTN_MSG = "处理成功！";

		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
	}

}
