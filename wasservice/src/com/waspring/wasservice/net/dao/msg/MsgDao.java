package com.waspring.wasservice.net.dao.msg;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.aiyc.framework.utils.UID;
import com.waspring.wasdbtools.DaoUtil;
import com.waspring.wasservice.net.model.msg.GetGroupMsgReqMessage;
import com.waspring.wasservice.net.model.msg.SendGroupMsgReqMessage;
import com.waspring.wasservice.net.model.msg.SengSingleMsgReqMessage;

public class MsgDao {

	public ResultSet getGroupMsg(String groupId, String rcver) throws Exception {
		String sql = "SELECT sender_no, msg_sort_code, content_msg, send_time\n"
				+ "\tFROM t_group_rcv_msg\n"
				+ " WHERE t_group_rcv_msg. group_id = ?\n"
				+ "\t\t\t AND EXISTS (SELECT 1\n"
				+ "\t\t\t\t\tFROM t_group_rcv_status\n"
				+ "\t\t\t\t WHERE t_group_rcv_status. rcv_id = t_group_rcv_msg.id\n"
				+ "\t\t\t\t\t\t\t AND rcv_no = ?\n"
				+ "\t\t\t\t\t\t\t AND is_rcv = '01')\n"
				+ "\n"
				+ "               order by    send_time";

		return DaoUtil.queryData(sql, new Object[] { groupId, rcver });

	}

	public void arcGroupMsg(GetGroupMsgReqMessage model) throws Exception {
		// ///
		String sql = "UPDATE t_group_rcv_status\n"
				+ "\t SET rcv_date = now(), is_rcv = '02'\n"
				+ " WHERE rcv_no = ?\n" + "\t\t\t AND rcv_id IN (SELECT id\n"
				+ "\t\t\t\t\t\t\t\t\t\t\t\tFROM t_group_rcv_msg\n"
				+ "\t\t\t\t\t\t\t\t\t\t\t WHERE group_id = ?)";
		DaoUtil.executeUpdate(sql, new Object[] { model.MESSAGE.RCVER_NO,
				model.MESSAGE.GROUP_ID });

		// //�鿴������Ϣ�������Ƿ����
		sql = "select count(1) num from   t_group_rcv_status,t_group_rcv_msg where "
				+ "  t_group_rcv_status.rcv_id= t_group_rcv_msg.id and   "
				+ "t_group_rcv_status.is_rcv='01' and t_group_rcv_msg.group_id=? "
				+ "";

		ResultSet rs = DaoUtil.queryData(sql,
				new Object[] { model.MESSAGE.GROUP_ID

				});
		int count = -1;

		if (rs.next()) {
			count = rs.getInt("num");
		}

		if (count == 0) {
			sql = "INSERT INTO arc_t_group_rcv_status\n"
					+ "\t\t(id, rcv_id, rcv_no, is_rcv,rcv_date )\n"
					+ "\t\tSELECT id, rcv_id, rcv_no,  is_rcv,rcv_date from " +
							"  t_group_rcv_status WHERE rcv_id IN (SELECT id\n"
					+ "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tFROM t_group_rcv_msg\n"
					+ "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t WHERE group_id = ?)";
			DaoUtil.executeUpdate(sql, new Object[] { model.MESSAGE.GROUP_ID });

			sql = "delete from  t_group_rcv_status where   rcv_id IN (SELECT id\n"
					+ "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tFROM t_group_rcv_msg\n"
					+ "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t WHERE group_id = ?)";

			DaoUtil.executeUpdate(sql, new Object[] { model.MESSAGE.GROUP_ID });
			sql = "INSERT INTO arc_t_group_rcv_msg\n"
					+ "\t\t(id, group_id, sender_no, msg_sort_code, content_msg, send_time)\n"
					+ "\t\tSELECT id, group_id, sender_no, msg_sort_code, content_msg, send_time\n"
					+ "\t\t\tFROM t_group_rcv_msg\n"
					+ "\t\t WHERE group_id = ?";

			DaoUtil.executeUpdate(sql, new Object[] { model.MESSAGE.GROUP_ID });

			sql = "delete from  t_group_rcv_msg where group_id = ?  ";
			DaoUtil.executeUpdate(sql, new Object[] { model.MESSAGE.GROUP_ID });

		}

	}

