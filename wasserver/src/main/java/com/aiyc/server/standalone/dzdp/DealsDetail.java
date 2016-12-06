package com.aiyc.server.standalone.dzdp;

import java.util.ArrayList;
import java.util.List;

public class DealsDetail  implements NetJosnObject{
	public String getObjectType() {
		 return this.getClass().getSimpleName();
	}
	public String deal_id;// �Ź���ID
	public String title;// �Ź�����
	public String description;// �Ź�����
	public String city;// ������ƣ�cityΪ��ȫ���ʾȫ������Ϊ���ص������з�Χ�����API���ؽ��
	public String list_price;// �Ź�����Ʒԭ��ֵ
	public String current_price;// �Ź��۸�

	public List<String> regions = new ArrayList<String>();// /��������
	public List<String> categories = new ArrayList<String>();// /����
	public String purchase_count;// �Ź���ǰ�ѹ�����
	public String publish_date;// �Ź�������������
	public String details;// �Ź�����
	public String purchase_deadline;// �Ź����Ľ�ֹ��������
	public String image_url;// �Ź�ͼƬ���ӣ����ͼƬ�ߴ�450��280
	public String s_image_url;// С�ߴ��Ź�ͼƬ���ӣ����ͼƬ�ߴ�160��100
	public List<String> more_image_urls=new   ArrayList<String>();// ����ߴ�ͼƬ
	public List<String> more_s_image_urls=new   ArrayList<String>();// ���С�ߴ�ͼƬ
	public String is_popular;// �Ƿ�Ϊ�����Ź���0�����ǣ�1����
	public  Restrictions restrictions = new  Restrictions(); // Restrictions
																			// �Ź���������

	public static class Restrictions {
		public String is_reservation_required;// �Ƿ���ҪԤԼ��0�����ǣ�1����
		public String is_refundable;// int �Ƿ�֧����ʱ�˿0�����ǣ�1����
		public String special_tips;// string ������Ϣ(һ��Ϊ�Ź���Ϣ���ر���ʾ)
	}

	public String notice;// ��Ҫ֪ͨ(һ��Ϊ�Ź���Ϣ����ʱ���)
	public String deal_url;// �Ź�Webҳ�����ӣ���������ҳӦ��
	public String deal_h5_url;// �Ź�HTML5ҳ�����ӣ��������ƶ�Ӧ�ú�������Ӧ��
	public String commission_ratio;// ��ǰ�ŵ���Ӷ�����

}
