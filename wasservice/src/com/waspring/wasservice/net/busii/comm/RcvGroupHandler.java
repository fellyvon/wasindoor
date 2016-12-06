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
import com.waspring.wasservice.net.dao.comm.GroupDao;
import com.waspring.wasservice.net.model.CommonRepMessage;
import com.waspring.wasservice.net.model.comm.RcvFriendReqMessage;

/**
 * 接收入群请求
 * 
 * @author felly
 * 
 */
@Requestable(serverName = "RCV_GROUP_REQ")
public class RcvGroupHandler implements IHandler {
	private GroupDao dao = new GroupDao();

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
		dao.rcvGroup(reqNo, rlt, model.MESSAGE.CONFU_RESON);

		// 02忽略，03同意，04，拒绝
		ResultSet rs = dao.queryAddGroupReq(reqNo);

		if ("03".equals(rlt)) {
			rs.beforeFirst();
			while (rs.next()) {
				dao.addGroupUser(rs.getString("group_id"), rs
						.getString("RCV_NO"));

				String sort=rs.getString("add_sort");
			   String msg="";
			   if("01".equals(sort)){
				   msg="好友"
						+ rs.getString("RCV_NAME") + "同意加入群组"+rs.getString("group_name");
			   }
			   else{
				   msg="同意加入群组"+rs.getString("group_name");
			   
			   }
				(new CommDao()).addSysMsg(rs.getString("SEND_USER_NO"), rs
						.getString("RCV_NO"),msg );
			}
		}
		if ("04".equals(rlt)) {
			rs.beforeFirst();
			while (rs.next()) {
			 

				String sort=rs.getString("add_sort");
			   String msg="";
			   if("01".equals(sort)){
				   msg="好友"
						+ rs.getString("RCV_NAME") + "拒绝加入群组"+rs.getString("group_name");
			   }
			   else{
				   msg="拒绝加入群组"+rs.getString("group_name");
			   
			   }
				(new CommDao()).addSysMsg(rs.getString("SEND_USER_NO"), rs
						.getString("RCV_NO"),msg );
			}
		}
		
		rep.RTN_FLAG = "1";
		rep.RTN_MSG = "处理成功！";

		return new Response(Status.ok, rep.RTN_MSG, rep.toJson());
	}

}
