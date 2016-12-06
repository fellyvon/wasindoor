package com.aiyc.server.standalone.dzdp;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;


import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.util.Log;

/**
 * ͬ�����ڵ�����Ϣ
 * 
 * @author felly
 * 
 */
public class NotifyDaz implements Runnable {
	private Logger log = Log.getLogger();
	private INet source = null;
	public static final int MAX_PAGE = 2000;
	private String areaNo,buildNo, jd, wd;// /ÿ������һ���߳̽���ͬ��
	private int radias;
	private DataHandler db = new DataHandler();

	public NotifyDaz(String areaNo,String buildNo, String jd, String wd, int radias) {
		this.jd = jd;
		this.wd = wd;
		this.radias = radias;
		this.buildNo = buildNo;
		this.areaNo=areaNo;
		
		try {
			Thread.currentThread().setName(buildNo);
			source = (new BaseSource())
					.getSource("org.icscn.server.standalone.dzdp.DzdpNet");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int index = 0;// /�����αꡣ
 
	public void run() {

		index++;

		A:while (index < MAX_PAGE )
			// ////Ȼ�����������
			try {

				// /�õ��̻���Ϣ
				List<Businesses> bs = source.searchBussniss(index, jd, wd,
						radias);
				Thread.sleep(1000);
				if (bs!=null&&bs.size() > 0) {
					Iterator<Businesses> it = bs.iterator();

					B:while (it.hasNext()) {
						Businesses bz = it.next();
						bz.area_no=areaNo;
						bz.build_no=buildNo;
						db.clearData(bz);
						db.saveBusinesses(bz);
						// 

						String bsId = bz.business_id;
						if (StringUtils.nullToEmpty(bsId).equals("")) {
							break A;

						}
						// /ͬ���̻���ϸ��Ϣ
						Thread.sleep(1000);
						BusinessesDetail bsDet = source.getBussniss(bsId);
						db.saveBusinessesDetail(bsDet);

						// ����̻�ͬ���Ź�
						Thread.sleep(1000);
						Iterator<Businesses.Deal> ist = bz.deals.iterator();

						while (ist.hasNext()) {
							Businesses.Deal det = ist.next();
							DealsDetail deal = source.getDeals(det.id);

							db.saveDealsDetail(bsId, deal);

						}

						// ����̻�ͬ���Ż�
						Thread.sleep(1000);
						CouponDetail cou = source.getCoupons(bz.coupon_id);
						db.saveCouponDetail(bsId, cou);
					}
					 

				} else {
					break;
				}
				Thread.sleep(1000);
				index++;
System.out.println("�̣߳�"+Thread.currentThread().getName()+"��ȷͬ����"+index);
			} catch (Exception e) {
				e.printStackTrace();
				log.log(log.getLevel(), e.getMessage(), e);
				try {
					Thread.sleep(1000);
				} catch (Exception er) {
					er.printStackTrace();
				}
				System.out.println("�̣߳�"+Thread.currentThread().getName()+"�쳣ͬ����"+index);
				index++;
				continue;

			}

	}

	public static void main(String s[]) throws Exception {

	}

}
