package com.waspring.wasservice.net.model.seller;

import java.util.ArrayList;
import java.util.List;

import com.waspring.wasservice.net.model.CommonRepMessage;

public class SearchDealRepMessage extends CommonRepMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<DataList> DATA_LIST = new ArrayList<DataList>();

	public static class DataList {
		public String deal_id;// 团购单ID string 团购单ID
		public String title;// 团购标题 string 团购标题
		public String description;// 团购描述 string 团购描述
		public String list_price;// 团购包含商品原价值 float 团购包含商品原价值
		public String current_price;// 团购价格 float 团购价格
		public String regions;// 团购适用商户所在行政区 list 团购适用商户所在行政区
		public String categories;// 团购所属分类 list 团购所属分类
		public String purchase_count;// 团购当前已购买数 int 团购当前已购买数
		public String publish_date;// 团购发布上线日期 string 团购发布上线日期
		public String purchase_deadline;// 团购单的截止购买日期 string 团购单的截止购买日期
		public String distance;// 团购单所适用商户中距离参数坐标点最近的一家与坐标点的距离 int
								// 团购单所适用商户中距离参数坐标点最近的一家与坐标点的距离，单位为米；如不传入经纬度坐标，结果为-1；如团购单无关联商户，结果为MAXINT
		public String image_url;// 团购图片链接 string 团购图片链接，最大图片尺寸450×280
		public String s_image_url;// 小尺寸团购图片链接 string 小尺寸团购图片链接，最大图片尺寸160×100
		public String deal_url;// 团购Web页面链接 string 团购Web页面链接，适用于网页应用
		public String deal_h5_url;// 团购HTML5页面链接 string
									// 团购HTML5页面链接，适用于移动应用和联网车载应用
		public String commission_ratio;// 当前团单的佣金比例 float 当前团单的佣金比例
		public String area_no;// 区域编号 string 区域编号
		public String build_no;// 建筑编号 string 建筑编号
		public String floor_no;// 楼层编号 string 楼层编号
		public String door_no;// 房间编号 string 房间编号
		
		public String business_id;// 商户ID int 商户ID
		public String business_name;// 商户名 string 商户名
		
		public String latitude;// 纬度坐标 float 纬度坐标， 
		public String longitude;// 经度坐标 float 经度坐标， 
		
		

	}
}
