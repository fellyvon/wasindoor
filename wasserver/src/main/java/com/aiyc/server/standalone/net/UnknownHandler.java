 
package com.aiyc.server.standalone.net;


import com.aiyc.server.standalone.net.Response.Status;
import com.aiyc.server.standalone.util.Log;
import com.google.gson.JsonElement;

/**
 * Handler which is used if no appropriate handler could be found
 * 
 * @see IHandler
 * @author felly
 */
public class UnknownHandler implements IHandler {

	/**
	 * @see IHandler#handle(JsonElement)
	 */
	public Response handle(JsonElement data) {
		Log.getLogger().fine("unkown handler called");
		return new Response(Status.failed, "no such action", null);
	}

}
