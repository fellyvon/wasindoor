package com.waspring.wasservice.net.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.aiyc.server.standalone.db.DaoUtil;
import com.waspring.wasservice.net.model.GetCodeRepMessage;
import com.waspring.wasservice.net.model.GetCodeReqMessage;

public class CodeDao {

	public GetCodeRepMessage getCode(GetCodeReqMessage model) throws Exception {
		GetCodeRepMessage res = new GetCodeRepMessage();
		res.RTN_FLAG = "1";
		res.RTN_MSG = "";
		List<GetCodeRepMessage.CodeList> list = new ArrayList<GetCodeRepMessage.CodeList>();

		res.CODE_LIST = list;
		String sql = "SELECT\n" + "\n" + "k_code.CODE_TYPE,\n"
				+ "k_code.CODE_NAME,\n" + "k_code.VALUE AS CODE_VALUE,\n"
				+ "'' AS CONTENT1,\n" + "'' AS CONTENT2,\n"
				+ "'' AS CONTENT3,\n" + "'' AS CONTENT4,\n"
				+ "'' AS CONTENT5,\n" + "k_code.PARENT_CODE_ID P_CODE_VALUE\n"
				+ "from k_code where code_type=? ";
		List para = new ArrayList();
		para.add(model.MESSAGE.CODE_TYPE);
		if (model.MESSAGE.CODE_NAME != null
				&& !"".equals(model.MESSAGE.CODE_NAME)) {
			sql += " and   code_name =?  ";
			para.add(model.MESSAGE.CODE_NAME);
		}

		if (model.MESSAGE.CODE_VALUE != null
				&& !"".equals(model.MESSAGE.CODE_VALUE)) {
			sql += " and   value =?  ";
			para.add(model.MESSAGE.CODE_VALUE);
		}

		ResultSet rs = DaoUtil.queryData(sql, para.toArray());

		while (rs.next()) {
			GetCodeRepMessage.CodeList tmp = new GetCodeRepMessage.CodeList();
			tmp.CODE_NAME = rs.getString("CODE_NAME");
			tmp.CODE_TYPE = rs.getString("CODE_TYPE");
			tmp.CODE_VALUE = rs.getString("CODE_VALUE");

			tmp.CONTENT1 = rs.getString("CONTENT1");
			tmp.CONTENT2 = rs.getString("CONTENT2");
			tmp.CONTENT3 = rs.getString("CONTENT3");
			tmp.CONTENT4 = rs.getString("CONTENT4");
			tmp.CONTENT5 = rs.getString("CONTENT5");
			tmp.P_CODE_VALUE = rs.getString("P_CODE_VALUE");

			list.add(tmp);
		}

		return res;
	}
}
