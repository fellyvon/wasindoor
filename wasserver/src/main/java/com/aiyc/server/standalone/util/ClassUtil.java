package com.aiyc.server.standalone.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
/**
 * class
 * 加载处理
 *@author felly
 *@date Jul 13, 2015
 *
 */
public class ClassUtil {
	/**
	 * ��ȡĳ���£���(�ð�������Ӱ�������
	 * 
	 * @param packageName
	 *            ����
	 * @return ����������
	 */
	public static List<String> getClassName(String packageName,
			URLClassLoader loader) throws Exception {
		return getClassName(packageName, loader, true);
	}

	/**
	 * ��ȡĳ����������
	 * 
	 * @param packageName
	 *            ����
	 * @param childPackage
	 *            �Ƿ�����Ӱ�
	 * @return ����������
	 */
	public static List<String> getClassName(String packageName,
			URLClassLoader loader, boolean childPackage) throws Exception {
		List<String> fileNames = null;

		String packagePath = packageName.replace(".", "/");

		fileNames = getClassNameByJars(packageName, loader.getURLs(),
				packagePath, childPackage);

		return fileNames;
	}

	/**
	 * ����Ŀ�ļ���ȡĳ����������
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * @param className
	 *            �����
	 * @param childPackage
	 *            �Ƿ�����Ӱ�
	 * @return ����������
	 */
	private static List<String> getClassNameByFile(String packName,
			String filePath, List<String> className, boolean childPackage) {
		List<String> myClassName = new ArrayList<String>();
		File file = new File(filePath);
		File[] childFiles = file.listFiles();
		for (File childFile : childFiles) {
			if (childFile.isDirectory()) {
				if (childPackage) {
					myClassName.addAll(getClassNameByFile(packName, childFile
							.getPath(), myClassName, childPackage));
				}
			} else {
				String childFilePath = childFile.getPath();
				if (childFilePath.endsWith(".class")) {
					childFilePath = childFilePath.substring(childFilePath
							.indexOf("\\classes") + 9, childFilePath
							.lastIndexOf("."));
					childFilePath = childFilePath.replace("\\", ".");
					childFilePath = childFilePath.substring(childFilePath
							.indexOf(packName));
					myClassName.add(childFilePath);
				}
			}
		}

		return myClassName;
	}

	/**
	 * ��jar��ȡĳ����������
	 * 
	 * @param jarPath
	 *            jar�ļ�·��
	 * @param childPackage
	 *            �Ƿ�����Ӱ�
	 * @return ����������
	 */
	private static List<String> getClassNameByJar(String packName,
			String jarPath, boolean childPackage) {
		List<String> myClassName = new ArrayList<String>();
		String[] jarInfo = jarPath.split("!");
		String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
		String packagePath = jarInfo[1].substring(1);
		try {
			JarFile jarFile = new JarFile(jarFilePath);
			Enumeration<JarEntry> entrys = jarFile.entries();
			while (entrys.hasMoreElements()) {
				JarEntry jarEntry = entrys.nextElement();
				String entryName = jarEntry.getName();
				if (entryName.endsWith(".class")) {
					if (childPackage) {
						if (entryName.startsWith(packagePath)) {
							entryName = entryName.replace("/", ".").substring(
									0, entryName.lastIndexOf("."));
							entryName = entryName.substring(entryName
									.indexOf(packName));
							myClassName.add(entryName);
						}
					} else {
						int index = entryName.lastIndexOf("/");
						String myPackagePath;
						if (index != -1) {
							myPackagePath = entryName.substring(0, index);
						} else {
							myPackagePath = entryName;
						}
						if (myPackagePath.equals(packagePath)) {
							entryName = entryName.replace("/", ".").substring(
									0, entryName.lastIndexOf("."));
							entryName = entryName.substring(entryName
									.indexOf(packName));
							myClassName.add(entryName);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myClassName;
	}

	/**
	 * ������jar������ð��ȡ�ð���������
	 * 
	 * @param urls
	 *            URL����
	 * @param packagePath
	 *            ��·��
	 * @param childPackage
	 *            �Ƿ�����Ӱ�
	 * @return ����������
	 */
	private static List<String> getClassNameByJars(String packNam, URL[] urls,
			String packagePath, boolean childPackage) {
		List<String> myClassName = new ArrayList<String>();
		if (urls != null) {
			for (int i = 0; i < urls.length; i++) {
				URL url = urls[i];
				String urlPath = url.getPath();
				// ��������classes�ļ���
				if (urlPath.endsWith("classes/")) {
					continue;
				}
				String jarPath = urlPath + "!/" + packagePath;
				myClassName.addAll(getClassNameByJar(packNam, jarPath,
						childPackage));
			}
		}
		return myClassName;
	}

}
