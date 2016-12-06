package com.aiyc.server.standalone.dzdp;

import java.util.ArrayList;
import java.util.List;

public class CouponDetail implements NetJosnObject {
	public String getObjectType() {
		 return this.getClass().getSimpleName();
	}
	public String coupon_id;// �Ż�ȯID
	public String title;// �Ż�ȯ����
	public String description;// �Ż�ȯ����

	public String download_count;// �Ż�ȯ��ǰ��������
	public String publish_date;// �Ż�ȯ������������
	public String expiration_date;// �Ż�ȯ�Ľ�ֹʹ������
	public String logo_img_url;// �Ż�ȯ��ͼ�꣬�ߴ�120��90
	public String coupon_url;// �Ż�ȯWebҳ�����ӣ���������ҳӦ��
	public String coupon_h5_url;// �Ż�ȯHTML5ҳ�����ӣ��������ƶ�Ӧ�ú�������Ӧ��

	public List<String> regions = new ArrayList<String>();// /��������
	public List<String> categories = new ArrayList<String>();// /����
}
