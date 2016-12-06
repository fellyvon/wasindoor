package com.aiyc.framework.utils;

import java.util.Date;

public class UID {
	private static Date date = new Date();
	private static StringBuilder buf = new StringBuilder();
	private static int seq = 0;
	private static final long ROTATION = 0xfffffff;

 

	public static synchronized String next() {
		if (seq > ROTATION)
			seq = 0;
		buf.delete(0, buf.length());
		date.setTime(System.currentTimeMillis());
		String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d",
				date, seq++);
		return str;
	}
 

	public static synchronized int  nextInt() {
	    return UUIDHexGenerator.getInstance().getJVM();
	}
	
	
}
