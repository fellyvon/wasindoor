package com.aiyc.server.standalone.net;

import com.aiyc.server.standalone.core.Location;
import com.aiyc.server.standalone.db.HomeFactory;
import com.aiyc.server.standalone.db.homes.LocationHome;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.Response.Status;
import com.aiyc.server.standalone.util.Log;
import com.google.gson.JsonElement;

/**
 * @see IHandler
 * @author felly
 */
public class UpdateLocationHandler implements IHandler {

	LocationHome locHome;

	public UpdateLocationHandler() {
		locHome = HomeFactory.getLocationHome();
	}

	/**
	 * @see IHandler#handle(JsonElement)
	 */

	public Response handle(JsonElement data) {
		Response res;

		Location loc = GsonFactory.getGsonInstance().fromJson(data,
				Location.class);

		if (locHome.update(loc)) {
			res = new Response(Status.ok, null, null);
			Log.getLogger().finer("location updated: " + loc);

		} else {
			res = new Response(Status.failed, "could not update to database",
					null);
			Log.getLogger().fine(
					"location could not be updated to the database");
		}

		return res;
	}

}
