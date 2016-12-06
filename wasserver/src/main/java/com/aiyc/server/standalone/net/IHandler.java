 
package com.aiyc.server.standalone.net;

import com.google.gson.JsonElement;

/**
 * Interface for an request handler
 * 
 * @author felly
 *
 */
public interface IHandler {
	
	/**
	 * Handles a request
	 * @param data Data sent with the request
	 * @return {@link Response}
	 */
	public Response handle(JsonElement data) throws Exception;
}
