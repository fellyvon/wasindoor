package com.waspring.wasservice.net.dao.sell;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.aiyc.framework.utils.DateUtils;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.framework.utils.UID;
import com.aiyc.server.standalone.db.DaoUtil;
import com.waspring.wasservice.net.dao.area.GraphDao;
import com.waspring.wasservice.net.dao.comm.CommDao;
import com.waspring.wasservice.net.model.seller.AddFreeReqMessage;
import com.waspring.wasservice.net.model.seller.GetProductRepMessage;
import com.waspring.wasservice.net.model.seller.GetSellerFreeRepMessage;
import com.waspring.wasservice.net.model.seller.PublishProductReqMessage;
import com.waspring.wasservice.net.model.seller.SellerJoinReqMessage;

public class SellerDao {
	private GraphDao dao = new GraphDao();
	private CommDao cdao = new CommDao();

	public void saveFree(AddFreeReqMessage model) throws Exception {
		String sjNo = model.MESSAGE.SJ_NO;
		String sql = "insert into s_hd\n" + "(  `SJ_ID` ,\n"
				+ "  `HD_NAME` ,\n" + "  `DETAIL`,\n" + "  `SDATE` ,\n"
				+ "  `EDATE` ,\n" + "  `HB`  )\n" + "\n" + "  select  id , "
				+ DaoUtil.makePix(5) + " from s_seller s where s.sj_no=?";

		Iterator<GetSellerFreeRepMessage> it = model.MESSAGE.FREE_LIST
				.iterator();
		int index = 1;
		while (it.hasNext()) {
			GetSellerFreeRepMessage next = it.next();

			List list = new ArrayList();
			list.add(next.HD_NAME);
			list.add(next.DETAIL);
			list.add(next.SDATE);
			list.add(next.EDATE);
			list.add(next.HB);
			DaoUtil.executeQuery(sql, list.toArray());
		}
	}

	/**
	 * 获取优惠
	 */

	public ResultSet getSellerFree(String sjNo, String key) throws Exception {
		String sql = "select\n" + "  s_seller.SJ_NO ,\n" + " `HD_NAME`,\n"
				+ " `DETAIL`,\n" + " `SDATE`,\n" + " `EDATE` ,\n"
				+ " `HB`,'' RTN_FLAG,'' RTN_MSG  from s_hd ,s_seller   where\n"
				+ "\n" + "    s_hd.sj_id=s_seller.id\n" + "";
		List list = new ArrayList();
		if (!StringUtils.isNullOrBank(sjNo)) {
			sql += " and s_seller.SJ_NO=? ";
			list.add(sjNo);
		}

		if (!StringUtils.isNullOrBank(key)) {
			sql += " and DETAIL like CONCAT('%',?,'%') ";
			list.add(key);
		}

		return DaoUtil.queryData(sql, list.toArray());
	}

	/**
	 * 添加商品
	 * 
	 * @param model
	 * @throws Exception
	 */
	public void saveProduct(PublishProductReqMessage model) throws Exception {
		String sjNo = model.MESSAGE.SJ_NO;
		Iterator<GetProductRepMessage> it = model.MESSAGE.CP_LIST.iterator();
		String sql = "insert into   `s_product` (\n" + "`SJ_ID` ,\n"
				+ "`CP_NO`  ,\n" + "`CP_NAME` ,\n" + "`CP_MODEL` ,\n"
				+ "`CP_DES` ,\n" + "`CP_SORT` ,\n" + "`UP_DATE` ,\n"
				+ "`DOWN_DATE` ,\n" + "`PRICE` ,\n" + "`RATE` ,\n"
				+ "`TOTAL` ,\n" + "`PRE_NUM` ,\n" + "`STATUS`,\n"
				+ "`CP_KEY`)\n" + "select   sss.id,  " + DaoUtil.makePix(13)
				+ "   from   s_seller sss where sj_no=?";

		while (it.hasNext()) {
			GetProductRepMessage next = it.next();
			DaoUtil.executeQuery("delete from s_product where cp_no=?     ",
					new Object[] { next.CP_NO });

			List list = new ArrayList();
			String cpNo = next.CP_NO==null||"".equals(next.CP_NO)?UID.next():next.CP_NO 
					 ;
			list.add(cpNo);
			list.add(next.CP_NAME);
			list.add(next.CP_MODEL);
			list.add(next.CP_DES);
			list.add(next.CP_SORT);

			if (next.UP_DATE == null || "".equals(next.UP_DATE)) {
				list.add(DateUtils.getCurrentDateStr());
			} else {
				list.add(next.UP_DATE);
			}
			if (next.DOWN_DATE == null || "".equals(next.DOWN_DATE)) {
				list.add(DateUtils.getCurrentDateStr());
			} else {
				list.add(next.DOWN_DATE);
			}

			list.add(next.PRICE);
			list.add(next.RATE);
			list.add(next.TOTAL);
			list.add(next.PRE_NUM);
			list.add("01");
			list.add(next.CP_KEY);
			list.add(sjNo);
			DaoUtil.executeQuery(sql, list.toArray());
		}

	}

