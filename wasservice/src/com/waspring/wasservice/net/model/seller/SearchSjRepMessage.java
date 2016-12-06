package com.waspring.wasservice.net.model.seller;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.CommonRepMessage;

public class SearchSjRepMessage extends CommonRepMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<DataList> DATA_LIST = new ArrayList<DataList>();

	public static class DataList {
		public String avg_price;// 人均价格 int 人均价格，单位:元，若没有人均，返回-1
		public String review_count;// 点评数量 int 点评数量
		public String review_list_url;// 点评页面URL链接 list 点评页面URL链接
		public String distance;// 商户与参数坐标的距离 int
		// 商户与参数坐标的距离，单位为米，如不传入经纬度坐标，结果为-1
		public String business_url;// 商户页面链接 string 商户页面链接
		public String photo_url;// 照片链接 string 照片链接，照片最大尺寸700×700
		public String s_photo_url;// 小尺寸照片链接 string 小尺寸照片链接，照片最大尺寸278×200
		public String photo_count;// 照片数量 int 照片数量
		public String photo_list_url;// 照片页面URL链接 list 照片页面URL链接
		public String has_coupon;// 是否有优惠券 int 是否有优惠券，0:没有，1:有
		public String coupon_id;// 优惠券ID int 优惠券ID
		public String coupon_description;// 优惠券描述 string 优惠券描述
		public String coupon_url;// 优惠券页面链接 string 优惠券页面链接
		public String has_deal;// 是否有团购 int 是否有团购，0:没有，1:有
		public String deal_count;// 商户当前在线团购数量 int 商户当前在线团购数量
		public List<Deal> deals = new ArrayList<Deal>(); // 团购列表 list 团购列表

		public String has_online_reservation;// 是否有在线预订 int 是否有在线预订，0:没有，1:有
		public String online_reservation_url;// 在线预订页面链接 string
		// 在线预订页面链接，目前仅返回HTML5站点链接
		public String business_id;// 商户ID int 商户ID
		public String name;// 商户名 string 商户名
		public String branch_name;// 分店名 string 分店名
		public String address;// 地址 string 地址
		public String telephone;// 带区号的电话 string 带区号的电话
		public String city;// 所在城市 string 所在城市
		public String regions;// 所在区域信息列表 list 所在区域信息列表，如[徐汇区，徐家汇]
		public String categories;// 所属分类信息列表 list 所属分类信息列表，如[宁波菜，婚宴酒店]
		public String latitude;// 纬度坐标 float 纬度坐标
		public String longitude;// 经度坐标 float 经度坐标
		public String avg_rating;// 星级评分 float 星级评分，5.0代表五星，4.5代表四星半，依此类推
		public String rating_img_url;// 星级图片链接 string 星级图片链接
		public String rating_s_img_url;// 小尺寸星级图片链接 string 小尺寸星级图片链接
		public String product_grade;// 产品/食品口味评价 int
		// 产品/食品口味评价，1:一般，2:尚可，3:好，4:很好，5:非常好
		public String decoration_grade;// 环境评价 int
		// 环境评价，1:一般，2:尚可，3:好，4:很好，5:非常好
		public String service_grade;// 服务评价 int 服务评价，1:一般，2:尚可，3:好，4:很好，5:非常好
		public String product_score;// 产品/食品口味评价单项分 float
		// 产品/食品口味评价单项分，精确到小数点后一位（十分制）
		public String decoration_score;// 环境评价单项分 float 环境评价单项分，精确到小数点后一位（十分制）
		public String service_score;// 服务评价单项分 float 服务评价单项分，精确到小数点后一位（十分制）
		public String area_no;// 区域编号 string 区域编号
		public String build_no;// 建筑编号 string 建筑编号
		public String floor_no;// 楼层编号 string 楼层编号
		public String door_no;// 房间编号 string 房间编号

		public static class Deal {
			public String id;// /团购ID string 团购ID
			public String description;// 团购描述 string 团购描述
			public String url;// 团购页面链接 string 团购页面链接
		}

	}
}
