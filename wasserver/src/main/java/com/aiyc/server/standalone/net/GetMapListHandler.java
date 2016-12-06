 
package com.aiyc.server.standalone.net;

import java.util.List;


import com.aiyc.server.standalone.core.Map;
import com.aiyc.server.standalone.db.HomeFactory;
import com.aiyc.server.standalone.db.homes.MapHome;
import com.aiyc.server.standalone.net.Response.Status;
import com.aiyc.server.standalone.util.Log;
import com.google.gson.JsonElement;

/**
 * @see IHandler
 * @author  felly
 *
 */
public class GetMapListHandler implements IHandler {
	
	
	MapHome mapHome;
	
	public GetMapListHandler() {
		mapHome = HomeFactory.getMapHome();
	}
		
	/**
	 * @see IHandler#handle(JsonElement)
	 */
 
	public Response handle(JsonElement data) {
		
		Response res;
		
		List<Map> maps = mapHome.getAll();
		
		if(maps.contains(null)) {
			res = new Response(Status.failed, "could not fetch all maps", null);
			Log.getLogger().fine("could not fetch all maps");
		} else {
			res = new Response(Status.ok, null, maps);
			Log.getLogger().finer("fetched "+ maps.size()+ " maps");
		}
		
		return res;
	}

}