	/**
	 * 商家入驻
	 */
	public String saveSeller(SellerJoinReqMessage model) throws Exception {
		String sql = "INSERT INTO s_seller\n"
				+ "\t\t(  sj_no, sj_name, sj_key, jy_sort, yy_sdate, yy_edate, description, area_no,\n"
				+ "\t\t build_no, floor_no, door_no, status, zz_no)\n"
				+ "     values(" + DaoUtil.makePix(13) + ")";
		List list = new ArrayList();
		String sjNo = UID.next();
		list.add(sjNo);
		list.add(model.MESSAGE.SJ_NAME);
		list.add(model.MESSAGE.KEY);
		list.add(model.MESSAGE.JY_SORT);
		if (model.MESSAGE.YY_SDATE == null || "".equals(model.MESSAGE.YY_SDATE)) {
			list.add(DateUtils.getCurrentDateStr());
		} else {
			list.add(model.MESSAGE.YY_SDATE);
		}
		if (model.MESSAGE.YY_EDATE == null || "".equals(model.MESSAGE.YY_EDATE)) {
			list.add(DateUtils.getCurrentDateStr());
		} else {
			list.add(model.MESSAGE.YY_EDATE);
		}
		list.add(model.MESSAGE.DESCRIPTION);
		list.add(model.MESSAGE.AREA_NO);
		list.add(model.MESSAGE.BUILD_NO);
		list.add(model.MESSAGE.FLOOR_NO);
		list.add(model.MESSAGE.DOOR_NO);
		list.add("01");
		list.add(model.MESSAGE.USER_NO);
		DaoUtil.executeQuery(sql, list.toArray());
		return sjNo;
	}

	/**
	 * 商家信息修改
	 */
	public String editSeller(SellerJoinReqMessage model) throws Exception {

		String sql = "update s_seller set sj_name=?, sj_key=?, jy_sort=?, yy_sdate=?"
				+ ", yy_edate=?, description=?  ";
		List list = new ArrayList();

		list.add(model.MESSAGE.SJ_NAME);
		list.add(model.MESSAGE.KEY);

		if (model.MESSAGE.YY_SDATE == null || "".equals(model.MESSAGE.YY_SDATE)) {
			list.add(DateUtils.getCurrentDateStr());
		} else {
			list.add(model.MESSAGE.YY_SDATE);
		}
		if (model.MESSAGE.YY_EDATE == null || "".equals(model.MESSAGE.YY_EDATE)) {
			list.add(DateUtils.getCurrentDateStr());
		} else {
			list.add(model.MESSAGE.YY_EDATE);
		}
		list.add(model.MESSAGE.DESCRIPTION);
		if (!StringUtils.isNullOrBank(model.MESSAGE.AREA_NO)) {
			sql += " ,area_no=?   ";
			list.add(model.MESSAGE.AREA_NO);
		}
		if (!StringUtils.isNullOrBank(model.MESSAGE.BUILD_NO)) {
			sql += " ,build_no=?   ";
			list.add(model.MESSAGE.BUILD_NO);
		}
		if (!StringUtils.isNullOrBank(model.MESSAGE.FLOOR_NO)) {
			sql += " ,floor_no=?   ";
			list.add(model.MESSAGE.FLOOR_NO);
		}
		if (!StringUtils.isNullOrBank(model.MESSAGE.DOOR_NO)) {
			sql += " ,door_no=?   ";
			list.add(model.MESSAGE.DOOR_NO);
		}
		if (!StringUtils.isNullOrBank(model.MESSAGE.JY_SORT)) {
			sql += " ,jy_sort=?   ";
			list.add(model.MESSAGE.JY_SORT);
		}
		sql += " where sj_no=? ";
		list.add(model.MESSAGE.USER_NO);

		DaoUtil.executeQuery(sql, list.toArray());
		return model.MESSAGE.SJ_NO;
	}

