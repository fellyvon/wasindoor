package com.waspring.wasservice.net.dao.comm;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.aiyc.framework.utils.StringUtils;
import com.waspring.wasdbtools.DaoUtil;
import com.waspring.wasservice.net.model.comm.AddFriendReqMessage;
import com.waspring.wasservice.net.model.comm.SearchUserReqMessage;

/**
 * 
 * @author felly
 * 
 */
public class CommDao {
	
	
	public static void main(String sdf[]) throws Exception
	{
		CommDao s=new CommDao();
		SearchUserReqMessage model=new SearchUserReqMessage();
		model.MESSAGE.USER_NAME=";1";
		model.MESSAGE.USER_NO="1100;";
		model.MESSAGE.SEX="1";
		model.MESSAGE.R="1";
		model.MESSAGE.WD="1";
		model.MESSAGE.JD="2";
	 
		s.queryUserList(model);
		
	}

	public ResultSet queryUserList(SearchUserReqMessage model) throws Exception {
		String sql = "SELECT k_user.user_no, k_user.user_name, k_user.STATUS status, "
				+ " t_online_user.area_no, t_online_user.mp_no, t_online_user.x map_x, "
				+ "  t_online_user.y map_y,'' last_hd, '' description,k_user_ext.USER_IMG  "
				+ "  FROM   k_user "
				+ " LEFT JOIN t_online_user "
				+ " ON k_user.user_no = t_online_user.user_no" +
						" left JOIN k_user_ext on  k_user_ext.user_no=k_user.user_no  "
				+ " where   k_user.USER_NO is not null    ";
		List list = new ArrayList();
		if (!"".equals(StringUtils.nullToEmpty(model.MESSAGE.USER_NO))) {
			sql += " and k_user.user_no= ?  ";
			list.add(model.MESSAGE.USER_NO);

		}
		if (!"".equals(StringUtils.nullToEmpty(model.MESSAGE.USER_NAME))) {
			sql += " and k_user.user_name  like CONCAT('%',?,'%') ";
			list.add(model.MESSAGE.USER_NAME);

		}

		if (!"".equals(StringUtils.nullToEmpty(model.MESSAGE.SEX))) {
			sql += " and k_user.sex  =? ";
			list.add(model.MESSAGE.SEX);

		}

		if (!"".equals(StringUtils.nullToEmpty(model.MESSAGE.MP_NO))) {
			sql += " and t_online_user.map_no  =? ";
			list.add(model.MESSAGE.MP_NO);
		}

		if (!"".equals(StringUtils.nullToEmpty(model.MESSAGE.WD))) {


		sql+=	"and t_online_user.MP_NO in\n" +
			"     (select map_no\n" + 
			"        from d_floor, d_building\n" + 
			"       where d_floor.BUILD_NO = d_building.BUILDING_NO\n" + 
			"         and sqrt((((? -JD) * PI() * 12656 *\n" + 
			"                  cos(((? +WD) / 2) * PI() / 180) / 180) *\n" + 
			"                  ((? -JD) * PI() * 12656 *\n" + 
			"                  cos(((? +WD) / 2) * PI() / 180) / 180)) +\n" + 
			"                  (((? -WD) * PI() * 12656 / 180) *\n" + 
			"                  ((? -WD) * PI() * 12656 / 180))) < ? / 1000)";


			list.add(model.MESSAGE.JD);
			list.add(model.MESSAGE.WD);

			list.add(model.MESSAGE.JD);
			list.add(model.MESSAGE.WD);
			list.add(model.MESSAGE.WD);
			list.add(model.MESSAGE.WD);

			list.add(model.MESSAGE.R);

		}

		ResultSet rs = DaoUtil.queryData(sql, list.toArray());

		return rs;

	}

	/**
	 * ͨ��mapno��������¥�¥����Ϣ
	 */