	public void arcMsg(String send, String rcver) throws Exception {
		String sql = "INSERT INTO arc_t_rcv_msg\n"
				+ "\t\t(id, sender_no, rcver_no, msg_sort_code, content_msg, send_time, rcv_date)\n"
				+ "\t\tSELECT id, sender_no, rcver_no, msg_sort_code, content_msg, send_time, now()\n"
				+ "\t\t\tFROM t_rcv_msg g\n" + "\t\t WHERE g. sender_no = ?\n"
				+ "\t\t\t\t\t AND g.rcver_no = ?";
		DaoUtil.executeUpdate(sql, new Object[] { send, rcver });

		sql = " delete     from t_rcv_msg   where   sender_no = ?\n"
				+ "\t\t\t\t\t AND  rcver_no = ?";
		DaoUtil.executeUpdate(sql, new Object[] {send,rcver});
	}

	public ResultSet getMsg(String send, String rcver) throws Exception {
		String sql = "select  `MSG_SORT_CODE` ,`CONTENT_MSG`,`SEND_TIME`"
				+ "   from t_rcv_msg g where g.`SENDER_NO` =? and  g.RCVER_NO=?  order by SEND_TIME     ";
		return DaoUtil.queryData(sql, new Object[] { send, rcver });

	}

	public ResultSet getMsgNum(String rcver) throws Exception {
		String sql = "select `SENDER_NO`,count(1) num    from t_rcv_msg g where g.RCVER_NO=?  group by SENDER_NO ";
		return DaoUtil.queryData(sql, new Object[] { rcver });

	}

	public ResultSet getGroupMsgNum(String rcver) throws Exception {
		String sql = "select `GROUP_ID`,count(1) num    from `t_group_rcv_msg` g "
				+ "where exists (select 1 "
				+ " from `t_group_rcv_status`,t_group_rcv_msg where   t_group_rcv_status.`RCV_ID`="
				+ "t_group_rcv_msg.id and t_group_rcv_status.`RCV_NO`=? and "
				+ "t_group_rcv_status.`IS_RCV`='01' )  group by `GROUP_ID` ";
		return DaoUtil.queryData(sql, new Object[] { rcver });

	}

	public void saveSingleMsg(SengSingleMsgReqMessage model) throws Exception {
		String sql = "insert into    `t_rcv_msg`(\n" + " `SENDER_NO` ,\n"
				+ " `RCVER_NO`,\n" + " `MSG_SORT_CODE` ,\n"
				+ " `CONTENT_MSG` ,\n" + " `SEND_TIME`)\n"
				+ " values(?,?,?,?,now())";
		List list = new ArrayList();
		list.add(model.MESSAGE.SENDER_NO);
		list.add(model.MESSAGE.RCVER_NO);
		list.add(model.MESSAGE.MSG_SORT_CODE);
		list.add(model.MESSAGE.CONTENT_MSG);
		DaoUtil.executeUpdate(sql, list.toArray());
	}

	public String saveGroupMsg(SendGroupMsgReqMessage model) throws Exception {
		String sql = "insert into   `t_group_rcv_msg`(id,\n"
				+ " `GROUP_ID` ,\n" + " `SENDER_NO` ,\n"
				+ " `MSG_SORT_CODE` ,\n" + " `CONTENT_MSG` ,\n"
				+ " `SEND_TIME`\n" + "\n" + " )\n" + " values(?,?,?,?,?,now())";
		String id = UID.next();
		List list = new ArrayList();
		list.add(id);
		list.add(model.MESSAGE.GROUP_ID);
		list.add(model.MESSAGE.SENDER_NO);

		list.add(model.MESSAGE.MSG_SORT_CODE);
		list.add(model.MESSAGE.CONTENT_MSG);
		DaoUtil.executeUpdate(sql, list.toArray());

		return id;
	}

	/**
	 * д��Ⱥ�����״̬
	 */

	public void saveGroupMsgStatus(String id, SendGroupMsgReqMessage model)
			throws Exception {
		String sql = "insert into   `t_group_rcv_status`(\n"
				+ " `RCV_ID` ,\n"
				+ " `RCV_NO` ,\n"
				+ " `IS_RCV`\n"
				+ "\n"
				+ " )\n"
				+ "select  ?,`USER_NO`,'01'   from `t_group_user`  where t_group_user.`GROUP_ID`=?" +
						" and  t_group_user.USER_NO<>?    ";
		List list = new ArrayList();
		list.add(id);
		list.add(model.MESSAGE.GROUP_ID);
		list.add(model.MESSAGE.SENDER_NO);
		DaoUtil.executeUpdate(sql, list.toArray());

	}

}