	/**
	 * 查询产品
	 */

	public ResultSet getProduct(String sjNo, String key, String cpNo)
			throws Exception {
		String sql = "SELECT\n" + "s_seller.SJ_NO,\n" + "s_product.CP_NO,\n"
				+ "s_product.CP_NAME,\n" + "s_product.CP_MODEL,\n"
				+ "s_product.CP_DES,\n" + "s_product.CP_SORT,\n"
				+ "s_product.UP_DATE,\n" + "s_product.DOWN_DATE,\n"
				+ "s_product.PRICE,\n" + "s_product.RATE,\n"
				+ "s_product.TOTAL,\n" + "s_product.PRE_NUM,\n"
				+ "s_product.STATUS,\n"
				+ "s_product.CP_KEY ,'' RTN_FLAG,'' RTN_MSG\n" + "FROM\n"
				+ "s_product,s_seller\n"
				+ " where  s_product.SJ_ID=s_seller.id";

		List list = new ArrayList();
		if (!StringUtils.isNullOrBank(sjNo)) {
			sql += " and s_seller.SJ_NO=? ";
			list.add(sjNo);
		}

		if (!StringUtils.isNullOrBank(key)) {
			sql += " and s_product.CP_KEY like CONCAT('%',?,'%') ";
			list.add(key);
		}

		if (!StringUtils.isNullOrBank(cpNo)) {
			sql += " and s_product.CP_NO =?";
			list.add(cpNo);
		}

		return DaoUtil.queryData(sql, list.toArray());
	}

	/**
	 * 通过关键字和商家编号查询商家
	 */
	public ResultSet getSeller(String sjNo, String key) throws Exception {
		String sql = "SELECT\n" + "s_seller.SJ_NO,\n" + "s_seller.SJ_NAME,\n"
				+ "s_seller.SJ_KEY,\n" + "s_seller.JY_SORT,\n"
				+ "s_seller.YY_SDATE,\n" + "s_seller.YY_EDATE,\n"
				+ "s_seller.DESCRIPTION,\n" + "s_seller.AREA_NO,\n"
				+ "s_seller.BUILD_NO,\n" + "s_seller.FLOOR_NO,\n"
				+ "s_seller.DOOR_NO,\n"
				+ "s_seller.ZZ_NO,STATUS,'' RTN_FLAG,'' RTN_MSG\n" + "FROM\n"
				+ "s_seller  where   STATUS='01'   ";

		List list = new ArrayList();
		if (!StringUtils.isNullOrBank(sjNo)) {
			sql += " and SJ_NO=? ";
			list.add(sjNo);
		}

		if (!StringUtils.isNullOrBank(key)) {
			sql += " and SJ_KEY like CONCAT('%',?,'%') ";
			list.add(key);
		}

		if (list.size() == 0) {
			return null;
		}

		return DaoUtil.queryData(sql, list.toArray());
	}

	/**
	 * 
	 */

	public ResultSet getSeller(String areaNo, String buildNo, String floorNo,
			String doorNo) throws Exception {

		String sql = "select  " + "`ID`,  " + "`SJ_NO` ,  " + " `SJ_NAME` ,  "
				+ " `SJ_KEY`,  " + " `JY_SORT` ,  " + " `YY_SDATE` ,  "
				+ " `YY_EDATE` ,  " + " `DESCRIPTION` ,  " + "  `AREA_NO` ,  "
				+ " `BUILD_NO` ,  " + " `FLOOR_NO`,  " + " `DOOR_NO`    "
				+ "from   s_seller where    s_seller.AREA_NO=?  "
				+ "and s_seller.BUILD_NO=? and s_seller.FLOOR_NO=?  "
				+ "and s_seller.DOOR_NO=? ";

		return DaoUtil.queryData(sql, new Object[] { areaNo, buildNo, floorNo,
				doorNo });

	}
}
