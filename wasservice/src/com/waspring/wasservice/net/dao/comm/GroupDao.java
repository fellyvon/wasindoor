package com.waspring.wasservice.net.dao.comm;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.aiyc.framework.component.CachedRowSet;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.db.DaoUtil;
import com.waspring.wasservice.net.dao.ModelDao;
import com.waspring.wasservice.net.model.comm.CreateGroupReqMessage;
import com.waspring.wasservice.net.model.comm.InviAddGroupReqMessage;

public class GroupDao {

	/**
	 * 加入群组
	 */
	public void addGroupUser(String groupId, String userNo) throws Exception {
		String sql = "delete from t_group_user  where  USER_NO=? and  group_id=? ";
		DaoUtil.executeQuery(sql, new Object[] { userNo, groupId });

		sql = "insert into  t_group_user(USER_NO,group_id,ADD_TIME)"
				+ " values(?,?,now())";
		DaoUtil.executeQuery(sql, new Object[] { userNo, groupId });
	}

	/**
	 * 查询请求
	 */

	public ResultSet queryAddGroupReq(String reqNo) throws Exception {
		String sql = "select    (select user_name from "
				+ " k_user user where user.user_no="
				+ "RCV_NO limit 1) RCV_NAME,CONFU_RESON,   "
				+ "SEND_USER_NO,RCV_NO,CONTENT_MSG,add_sort"
				+ ",(select group_name from t_group g where g.group_id=inf.group_id limit 1)"
				+ " group_name,inf.group_id from  t_group_addinfo inf where id=?  ";

		return DaoUtil.queryData(sql, new Object[] { reqNo });

	}

	/**
	 * 保存处理结果
	 */
	public void rcvGroup(String reqNo, String rlt, String reason)
			throws Exception {
		String sql = "update    t_group_addinfo"
				+ " set RCV_DATE=now(),STATUS=?,CONFU_RESON=? where id=?";

		DaoUtil.executeQuery(sql, new Object[] { rlt, reason, reqNo

		});
	}

	/**
	 * 删除关系
	 */

	public void kickGroupUser(String groupId, String userNo, String kickNo,
			String msg) throws Exception {
		String sql = "INSERT INTO arc_t_group_user\n"
				+ "\t\t(group_id, user_no, un_no, un_date, un_reason,un_sort)\n"
				+ "\t\tSELECT g. group_id, p.group_no, g.user_no, now(), ?,'01'\n"
				+ "\t\t\tFROM t_group_user g, t_group p\n"
				+ "\t\t WHERE g.group_id = ?\n"
				+ "\t\t\t\t\t AND g.user_no = ?\n"
				+ "\t\t\t\t\t AND g.group_id = p.group_id";

		DaoUtil.executeQuery(sql, new Object[] { msg, groupId, kickNo });

		sql = "delete from  t_group_user g  where g.group_id = ? and  ND g.user_no = ?   ";
		DaoUtil.executeQuery(sql, new Object[] { groupId, kickNo });

	}

	/**
	 * 主动退出群组
	 */
	public void unGroupUser(String groupId, String userNo, String msg)
			throws Exception {
		String sql = "INSERT INTO arc_t_group_user\n"
				+ "\t\t(group_id, user_no, un_no, un_date, un_reason,un_sort)\n"
				+ "\t\tSELECT g. group_id, p.user_no, g.user_no, now(), ?,'02'\n"
				+ "\t\t\tFROM t_group_user g, t_group p\n"
				+ "\t\t WHERE g.group_id = ?\n"
				+ "\t\t\t\t\t AND g.user_no = ?\n"
				+ "\t\t\t\t\t AND g.group_id = p.group_id";

		DaoUtil.executeQuery(sql, new Object[] { msg, groupId, userNo });

		sql = "delete from  t_group_user g  where g.group_id = ? and  ND g.user_no = ?   ";
		DaoUtil.executeQuery(sql, new Object[] { groupId, userNo });

	}

	/**
	 * 判断是否拥有删除的权限
	 */

	public boolean haveDel(String groupId, String userNo) throws Exception {
		String sql = "select 1 from t_group where group_id=? and group_no=? ";
		return DaoUtil.queryData(sql, new Object[] { groupId, userNo }).next();
	}

	/**
	 * 判断人员是否在本群内
	 */
	public boolean isInGroup(String groupId, String userNo) throws Exception {
		String sql = "select 1 from t_group_user where group_id=? and user_No=? ";
		return DaoUtil.queryData(sql, new Object[] { groupId, userNo }).next();
	}

	/**
	 * 申请入群
	 */
	public void applyAddGroup(String groupId, String userNo, String msg)
			throws Exception {

		String sql = "INSERT INTO t_group_addinfo\n"
				+ "\t\t(send_user_no, rcv_no, group_id, content_msg, req_time, status,add_sort)\n"
				+ "  select   ?,GROUP_NO,group_id,?,now(),'01','02' from   t_group where  group_id=?      ";
		DaoUtil.executeQuery(sql, new Object[] { userNo, msg, groupId });

	}

	/**
	 * 查询邀请信息
	 */

