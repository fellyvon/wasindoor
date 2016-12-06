package com.aiyc.framework.utils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 * Date Utility Class This is used to convert Strings to Dates and Timestamps
 */
public class DateUtil implements Serializable {

	private static String defaultDatePattern = "yyyy-MM-dd";
	private static String timePattern = "HH:mm";

	// ~ Methods
	// ================================================================

	public static Date StrToDate(String s) {
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		if (s == null)
			return null;
		int j;
		int l;
		int j1;
		StringTokenizer stringtokenizer;
		try {
			char c = s.charAt(4);
			if (c >= '0' && c <= '9') {
				int i = parseInt(s, 0, 3) - 1900;
				int k = parseInt(s, 4, 5) - 1;
				int i1 = parseInt(s, 6, 7);
				k1 = parseInt(s, 9, 10);
				l1 = parseInt(s, 12, 13);
				i2 = parseInt(s, 15, 16);
				return new Date(i, k, i1, k1, l1, i2);
			}
		}

		catch (RuntimeException exception1) {
			throw new RuntimeException((new StringBuilder())
					.append("To date error:").append(s).toString());
		}
		stringtokenizer = new StringTokenizer(s, " \t\n\r\f,-/:.", false);
		j = Integer.parseInt(stringtokenizer.nextToken()) - 1900;
		l = Integer.parseInt(stringtokenizer.nextToken()) - 1;
		j1 = Integer.parseInt(stringtokenizer.nextToken());
		k1 = 0;
		l1 = 0;
		i2 = 0;
		if (stringtokenizer.hasMoreTokens()) {
			k1 = Integer.parseInt(stringtokenizer.nextToken());
			if (stringtokenizer.hasMoreTokens()) {
				l1 = Integer.parseInt(stringtokenizer.nextToken());
				if (stringtokenizer.hasMoreTokens())
					i2 = Integer.parseInt(stringtokenizer.nextToken());
			}
		}
		return new Date(j, l, j1, k1, l1, i2);

	}

	public static String DateTimeToStr(Date date) {
		return DateTimeToStr(date, ' ');
	}

	public static String DateTimeToISOStr(Date date) {
		return DateTimeToStr(date, 'T');
	}

	public static String DateTimeToStr(Date date, char c, char c1, char c2) {
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

	public static String DateTimeToStr(Date date, char c) {
		if (c == ' ')
			return DateTimeToStr(date, '-', ' ', ':');
		else
			return DateTimeToStr(date, '\0', 'T', ':');
	}

	static int parseInt(String s, int i, int j) {
		int k = 0;
		int l = s.length();
		if (i >= l || j >= l)
			return 0;
		for (int i1 = i; i1 <= j; i1++) {
			int j1 = s.charAt(i1) - 48;
			if (j1 >= 0 && j1 <= 9)
				k = k * 10 + j1;
		}

		return k;
	}

	private static void appendInt(StringBuffer stringbuffer, int i) {
		if (i < 10)
			stringbuffer.append("0");
		stringbuffer.append(i);
	}

	/**
	 * Return default datePattern (yyyy-MM-dd)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static synchronized String getDatePattern() {
		return defaultDatePattern;
	}

	public static String getDateTimePattern() {
		return DateUtil.getDatePattern() + " HH:mm:ss";
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		df = new SimpleDateFormat(aMask);
		returnValue = df.format(aDate);

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * 
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate)
			throws ParseException {
		Date aDate = null;

		try {

			aDate = convertStringToDate(getDatePattern(), strDate);
		} catch (ParseException pe) {

			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return aDate;
	}

}
