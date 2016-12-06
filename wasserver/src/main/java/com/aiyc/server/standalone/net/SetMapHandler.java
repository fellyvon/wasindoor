 
package com.aiyc.server.standalone.net;


import com.aiyc.server.standalone.core.Map;
import com.aiyc.server.standalone.db.HomeFactory;
import com.aiyc.server.standalone.db.homes.MapHome;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.Response.Status;
import com.aiyc.server.standalone.util.Log;
import com.google.gson.JsonElement;

/**
 * @see IHandler
 * @author felly
 */
public class SetMapHandler implements IHandler {

	MapHome mapHome;
	
	public SetMapHandler() {
		mapHome = HomeFactory.getMapHome();
	}
	
	/**
	 * @see IHandler#handle(JsonElement)
	 */
 
	public Response handle(JsonElement data) {
		
		Response res;
		
		Map map = GsonFactory.getGsonInstance().fromJson(data, Map.class);
		map = mapHome.add(map);
		
		if(map == null) {
			res = new Response(Status.failed, "could not add to database", null);
			Log.getLogger().fine("could not add map to database");
		} else {
			res = new Response(Status.ok, null, map);
			Log.getLogger().finer("added map to database");
		}
		
		
		return res;
	}

}
