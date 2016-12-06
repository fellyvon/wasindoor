package com.aiyc.framework.utils;

import java.util.GregorianCalendar;
import java.util.HashMap;

import com.aiyc.framework.component.BlobField;
import com.aiyc.framework.component.ConvertBlobField;
import com.aiyc.framework.component.ConvertDate;
import com.aiyc.framework.component.ConvertNumber;
import com.aiyc.framework.component.ConvertObject;

public class ConvertUtil {
	protected ConvertUtil() {
		coverMap = new HashMap();
		registerall();
	}

	public static Object CovertObject(Class class1, Object obj) {
		return _instance.Covert(class1, obj);
	}

	public Object Covert(Class class1, Object obj) {
		if (class1.isInstance(obj))
			return obj;
		ConvertObject convertobject = (ConvertObject) coverMap.get(class1);
		if (convertobject != null)
			return convertobject.covert(obj);
		else
			throw new RuntimeException((new StringBuilder())
					.append("cover for").append(class1).append(" not find!!")
					.toString());
	}

	public void register(Class class1, ConvertObject convertobject) {
		coverMap.put(class1, convertobject);
	}

	public void registerall() {
		register(Double.TYPE, new ConvertNumber(Double.TYPE, new Double(0.0D)));
		register(java.lang.Double.class, new ConvertNumber(
				java.lang.Double.class, null));
		register(java.lang.Number.class, new ConvertNumber(
				java.lang.Number.class, null));
		register(Integer.TYPE, new ConvertInteger(Integer.TYPE, new Integer(0)));
		register(java.lang.Integer.class, new ConvertInteger(
				java.lang.Integer.class, null));
		register(Short.TYPE, new ConvertShort(Short.TYPE, new Short((short) 0)));
		register(java.lang.Short.class, new ConvertShort(java.lang.Short.class,
				null));
		register(Byte.TYPE, new ConvertByte(Byte.TYPE, new Byte((byte) 0)));
		register(java.lang.Byte.class, new ConvertByte(java.lang.Byte.class,
				null));
		register(Long.TYPE, new ConvertLong(Long.TYPE, new Long(0L)));
		register(java.lang.Long.class, new ConvertLong(java.lang.Long.class,
				null));
		register(Float.TYPE, new ConvertFloat(Float.TYPE, new Float(0.0F)));
		register(java.lang.Float.class, new ConvertFloat(java.lang.Float.class,
				null));
		register(java.util.Date.class, new ConvertDate(java.util.Date.class,
				null));
		register(java.sql.Date.class,
				new ConvertDate(java.sql.Date.class, null));
		register(java.sql.Time.class,
				new ConvertDate(java.sql.Time.class, null));
		register(java.sql.Timestamp.class, new ConvertDate(
				java.sql.Timestamp.class, null));
		register(BlobField.class, new ConvertBlobField(BlobField.class, null));

		register(java.lang.Boolean.class, new ConvertBoolean(
				java.lang.Boolean.class, null));
		register(Boolean.TYPE, new ConvertBoolean(Boolean.TYPE, Boolean.FALSE));
		register(java.lang.String.class, new ConvertString(
				java.lang.String.class, null));
	}

	private HashMap coverMap;
	private static ConvertUtil _instance = new ConvertUtil();

	class ConvertBoolean extends ConvertObject {

		public ConvertBoolean(Class class1, Object obj) {
			super(class1, obj);
		}

		public Object doCoverit(Object obj) throws Exception {
			if (obj != null)
				return Boolean.valueOf(Boolean.parseBoolean(obj.toString()));
			else
				return null;
		}
	}

	class ConvertString extends ConvertObject {

		public ConvertString(Class class1, Object obj) {
			super(class1, obj);
		}

		public Object doCoverit(Object obj) throws Exception {
			if (java.util.Date.class.isInstance(obj))
				return DateTimeToISOStr((java.util.Date) obj);
			else
				return obj.toString();
		}

		String DateTimeToISOStr(java.util.Date date) {
			return DateTimeToStr(date, 'T');
		}

		String DateTimeToStr(java.util.Date date, char c) {
			if (c == ' ')
				return DateTimeToStr(date, '-', ' ', ':');
			else
				return DateTimeToStr(date, '\0', 'T', ':');
		}

		String DateTimeToStr(java.util.Date date, char c, char c1, char c2) {
			if (date == null)
				return null;
			StringBuffer stringbuffer = new StringBuffer(20);
			GregorianCalendar gregoriancalendar = new GregorianCalendar();
			gregoriancalendar.setTime(date);
			stringbuffer.append(gregoriancalendar.get(1));
			if (c != 0)
				stringbuffer.append(c);
			int i = gregoriancalendar.get(2) + 1;
			appendInt(stringbuffer, i);
			if (c != 0)
				stringbuffer.append(c);
			int j = gregoriancalendar.get(5);
			appendInt(stringbuffer, j);
			int k = gregoriancalendar.get(11);
			int l = gregoriancalendar.get(12);
			int i1 = gregoriancalendar.get(13);
			if (k + l + i1 > 0) {
				if (c1 != 0)
					stringbuffer.append(c1);
				appendInt(stringbuffer, k);
				if (c2 != 0)
					stringbuffer.append(c2);
				appendInt(stringbuffer, l);
				if (c2 != 0)
					stringbuffer.append(c2);
				appendInt(stringbuffer, i1);
			}
			return stringbuffer.toString();
		}

		private void appendInt(StringBuffer stringbuffer, int i) {
			if (i < 10)
				stringbuffer.append("0");
			stringbuffer.append(i);
		}
	}

	class ConvertInteger extends ConvertNumber {

		public ConvertInteger(Class class1, Object obj) {
			super(class1, obj);
		}

		public Object doCoverit(Number number) {
			if (number != null)
				return new Integer(number.intValue());
			else
				return null;
		}
	}

	class ConvertShort extends ConvertNumber {

		public ConvertShort(Class class1, Object obj) {
			super(class1, obj);
		}

		public Object doCoverit(Number number) {
			if (number != null)
				return new Short(number.shortValue());
			else
				return null;
		}
	}

	class ConvertByte extends ConvertNumber {

		public ConvertByte(Class class1, Object obj) {
			super(class1, obj);
		}

		public Object doCoverit(Number number) {
			if (number != null)
				return new Byte(number.byteValue());
			else
				return null;
		}
	}

	class ConvertLong extends ConvertNumber {

		public ConvertLong(Class class1, Object obj) {
			super(class1, obj);
		}

		public Object doCoverit(Number number) {
			if (number != null)
				return new Long(number.longValue());
			else
				return null;
		}
	}

	class ConvertFloat extends ConvertNumber {

		public ConvertFloat(Class class1, Object obj) {
			super(class1, obj);
		}

		public Object doCoverit(Number number) {
			if (number != null)
				return new Float(number.floatValue());
			else
				return null;
		}
	}
}
