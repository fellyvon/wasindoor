package com.aiyc.framework.utils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author felly
 * 
 */
public class ResultToObject {

	/**
	 * 
	 * @param type
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static List resultToBase(Class type, ResultSet rs) throws Exception {
		rs.beforeFirst();
		List reuslt = new ArrayList();
		while (rs.next()) {
			Object object = type.newInstance();

			java.lang.reflect.Field[] fields = type.getFields();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getType() == List.class) {
					continue;
				}
				fields[i].setAccessible(true);
				String name = fields[i].getName();
				Object os = rs.getString(name);

				fields[i].set(object, os);
			}
			reuslt.add(object);

		}

		return reuslt;

	}

	public static List resultToBase(Class type, ResultSet rs, Map exp)
			throws Exception {
		rs.beforeFirst();
		List reuslt = new ArrayList();
		while (rs.next()) {
			Object object = type.newInstance();

			java.lang.reflect.Field[] fields = type.getFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				String name = fields[i].getName();
				if (exp.get(name) != null) {
					continue;
				}
				Object os = rs.getString(name);

				fields[i].set(object, os);
			}
			reuslt.add(object);

		}

		return reuslt;

	}

}
