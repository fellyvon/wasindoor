package com.waspring.wasservice.net.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.db.DaoUtil;
import com.waspring.wasservice.net.model.GetPrivRepMessage;
import com.waspring.wasservice.net.model.GetSecRepMessage;
import com.waspring.wasservice.net.model.GetUserRepMessage;
import com.waspring.wasservice.net.model.SetSecReqMessage;
import com.waspring.wasservice.net.model.SetUserReqMessage;

public class UserDao {

	/**
	 * 获取头像文件
	 */

	public String getUserHead(String userNo) throws Exception {
		String sql = "select USER_IMG from  k_user_ext where USER_NO=? ";
		ResultSet rs = DaoUtil.queryData(sql, new Object[] { userNo });

		if (rs.next()) {
			return rs.getString("USER_IMG");

		}
		return "";
	}

	/**
	 * 修改头像
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public void modifyUserHead(String userNo, String head) throws Exception {
		String sql = "select 1 from  k_user_ext where USER_NO=? ";
		ResultSet rs = DaoUtil.queryData(sql, new Object[] { userNo });
		if (rs.next()) {// //更新
			sql = "insert into   arc_k_user_ext (user_no,user_img,up_date,arc_date)"
					+ " select    user_no,user_img,up_date ,now() from  k_user_ext where user_no=? ";
			DaoUtil.executeQuery(sql, new Object[] { userNo });

			sql = " update k_user_ext set USER_IMG=?,UP_DATE=now() where user_no=?  ";
			DaoUtil.executeQuery(sql, new Object[] { head, userNo });
		} else {// /记录
			sql = "insert into k_user_ext(user_no,user_img,up_date) values(?,?,now())";
			DaoUtil.executeQuery(sql, new Object[] { userNo, head });
		}
	}

	public int addSec(SetSecReqMessage model) throws Exception {
		String userId = getUserId(model.MESSAGE.USER_NO);
		String sql = "delete from p_user_tb where user_id =?   and k.config_type=? "
				+ "and config_value=? ";

		List<SetSecReqMessage.scrList> list = new ArrayList<SetSecReqMessage.scrList>();
		Iterator<SetSecReqMessage.scrList> it = list.iterator();
		while (it.hasNext()) {
			SetSecReqMessage.scrList li = it.next();
			DaoUtil.executeQuery(sql, new Object[] { userId, li.SERCTY_TYPE,
					li.SERCTY_VALUE });

			DaoUtil.executeQuery("insert into p_user_tb(user_id,config_type,"
					+ " config_value)values(?,?,?)", new Object[] { userId,
					li.SERCTY_TYPE, li.SERCTY_VALUE

			});
		}

		return 1;

	}

	public String getUserId(String usrNo) throws Exception {
		String userId = "";

		ResultSet rs = DaoUtil.queryData("select user_id "
				+ " from k_user where user_no=?", new Object[] { usrNo });

		if (rs.next()) {
			return rs.getString(1);
		}
		return userId;

	}

	public GetSecRepMessage getSec(String userNo) throws Exception {
		GetSecRepMessage sec = new GetSecRepMessage();

		sec.USER_NO = userNo;
		sec.RTN_FLAG = "1";
		sec.RTN_MSG = "";
		String sql = "select config_type,config_value,"
				+ "(select code_name from k_code kk where kk.value=config_value"
				+ "and kk.code_type='configSort' limit 1) config_name"
				+ "  from p_user_tb a,k_user b where a.user_id=b.user_id and "
				+ "  b.user_no=?  ";
		ResultSet rs = DaoUtil.queryData(sql, new Object[] { userNo });
		List<GetSecRepMessage.SetList> list = new ArrayList<GetSecRepMessage.SetList>();

		while (rs.next()) {
			GetSecRepMessage.SetList p = new GetSecRepMessage.SetList();
			p.CONFIG_NAME = rs.getString("CONFIG_NAME");
			p.CONFIG_TYPE = rs.getString("CONFIG_TYPE");
			p.CONFIG_VALUE = rs.getString("CONFIG_VALUE");
			list.add(p);
		}
		sec.SET_LIST = list;
		return sec;
	}

	public static void main(String sdfsdfsdf[]) throws Exception {
		GetPrivRepMessage model = new GetPrivRepMessage();

		model.USER_NO = "UYY";
		List list = new ArrayList();
		model.PRIV_LIST = list;
		GetPrivRepMessage.PrivList p = new GetPrivRepMessage.PrivList();

		p.PRIV_NAME = "111";
		p.PRIV_NO = "222";

		list.add(p);
		p = new GetPrivRepMessage.PrivList();
		p.PRIV_NAME = "342";
		p.PRIV_NO = "4242";

		list.add(p);

		System.out.println(model.toJson());
	}

	public GetPrivRepMessage getPriv(String userNo) throws Exception {
		GetPrivRepMessage message = new GetPrivRepMessage();
		message.USER_NO = userNo;
		message.RTN_FLAG = "1";
		message.RTN_MSG = "";
		String sql = "SELECT v.priv_id,\n" + "v.PRIV_NAME\n"
				+ "from  k_priv v ,k_priv_rela re,k_user u\n" + "WHERE\n"
				+ "u.USER_ID =  re.USER_ID AND\n"
				+ "v.PRIV_ID =  re.PRIV_ID and u.user_no=?   \n" + "union\n"
				+ "\n" + "select v.priv_id,v.priv_name from\n"
				+ " k_priv v,p_role_perm m,p_user_rolerela re,k_user u\n"
				+ "\n" + "where v.priv_id=m.auth_id and m.role_id=re.role_id\n"
				+ "and u.user_id=re.user_id  and u.user_no=? ";

		ResultSet rs = DaoUtil.queryData(sql, new Object[] { userNo, userNo });
		List<GetPrivRepMessage.PrivList> list = new ArrayList<GetPrivRepMessage.PrivList>();
		while (rs.next()) {
			GetPrivRepMessage.PrivList p = new GetPrivRepMessage.PrivList();
			p.PRIV_NAME = rs.getString("PRIV_NAME");
			p.PRIV_NO = rs.getString("priv_id");
			list.add(p);
		}
		message.PRIV_LIST = list;
		return message;

	}

	public GetUserRepMessage getUser(String userNo) throws Exception {
		GetUserRepMessage rm = new GetUserRepMessage();
		String sql = "select user_id,user_no,user_name,(select USER_IMG from "
				+ " k_user_ext ext  where ext.USER_NO=kk.user_no limit 1) user_head,(select ROLE_NAME from "
				+ "  P_ROLE aa,P_USER_ROLERELA bb where aa.ROLE_ID=bb.ROLE_ID"
				+ " and bb.user_id=kk.user_id limit 1) USER_ROLE,"
				+ ""
				+ "LOGIN_DATE,LOGIN_IP,PHONE,MAIL,REG_DATE,sex,act_name from k_user kk "
				+ "  where user_no=? ";
		ResultSet rs = DaoUtil.queryData(sql, new Object[] { userNo });
		if (rs.next()) {
			rm.ACT_NAME = rs.getString("ACT_NAME");
			rm.USER_NO = rs.getString("USER_NO");
			rm.USER_NAME = rs.getString("USER_NAME");
			rm.USER_HEAD = rs.getString("user_head");
			rm.LAST_LOGIN_TIME = rs.getString("LOGIN_DATE");
			rm.LAST_LOGIN_IP = rs.getString("LOGIN_IP");
			rm.MAIL = rs.getString("MAIL");
			rm.PHONE = rs.getString("PHONE");
			rm.SEX = rs.getString("sex");
			rm.RTN_FLAG = "1";
			rm.RTN_MSG = "";

		} else {
			rm.RTN_FLAG = "0";
			rm.RTN_MSG = "无用户信息！";
		}
		return rm;

	}

	public int modifyUser(SetUserReqMessage model) throws Exception {
		String sql = "update k_user set  ";
		List list = new ArrayList();
		int index = 0;
		if (!StringUtils.isNullOrBank(model.MESSAGE.PHONE)) {
			if (index == 0) {
				sql += " PHONE=? ";
			} else {
				sql += " ,PHONE=? ";
			}
			index++;
			list.add(model.MESSAGE.PHONE);
		}
		if (!StringUtils.isNullOrBank(model.MESSAGE.MAIL)) {
			if (index == 0) {
				sql += " MAIL=? ";
			} else {
				sql += " ,MAIL=? ";
			}
			index++;

			list.add(model.MESSAGE.MAIL);
		}
		if (!StringUtils.isNullOrBank(model.MESSAGE.SEX)) {
			if (index == 0) {
				sql += " sex=? ";
			} else {
				sql += " ,sex=? ";
			}
			index++;

			list.add(model.MESSAGE.SEX);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.ACT_NAME)) {
			if (index == 0) {
				sql += " act_name=? ";
			} else {
				sql += " ,act_name=? ";
			}
			index++;

			list.add(model.MESSAGE.ACT_NAME);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.USER_NAME)) {
			if (index == 0) {
				sql += " USER_NAME=? ";
			} else {
				sql += " ,USER_NAME=? ";
			}
			index++;

			list.add(model.MESSAGE.USER_NAME);
		}
		if (list.size() == 0) {
			return -100;
		}
		sql += "   where user_no=?";

		list.add(model.MESSAGE.USER_NO);

		return DaoUtil.executeQuery(sql, list.toArray());
	}
}