	public ResultSet getLoc(String mapNo) throws Exception {
		String sql = "select  `FLOOR_NO` ,  `BUILD_NO` ,  `AREA_NO`  from  `d_floor` where   `MAP_NO`=?";

		ResultSet rs = DaoUtil.queryData(sql, new Object[] { mapNo });

		return rs;
	}

	/**
	 * ��ѯ��ͼ���
	 */

	public String getMapNo(String mac) throws Exception {
		String sql = "select `MAP_ID` from     `macadresslist` where `MAC`=? ";

		ResultSet rs = DaoUtil.queryData(sql, new Object[] { mac });
		if (rs.next()) {
			return rs.getString("MAP_ID");
		}
		return null;
	}

	/**
	 * �ж��ǲ����Ѿ��Ǻ�����
	 */

	public boolean isFriend(String userNo, String friendNo) throws Exception {
		String sql = "select 1 from t_user_friend  where user_no=?  and FRIEND_USER_NO=?  ";

		return DaoUtil.queryData(sql, new Object[] { userNo, friendNo }).next();

	}

	/**
	 * ��ȡ�����б�
	 */

	public ResultSet queryFriendList(String userNo) throws Exception {
		String sql = "SELECT k_user.user_no,\n" + "       k_user.user_name,\n"
				+ "       t_user_friend.friend_status as STATUS,\n"
				+ "       t_online_user.area_no,\n"
				+ "       t_online_user.mp_no,\n"
				+ "       t_online_user.x map_x,\n"
				+ "       t_online_user.y map_y,\n"
				+ "       t_user_friend.last_hd,\n"
				+ "       '' description,\n" + "       k_user_ext.USER_IMG\n"
				+ "\n" + "  FROM t_user_friend\n" + "  LEFT JOIN k_user\n"
				+ "    ON t_user_friend.friend_user_no = k_user.user_no\n"
				+ "  left join k_user_ext\n" + "\n"
				+ "    on k_user_ext.USER_NO = k_user.user_no\n"
				+ "  LEFT JOIN t_online_user\n"
				+ "    ON k_user.user_no = t_online_user.user_no\n" + "\n"
				+ " WHERE t_user_friend.user_no = ?";

		return DaoUtil.queryData(sql, new Object[] { userNo });

	}

	/**
	 * ��ȡ�����б�
	 */

	public ResultSet queryFriendByName(String userName) throws Exception {
		String sql = "SELECT k_user.user_no, k_user.user_name, t_user_friend.friend_status status,\n"
				+ "\t\t\t t_online_user.area_no, t_online_user.mp_no, t_online_user.x map_x,\n"
				+ "\t\t\t t_online_user.y map_y, t_user_friend.last_hd, '' description\n"
				+ "\tFROM t_user_friend\n"
				+ "\tLEFT JOIN k_user\n"
				+ "\t\tON t_user_friend.friend_user_no = k_user.user_no\n"
				+ "\tLEFT JOIN t_online_user\n"
				+ "\t\tON k_user.user_no = t_online_user.user_no\n"
				+ " WHERE k_user.user_name  like CONCAT('%',?,'%')";

		return DaoUtil.queryData(sql, new Object[] { userName });

	}

	/**
	 * ��ȡ�����б�
	 */

	public ResultSet queryFriendByMap(String mapNo) throws Exception {
		String sql = "SELECT k_user.user_no, k_user.user_name, t_user_friend.friend_status status,\n"
				+ "\t\t\t t_online_user.area_no, t_online_user.mp_no, t_online_user.x map_x,\n"
				+ "\t\t\t t_online_user.y map_y, t_user_friend.last_hd, '' description\n"
				+ "\tFROM t_user_friend\n"
				+ "\tLEFT JOIN k_user\n"
				+ "\t\tON t_user_friend.friend_user_no = k_user.user_no\n"
				+ "\tLEFT JOIN t_online_user\n"
				+ "\t\tON k_user.user_no = t_online_user.user_no\n"
				+ " WHERE t_online_user.mp_no=? ";

		return DaoUtil.queryData(sql, new Object[] { mapNo });

	}

