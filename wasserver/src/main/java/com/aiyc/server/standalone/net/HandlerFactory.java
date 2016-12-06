 
package com.aiyc.server.standalone.net;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

import com.aiyc.server.standalone.net.RequestHandler.RequestType;
import com.aiyc.server.standalone.util.ConsoleData;

/**
 * Factory in oder to get the different request handler
 * 
 * @author felly
 * 
 */
public class HandlerFactory {

	/**
	 * Finds the correct {@link IHandler} for the request type. if no
	 * {@link IHandler} can be found, the {@link UnknownHandler} is returned
	 * 
	 * @param type
	 *            Request type
	 * @return appropriate handler
	 */
	public static IHandler findHandler(RequestType type) {

		switch (type) {
		case setFingerprint:
			return getSetFingerprintHandler();
		case getMapList:
			return getGetMapHandler();
		case setMap:
			return getSetMapHandler();
		case removeMap:
			return getRemoveMapHandler();
		case getLocation:
			return getGetLocationHandler();
		case getLocationList:
			return getGetLocationListHandler();
		case updateLocation:
			return getUpdateLocationHandler();
		case removeLocation:
			return getRemoveLocationHandler();
	 
		default:
			return getUnknownHandler();
		}
	}

	private static Properties p = null;

	public static IHandler annoHandler(String type) {
		IHandler hand = (IHandler) ((Map) ConsoleData.instance()
				.getCachedValue(ConsoleData.HNADER)).get(type);
		if (hand == null) {
			return getUnknownHandler();
		}

		return hand;

	}

	public static IHandler findHandler(String type) {
		// ////��ȡ�����ļ���Ȼ���෵��
		if (p == null) {
			p = new Properties();
			File f = new File("hander.properties");

			if (f.exists()) {
				try {
					FileInputStream reader = new FileInputStream(f);
					p.load(reader);

				} catch (Exception e) {
					e.printStackTrace();
					return HandlerFactory.getUnknownHandler();
				}

			} else {
				return HandlerFactory.getUnknownHandler();
			}

		}

		String path = p.getProperty(type);
		try {
			return (IHandler) Class.forName(path).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return HandlerFactory.getUnknownHandler();
		}

	}

	private static UnknownHandler unkownHandler;

	private static UnknownHandler getUnknownHandler() {
		if (unkownHandler == null) {
			unkownHandler = new UnknownHandler();
		}
		return unkownHandler;
	}

	private static SetMapHandler setMapHandler;

	private static SetMapHandler getSetMapHandler() {
		if (setMapHandler == null) {
			setMapHandler = new SetMapHandler();
		}
		return setMapHandler;
	}

	private static SetFingerprintHandler setFingerprintHandler;

	private static SetFingerprintHandler getSetFingerprintHandler() {
		if (setFingerprintHandler == null) {
			setFingerprintHandler = new SetFingerprintHandler();
		}

		return setFingerprintHandler;
	}

	private static GetMapListHandler getMapListHandler;

	public static GetMapListHandler getGetMapHandler() {
		if (getMapListHandler == null) {
			getMapListHandler = new GetMapListHandler();
		}

		return getMapListHandler;
	}

	private static RemoveMapHandler removeMapHandler;

	public static RemoveMapHandler getRemoveMapHandler() {
		if (removeMapHandler == null) {
			removeMapHandler = new RemoveMapHandler();
		}

		return removeMapHandler;
	}

	private static UpdateLocationHandler updateLocationHandler;

	public static UpdateLocationHandler getUpdateLocationHandler() {
		if (updateLocationHandler == null) {
			updateLocationHandler = new UpdateLocationHandler();
		}

		return updateLocationHandler;

	}

 

	private static GetLocationHandler getLocationHandler;

	public static GetLocationHandler getGetLocationHandler() {
		if (getLocationHandler == null) {
			getLocationHandler = new GetLocationHandler();
		}

		return getLocationHandler;

	}

	private static GetLocationListHandler getLocationListHandler;

	public static GetLocationListHandler getGetLocationListHandler() {
		if (getLocationListHandler == null) {
			getLocationListHandler = new GetLocationListHandler();
		}

		return getLocationListHandler;
	}

	private static RemoveLocationHandler removeLocationHandler;

	public static RemoveLocationHandler getRemoveLocationHandler() {
		if (removeLocationHandler == null) {
			removeLocationHandler = new RemoveLocationHandler();
		}

		return removeLocationHandler;
	}

}
