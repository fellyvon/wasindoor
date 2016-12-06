package com.aiyc.base.util;

import com.aiyc.base.util.FixedPointLong;
import com.aiyc.base.util.FixedPointLongException;

/**
 * Implements fixed-point numbers in the long primitive type
 * 
 * 
 * @version 0.1
 */
public final class FixedPointLong {

	private static final int OFFSET = 32;
	private static final int LENGTH = 63;

	/**
	 * make a flong from an int, but without checking for overflow -- use this
	 * only on constants
	 */
	public static final long intToFlongSafe(int i) {
		return ((long) i) << OFFSET;
	}

	/**
	 * make a flong from an integer
	 * 
	 * @throws FixedPointLongException
	 *             if an overflow occurs
	 */
	public static final long intToFlong(int i) throws FixedPointLongException {
		long ans = ((long) i) << OFFSET;
		if (ans >> OFFSET != i)
			throw new FixedPointLongException("intToFlong: number too large: "
					+ i);
		return ans;
	}

	/**
	 * make a flong from a string. E-notation numbers (e.g. -783.23E-6) are
	 * understood.
	 * 
	 * @throws FixedPointLongException
	 *             if the string is not parseable, or if overflow occurs
	 */
	public static final long stringToFlong(String s)
			throws FixedPointLongException {
		try {
			boolean negative = false;
			final int maxAfterPointLen = ("" + (1L << (OFFSET))).length();
			/*
			 * ("" + (1L << (LENGTH - 4))).length();
			 */
			if (s == null || s.equals(""))
				return 0;

			// split into integer and fractional parts
			int pointIndex = s.indexOf('.');
			int eIndex = s.indexOf('e');
			if (eIndex == -1)
				eIndex = s.indexOf('E');
			if (eIndex > 0 && pointIndex > 0 && eIndex <= pointIndex) {
				throw new FixedPointLongException(
						"stringToFlong: incorrect number format: \"" + s + "\"");
			}
			String beforePoint, afterPoint, eString;
			if (pointIndex < 0) {
				afterPoint = "0";
				if (eIndex < 0) {
					beforePoint = s;
					eString = "0";
				} else {
					beforePoint = s.substring(0, eIndex);
					eString = s.substring(eIndex + 1, s.length());
				}
			} else {
				beforePoint = s.substring(0, pointIndex);
				if (eIndex < 0) {
					afterPoint = s.substring(pointIndex + 1, s.length());
					eString = "0";
				} else {
					afterPoint = s.substring(pointIndex + 1, eIndex);
					eString = s.substring(eIndex + 1, s.length());
				}
			}

			// parse and check integer part
			if (beforePoint.charAt(0) == '-')
				negative = true;
			long intpart;
			try {
				intpart = Long.parseLong(beforePoint);
			} catch (NumberFormatException nfe) {
				throw new FixedPointLongException(
						"stringToFlong: cannot parse intpart of \"" + s + "\"");
			}
			if (negative)
				intpart = -intpart; // make positive for now
			if (bitsUsed(intpart) > LENGTH - OFFSET)
				throw new FixedPointLongException(
						"stringToFlong: number too large: \"" + s
								+ "\" : bitsUsed " + bitsUsed(intpart));

			// parse and check fractional part
			if (afterPoint.charAt(0) == '-')
				throw new FixedPointLongException(
						"stringToFlong: bad number format: \"" + s + "\"");
			if (afterPoint.length() > maxAfterPointLen) {

				/* commented out to remove warning */
				// String ap = afterPoint;
				afterPoint = afterPoint.substring(0, maxAfterPointLen);
			}
			long fracpart;
			try {
				fracpart = Long.parseLong(afterPoint);
			} catch (NumberFormatException nfe) {
				throw new FixedPointLongException(
						"stringToFlong: cannot parse fracpart of \"" + s
								+ "\": " + nfe);
			}
			if (fracpart != 0) {
				int oldbitsused = bitsUsed(fracpart);
				fracpart = fracpart << LENGTH - oldbitsused;
				for (int i = 0; i < afterPoint.length() - 1; i++) {
					fracpart /= 10;
				}
				fracpart = fracpart / 10 + (fracpart % 10 >= 5 ? 1 : 0);
				int shiftback = LENGTH - OFFSET - oldbitsused;
				if (shiftback > 0) {
					fracpart = fracpart >> shiftback - 1;
					fracpart = (fracpart + (fracpart & 1)) >> 1; // rounding
				} else {
					fracpart = fracpart << -shiftback;
				}
				if (bitsUsed(fracpart) > OFFSET + 1)
					throw new FixedPointLongException(
							"stringToFlong: error parsing " + s + " bitsused "
									+ bitsUsed(fracpart));
			}
			// output
			long result = ((intpart << OFFSET) + fracpart);

			// now do offset
			int shift;
			try {
				shift = Integer.parseInt(eString);
			} catch (NumberFormatException nfe) {
				throw new FixedPointLongException(
						"stringToFlong: cannot parse epart of \"" + s + "\"");
			}
			while (shift > 0) {
				long oldresult = result;
				result = result * 10;
				if (result / 10 != oldresult)
					throw new FixedPointLongException(
							"stringToFlong: overflow applying E shift when parsing \""
									+ s + "\"");
				shift--;
			}
			while (shift < 0) {
				result = result / 10;
				shift++;
			}

			result = negative ? -result : result;
			// System.out.println("Flong representation of " + s + " is " +
			// result + ", intpart " + intValue(result) + ", flongToString " +
			// flongToString(result));
			return result;
		} catch (Throwable t) {
			throw new FixedPointLongException(
					"FixedPointLong.stringToFlong: err "
							+ t.getClass().getName() + ":" + t.getMessage());
		}
	}

