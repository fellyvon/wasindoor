package com.aiyc.server.standalone.dzdp;

import java.util.List;

/**
 * ��վͨѶ
 * 
 * @author felly
 * 
 */
public interface INet {

 

	public List<Businesses> searchBussniss(int page, String jd, String wd, int radias) throws Exception;
	
	public int getSearchBussnissCount(String  json) throws Exception;

	public BusinessesDetail getBussniss(String bussnissId) throws Exception;

	public DealsDetail getDeals(String dealId) throws Exception;

	public CouponDetail getCoupons(String couponId) throws Exception;

	
}
