package com.aiyc.server.standalone.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectHelper {

	/**
	 * 通过构�?�器取得实例
	 * 
	 * @param className
	 *            类的全限定名
	 * @param intArgsClass
	 *            构�?�函数的参数类型
	 * @param intArgs
	 *            构�?�函数的参数�?
	 * 
	 * @return Object
	 */
	public static Object getObjectByConstructor(String className,
			Class[] intArgsClass, Object[] intArgs) {

		Object returnObj = null;
		try {
			Class classType = Class.forName(className);
			Constructor constructor = classType
					.getDeclaredConstructor(intArgsClass); // 找到指定的构造方�?
			constructor.setAccessible(true);// 设置安全�?查，访问私有构�?�函数必�?
			returnObj = constructor.newInstance(intArgs);
		} catch (NoSuchMethodException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnObj;
	}

	/**
	 * 修改成员变量的�??
	 * 
	 * @param Object
	 *            修改对象
	 * @param filedName
	 *            指定成员变量�?
	 * @param filedValue
	 *            修改的�??
	 * 
	 * @return
	 */
	public static void modifyFileValue(Object object, String filedName,
			Object filedValue) {
		Class classType = object.getClass();
		Field fild = null;
		try {
			fild = classType.getDeclaredField(filedName);
			Class type = fild.getType();
			if (type == Integer.TYPE) {
				filedValue = new Integer(Integer.parseInt((String) filedValue));
			} else if (type == Byte.TYPE) {
				filedValue = new Byte(Byte.parseByte((String) filedValue));
			} else if (type == Short.TYPE) {
				filedValue = new Short(Short.parseShort((String) filedValue));
			} else if (type == Double.TYPE) {
				filedValue = new Double(Double.parseDouble((String) filedValue));
			} else if (type == Long.TYPE) {
				filedValue = new Long(Long.parseLong((String) filedValue));
			} else if (type == Boolean.TYPE) {
				filedValue = new Boolean(Boolean
						.parseBoolean((String) filedValue));
			}
			fild.setAccessible(true);// 设置安全�?查，访问私有成员变量必须
			fild.set(object, filedValue);
		} catch (NoSuchFieldException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 访问类成员变�?
	 * 
	 * @param Object
	 *            访问对象
	 * @param filedName
	 *            指定成员变量�?
	 * @return Object 取得的成员变量的�?
	 */
	public static Object getFileValue(Object object, String filedName) {
		Class classType = object.getClass();
		Field fild = null;
		Object fildValue = null;
		try {
			fild = classType.getDeclaredField(filedName);
			fild.setAccessible(true);// 设置安全�?查，访问私有成员变量必须
			fildValue = fild.get(object);
			Class type = fild.getType();
			if (type == Integer.TYPE) {
				fildValue = String.valueOf(fildValue);
			} else if (type == Byte.TYPE) {
				fildValue =  String.valueOf(fildValue);
			} else if (type == Short.TYPE) {
				fildValue =  String.valueOf(fildValue);
			} else if (type == Double.TYPE) {
				fildValue =  String.valueOf(fildValue);
			} else if (type == Long.TYPE) {
				fildValue =  String.valueOf(fildValue);
			} else if (type == Boolean.TYPE) {
				fildValue =  String.valueOf(fildValue);
			}

		} catch (NoSuchFieldException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fildValue;
	}

	/**
	 * 调用类的方法，包括私�?
	 * 
	 * @param Object
	 *            访问对象
	 * @param methodName
	 *            指定成员变量�?
	 * @param type
	 *            方法参数类型
	 * @param value
	 *            方法参数�?
	 * @return Object 方法的返回结果对�?
	 */
	public static Object useMethod(Object object, String methodName,
			Class[] type, Class[] value) {
		Class classType = object.getClass();
		Method method = null;
		Object fildValue = null;
		try {
			method = classType.getDeclaredMethod(methodName, type);
			method.setAccessible(true);// 设置安全�?查，访问私有成员方法必须
			fildValue = method.invoke(object, value);

		} catch (NoSuchMethodException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fildValue;
	}

	public static void main(String[] args) {
		ReflectHelper runbing = new ReflectHelper();

		// 访问成员变量
		String value = (String) ReflectHelper.getFileValue(runbing, "sex");
		System.out.println("value:" + value);
		// 修改成员变量
		ReflectHelper.modifyFileValue(runbing, "sex", "bbb");

		// �?查修改后的变量�??
		value = (String) ReflectHelper.getFileValue(runbing, "sex");
		System.out.println("value:" + value);

		// 调用方法
		value = (String) ReflectHelper.useMethod(runbing, "add",
				new Class[] {}, new Class[] {});
		System.out.println("value:" + value);

		// 使用指定构�?�函�?
		Class[] inArgs = new Class[] { String.class };
		Object[] inArgsParms = new Object[] { "hanj" };
		ReflectHelper runBing2 = (ReflectHelper) ReflectHelper
				.getObjectByConstructor("com.ccit.hj.reflect.RunBing", inArgs,
						inArgsParms);
		value = (String) ReflectHelper.getFileValue(runBing2, "name");
		System.out.println("cc -- value:" + value);
	}
}
