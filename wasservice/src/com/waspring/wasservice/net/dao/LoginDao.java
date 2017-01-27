package com.waspring.wasservice.net.dao;

import java.sql.ResultSet;

import com.waspring.wasdbtools.DaoUtil;

public class LoginDao {

	public ResultSet queryUser(String userName, String userPass)
			throws Exception {
		String sql = "select user_id from k_user where user_no=? and user_pass=? ";

		return DaoUtil.queryData(sql, new Object[] { userName, userPass });
	}

	public ResultSet queryUserByName(String userName) throws Exception {
		String sql = "select user_id from k_user where user_no=? ";

		return DaoUtil.queryData(sql, new Object[] { userName });
	}

	public ResultSet queryUserByOpenID(String openId) throws Exception {
		String sql = "select user_id from k_user k where exists (select 1 from "
				+ "   k_user_rela r where r.user_id=k.user_id and r.open_id=? ) ";

		return DaoUtil.queryData(sql, new Object[] { openId });
	}

}
