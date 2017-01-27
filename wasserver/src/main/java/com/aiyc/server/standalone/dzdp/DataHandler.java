package com.aiyc.server.standalone.dzdp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.dzdp.DealsDetail.Restrictions;
import com.aiyc.server.standalone.util.ReflectHelper;
import com.waspring.wasdbtools.DaoUtil;

public class DataHandler {

	public void clearData(Businesses bs) throws Exception {
		String bsId = bs.business_id;
		if (StringUtils.nullToEmpty(bsId).equals("")) {
			return;
		}
		String sql = "delete from     s_restrictions where "
				+ " deal_id in(select deal_id from  s_deals  "
				+ " where  business_id=?)";
		DaoUtil.executeUpdate(sql, new Object[] { bsId });

		sql = "delete from     s_deals_detail where "
				+ " deal_id in(select deal_id from  s_deals  "
				+ " where  business_id=?)";
		DaoUtil.executeUpdate(sql, new Object[] { bsId });

		sql = "delete from   s_deals  where business_id=?";
		DaoUtil.executeUpdate(sql, new Object[] { bsId });

		sql = "delete from   s_businesses_detail   where  business_id=?";
		DaoUtil.executeUpdate(sql, new Object[] { bsId });

		sql = "delete from   s_coupon_detail  where  coupon_id "
				+ "in(select  coupon_id  from s_coupon     where    business_id=?  )";
		DaoUtil.executeUpdate(sql, new Object[] { bsId });

		sql = "delete from     s_coupon    where   business_id=?  ";
		DaoUtil.executeUpdate(sql, new Object[] { bsId });

		sql = "delete from     s_businesses    where    business_id=?  ";
		DaoUtil.executeUpdate(sql, new Object[] { bsId });
	}

	public void saveBusinesses(Businesses bs) throws Exception {
		String bsId = bs.business_id;
		if (StringUtils.nullToEmpty(bsId).equals("")) {
			return;
		}

		String sql = "insert into s_businesses(  `business_id` ,  `business_name` ,"
				+ " `business_url` ,  `business_h5_url`,area_no,build_no,floor_no,door_no"
				+ ")values(?,?,?,?,?,?,?,?) ";
		DaoUtil.executeUpdate(sql, new Object[] { bs.business_id, bs.name,
				bs.business_url, bs.business_url, bs.area_no, bs.build_no,
				bs.floor_no, bs.door_no });

		if (!StringUtils.nullToEmpty(bs.coupon_id).equals("")
				&& !"0".equals(bs.coupon_id)) {
			sql = "insert into s_coupon(  `coupon_id` ,  `business_id` ,"
					+ " `coupon_description` ,  `coupon_url`)values(?,?,?,?) ";
			DaoUtil.executeUpdate(sql, new Object[] { bs.coupon_id,
					bs.business_id, bs.coupon_description, bs.coupon_url });

		}

		Iterator<Businesses.Deal> it = bs.deals.iterator();

		while (it.hasNext()) {
			Businesses.Deal det = it.next();

			sql = "insert into s_deals(  `deal_id` ,  `business_id` ,"
					+ " `deals_description` ,  `deals_url`)values(?,?,?,?) ";

			DaoUtil.executeUpdate(sql, new Object[] { det.id, bs.business_id,
					det.description, det.url });
		}

	}

