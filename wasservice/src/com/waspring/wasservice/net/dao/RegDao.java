package com.waspring.wasservice.net.dao;

import com.aiyc.framework.utils.StringUtils;
import com.waspring.wasdbtools.DaoUtil;
import com.waspring.wasservice.net.model.RegReqMessage;

public class RegDao {

	public int addUser(RegReqMessage req) throws Exception {
		String sql = "INSERT INTO k_user\n"
				+ "\t\t(user_no, user_name, user_pass, base_org, login_ip, status, is_admin, css,\n"
				+ "\t\t phone, mail,reg_date,sex,act_Name,open_id)\n" + "VALUES\n"
				+ "\t\t(?, ?, ?, ?,  '', '01', '02', 'blue', ?, ?,now(),?,?,?)";
		
		String userName="";
		if(StringUtils.isNullOrBank(req.MESSAGE.USER_NAME)){
			userName=	req.MESSAGE.USER_NO;
		}
		else{
			userName=	req.MESSAGE.USER_NAME;
		}
		return DaoUtil.executeUpdate(sql, new Object[] {

		req.MESSAGE.USER_NO, userName, req.MESSAGE.USER_PWD, "",

		"1".equals(req.MESSAGE.IS_PHONE) ? req.MESSAGE.USER_NO : "",
				"1".equals(req.MESSAGE.IS_MAIL) ? req.MESSAGE.USER_NO : "",
				req.MESSAGE.SEX, req.MESSAGE.ACT_NAME,
				req.MESSAGE.OPEN_ID

		});
	}

}
