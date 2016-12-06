package com.aiyc.server.standalone.dzdp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.util.Configuration;
import com.aiyc.server.standalone.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * ͨѶʵ��
 * 
 * @author felly
 * 
 */
public class DzdpNet implements INet {
	private Logger log = Log.getLogger();
	private String appKey = Configuration.appkey;
	private String secret = Configuration.secret;

	private String getRequestUrl(String url, Map<String, String> condition) {
		StringBuilder stringBuilder = new StringBuilder();

		// �Բ���������ֵ�����
		String[] keyArray = condition.keySet().toArray(new String[0]);
		Arrays.sort(keyArray);
		// ƴ������Ĳ�����-ֵ��
		stringBuilder.append(appKey);
		for (String key : keyArray) {
			stringBuilder.append(key).append(condition.get(key));
		}
		String codes = stringBuilder.append(secret).toString();
		String sign = org.apache.commons.codec.digest.DigestUtils.shaHex(codes)
				.toUpperCase();

		// ���ǩ��
		stringBuilder = new StringBuilder();
		stringBuilder.append("appkey=").append(appKey).append("&sign=").append(
				sign);
		for (Map.Entry<String, String> entry : condition.entrySet()) {
			stringBuilder.append('&').append(entry.getKey()).append('=')
					.append(strToURL(entry.getValue()));
		}
		String queryString = stringBuilder.toString();

		return url + "?" + queryString;

	}

	private Gson gson = GsonFactory.getGsonInstance();
	private JsonParser parser = new JsonParser();

	public int getSearchBussnissCount(String json) throws Exception {
		JsonElement root = parser.parse(json);

		if (root.isJsonObject()) {
			JsonObject rootobj = root.getAsJsonObject();
			JsonElement count = rootobj.get("count");
			int sum = gson.fromJson(count, Integer.class);
			return sum;
		}

		return 0;

	}

	public DzdpNet() {

	}

	public List<Businesses> searchBussniss(int page, String jd, String wd,
			int radias) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("page", page + "");
		map.put("latitude", wd + "");
		map.put("longitude", jd + "");
		map.put("radius", radias + "");
		map.put("offset_type", "1");
		// map.put("city", "����");
		String url = getRequestUrl(Configuration.find_businesses_url, map);

		String json = this.getHttpServerResponse(url);
		JsonElement root = parser.parse(json);

		if (root.isJsonObject()) {
			JsonObject rootobj = root.getAsJsonObject();
			JsonElement status = rootobj.get("status");
			String stats = gson.fromJson(status, String.class);
			if (!"ok".equalsIgnoreCase(stats)) {

				log.log(Level.WARNING, "output:url=" + url + ",json=" + json,
						json + "\n");

				return null;
			}
			JsonElement businesses = rootobj.get("businesses");
			// []
			if (businesses.isJsonNull())
				return null;

			List<Businesses> result = gson.fromJson(businesses,
					new TypeToken<List<Businesses>>() {
					}.getType());

			return result;
		}

		return null;
	}

	public BusinessesDetail getBussniss(String bussnissId) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("business_ids", bussnissId);

		String url = getRequestUrl(Configuration.get_businesses_url, map);

		JsonElement root = parser.parse(this.getHttpServerResponse(url));

		if (root.isJsonObject()) {
			JsonObject rootobj = root.getAsJsonObject();
			JsonElement businesses = rootobj.get("businesses");
			// []
			if (businesses.isJsonNull())
				return new BusinessesDetail();
			if (businesses.isJsonArray()) {
				List<BusinessesDetail> result = gson.fromJson(businesses,
						new TypeToken<List<BusinessesDetail>>() {
						}.getType());

				if (result.size() > 0) {

					return result.get(0);
				}
			} else {
				return gson.fromJson(businesses,
						new TypeToken<BusinessesDetail>() {
						}.getType());
			}
		}

		return new BusinessesDetail();
	}

	public DealsDetail getDeals(String dealId) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("deal_id", dealId);

		String url = getRequestUrl(Configuration.get_deal_url, map);

		JsonElement root = parser.parse(this.getHttpServerResponse(url));

		if (root.isJsonObject()) {
			JsonObject rootobj = root.getAsJsonObject();
			JsonElement businesses = rootobj.get("deals");
			if (businesses.isJsonNull()) {
				return new DealsDetail();
			}
			if (businesses.isJsonArray()) {
				List<DealsDetail> result = gson.fromJson(businesses,
						new TypeToken<List<DealsDetail>>() {
						}.getType());
				if (result.size() > 0) {

					return result.get(0);
				}
			} else {
				return gson.fromJson(businesses, new TypeToken<DealsDetail>() {
				}.getType());
			}
		}

		return new DealsDetail();
	}

	public CouponDetail getCoupons(String couponId) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("coupon_id", couponId);

		String url = getRequestUrl(Configuration.get_coupon_url, map);

		JsonElement root = parser.parse(this.getHttpServerResponse(url));

		if (root.isJsonObject()) {
			JsonObject rootobj = root.getAsJsonObject();
			JsonElement businesses = rootobj.get("coupons");
			if (businesses.isJsonNull()) {
				return new CouponDetail();
			}
			if (businesses.isJsonArray()) {
				List<CouponDetail> result = gson.fromJson(businesses,
						new TypeToken<List<CouponDetail>>() {
						}.getType());
				if (result.size() > 0)
					return result.get(0);
			} else {
				return gson.fromJson(businesses, new TypeToken<CouponDetail>() {
				}.getType());
			}
		}

		return new CouponDetail();
	}

	private String getHttpServerResponse(String url) throws Exception {

		URL getUrl = new URL(url);
		System.out.println(url);
		// ���ƴ�յ�URL�������ӣ�URL.openConnection()�������
		// URL�����ͣ����ز�ͬ��URLConnection����Ķ������������ǵ�URL��һ��http�������ʵ���Ϸ��ص���HttpURLConnection

		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();

		// ����������������ӣ���δ�������

		connection.connect();

		// ������ݵ���������ʹ��Reader��ȡ���ص����

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "UTF-8"));

		StringBuffer lines = new StringBuffer(9082);
		String line = "";
		while ((line = reader.readLine()) != null) {

			lines.append(line).append(System.getProperty("line.separator"));
		}

		reader.close();

		connection.disconnect();
		System.out.println(lines.toString());
		return lines.toString();

	}

	private static String strToURL(String str) {
		String result = null;
		try {
			result = URLEncoder.encode(str, "utf-8");// gb2312
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
