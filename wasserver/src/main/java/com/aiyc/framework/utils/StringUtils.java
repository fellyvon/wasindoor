package com.aiyc.framework.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 常用字符串操作函数
 */
public class StringUtils {
	// ~ Static fields/initializers
	// =============================================
	public static int compareString(String s, String s1, String s2) {
		if (s1 == null)
			return s == null ? 0 : 1;
		if (s == null)
			return -1;
		byte abyte0[];
		byte abyte1[];
		int i;
		int j;
		int l;
		int i1;
		try {
			abyte0 = s.getBytes(s2);
			abyte1 = s1.getBytes(s2);
			i = abyte0.length;
			j = abyte1.length;
			int k = Math.min(i, j);
			l = 0;
			i1 = k;
			while (l < i1) {

				int j1 = abyte0[l] & 255;
				int k1 = abyte1[l] & 255;
				if (j1 != k1)
					return j1 - k1;
				l++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return i - j;

	}

	// ~ Methods
	// ================================================================

	/**
	 * Encode a string using algorithm specified in web.xml and return the
	 * resulting encrypted password. If exception, the plain credentials string
	 * is returned
	 * 
	 * @param password
	 *            Password or other credentials to use in authenticating this
	 *            username
	 * @param algorithm
	 *            Algorithm used to do the digest
	 * 
	 * @return encypted password based on the algorithm.
	 */
	public static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {

			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}

	/**
	 * Encode a string using Base64 encoding. Used when storing passwords as
	 * cookies.
	 * 
	 * This is weak encoding in that anyone can use the decodeString routine to
	 * reverse the encoding.
	 * 
	 * @param str
	 * @return String
	 */
	public static String encodeString(String str) {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		return encoder.encodeBuffer(str.getBytes()).trim();
	}

	/**
	 * Decode a string using Base64 encoding.
	 * 
	 * @param str
	 * @return String
	 */
	public static String decodeString(String str) {
		sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
		try {
			return new String(dec.decodeBuffer(str));
		} catch (IOException io) {
			throw new RuntimeException(io.getMessage(), io.getCause());
		}
	}

	private static MessageDigest digest = null;

	public static boolean strEquals(String s, String s1) {
		if (s != null && s1 != null)
			return s.equals(s1);
		else
			return s == s1;
	}

	public static String join(Object aobj[], String s) {
		return join(Arrays.asList(aobj).iterator(), s);
	}

	public static String join(Iterator iterator, String s) {
		StringBuffer stringbuffer = new StringBuffer();
		do {
			if (!iterator.hasNext())
				break;
			stringbuffer.append(iterator.next());
			if (iterator.hasNext())
				stringbuffer.append(s);
		} while (true);
		return stringbuffer.toString();
	}

	public static synchronized String formatChineseString(String s) {
		String s1 = s;
		if (s1 == null) {
			return s1;
		} else {
			s1 = replace(s1, "\uFF10", "0");
			s1 = replace(s1, "\uFF11", "1");
			s1 = replace(s1, "\uFF12", "2");
			s1 = replace(s1, "\uFF13", "3");
			s1 = replace(s1, "\uFF14", "4");
			s1 = replace(s1, "\uFF15", "5");
			s1 = replace(s1, "\uFF16", "6");
			s1 = replace(s1, "\uFF17", "7");
			s1 = replace(s1, "\uFF18", "8");
			s1 = replace(s1, "\uFF19", "9");
			s1 = replace(s1, "\uFF03", "#");
			return s1;
		}
	}

	public static String trimToByteSize(String s, String s1, int i) {
		if (s != null) {
			int j = 0;
			byte byte0 = 2;
			int k = s.length();
			if (s1.charAt(0) == 'U')
				byte0 = 3;
			for (int l = 0; l < k; l++) {
				if (j > i)
					return s.substring(0, l - 1);
				if ((s.charAt(l) & -256) != 0)
					j += byte0;
				else
					j++;
			}

		}
		return s;
	}

	public static boolean objectEquals(Object obj, Object obj1) {
		if (obj != null && obj1 != null)
			return obj.equals(obj1);
		else
			return obj == null && obj1 == null;
	}

	/**
	 * 合并字符串数组为一个串
	 * 
	 * @param src
	 *            String[] 字符串数组
	 * @param delimiter
	 *            隔开字符
	 * @return String
	 */
	public static String merge(String[] src, String delimiter) {
		StringBuffer newSrc = new StringBuffer();
		for (int i = 0; i < src.length; i++) {
			if (i < src.length - 1) {
				newSrc.append(src[i]).append(delimiter);
			} else {
				newSrc.append(src[i]);
			}
		}
		return newSrc.toString();
	}

	public static String defaultString(String ab) {
		return ab;
	}

	public static String[] split(String ext) {
		return split(ext, ',');
	}

	public static String[] split(String ext, char pix) {
		return ext.split(String.valueOf(pix));
	}

	public static boolean isNullOrBank(String ext) {
		if (ext == null || ext.equals("")) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String ext) {
		return isNullOrBank(ext);
	}

	/**
	 * 分解字符串
	 * 
	 * @param str
	 *            String
	 * @param sect
	 *            int 分解的段数
	 * @param len
	 *            int 每段的字符长度
	 * @throws Exception
	 * @return String[]
	 */
	static public String[] split(String str, int sect, int len)
			throws Exception {
		String[] result = new String[sect];
		int j = 0;
		for (j = 0; j < sect; j++) {
			result[j] = "";
		}
		for (j = 0; j < sect; j++) {
			if (str.length() < len * j) {
				break;
			} else if (str.length() < len * (j + 1)) {
				result[j] = str.substring(len * j, str.length());
			} else {
				result[j] = str.substring(len * j, len * (j + 1));
			}
		}
		return result;
	}

	/**
	 * 对整个url进行编码转换
	 * 
	 * @param srcStr
	 *            url串
	 * @param encoding
	 *            编码
	 * @return String
	 */
	public static String URLEncode(String srcStr, String encoding) {
		String[] arrayUrl = srcStr.split("?");
		if (arrayUrl.length <= 1) {
			return srcStr;
		}
		String qryStr = arrayUrl[1];
		String[] arrayQryStr = qryStr.split("&");
		StringBuffer newQryStr = new StringBuffer(120);
		StringBuffer tmp = new StringBuffer(20);
		String param;
		for (int i = 0; i < arrayQryStr.length; i++) {
			param = arrayQryStr[i];
			String[] arrayParam = param.split("=");
			if (arrayParam.length > 1) {
				try {
					arrayParam[1] = URLEncoder.encode(arrayParam[1], encoding);
				} catch (Exception e) {
					e.printStackTrace();
				}
				tmp.append(arrayParam[0]).append("=").append(arrayParam[1]);
				arrayQryStr[i] = tmp.toString();
			} else {
				tmp.append(arrayParam[0]).append("=");
				arrayQryStr[i] = tmp.toString();
			}
			newQryStr.append(arrayQryStr[i]).append("&");
		}
		tmp = new StringBuffer(150);
		tmp.append(arrayUrl[0]).append("?").append(newQryStr.toString());
		return tmp.toString();
	}

	/**
	 * 用 newString 替换 line 中的所有的 OldString。 不支持正则表达式
	 * 
	 * @param line
	 *            原字符串
	 * @param oldString
	 *            被替换的字符串
	 * @param newString
	 *            新的要替换oldString的字符串
	 * @return 返回所有oldString都被newString替换的字符串
	 */
	public static final String replace(String line, String oldString,
			String newString) {
		// 如果line是null，直接返回
		if (line == null) {
			return null;
		}
		int i = 0;
		// 如果在line中确实存在oldString那么将进行以下的替换
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		// 如果在line中没有oldString 返回line
		return line;
	}

	/**
	 * 用 newString 替换 line 中的所有的 OldString count返回被替换的数目
	 * 
	 * @param line
	 *            原字符串
	 * @param oldString
	 *            被替换的字符串
	 * @param newString
	 *            新的要替换oldString的字符串
	 * @return 返回所有oldString都被newString替换的字符串
	 */
	public static final String replace(String line, String oldString,
			String newString, int[] count) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			int counter = 0;
			counter++;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/**
	 * 做不区分大小写的模式匹配，并用newString 来替换 oldString
	 * 
	 * @param line
	 *            原字符串
	 * @param oldString
	 *            被替换的字符串
	 * @param newString
	 *            新的要替换oldString的字符串
	 * @return 返回所有oldString都被newString替换的字符串
	 */
	public static final String replaceIgnoreCase(String line, String oldString,
			String newString) {
		// 如果line是null，直接返回
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * 从数组中得到字符串的位置
	 * 
	 * @param s
	 *            要查询的字符串
	 * @param args
	 *            待查的数组
	 * @return s的位置，没有找到就是-1
	 */
	public static int getStrIndex(String s, String args[]) {
		int length = args.length;
		for (int i = 0; i < length; i++) {
			if (args[i].equals(s)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 判断 如果输入的字符为null或者'null',输出空字符串""
	 * 
	 * @param src
	 * @return
	 */
	public static String nullToEmpty(String src) {
		if (src == null || src.equalsIgnoreCase("NULL"))
			return "";
		return src;
	}

	/**
	 * 将下划线连接的String替换为驼峰风格
	 * 
	 * @param s
	 * @return
	 */
	public static String toCamelCasing(String s) {
		if (s == null) {
			return s;
		}

		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < s.length() - 1; i++) {
			char ch = s.charAt(i);
			if (ch != '_') {
				buffer.append(ch);
			} else {
				char nextChar = s.charAt(i + 1);
				if (nextChar != '_') {
					if (buffer.toString().length() < 2) {
						buffer.append(Character.toLowerCase(nextChar));
					} else {
						buffer.append(Character.toUpperCase(nextChar));
					}
					i++;
				}
			}
		}
		char lastChar = s.charAt(s.length() - 1);
		if (lastChar != '_') {
			buffer.append(lastChar);
		}

		return buffer.toString();
	}

	/**
	 * 将首字母小写
	 * 
	 * @param s
	 * @return
	 */
	public static String lowerFirstChar(String s) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(Character.toLowerCase(s.charAt(0)))
				.append(s.substring(1));

		return buffer.toString();
	}

	/**
	 * 将首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public static String upperFirstChar(String s) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(Character.toUpperCase(s.charAt(0)))
				.append(s.substring(1));

		return buffer.toString();
	}
}