	public void saveBusinessesDetail(BusinessesDetail det) throws Exception {
		String bsId = det.business_id;
		if (StringUtils.nullToEmpty(bsId).equals("")) {
			return;
		}

		String tables[] = { "business_id",//
				"name",// //varchar(256) DEFAULT NULL COMMENT '�̻��� ',
				"branch_name",// // varchar(64) DEFAULT NULL COMMENT '�ֵ���',
				"address",// // varchar(256) DEFAULT NULL COMMENT '��ַ',
				"city",// // varchar(64) DEFAULT NULL COMMENT '���ڳ���',
				"telephone",// // varchar(64) DEFAULT NULL COMMENT '����ŵĵ绰 ',
				"latitude",// // decimal(12,4) DEFAULT NULL COMMENT 'γ����� ',
				"longitude",// //decimal(12,4) DEFAULT NULL COMMENT '������� ',
				"avg_rating",// // decimal(12,4) DEFAULT NULL COMMENT
				// '�Ǽ����֣�5.0������ǣ�4.5������ǰ룬��������',
				"rating_img_url",// // varchar(256) DEFAULT NULL COMMENT
				// '�Ǽ�ͼƬ���� ',
				"rating_s_img_url",// // varchar(256) DEFAULT NULL COMMENT
				// 'С�ߴ��Ǽ�ͼƬ���� ',
				"product_grade",// // varchar(8) DEFAULT NULL COMMENT
				// '��Ʒ/ʳƷ��ζ���ۣ�1:һ�㣬2:�пɣ�3:�ã�4:�ܺã�5:�ǳ��� ',
				"decoration_grade",// // varchar(8) DEFAULT NULL COMMENT
				// '�������ۣ�1:һ�㣬2:�пɣ�3:�ã�4:�ܺã�5:�ǳ���',
				"service_grade",// // varchar(8) DEFAULT NULL COMMENT
				// '�������ۣ�1:һ�㣬2:�пɣ�3:�ã�4:�ܺã�5:�ǳ��� ',
				"product_score",// // decimal(12,4) DEFAULT NULL COMMENT
				// '��Ʒ/ʳƷ��ζ���۵���֣���ȷ��С����һλ��ʮ���ƣ� ',
				"decoration_score",// // decimal(12,4) DEFAULT NULL COMMENT
				// '�������۵���֣���ȷ��С����һλ��ʮ���ƣ� ',
				"service_score",// // decimal(12,4) DEFAULT NULL COMMENT
				// '�������۵���֣���ȷ��С����һλ��ʮ���ƣ�',
				"avg_price",// // decimal(5,0) DEFAULT NULL COMMENT
				// '�˾�۸񣬵�λ:Ԫ����û���˾���-1 ',
				"review_count",// // decimal(15,0) DEFAULT NULL COMMENT '��������
				// ',
				"review_list_url",// // varchar(3000) DEFAULT NULL COMMENT
				// '����ҳ��URL���� ',
				"business_url",// // varchar(256) DEFAULT NULL COMMENT '�̻�ҳ������
				// ',
				"photo_url",// // varchar(256) DEFAULT NULL COMMENT
				// '��Ƭ���ӣ���Ƭ���ߴ�700��700 ',
				"s_photo_url",// // varchar(256) DEFAULT NULL COMMENT
				// 'С�ߴ���Ƭ���ӣ���Ƭ���ߴ�278��200 ',
				"photo_count",// // decimal(5,0) DEFAULT NULL COMMENT '��Ƭ���� ',
				"photo_list_url",// // varchar(3000) DEFAULT NULL COMMENT
				// '��Ƭҳ��URL���� ',
				"has_coupon",// // varchar(8) DEFAULT NULL COMMENT
				// '�Ƿ����Ż�ȯ��0:û�У�1:�� ',

				// ',
				"has_deal",// // varchar(8) DEFAULT NULL COMMENT
				// '�Ƿ����Ź���0:û�У�1:�� ',
				"deal_count",// // decimal(5,0) DEFAULT NULL COMMENT
				// '�̻���ǰ�����Ź����� ',
				"has_online_reservation",// //varchar(8) DEFAULT NULL COMMENT
				// '�Ƿ�������Ԥ����0:û�У�1:�� ',
				"online_reservation_url",// //varchar(256) DEFAULT NULL
				// COMMENT '����Ԥ��ҳ�����ӣ�Ŀǰ������HTML5վ������
				// ',
				"regions",// // varchar(3000) DEFAULT NULL COMMENT
				// '����������Ϣ�б?��[�������һ�] ',
				"categories"// // varchar(3000) DEFAULT NULL COMMENT '�Ź��������� ')"
		};

		String sql = " insert into  `s_businesses_detail` ( ";
		for (int i = 0; i < tables.length; i++) {
			if (i == tables.length - 1) {
				sql += tables[i];
			} else
				sql += tables[i] + ",";
		}
		sql += ")  values(" + DaoUtil.makePix(tables.length) + ")";

		List list = new ArrayList();
		for (int i = 0; i < tables.length; i++) {
			if (tables[i].equalsIgnoreCase("regions")
					|| tables[i].equalsIgnoreCase("categories")) {
				List paras = (List) ReflectHelper.getFileValue(det, tables[i]);
				Iterator rt = paras.iterator();
				String line = "";
				while (rt.hasNext()) {
					String value = (String) rt.next();
					line += value + ",";
				}

				list.add(line);
			} else {
				list.add(ReflectHelper.getFileValue(det, tables[i]));
			}
		}

		DaoUtil.executeUpdate(sql, list.toArray());

	}

