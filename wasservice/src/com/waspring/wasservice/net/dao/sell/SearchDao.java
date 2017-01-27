package com.waspring.wasservice.net.dao.sell;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aiyc.framework.utils.StringUtils;
import com.waspring.wasdbtools.DaoUtil;
import com.waspring.wasservice.net.model.seller.SearchCouponReqMessage;
import com.waspring.wasservice.net.model.seller.SearchDealReqMessage;
import com.waspring.wasservice.net.model.seller.SearchSjReqMessage;

public class SearchDao {
	/**
	 * ��ѯ�Ż���Ϣ
	 */

	public ResultSet queryCoupon(SearchCouponReqMessage model) throws Exception {
		String sql = "select c.coupon_id,\n"
				+ "       c.title,\n"
				+ "       c.               description,\n"
				+ "       c.               regions,\n"
				+ "       c.categories,\n"
				+ "       c.download_count,\n"
				+ "       c.publish_date,\n"
				+ "       c.               expiration_date,\n"
				+ "       c.               logo_img_url,\n"
				+ "       c.coupon_url,\n"
				+ "       c.               coupon_h5_url,\n"
				+ "       b.area_no,\n"
				+ "       b.build_no,\n"
				+ "       b.floor_no,\n"
				+ "       b.door_no,\n"
				+ "       b.business_id,\n"
				+ "       sqrt((((? -longitude) * PI() * 12656 *\n"
				+ "            cos(((? +latitude) / 2) * PI() / 180) / 180) *\n"
				+ "            ((? -longitude) * PI() * 12656 *\n"
				+ "            cos(((? +latitude) / 2) * PI() / 180) / 180)) +\n"
				+ "            (((? -latitude) * PI() * 12656 / 180) *\n"
				+ "            ((? -latitude) * PI() * 12656 / 180))) * 1000 distance,\n"
				+ "       b.business_name\n"
				+ "  from s_businesses_detail a, s_businesses b, s_coupon_detail c, s_coupon d\n"
				+ " where a.business_id = b.business_id\n"
				+ "   and a.business_id = d.business_id\n"
				+ "   and c.coupon_id = d.coupon_id";
		List list = new ArrayList();
		list.add(model.MESSAGE.longitude);
		list.add(model.MESSAGE.latitude);

		list.add(model.MESSAGE.longitude);
		list.add(model.MESSAGE.latitude);
		list.add(model.MESSAGE.latitude);
		list.add(model.MESSAGE.latitude);
		if (!StringUtils.isNullOrBank(model.MESSAGE.coupon_id)) {
			sql += " and    c.coupon_id=?  ";
			list.add(model.MESSAGE.coupon_id);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.latitude)) {
			sql += " and  sqrt(\n"
					+ "    (\n"
					+ "     ((?-longitude)*PI()*12656*cos(((?+latitude)/2)*PI()/180)/180)\n"
					+ "     *\n"
					+ "     ((?-longitude)*PI()*12656*cos (((?+latitude)/2)*PI()/180)/180)\n"
					+ "    )\n" + "    +\n" + "    (\n"
					+ "     ((?-latitude)*PI()*12656/180)\n" + "     *\n"
					+ "     ((?-latitude)*PI()*12656/180)\n" + "    )\n"
					+ ")<?/1000";

			list.add(model.MESSAGE.longitude);
			list.add(model.MESSAGE.latitude);

			list.add(model.MESSAGE.longitude);
			list.add(model.MESSAGE.latitude);
			list.add(model.MESSAGE.latitude);
			list.add(model.MESSAGE.latitude);

			list.add(model.MESSAGE.radius);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.area_no)) {
			sql += " and  b.area_no=? ";
			list.add(model.MESSAGE.area_no);
		}
		if (!StringUtils.isNullOrBank(model.MESSAGE.build_no)) {
			sql += " and  b.build_no=? ";
			list.add(model.MESSAGE.build_no);
		}
		if (!StringUtils.isNullOrBank(model.MESSAGE.floor_no)) {
			sql += " and  b.floor_no=? ";
			list.add(model.MESSAGE.floor_no);
		}
		if (!StringUtils.isNullOrBank(model.MESSAGE.door_no)) {
			sql += " and  b.door_no=? ";
			list.add(model.MESSAGE.door_no);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.region)) {
			sql += " and  c.regions like CONCAT('%',?,'%') ";
			list.add(model.MESSAGE.region);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.category)) {
			sql += " and  c.categories like CONCAT('%',?,'%') ";
			list.add(model.MESSAGE.category);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.keyword)) {
			sql += " and  (c.title like CONCAT('%',?,'%') or c.description like CONCAT('%',?,'%')    ) ";
			list.add(model.MESSAGE.keyword);
			list.add(model.MESSAGE.keyword);
		}

		int n1 = (Integer.parseInt(model.MESSAGE.page) - 1)
				* Integer.parseInt(model.MESSAGE.limit);
		int n2 = Integer.parseInt(model.MESSAGE.page)
				* Integer.parseInt(model.MESSAGE.limit);

		String sort = model.MESSAGE.sort;
		// 1:Ĭ�ϣ�
		// 2:�Ǽ������ȣ�
		// 3:��Ʒ���۸����ȣ�
		// 4:�������۸����ȣ�
		// 5:�������۸����ȣ�
		// 6:�������������ȣ�
		// 7:�봫�뾭γ������������ȣ�
		// 8:�˾�۸�����ȣ�
		// 9���˾�۸������

		Map map = new HashMap();
		map.put("2", "avg_rating desc");
		map.put("3", "product_grade desc ");
		map.put("4", "decoration_grade desc");
		map.put("5", "service_grade desc ");
		map.put("6", "product_score desc");
		map.put("7", "distance desc");
		map.put("8", "avg_price asc");
		map.put("9", "avg_price desc ");

		String s = (String) map.get(sort);
		if (s != null && !"".equals(s)) {
			sql += " order by " + s + "   ";
		}
		sql += " limit  ?,? ";

		list.add(n1);
		list.add(n2);

		return DaoUtil.queryData(sql, list.toArray());
	}

	/**
	 * ��ѯ�Ź���Ϣ
	 */
	public ResultSet queryDeals(String bsId) throws Exception {

		String sql = " select   `coupon_id` id ,     `coupon_description` description"
				+ " ,  `coupon_url` url from `s_coupon`"
				+ " where business_id=? ";

		ResultSet rs = DaoUtil.queryData(sql, new Object[] { bsId });
		return rs;

	}

	/**
	 * ��ѯ
	 */
	public ResultSet querySJ(SearchSjReqMessage model) throws Exception {

		String sql =

		"select a.business_id,\n"
				+ "       name,\n"
				+ "       branch_name,\n"
				+ "       address,\n"
				+ "       city,\n"
				+ "       telephone,\n"
				+ "       latitude,\n"
				+ "       longitude,\n"
				+ "       avg_rating,\n"
				+ "       rating_img_url,\n"
				+ "       rating_s_img_url,\n"
				+ "       product_grade,\n"
				+ "       decoration_grade,\n"
				+ "       service_grade,\n"
				+ "       product_score,\n"
				+ "       decoration_score,\n"
				+ "       service_score,\n"
				+ "       avg_price,\n"
				+ "       review_count,\n"
				+ "       review_list_url,\n"
				+ "       a.business_url,\n"
				+ "       photo_url,\n"
				+ "       s_photo_url,\n"
				+ "       photo_count,\n"
				+ "       photo_list_url,\n"
				+ "       has_coupon,\n"
				+ "       coupon_id,\n"
				+ "       coupon_description,\n"
				+ "       coupon_url,\n"
				+ "       has_deal,\n"
				+ "       deal_count,\n"
				+ "       has_online_reservation,\n"
				+ "       online_reservation_url,\n"
				+ "       regions,\n"
				+ "       categories,\n"
				+ "       b.area_no,\n"
				+ "       b.build_no,\n"
				+ "       b.floor_no,\n"
				+ "       b.door_no, sqrt(\n"
				+ "    (\n"
				+ "     ((?-longitude)*PI()*12656*cos(((?+latitude)/2)*PI()/180)/180)\n"
				+ "     *\n"
				+ "     ((?-longitude)*PI()*12656*cos (((?+latitude)/2)*PI()/180)/180)\n"
				+ "    )\n" + "    +\n" + "    (\n"
				+ "     ((?-latitude)*PI()*12656/180)\n" + "     *\n"
				+ "     ((?-latitude)*PI()*12656/180)\n" + "    )\n"
				+ ")*1000 distance  \n"
				+ "  from s_businesses_detail a, s_businesses b\n"
				+ " where a.business_id = b.business_id";

		List list = new ArrayList();
		list.add(model.MESSAGE.longitude);
		list.add(model.MESSAGE.latitude);

		list.add(model.MESSAGE.longitude);
		list.add(model.MESSAGE.latitude);
		list.add(model.MESSAGE.latitude);
		list.add(model.MESSAGE.latitude);

		if (!StringUtils.isNullOrBank(model.MESSAGE.business_id)) {
			sql += " and    b.business_id=?   ";
			list.add(model.MESSAGE.business_id);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.latitude)) {
			sql += " and  sqrt(\n"
					+ "    (\n"
					+ "     ((?-longitude)*PI()*12656*cos(((?+latitude)/2)*PI()/180)/180)\n"
					+ "     *\n"
					+ "     ((?-longitude)*PI()*12656*cos (((?+latitude)/2)*PI()/180)/180)\n"
					+ "    )\n" + "    +\n" + "    (\n"
					+ "     ((?-latitude)*PI()*12656/180)\n" + "     *\n"
					+ "     ((?-latitude)*PI()*12656/180)\n" + "    )\n"
					+ ")<?/1000";

			list.add(model.MESSAGE.longitude);
			list.add(model.MESSAGE.latitude);

			list.add(model.MESSAGE.longitude);
			list.add(model.MESSAGE.latitude);
			list.add(model.MESSAGE.latitude);
			list.add(model.MESSAGE.latitude);

			list.add(model.MESSAGE.radius);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.area_no)) {
			sql += " and  b.area_no=? ";
			list.add(model.MESSAGE.area_no);
		}
		if (!StringUtils.isNullOrBank(model.MESSAGE.build_no)) {
			sql += " and  b.build_no=? ";
			list.add(model.MESSAGE.build_no);
		}
		if (!StringUtils.isNullOrBank(model.MESSAGE.floor_no)) {
			sql += " and  b.floor_no=? ";
			list.add(model.MESSAGE.floor_no);
		}
		if (!StringUtils.isNullOrBank(model.MESSAGE.door_no)) {
			sql += " and  b.door_no=? ";
			list.add(model.MESSAGE.door_no);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.region)) {
			sql += " and  a.regions like CONCAT('%',?,'%') ";
			list.add(model.MESSAGE.region);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.category)) {
			sql += " and  a.categories like CONCAT('%',?,'%') ";
			list.add(model.MESSAGE.category);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.keyword)) {
			sql += " and  (a.name like CONCAT('%',?,'%') or a.address like CONCAT('%',?,'%')    ) ";
			list.add(model.MESSAGE.keyword);
			list.add(model.MESSAGE.keyword);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.has_coupon)) {
			sql += " and  a.has_coupon =? ";
			list.add(model.MESSAGE.has_coupon);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.has_deal)) {
			sql += " and  a.has_deal =? ";
			list.add(model.MESSAGE.has_deal);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.has_online_reservation)) {
			sql += " and  a.has_online_reservation =? ";
			list.add(model.MESSAGE.has_online_reservation);
		}

		int n1 = (Integer.parseInt(model.MESSAGE.page) - 1)
				* Integer.parseInt(model.MESSAGE.limit);
		int n2 = Integer.parseInt(model.MESSAGE.page)
				* Integer.parseInt(model.MESSAGE.limit);

		String sort = model.MESSAGE.sort;
		// 1:Ĭ�ϣ�
		// 2:�Ǽ������ȣ�
		// 3:��Ʒ���۸����ȣ�
		// 4:�������۸����ȣ�
		// 5:�������۸����ȣ�
		// 6:�������������ȣ�
		// 7:�봫�뾭γ������������ȣ�
		// 8:�˾�۸�����ȣ�
		// 9���˾�۸������

		Map map = new HashMap();
		map.put("2", "avg_rating desc");
		map.put("3", "product_grade desc ");
		map.put("4", "decoration_grade desc");
		map.put("5", "service_grade desc ");
		map.put("6", "product_score desc");
		map.put("7", "distance desc");
		map.put("8", "avg_price asc");
		map.put("9", "avg_price desc ");

		String s = (String) map.get(sort);
		if (s != null && !"".equals(s)) {
			sql += " order by " + s + "   ";
		}
		sql += " limit  ?,? ";

		list.add(n1);
		list.add(n2);

		return DaoUtil.queryData(sql, list.toArray());
	}

	/**
	 * ��ѯ�Ź���Ϣ
	 */

	public ResultSet queryDeal(SearchDealReqMessage model) throws Exception {
		String sql = "select c.deal_id,\n"
				+ "       title,\n"
				+ "       description,\n"
				+ "       list_price,\n"
				+ "       current_price,\n"
				+ "       c.regions ,\n"
				+ "       c.categories,\n"
				+ "       purchase_count,\n"
				+ "       publish_date,\n"
				+ "       purchase_deadline,\n"
				+ "       image_url,\n"
				+ "       s_image_url,\n"
				+ "       deal_url,\n"
				+ "       deal_h5_url,\n"
				+ "       commission_ratio,\n"
				+ "       sqrt((((? -longitude) * PI() * 12656 *\n"
				+ "            cos(((? +latitude) / 2) * PI() / 180) / 180) *\n"
				+ "            ((? -longitude) * PI() * 12656 *\n"
				+ "            cos(((? +latitude) / 2) * PI() / 180) / 180)) +\n"
				+ "            (((? -latitude) * PI() * 12656 / 180) *\n"
				+ "            ((? -latitude) * PI() * 12656 / 180))) * 1000 distance,\n"
				+ "       b.area_no,\n"
				+ "       b.build_no,\n"
				+ "       b.floor_no,\n"
				+ "       b.door_no,\n"
				+ "       b.business_name,\n"
				+ "       b.business_id,longitude,latitude\n"
				+ "\n"
				+ "  from s_businesses_detail a, s_businesses b, s_deals_detail c, s_deals d\n"
				+ " where a.business_id = b.business_id\n"
				+ "   and a.business_id = d.business_id\n"
				+ "   and c.deal_id = d.deal_id";

		List list = new ArrayList();
		list.add(model.MESSAGE.longitude);
		list.add(model.MESSAGE.latitude);

		list.add(model.MESSAGE.longitude);
		list.add(model.MESSAGE.latitude);
		list.add(model.MESSAGE.latitude);
		list.add(model.MESSAGE.latitude);
		if (!StringUtils.isNullOrBank(model.MESSAGE.deal_id)) {
			sql += " and    c.deal_id=?  ";
			list.add(model.MESSAGE.deal_id);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.latitude)) {
			sql += " and  sqrt(\n"
					+ "    (\n"
					+ "     ((?-longitude)*PI()*12656*cos(((?+latitude)/2)*PI()/180)/180)\n"
					+ "     *\n"
					+ "     ((?-longitude)*PI()*12656*cos (((?+latitude)/2)*PI()/180)/180)\n"
					+ "    )\n" + "    +\n" + "    (\n"
					+ "     ((?-latitude)*PI()*12656/180)\n" + "     *\n"
					+ "     ((?-latitude)*PI()*12656/180)\n" + "    )\n"
					+ ")<?/1000";

			list.add(model.MESSAGE.longitude);
			list.add(model.MESSAGE.latitude);

			list.add(model.MESSAGE.longitude);
			list.add(model.MESSAGE.latitude);
			list.add(model.MESSAGE.latitude);
			list.add(model.MESSAGE.latitude);

			list.add(model.MESSAGE.radius);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.area_no)) {
			sql += " and  b.area_no=? ";
			list.add(model.MESSAGE.area_no);
		}
		if (!StringUtils.isNullOrBank(model.MESSAGE.build_no)) {
			sql += " and  b.build_no=? ";
			list.add(model.MESSAGE.build_no);
		}
		if (!StringUtils.isNullOrBank(model.MESSAGE.floor_no)) {
			sql += " and  b.floor_no=? ";
			list.add(model.MESSAGE.floor_no);
		}
		if (!StringUtils.isNullOrBank(model.MESSAGE.door_no)) {
			sql += " and  b.door_no=? ";
			list.add(model.MESSAGE.door_no);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.region)) {
			sql += " and  c.regions like CONCAT('%',?,'%') ";
			list.add(model.MESSAGE.region);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.category)) {
			sql += " and  c.categories like CONCAT('%',?,'%') ";
			list.add(model.MESSAGE.category);
		}

		if (!StringUtils.isNullOrBank(model.MESSAGE.keyword)) {
			sql += " and  (c.title like CONCAT('%',?,'%') or c.description like CONCAT('%',?,'%')    ) ";
			list.add(model.MESSAGE.keyword);
			list.add(model.MESSAGE.keyword);
		}

		int n1 = (Integer.parseInt(model.MESSAGE.page) - 1)
				* Integer.parseInt(model.MESSAGE.limit);
		int n2 = Integer.parseInt(model.MESSAGE.page)
				* Integer.parseInt(model.MESSAGE.limit);

		String sort = model.MESSAGE.sort;
		// 1:Ĭ�ϣ�
		// 2:�Ǽ������ȣ�
		// 3:��Ʒ���۸����ȣ�
		// 4:�������۸����ȣ�
		// 5:�������۸����ȣ�
		// 6:�������������ȣ�
		// 7:�봫�뾭γ������������ȣ�
		// 8:�˾�۸�����ȣ�
		// 9���˾�۸������

		Map map = new HashMap();
		map.put("2", "avg_rating desc");
		map.put("3", "product_grade desc ");
		map.put("4", "decoration_grade desc");
		map.put("5", "service_grade desc ");
		map.put("6", "product_score desc");
		map.put("7", "distance desc");
		map.put("8", "avg_price asc");
		map.put("9", "avg_price desc ");

		String s = (String) map.get(sort);
		if (s != null && !"".equals(s)) {
			sql += " order by " + s + "   ";
		}
		sql += " limit  ?,? ";

		list.add(n1);
		list.add(n2);
		return DaoUtil.queryData(sql, list.toArray());
	}
}