	/**
	 * �����������
	 */

	public void addFriend(AddFriendReqMessage model) throws Exception {
		String sql = "insert into   T_USER_ADDINFO(SEND_USER_NO,RCVER_NO,CONTENT_MSG,REQ_TIME,STATUS)"
				+ " values(?,?,?,now(),?)";

		DaoUtil.executeUpdate(sql, new Object[] { model.MESSAGE.SEND_USER_NO,
				model.MESSAGE.RCVER_NO, model.MESSAGE.CONTENT_MSG, "01"

		});
	}

	/**
	 * ���洦����
	 */
	public void rcvFriend(String reqNo, String rlt, String reason)
			throws Exception {
		String sql = "update    T_USER_ADDINFO set RCV_DATE=now(),STATUS=?,CONFU_RESON=? where id=?";

		DaoUtil.executeUpdate(sql, new Object[] { rlt, reason, reqNo

		});
	}

	/**
	 * ��ѯ����
	 */

	public ResultSet queryAddFriendReq(String reqNo) throws Exception {
		String sql = "select    (select user_name from "
				+ " k_user user where user.user_no="
				+ "RCVER_NO limit 1) RCV_NAME,CONFU_RESON,   SEND_USER_NO,RCVER_NO,CONTENT_MSG from  T_USER_ADDINFO where id=?  ";

		return DaoUtil.queryData(sql, new Object[] { reqNo });

	}

	/**
	 * 
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	public ResultSet queryAddAddFriendList(String userNo) throws Exception {
		String sql = "select    ID,SEND_USER_NO,RCVER_NO,CONTENT_MSG,REQ_TIME,STATUS from "
				+ "   T_USER_ADDINFO  where RCVER_NO=? and  STATUS in('01','02') ";

		return DaoUtil.queryData(sql, new Object[] { userNo });
	}

	/**
	 * ����ϵ����¼
	 */
	public void delFreind(String sendUserNo, String delUserNo, String delReason)
			throws Exception {
		String sql = "delete from T_USER_FRIEND  where  USER_NO=? and  FRIEND_USER_NO=? ";
		DaoUtil.executeUpdate(sql, new Object[] { sendUserNo, delUserNo });

		sql = "insert into  T_USER_UNADD(SEND_USER_NO,DEL_USER_NO,DEL_REASON,DEL_TIME)"
				+ " values(?,?,?,now())";
		DaoUtil.executeUpdate(sql, new Object[] { sendUserNo, delUserNo,
				delReason });
	}

	/**
	 * ��Ӻ���
	 */
	public void addFreind(String sendUserNo, String rcvUserNo) throws Exception {
		String sql = "delete from T_USER_FRIEND  where  USER_NO=? and  FRIEND_USER_NO=? ";
		DaoUtil.executeUpdate(sql, new Object[] { sendUserNo, rcvUserNo });

		sql = "insert into  T_USER_FRIEND(USER_NO,FRIEND_USER_NO,ADD_TIME)"
				+ " values(?,?,now())";
		DaoUtil.executeUpdate(sql, new Object[] { sendUserNo, rcvUserNo });
	}

	/**
	 * ���ϵͳ��Ϣ
	 */
	public void addSysMsg(String userNo, String rcvNo, String msg)
			throws Exception {
		String sql = "insert into  T_MSG(USER_NO,RCV_NO,MSG,STATUS,SEND_TIME)"
				+ " values(?,?,?,'01',now())";
		DaoUtil.executeUpdate(sql, new Object[] { userNo, rcvNo, msg });
	}

	/**
	 * ��ѯ�û�
	 */

	public boolean haveUser(String userNo) throws Exception {
		String sql = "select 1 from k_user where user_no=? ";
		return DaoUtil.queryData(sql, new Object[] { userNo }).next();
	}
}
