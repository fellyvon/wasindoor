 
package com.aiyc.server.standalone.net;

import java.util.logging.Logger;


import com.aiyc.server.standalone.core.Location;
import com.aiyc.server.standalone.core.Measurement;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.locator.LocatorHome;
import com.aiyc.server.standalone.net.Response.Status;
import com.aiyc.server.standalone.util.Log;
import com.google.gson.JsonElement;

/**
 * @see IHandler
 * @author felly
 */
public class GetLocationHandler implements IHandler {

	public static void main(String dsfsf[]) throws Exception{
		GetLocationHandler s=new GetLocationHandler();
		String json="";
	}
	private Logger log;	
	
	public GetLocationHandler() {
		log = Log.getLogger();
	}
	
	
	/**
	 * @see IHandler#handle(JsonElement)
	 */
 
	public Response handle(JsonElement data) {
		Response res;
		Location loc;
		
		Measurement currentMeasurement = GsonFactory.getGsonInstance().fromJson(data, Measurement.class);
		log.finer("got measurement: " + data);
		
		loc = LocatorHome.getLocator().locate(currentMeasurement);
		
			
		if(loc == null) {
			log.fine("no matching location found");
			res = new Response(Status.failed, "no matching location found", null);
			
		} else {
			res = new Response(Status.ok, null, loc);
			log.finer("location found: " + loc + " accuracy: "+loc.getAccuracy());
		}
		
		
		return res;
	}
	

}
