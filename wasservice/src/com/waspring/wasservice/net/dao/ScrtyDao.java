package com.waspring.wasservice.net.dao;

import java.util.Iterator;
import java.util.List;

import com.aiyc.server.standalone.db.DaoUtil;
import com.waspring.wasservice.net.model.ScrtyReqMessage;

public class ScrtyDao {

	public int setScrty(ScrtyReqMessage req) throws Exception {
		String userNo = req.MESSAGE.USER_NO;
		List<ScrtyReqMessage.scrList> list = req.MESSAGE.SERCTY_LIST;

		Iterator<ScrtyReqMessage.scrList> it = list.iterator();

		while (it.hasNext()) {

			ScrtyReqMessage.scrList rt = it.next();
			this.deleteScrty(userNo, rt.SERCTY_TYPE);
			this.addScrty(userNo, rt.SERCTY_TYPE, rt.SERCTY_VALUE);
		}

		return 0;
	}

	public int deleteScrty(String userNo, String scrtyType) throws Exception {
		String sql = "delete from   k_user_scry sc where  SCRTY_QUS=? and user_no=?  ";
		return DaoUtil.executeQuery(sql, new Object[] { scrtyType, userNo });

	}

	public int addScrty(String userNo, String scrtyType, String value)
			throws Exception {
		String sql = "insert into     k_user_scry sc ( SCRTY_QUS,user_no,SCRTY_ANS)"
				+ " values(?,?,?)  ";
		return DaoUtil.executeQuery(sql, new Object[] { scrtyType, userNo,
				value });

	}
}
