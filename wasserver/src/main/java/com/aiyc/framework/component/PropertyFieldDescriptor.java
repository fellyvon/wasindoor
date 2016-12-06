package com.aiyc.framework.component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.aiyc.framework.utils.PropertyFieldUtils;

public class PropertyFieldDescriptor {

	public Class getPropertyType() {
		return propertyType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public PropertyFieldDescriptor(Field field1) {
		readMethod = null;
		writeMethod = null;
		field = field1;
		propertyName = field1.getName();
		propertyType = field1.getType();
	}

	public PropertyFieldDescriptor(String s, Method method, Method method1) {
		readMethod = method;
		writeMethod = method1;
		field = null;
		propertyName = s;
		propertyType = method.getReturnType();
	}

	public PropertyFieldDescriptor(Method method, Field field1) {
		readMethod = method;
		writeMethod = null;
		field = field1;
		propertyName = field1.getName();
		propertyType = field1.getType();
	}

	public PropertyFieldDescriptor(Field field1, Method method) {
		readMethod = null;
		writeMethod = method;
		field = field1;
		propertyName = field1.getName();
		propertyType = field1.getType();
	}

	public Object get(Object obj) {
		try {
			if (readMethod != null)
				return readMethod.invoke(obj, new Object[0]);
		} catch (Exception exception) {
			return null;
		}
		Object o = null;
		try {
			o = field.get(obj);
		} catch (Exception e) {

		}
		return o;
	}

	public void set(Object obj, Object obj1) {
		try {
			Object obj2 = PropertyFieldUtils.coverTo(obj1, propertyType);
			if (writeMethod != null)
				writeMethod.invoke(obj, new Object[] { obj2 });
			else if (field != null)
				field.set(obj, obj2);
			else
				throw new RuntimeException("Property protected!");
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private Class propertyType;
	private Method readMethod;
	private Method writeMethod;
	private Field field;
	private String propertyName;
}
