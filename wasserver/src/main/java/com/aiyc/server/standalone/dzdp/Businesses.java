package com.aiyc.server.standalone.dzdp;

import java.util.ArrayList;
import java.util.List;

public class Businesses implements NetJosnObject {
	public String getObjectType() {
		 return this.getClass().getSimpleName();
	}
	public String business_id, name, business_url,
	business_h5_url;
	public List<Deal> deals = new ArrayList<Deal>();

	public static class Deal {
		public String id;
		public String description;
		public String url;
	}
	
	
	public String coupon_id ;// �Ż�ȯID
	public String coupon_description ;// �Ż�ȯ����
	public String coupon_url ;// �Ż�ȯҳ������

	public String area_no,build_no,floor_no,door_no;
}
