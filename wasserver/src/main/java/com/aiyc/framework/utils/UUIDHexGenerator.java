package com.aiyc.framework.utils;

import java.net.InetAddress;

public class UUIDHexGenerator {

	private String sep = "";
	private static final int IP;
	private static short counter = (short) 0;
	private static final int JVM = (int) (System.currentTimeMillis() >>> 8); // 取得jvm
																				// 启动时的当前毫秒数。无符号右移8位

	private static UUIDHexGenerator uuidgen = new UUIDHexGenerator();

	static {
		int ipadd;
		try {
			ipadd = toInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd; // 获取客户端的ip。
	}

	public static UUIDHexGenerator getInstance() {
		return uuidgen;
	}

	// 字节转化成int型
	public static int toInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	// 整形的数字进行16进行化，然后在进行字符串转化
	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	// 短整形的数字进行16进行化，然后在进行字符串转化
	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	protected int getJVM() {
		return JVM;
	}

	// 获取计数器数值
	protected synchronized short getCount() {
		if (counter < 0) {
			counter = 0;
		}
		return counter++;
	}

	protected int getIP() {
		return IP;
	}

	// 获取当前时间毫秒数短整形
	protected short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	// 获取当前时间毫秒数长整型
	protected int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	// 获的各字符串相加组合。
	public String generate() {
		return new StringBuffer(36).append(format(getIP())).append(sep).append(
				format(getJVM())).append(sep).append(format(getHiTime()))
				.append(sep).append(format(getLoTime())).append(sep).append(
						format(getCount())).toString();
	}
}
