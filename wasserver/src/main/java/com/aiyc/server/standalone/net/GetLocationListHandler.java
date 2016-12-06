 
package com.aiyc.server.standalone.net;

import java.util.List;


import com.aiyc.server.standalone.core.Location;
import com.aiyc.server.standalone.db.HomeFactory;
import com.aiyc.server.standalone.db.homes.LocationHome;
import com.aiyc.server.standalone.net.Response.Status;
import com.aiyc.server.standalone.util.Log;
import com.google.gson.JsonElement;

/**
 * @see IHandler
 * @author  felly
 *
 */
public class GetLocationListHandler implements IHandler {
	
	LocationHome locHome;
	
	public GetLocationListHandler() {
		locHome = HomeFactory.getLocationHome();
	}
	
	
	/**
	 * @see IHandler#handle(JsonElement)
	 */
 
	public Response handle(JsonElement data) {
		
		Response res;
		
		List<Location> locations = locHome.getAll();
		
		if(locations.contains(null)) {
			res = new Response(Status.failed, "could not fetch all locations", null);
			Log.getLogger().fine("could not fetch all locations");
		} else {
			res = new Response(Status.ok, null, locations);
			Log.getLogger().finer("fetched "+ locations.size()+ " locations");
		}
		
		return res;
		
	}

}
