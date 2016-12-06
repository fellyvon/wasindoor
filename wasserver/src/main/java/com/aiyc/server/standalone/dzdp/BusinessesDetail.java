package com.aiyc.server.standalone.dzdp;

import java.util.ArrayList;
import java.util.List;

public class BusinessesDetail implements NetJosnObject {

	public String getObjectType() {
		 return this.getClass().getSimpleName();
	}
	public String business_id;// �̻�ID
	public String name;// �̻���
	public String branch_name;// �ֵ���
	public String address;// ��ַ
	public String telephone;// ����ŵĵ绰
	public String city;// ���ڳ���

	public String latitude;// γ�����
	public String longitude;// �������
	public String avg_rating;// �Ǽ����֣�5.0������ǣ�4.5������ǰ룬��������
	public String rating_img_url;// �Ǽ�ͼƬ����
	public String rating_s_img_url;// С�ߴ��Ǽ�ͼƬ����
	public String product_grade;// ��Ʒ/ʳƷ��ζ���ۣ�1:һ�㣬2:�пɣ�3:�ã�4:�ܺã�5:�ǳ���
	public String decoration_grade;// �������ۣ�1:һ�㣬2:�пɣ�3:�ã�4:�ܺã�5:�ǳ���
	public String service_grade;// �������ۣ�1:һ�㣬2:�пɣ�3:�ã�4:�ܺã�5:�ǳ���
	public String product_score;// ��Ʒ/ʳƷ��ζ���۵���֣���ȷ��С����һλ��ʮ���ƣ�
	public String decoration_score;// �������۵���֣���ȷ��С����һλ��ʮ���ƣ�
	public String service_score;// �������۵���֣���ȷ��С����һλ��ʮ���ƣ�
	public String avg_price;// �˾�۸񣬵�λ:Ԫ����û���˾���-1
	public String review_count;// ��������
	public String  review_list_url ;// ����ҳ��URL����
	public String business_url;// �̻�ҳ������
	public String photo_url;// ��Ƭ���ӣ���Ƭ���ߴ�700��700
	public String s_photo_url;// С�ߴ���Ƭ���ӣ���Ƭ���ߴ�278��200
	public String photo_count;// ��Ƭ����

	public String  photo_list_url ;// /list
	// ��Ƭҳ��URL����
	public String has_coupon;// �Ƿ����Ż�ȯ��0:û�У�1:��

	public String has_deal;// �Ƿ����Ź���0:û�У�1:��
	public String deal_count;// �̻���ǰ�����Ź�����
	
	public String has_online_reservation,online_reservation_url;
	
	
	public List<String> regions  = new ArrayList<String>();// /�������� 
	public List<String> categories  = new ArrayList<String>();// /���� 
	 
}