	public void saveDealsDetail(String bsId, DealsDetail deal) throws Exception {

		if (StringUtils.nullToEmpty(bsId).equals("")) {
			return;
		}

		if (deal.deal_id == null || "".equals(deal.deal_id)
				|| "0".equals(deal.deal_id)) {
			return;
		}

		String tables[] = { "deal_id",// bigint(16) DEFAULT NULL COMMENT
				// '�Ź�ID',
				"title",// varchar(256) DEFAULT NULL COMMENT '�Ź����� ',
				"description",// varchar(3000) DEFAULT NULL COMMENT '�Ź����� ',
				"city",// varchar(256) DEFAULT NULL COMMENT '�������',
				"list_price",// decimal(12,4) DEFAULT NULL COMMENT
				// '�Ź�����Ʒԭ��ֵv',
				"current_price",// decimal(12,4) DEFAULT NULL COMMENT '�Ź��۸� ',
				"purchase_count",// decimal(15,0) DEFAULT NULL COMMENT
				// '�Ź���ǰ�ѹ����� ',
				"publish_date",// varchar(32) DEFAULT NULL COMMENT '�Ź������������� ',
				"details",// varchar(3000) DEFAULT NULL COMMENT '�Ź����� ',
				"purchase_deadline",// varchar(32) DEFAULT NULL COMMENT
				// '�Ź����Ľ�ֹ�������� ',
				"image_url",// varchar(256) DEFAULT NULL COMMENT
				// '�Ź�ͼƬ���ӣ����ͼƬ�ߴ�450��280 ',
				"s_image_url",// varchar(256) DEFAULT NULL COMMENT
				// 'С�ߴ��Ź�ͼƬ���ӣ����ͼƬ�ߴ�160��100 ',
				"more_image_urls",// varchar(3000) DEFAULT NULL COMMENT
				// '����ߴ�ͼƬ ',
				"more_s_image_urls",// varchar(3000) DEFAULT NULL COMMENT
				// '���С�ߴ�ͼƬ ',
				"is_popular",// varchar(8) DEFAULT NULL COMMENT
				// '�Ƿ�Ϊ�����Ź���0�����ǣ�1���� ',
				"notice",// varchar(256) DEFAULT NULL COMMENT
				// '��Ҫ֪ͨ(һ��Ϊ�Ź���Ϣ����ʱ���) ',
				"deal_url",// varchar(256) DEFAULT NULL COMMENT
				// '�Ź�Webҳ�����ӣ���������ҳӦ�� ',
				"deal_h5_url",// varchar(256) DEFAULT NULL COMMENT
				// '�Ź�HTML5ҳ�����ӣ��������ƶ�Ӧ�ú�������Ӧ�� ',
				"commission_ratio",// decimal(12,4) DEFAULT NULL COMMENT
				// '��ǰ�ŵ���Ӷ����� ',
				"regions",// varchar(3000) DEFAULT NULL COMMENT
				// '����������Ϣ�б?��[�������һ�] ',
				"categories" // varchar(3000) DEFAULT NULL COMMENT '�Ź��������� ',

		};

		String sql = " insert into  `s_deals_detail` ( ";
		for (int i = 0; i < tables.length; i++) {
			if (i == tables.length - 1) {
				sql += tables[i];
			} else
				sql += tables[i] + ",";
		}

		sql += ")  values(" + DaoUtil.makePix(tables.length) + ")";

		List list = new ArrayList();
		for (int i = 0; i < tables.length; i++) {
			if (tables[i].equalsIgnoreCase("more_image_urls")
					|| tables[i].equalsIgnoreCase("more_s_image_urls")
					|| tables[i].equalsIgnoreCase("regions")
					|| tables[i].equalsIgnoreCase("categories")) {
				List paras = (List) ReflectHelper.getFileValue(deal, tables[i]);
				Iterator rt = paras.iterator();
				String line = "";
				while (rt.hasNext()) {
					String value = (String) rt.next();
					line += value + ",";
				}

				list.add(line);
			} else {
				list.add(ReflectHelper.getFileValue(deal, tables[i]));
			}
		}

		DaoUtil.executeUpdate(sql, list.toArray());

		Restrictions o = deal.restrictions;

		if (o.special_tips != null && !"".equals(o.special_tips)) {
			sql = "insert into  s_restrictions(deal_id,is_reservation_required,"
					+ "is_refundable,special_tips) values(?,?,?,?)";

			DaoUtil.executeUpdate(sql, new Object[] { deal.deal_id,
					ReflectHelper.getFileValue(o, "is_reservation_required"),
					ReflectHelper.getFileValue(o, "is_refundable"),
					ReflectHelper.getFileValue(o, "special_tips")

			});
		}

	}

