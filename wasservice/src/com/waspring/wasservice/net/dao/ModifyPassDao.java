package com.waspring.wasservice.net.dao;

import java.sql.SQLException;

import com.waspring.wasdbtools.DaoUtil;

public class ModifyPassDao {

	public boolean oldPassOK(String userNo, String pass) throws SQLException {
		String sql = "select  1 from k_user where  user_no=?"
				+ " and   user_pass=? ";

		return DaoUtil.queryData(sql, new Object[] { userNo, pass }).next();
	}

	public int modifyPass(String userNo, String pass) throws SQLException {

		String sql = "update k_user   set user_pass=?  where user_no=?   ";

		return DaoUtil.executeUpdate(sql, new Object[] { pass, userNo });
	}

}
