package com.waspring.wasservice.net.busii;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Vector;

import com.aiyc.framework.annotation.Requestable;
import com.aiyc.framework.utils.StringUtils;
import com.aiyc.server.standalone.core.Location;
import com.aiyc.server.standalone.core.Measurement;
import com.aiyc.server.standalone.core.measure.WiFiReading;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.locator.ILocator;
import com.aiyc.server.standalone.net.IHandler;
import com.aiyc.server.standalone.net.Response;
import com.aiyc.server.standalone.net.Response.Status;
import com.aiyc.server.standalone.svm.MapSVMSupport;
import com.google.gson.JsonElement;
import com.waspring.wasservice.net.busii.loc.MapLocator;
import com.waspring.wasservice.net.busii.loc.MapSVMLocator;
import com.waspring.wasservice.net.dao.comm.CommDao;
import com.waspring.wasservice.net.model.GetPosRepMessage;
import com.waspring.wasservice.net.model.GetPosReqMessage;

@Requestable(serverName = "GET_POS_REQ")
public class GetPosHandler implements IHandler {
	private static ILocator locator = null;
   private CommDao dao=new CommDao();
	public static void main(String sdfsdf[]) throws Exception {

	}

	public Response handle(JsonElement data) throws Exception {
		GetPosRepMessage model = GsonFactory.getGsonInstance().fromJson(data,
				GetPosRepMessage.class);
		String mapNo = model.MESSAGE.MAP_NO;
		if (StringUtils.isNullOrBank(mapNo)) {
			Iterator<GetPosRepMessage.Message.AP_INFO> it = model.MESSAGE.AP_INFO
					.iterator();
			while (it.hasNext()) {
				GetPosRepMessage.Message.AP_INFO ap = it.next();
                     String temp=dao.getMapNo(ap.AP_MAC);
                     if(temp==null||"".equals(temp)){
                    	 continue;
                     }
                     
                     mapNo=temp;
                     break;
			}
			
		}
		locator = getLocator(mapNo);

		Location loc = locator.locate(changeByGetPosRepMessage(model));

		GetPosReqMessage qm = new GetPosReqMessage();
		if (loc != null) {
			qm.AC = loc.getAccuracy() + "";
			qm.MAP_NO = mapNo;
			qm.X = loc.getMapXcord() + "";
			qm.Y = loc.getMapYcord() + "";
			ResultSet rs=dao.getLoc(mapNo);
			
			if(rs.next()){
				qm.AREA_NO=rs.getString("AREA_NO");
				qm.BUILD_NO=rs.getString("BUILD_NO");
				qm.FLOOR_NO=rs.getString("FLOOR_NO");
			}
			qm.RTN_FLAG = "1";
			qm.RTN_MSG = "";
		} else {
			qm.RTN_FLAG = "0";
			qm.RTN_MSG = "定位失败";
			return new Response(Status.failed, qm.RTN_MSG, qm.toJson());

		}
		return new Response(Status.ok, qm.RTN_MSG, qm.toJson());

	}

	/**
	 * 将 GetPosRepMessage 转化为 Measurement
	 * 
	 * @param mapId
	 * @return
	 */

	public Measurement changeByGetPosRepMessage(GetPosRepMessage model) {
		Measurement m = new Measurement();
		Vector<WiFiReading> wifiReadings = new Vector<WiFiReading>();
		Iterator<GetPosRepMessage.Message.AP_INFO> it = model.MESSAGE.AP_INFO
				.iterator();
		while (it.hasNext()) {
			GetPosRepMessage.Message.AP_INFO ap = it.next();
			WiFiReading wifi = new WiFiReading();
			wifi.setBssid(ap.AP_MAC);
			wifi.setRssi(Integer.parseInt(ap.AP_VALUE));
			wifi.setSsid(ap.AP_NAME);
			wifi.setWepEnabled("true".equals(ap.WEPENABLED)||"1".equals(ap.WEPENABLED));
			wifi.setInfrastructure("true".equals(ap.ISINFRASTRUCTURE)||"1".equals(ap.ISINFRASTRUCTURE) );
			wifiReadings.add(wifi);
		}

		m.setWiFiReadings(wifiReadings);
	 
		return m;
	}
/**
 *获取位置信息
 * @param mapId
 * @return
 */
	public static synchronized ILocator getLocator(String mapId) {
	 
		

	 
			if (MapSVMSupport.isTrained(mapId)) {
				locator = new MapSVMLocator(mapId); // locator is changed only
				// once
			}
			else{
				locator = new MapLocator(mapId);
				 
			}
		 
		return locator;
	}
}