	public com.aiyc.framework.component.CachedRowSet getAddGroup(String userNo)
			throws Exception {
		String sql =

			"SELECT t_group_addinfo.id, t_group_addinfo.send_user_no, t_group_addinfo.rcv_no,\n" +
			"\t\t\t t_group_addinfo.group_id, t_group_addinfo.content_msg, t_group_addinfo.req_time,\n" + 
			"\t\t\t t_group_addinfo.status, t_group_addinfo.add_sort\n" + 
			"\tFROM t_group_addinfo\n" + 
			" WHERE t_group_addinfo.rcv_no = ?\n" + 
			"\t\t\t AND t_group_addinfo.status IN ('01', '02')\n" + 
			"\t\t\t AND t_group_addinfo.add_sort IN ('01', '02') LIMIT 500";


		return (CachedRowSet) DaoUtil.queryData(sql, new Object[] {  
				userNo });
	}

	/**
	 * 邀请入群
	 */

	public void inviAddGroup(InviAddGroupReqMessage model) throws Exception {
		String userNo = model.MESSAGE.USER_NO;
		List<InviAddGroupReqMessage.Invi> dets = model.MESSAGE.INVI_LIST;
		Iterator<InviAddGroupReqMessage.Invi> it = dets.iterator();
		String sql = "INSERT INTO t_group_addinfo\n"
				+ "\t\t(send_user_no, rcv_no, group_id, content_msg, req_time, status,add_sort)\n"
				+ "    values(?,?,?,?,now(),'01','01')";

		while (it.hasNext()) {
			InviAddGroupReqMessage.Invi invi = it.next();
			List list = new ArrayList();
			list.add(userNo);
			list.add(invi.RCV_NO);
			list.add(invi.GROUP_ID);
			list.add(invi.CONTENT_MSG);
			DaoUtil.executeQuery(sql, list.toArray());
		}

	}

	/**
	 * 
	 * @param userNo
	 * @return
	 * @throws Exception
	 */

	public ResultSet queryGroup(String userNo) throws Exception {
		String sql = "SELECT   t_group.group_id, t_group.group_name, t_group.group_no,\n"
				+ "\t\t\t t_group.group_date, t_group.group_fun, t_group.group_num, t_group.max_num,\n"
				+ "\t\t\t t_group.group_remark\n"
				+ "\tFROM t_group, t_group_user\n"
				+ " WHERE t_group.group_id = t_group_user.group_id\n"
				+ "\t\t\t AND t_group_user.user_no = ?";

		return DaoUtil.queryData(sql, new Object[] { userNo });
	}

	public ResultSet searchGroup(String groupNo, String groupName,
			String groupFun) throws Exception {
		String sql = "SELECT   t_group.group_id, t_group.group_name, t_group.group_no,\n"
				+ "\t\t\t t_group.group_date, t_group.group_fun, t_group.group_num, t_group.max_num,\n"
				+ "\t\t\t t_group.group_remark\n"
				+ "\tFROM t_group\n"
				+ " WHERE  ";
		List list = new ArrayList();
		int index = 0;
		if (!StringUtils.nullToEmpty(groupNo).equals("")) {
			if (index == 0) {
				sql += "  group_id=? ";
			} else {
				sql += " and  group_id=? ";
			}
			index++;

			list.add(groupNo);

		}
		if (!StringUtils.nullToEmpty(groupName).equals("")) {
			if (index == 0) {
				sql += "  group_name like CONCAT( ?,'%') ";
			} else {
				sql += " and  group_name like CONCAT( ?,'%') ";
			}
			index++;

			list.add(groupName);

		}
		if (!StringUtils.nullToEmpty(groupFun).equals("")) {
			if (index == 0) {
				sql += "  group_fun like CONCAT( ?,'%') ";
			} else {
				sql += " and  group_fun like CONCAT( ?,'%') ";
			}
			index++;

			list.add(groupFun);

		}

		if (index == 0) {
			return null;
		}

		return DaoUtil.queryData(sql, list.toArray());
	}

	/**
	 * 组群
	 */
	public String createGroup(CreateGroupReqMessage model) throws Exception {

		String sql = "insert  into    t_group\n" + "\n" + "  (GROUP_ID,\n"
				+ "  GROUP_NAME,\n" + "  GROUP_NO,\n" + "  GROUP_DATE,\n"
				+ "  GROUP_FUN,\n" + "  GROUP_NUM,\n" + "  MAX_NUM,\n"
				+ "  GROUP_REMARK)\n" + "  values(?,?,?,now(),?,0,?,?)";
		int groupId = (new ModelDao()).getPrimaryKeyId("t_group", "ID");
		List list = new ArrayList();
		list.add(groupId);
		list.add(model.MESSAGE.GROUP_NAME);
		list.add(model.MESSAGE.GROUP_NO);
		list.add(model.MESSAGE.GROUP_FUN);
		list.add(MAX_GROUP_USER);
		list.add(model.MESSAGE.GROUP_REMARK);
		DaoUtil.executeQuery(sql, list.toArray());

		
		////自己肯定要加入群
	    this.addGroupUser(groupId+"", model.MESSAGE.GROUP_NO);
		
		return groupId + "";

	}

	public static final int MAX_GROUP_USER = 200;

}
