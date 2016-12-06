 
package com.aiyc.server.standalone.net;


import com.aiyc.server.standalone.core.Location;
import com.aiyc.server.standalone.db.HomeFactory;
import com.aiyc.server.standalone.db.homes.LocationHome;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.Response.Status;
import com.aiyc.server.standalone.util.Log;
import com.google.gson.JsonElement;

/**
 * Does remove location and the corresponding fingerprint (+measurements)
 * 
 * @author felly
 */
public class RemoveLocationHandler implements IHandler {

	LocationHome locHome;
	
	public RemoveLocationHandler() {
		locHome = HomeFactory.getLocationHome();
	}
	
	/**
	 * @see IHandler#handle(JsonElement)
	 */
 
	public Response handle(JsonElement data) {
		
		Response res;
		
		Location loc = GsonFactory.getGsonInstance().fromJson(data, Location.class);
		
		if(loc != null) {
			
			boolean locRemove = locHome.remove(loc);
			
			if(locRemove) { 
				res = new Response(Status.ok, null, null);
				Log.getLogger().finer("removed location from database");
			} else {
				res = new Response(Status.failed, "could not remove from database", loc);
				Log.getLogger().fine("could not remove location from database ");
			}
			
			
		} else {
			res = new Response(Status.ok, null, null);
			Log.getLogger().fine("fingerprint is not in the database");
		}
		
		
		
		
		
		return res;
	}

}