	private static final int bitsUsed(long x) {
		if (x < 0)
			x = -x;

		for (int i = LENGTH - 1; i >= 0; i--) {
			if (((x >> i) & 1L) == 1)
				return i + 1;
		}
		return 0;
	}

	/** Makes a string from a flong, to the full precision available */
	public static final String flongToString(long l)
			throws FixedPointLongException {
		return flongToString(l, -1);
	}

	/**
	 * Makes a string from a flong, with precisely the specified number of
	 * decimal places
	 */
	public static final String flongToString(long l, int dp)
			throws FixedPointLongException {
		try {
			boolean negative = l < 0;
			if (negative)
				l = -l;
			long intpart = l >> OFFSET;
			long fracpart = ((l & ((1L << OFFSET) - 1)));

			StringBuffer flongToStringBuf = new StringBuffer();

			if (fracpart != 0L) {
				int power10 = 0;
				while (bitsUsed(fracpart) < LENGTH - 3) {
					fracpart *= 10L;
					power10++;
				}
				fracpart = fracpart >> OFFSET - 1;
				fracpart = (fracpart >> 1) + (fracpart & 1); // rounding
				if (dp > -1) {
					while (power10 > dp + 1) {

						fracpart = fracpart / 10;
						power10--;
					}
					if (power10 > dp) {
						fracpart = (fracpart / 10)
								+ ((fracpart % 10 > 5) ? 1 : 0);
						power10--;
					}
				}
				flongToStringBuf.append(fracpart);
				while (flongToStringBuf.length() < power10)
					flongToStringBuf.insert(0, '0');
				if (flongToStringBuf.length() > power10) {
					// This is overkill
					// char[] cs = new char[afterPointBuf.length()-power10];
					// afterPointBuf.getChars(0,afterPointBuf.length()-power10,cs,0);
					// long carry=Long.parseLong(new String(cs));
					// System.out.println("afterPointBuf.length " +
					// afterPointBuf.length() + " >power10 " + power10 + " : " +
					// afterPointBuf + " : intpart " + intpart + ", carry " +
					// carry);
					// intpart += carry;
					// afterPointBuf.delete(0,afterPointBuf.length()-power10);

					intpart += 1;
					flongToStringBuf.deleteCharAt(0);
				}
				if (dp == -1) {
					int newlength = flongToStringBuf.length();
					while (newlength > 0
							&& flongToStringBuf.charAt(newlength - 1) == '0')
						newlength--;
					flongToStringBuf.setLength(newlength);
				} else {
					while (flongToStringBuf.length() < dp) {
						flongToStringBuf.append('0');
					}
				}
				if (flongToStringBuf.length() > 0) {
					flongToStringBuf.insert(0, '.');
				}
			}
			flongToStringBuf.insert(0, intpart);
			if (negative)
				flongToStringBuf.insert(0, '-');

			// System.out.println("String rep of flong " + l + " (int " +
			// intValue(l) + ") is " + flongToStringBuf);
			return flongToStringBuf.toString();
		} catch (Exception e) {
			throw new FixedPointLongException("flongToString Exception: " + e);
		}

	}

