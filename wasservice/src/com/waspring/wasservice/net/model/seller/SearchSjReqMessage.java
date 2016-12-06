package com.waspring.wasservice.net.model.seller;

import com.waspring.wasservice.net.model.BaseObject;

public class SearchSjReqMessage extends BaseObject {

	public Message MESSAGE = new Message();

	public static  class Message {
		public String business_id;// 商户ID int 商户ID
		public String latitude;// 纬度坐标 float 纬度坐标，须与经度坐标同时传入，与城市名称二者必选其一传入
		public String longitude;// 经度坐标 float 经度坐标，须与纬度坐标同时传入，与城市名称二者必选其一传入
		public String radius;// 搜索半径 int 搜索半径，单位为米，最小值1，最大值5000，如不传入默认为1000
		public String area_no;// 区域编号 string 区域编号(经纬度没有传入的情况下必须传入)
		public String build_no;// 建筑编号 string 建筑编号
		public String floor_no;// 楼层编号 string 楼层编号
		public String door_no;// 房间编号 string 房间编号
		public String region;// 城市区域名 string
								// 城市区域名，可选范围见相关API返回结果（不含返回结果中包括的城市名称信息），如传入城市区域名，则城市名称必须传入
		public String category;// 分类名 string
								// 分类名，可选范围见相关API返回结果；支持同时输入多个分类，以逗号分隔，最大不超过5个。
		public String keyword;// 关键词 string 关键词，搜索范围包括商户名、地址、标签等
		public String out_offset_type;// 传出经纬度偏移类型1 int
										// 传出经纬度偏移类型，1:高德坐标系偏移，2:图吧坐标系偏移，如不传入，默认值为1
		public String platform;// 传出链接类型 int
								// 传出链接类型，1:web站链接（适用于网页应用），2:HTML5站链接（适用于移动应用和联网车载应用），如不传入，默认值为1
		public String has_coupon;// 是否有优惠券 int 根据是否有优惠券来筛选返回的商户，1:有，0:没有
		public String has_deal;// 是否有团购 int 根据是否有团购来筛选返回的商户，1:有，0:没有
		public String has_online_reservation;// 是否支持在线预订 int
												// 根据是否支持在线预订来筛选返回的商户，1:有，0:没有
		public String sort;// 结果排序 int
							// 结果排序，1:默认，2:星级高优先，3:产品评价高优先，4:环境评价高优先，5:服务评价高优先，6:点评数量多优先，7:离传入经纬度坐标距离近优先，8:人均价格低优先，9：人均价格高优先
		public String limit;// 结果条目数上限 int 每页返回的商户结果条目数上限，最小值1，最大值40，如不传入默认为20
		public String page;// 页码 int 页码，如不传入默认为1，即第一页

	}

}