	public void saveCouponDetail(String bsId, CouponDetail cou)
			throws Exception {

		if (StringUtils.nullToEmpty(bsId).equals("")) {
			return;
		}

		if (StringUtils.nullToEmpty(cou.coupon_id).equals("")
				|| "0".equals(cou.coupon_id)) {
			return;
		}

		String tables[] = {

		"coupon_id", // bigint(16) comment '�Ż�ȯID ',
				"title", // varchar(64) comment '�Ż�ȯ���� ',
				"description", // varchar(4000) comment '�Ż�ȯ���� ',
				"regions", // varchar(4000) comment '�Ż�ȯ�����̻����������� ',
				"categories", // varchar(4000) comment '�Ż�ȯ�������� ',
				"download_count", // numeric(15,0) comment '�Ż�ȯ��ǰ�������� ',
				"publish_date", // varchar(64) comment '�Ż�ȯ������������ ',
				"expiration_date", // varchar(64) comment '�Ż�ȯ�Ľ�ֹʹ������ ',
				"logo_img_url", // varchar(256) comment '�Ż�ȯ��ͼ�꣬�ߴ�120��90 ',
				"coupon_url", // varchar(256) comment '�Ż�ȯWebҳ�����ӣ���������ҳӦ�� ',
				"coupon_h5_url" // varchar(256) comment
		// '�Ż�ȯHTML5ҳ�����ӣ��������ƶ�Ӧ�ú�������Ӧ�� '

		};

		String sql = " insert into  `s_coupon_detail` ( ";
		for (int i = 0; i < tables.length; i++) {
			if (i == tables.length - 1) {
				sql += tables[i];
			} else
				sql += tables[i] + ",";
		}
		sql += ")  values(" + DaoUtil.makePix(tables.length) + ")";

		List list = new ArrayList();
		for (int i = 0; i < tables.length; i++) {
			if (tables[i].equalsIgnoreCase("regions")
					|| tables[i].equalsIgnoreCase("categories")) {
				List paras = (List) ReflectHelper.getFileValue(cou, tables[i]);
				Iterator rt = paras.iterator();
				String line = "";
				while (rt.hasNext()) {
					String value = (String) rt.next();
					line += value + ",";
				}

				list.add(line);
			} else {
				list.add(ReflectHelper.getFileValue(cou, tables[i]));
			}
		}

		DaoUtil.executeUpdate(sql, list.toArray());

	}

}