	/** returns a flong representing the integer portion of the parameter flong */
	public static final long intPart(long flong) {
		return (flong >> OFFSET) << OFFSET;
	}

	/**
	 * returns a flong representing the fractional portion of the parameter
	 * flong
	 */
	public static final long fracPart(long flong) {
		return flong - intPart(flong);
	}

	/** returns the integer value, after rounding, of the flong */
	public static final int intValue(long flong) {
		return (int) (flong >> OFFSET);
	}

	/*
	 * program used to generate the table below public static void main(String[]
	 * args) { System.out.print("static final long costable[] = new long[]{");
	 * for(int i=0;i<=89;i++) {
	 * System.out.print(FixedPointLong.stringToFlong(""+
	 * Math.cos(Math.toRadians(i))) + "L,"); } System.out.println("0L};"); }
	 */

	static final long costable[] = new long[] { 4294967296L, 4294967296L,
			4294967296L, 4286578688L, 4286578688L, 4278190080L, 4269801472L,
			4262985728L, 4253024256L, 4244635648L, 4229955584L, 4215799808L,
			4202692608L, 4185915392L, 4169138176L, 4152360960L, 4127195136L,
			4110417920L, 4085252096L, 4060086272L, 4034920448L, 4009754624L,
			3984588800L, 3951034368L, 3925868544L, 3892314112L, 3860332544L,
			3825205248L, 3792175104L, 3758096384L, 3720347648L, 3682598912L,
			3642228736L, 3601858560L, 3560964096L, 3519021056L, 3472883712L,
			3430940672L, 3384803328L, 3338665984L, 3289907200L, 3241672704L,
			3191865344L, 3141533696L, 3091202048L, 3036676096L, 2982150144L,
			2927624192L, 2873098240L, 2818572288L, 2759852032L, 2701131776L,
			2642411520L, 2583691264L, 2524971008L, 2463629312L, 2403336192L,
			2340421632L, 2277507072L, 2210398208L, 2147483648L, 2080374784L,
			2013265920L, 1946157056L, 1879048192L, 1811939328L, 1746927616L,
			1677721600L, 1608515584L, 1543503872L, 1468006400L, 1392508928L,
			1325400064L, 1258291200L, 1191182336L, 1107296256L, 1040187392L,
			973078528L, 889192448L, 822083584L, 746586112L, 671088640L,
			595591168L, 520093696L, 452984832L, 373293056L, 297795584L,
			234881024L, 148897792L, 74973184L, 0L };

	/** returns the cosine of a flong, as a flong */
	public static final long cos(long degreesFlong) {
		return cos(intValue(degreesFlong));
	}

	private static final long cos(int degrees) {
		if (degrees < 0)
			return cos(-degrees);
		if (degrees > 90)
			return -cos(degrees - 180);
		// System.out.println("cos of " + degrees + " is " +
		// costable[(int)degrees]);
		return costable[(int) degrees];
	}

	/**
	 * returns a flong representing the division of the parameter flongs
	 * 
	 * @throws FixedPointLongException
	 *             if a divide-by-zero occurs
	 */
	public static final long div(long topFlong, long bottomFlong)
			throws FixedPointLongException {
		int shift = OFFSET;
		if (bottomFlong == 0)
			throw new FixedPointLongException("Divide by zero");
		if (topFlong == 0)
			return 0;
		while (shift > 0 && (topFlong & (1L << LENGTH - 1)) == 0) {
			shift--;
			topFlong <<= 1;
		}
		while (shift > 0 && (bottomFlong & 1L) == 0) {
			shift--;
			bottomFlong = (bottomFlong >> 1) + (bottomFlong & 1L);
		}
		return (topFlong / bottomFlong) << shift;
	}

