package com.waspring.wasservice.net.model.seller;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.CommonRepMessage;

public class SearchCouponRepMessage extends CommonRepMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<DataList> DATA_LIST = new ArrayList<DataList>();

	public static class DataList {
		public String coupon_id;// 优惠券ID int
		public String title;// 优惠券标题 string
		public String description;// 优惠券描述 string
		public String regions;// 优惠券适用商户所在行政区 list
		public String categories;// 优惠券所属分类 list
		public String download_count;// 优惠券当前已下载量 int
		public String publish_date;// 优惠券发布上线日期 string
		public String expiration_date;// 优惠券的截止使用日期 string
		public String distance;// 优惠券所适用商户中距离参数坐标点最近的一家与坐标点的距离 int
		public String logo_img_url;// 优惠券的图标 string
		public String coupon_url;// 优惠券Web页面链接 string
		public String coupon_h5_url;// 优惠券HTML5页面链接 string
		public String area_no;// 区域编号 string 区域编号
		public String build_no;// 建筑编号 string 建筑编号
		public String floor_no;// 楼层编号 string 楼层编号
		public String door_no;// 房间编号 string 房间编号

		public String business_id;// 商户ID int 商户ID
		public String business_name;// 商户名 string 商户名

	}
}
