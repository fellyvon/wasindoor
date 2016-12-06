 
package com.aiyc.server.standalone.net;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import com.aiyc.framework.monitor.Session;
import com.aiyc.framework.monitor.SessionManager;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.Response.Status;
import com.aiyc.server.standalone.util.Configuration;
import com.aiyc.server.standalone.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * Handler for a request. Each request read by a connection handler is passed to
 * this handler
 * 
 * @author felly
 */
public class RequestHandler {

	public static final String ACTION_TOKEN = "action";
	public static final String DATA_TOKEN = "data";
	public static final String NO_ACTION = "no action specified";

	/**
	 * Different request type supported by the server
	 * 
	 * @author Pascal Brogle (broglep@student.ethz.ch)
	 * 
	 */
	public enum RequestType {
		setFingerprint, getLocation, getMapList, setMap, removeMap, getLocationList, updateLocation, removeLocation, currentLocation

	};

	public static String getFlowId(JsonElement data) {
		if (data.isJsonObject()) {
			JsonObject headRoot = data.getAsJsonObject();
			JsonElement head = headRoot.get("HEAD");
			if (head.isJsonObject()) {
				return head.getAsJsonObject().get("FLOWID").getAsString();
			}

		}
		return null;
	}

	/**
	 * Does handle an request
	 * 
	 * @param request
	 *            Request
	 * @return response as string
	 */
	public String request(String request) {

		Response response = new Response(Status.failed, null, null);

		Gson gson = GsonFactory.getGsonInstance();
		JsonParser parser = new JsonParser();

		IHandler handler = null;

		try {

			JsonElement root = parser.parse(request);

			if (root.isJsonObject()) {
				JsonObject rootobj = root.getAsJsonObject();
				JsonElement action = rootobj.get(ACTION_TOKEN);
				JsonElement data = rootobj.get(DATA_TOKEN);

				if (action == null) {
					throw new Exception(NO_ACTION);
				}

				String type = gson.fromJson(action, String.class);
				long end = System.currentTimeMillis();

				if (Configuration.ANNO.equals(Configuration.handerType)) {
					handler = HandlerFactory.annoHandler(type);
				} else {
					handler = HandlerFactory.findHandler(type);
				}

				// /////
				String flowId = getFlowId(data);
				if (flowId != null) {
					// ////session管理

					Session ses = null;
					ses = SessionManager.instance().getSession(flowId);

					if (ses == null) {
						ses = new Session();
						ses.setFlowId(flowId);
						SessionManager.instance().addSession(ses);
					} else {
						ses.reflushTime();
					}
				}

				/////业务处理
				response = handler.handle(data);

			}

		} catch (JsonParseException e) {
			e.printStackTrace();
			response = new Response(Status.jsonError, e.getMessage(), null);
			Log.getLogger().fine("json parse error: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(Status.failed, e.getMessage(), null);
			Log.getLogger().fine("error: " + e.getMessage());
		}

		String response_str = "";
		try {
			response_str = gson.toJson(response);
		} catch (Exception e) {
			e.printStackTrace();
			response_str = "{\"status\":\"" + Status.jsonError
					+ "\",\"message\":\"" + e + ": " + e.getMessage() + "\"}";
			Log.getLogger().fine("json serializaion error: " + e.getMessage());
		}

		if (Configuration.LogRequests) {
			try {
				File f, r;
				int i = 0;
				while (true) {
					f = new File(Configuration.LogRequestPath + "/"
							+ handler.getClass().getSimpleName() + "_" + i);
					r = new File(Configuration.LogRequestPath + "/"
							+ handler.getClass().getSimpleName() + "_" + i
							+ "_response");
					if (!f.exists()) {
						BufferedWriter bw = new BufferedWriter(
								new FileWriter(f));
						bw.write(request);
						bw.close();

						bw = new BufferedWriter(new FileWriter(r));
						bw.write(response_str);
						bw.close();
						break;
					} else {
						i++;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return response_str;
	}

}
