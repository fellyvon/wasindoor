package com.aiyc.server.standalone.util;

import java.lang.annotation.Annotation;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


import com.aiyc.framework.annotation.Requestable;
import com.aiyc.server.standalone.WasStandaloneServer;
import com.aiyc.server.standalone.net.IHandler;

public final class ConsoleData {
	public static final String HNADER = "hander";

	private static class ConsoleDataImpl {
		static ConsoleData instance = new ConsoleData();
	}

	private ConsoleData() {
		data = new Hashtable();
		init();
	}

	public static ConsoleData instance() {
		return ConsoleDataImpl.instance;
	}

	public Map data = null;

	public void addCached(Object objectName, Object value) {
		data.put(objectName, value);
	}

	public Object getCachedValue(Object name) {
		return data.get(name);
	}

	private void init() {
		if (data.get(HNADER) != null)
			return;

		List<String> loaderClasses = new ArrayList<String>();
		String patsh[] = Configuration.handers.split(";");

		URLClassLoader loader = WasStandaloneServer.getServiceClassLoader();

		for (int i = 0; i < patsh.length; i++) {
			try {
				loaderClasses.addAll(ClassUtil.getClassName(patsh[i], loader));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		URLClassLoader calcloader = WasStandaloneServer.getCalcClassLoader();
		List<String> calcloaderClasses = new ArrayList<String>();
		for (int i = 0; i < patsh.length; i++) {
			try {
				calcloaderClasses.addAll(ClassUtil.getClassName(patsh[i], calcloader));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Map handers = new Hashtable(1024);
		for (int i = 0; i < loaderClasses.size(); i++) {
			try {
				String path = loaderClasses.get(i);
				Class handClass = loader.loadClass(path);

				Object hander = handClass.newInstance();
				if (hander instanceof IHandler) {
					IHandler hand = (IHandler) hander;
					Annotation ano = handClass.getAnnotation(Requestable.class);
					if (ano != null) {
						Requestable req = (Requestable) ano;
						handers.put(req.serverName(), hand);
					}
				}

			} catch (java.lang.InstantiationException e) {
              /// e.printStackTrace();
               
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		for (int i = 0; i < calcloaderClasses.size(); i++) {
			try {
				String path = calcloaderClasses.get(i);
				Class handClass = calcloader.loadClass(path);

				Object hander = handClass.newInstance();
				if (hander instanceof IHandler) {
					IHandler hand = (IHandler) hander;
					Annotation ano = handClass.getAnnotation(Requestable.class);
					if (ano != null) {
						Requestable req = (Requestable) ano;
						handers.put(req.serverName(), hand);
					}
				}

			} catch (java.lang.InstantiationException e) {
              ///// e.printStackTrace();
               
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
		data.put(HNADER, handers);

	}
}