	/**
	 * Returns a flong representing the multiple of the two parameter flongs
	 * 
	 * @throws FixedPointLongException
	 *             if overflow occurs
	 */
	public static long mult(long flongOne, long flongTwo)
			throws FixedPointLongException {
		boolean negative = false;
		long a = flongOne;
		long b = flongTwo;
		if (flongOne < 0) {
			a = -a;
			negative = !negative;
		}
		if (flongTwo < 0) {
			b = -b;
			negative = !negative;
		}
		long intmult = (a >> OFFSET) * (b >> OFFSET);
		if (intmult >> OFFSET != 0) {
			throw new FixedPointLongException("Numbers too large to multiply: "
					+ flongToString(flongOne) + ", " + flongToString(flongTwo));
		}
		long fracmult1 = (a >> OFFSET) * fracPart(b);
		long fracmult2 = (b >> OFFSET) * fracPart(a);
		long fracmult3 = (fracPart(a) >> 1) * (fracPart(b) >> 1);
		// System.err.println("intmult " + intmult + " fracmult1 " + fracmult1 +
		// " fracmult2 " + fracmult2 + " fracmult3 " + fracmult3);
		// System.err.println("fracpart1 " + fracPart(a) + " fracpart2 " +
		// fracPart(b) + " fracmult " + fracmult3);
		long ret = intToFlong((int) intmult) + fracmult1 + fracmult2
				+ (fracmult3 >> (OFFSET - 2));
		if (ret < 0)
			throw new FixedPointLongException(
					"Numbers are too large to multiply: "
							+ flongToString(flongOne) + ", "
							+ flongToString(flongTwo));
		if (negative)
			ret = -ret;
		// System.err.println("" + flongToString(flongOne) + " times " +
		// flongToString(flongTwo) + " is " + flongToString(ret));
		return ret;
	}

	/**
	 * returns the flong representing the square of the parameter flong
	 * 
	 * @throws FixedPointLongException
	 *             if overflow occurs
	 */
	public static long square(long flong) throws FixedPointLongException {
		long ret = mult(flong, flong);
		// if(ret < 0) System.err.println("error! square of " +
		// flongToString(flong) + " is negative: " + flongToString(ret));
		return ret;
	}

	/**
	 * returns the flong representing the positive square root of a flong
	 * 
	 * @throws FixedPointLongException
	 *             if a negative number is passed in
	 */
	public static long sqrt(long flong) throws FixedPointLongException {
		if (flong < 0)
			throw new FixedPointLongException(
					"FixedPointLong: sqrt of -ve number!: "
							+ FixedPointLong.flongToString(flong));
		if (flong == 0)
			return 0;
		long oldans = 0;
		long ans = 1L << OFFSET;
		int i = 0;
		while (oldans != ans) {
			i++;
			// System.out.println("sqrtdebug: " + flongToString(oldans) + " " +
			// flongToString(ans));
			// dirty hack to enforce return
			if (i > 100)
				return ans;
			oldans = ans;
			ans = (oldans + div(flong, oldans)) >> 1;
		}
		return ans;
	}

	/**
	 * returns a flong representing sqrt(x*x+y*y), where x and y are the
	 * parameter flongs. Use this rather than doing it yourself because this
	 * won't overflow with numbers who's square is above the representable
	 * limit.
	 */
	public static long pythagoras(long x, long y)
			throws FixedPointLongException {
		int bitShift = (bitsUsed(x) + bitsUsed(y) + 1 - LENGTH) / 2;
		if (bitShift < 0)
			bitShift = 0;
		// System.err.println("bitsUsed(x) = " + bitsUsed(x) + " bitsUsed(y) = "
		// + bitsUsed(y) + " bitShift is " + bitShift);

		x >>= bitShift;
		y >>= bitShift;

		/* commented out to remove warning */
		// long sumsquares = square(x) + square(y);
		long ret = sqrt(square(x) + square(y)) << bitShift;
		// System.err.println("pythag of " + flongToString(xtemp) + " (bu " +
		// bitsUsed(xtemp) + ") and " + flongToString(ytemp) + " (bu " +
		// bitsUsed(ytemp) + ") is " + flongToString(ret) + " (bitShift " +
		// bitShift + ")");
		return ret;
	}

	/**
	 * rounded to the nearest integer
	 */
	public static long intValueRounded(long flong) {
		// 0x80000000 is "flong" for 0.5
		return intValue(flong + 0x80000000L);
	}
}
