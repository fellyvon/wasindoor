package com.aiyc.framework.utils;

public class ByteArrayUtils {

	public ByteArrayUtils() {
	}

	public static int compare(byte abyte0[], byte abyte1[], int i) {
		for (int j = 0; j < i; j++)
			if (abyte0[j] != abyte1[j])
				return abyte1[j] - abyte0[j];

		return 0;
	}

	public static int indexOf(byte abyte0[], byte abyte1[]) {
		return indexOf(abyte0, 0, abyte0.length, abyte1, 0, abyte1.length, 0);
	}

	public static int indexOf(byte abyte0[], byte abyte1[], int i) {
		return indexOf(abyte0, 0, abyte0.length, abyte1, 0, abyte1.length, i);
	}

	static int indexOf(byte abyte0[], int i, int j, byte abyte1[], int k,
			int l, int i1) {
		if (i1 >= j)
			return l != 0 ? -1 : j;
		if (i1 < 0)
			i1 = 0;
		if (l == 0)
			return i1;
		byte byte0 = abyte1[k];
		int j1 = i + i1;
		label0: do {
			int k1;
			for (k1 = i + (j - l); j1 <= k1 && abyte0[j1] != byte0; j1++)
				;
			if (j1 > k1)
				return -1;
			int l1 = j1 + 1;
			int i2 = (l1 + l) - 1;
			int j2 = k + 1;
			do
				if (l1 >= i2)
					break label0;
			while (abyte0[l1++] == abyte1[j2++]);
			j1++;
		} while (true);
		return j1 - i;
	}

	public static int checkSum(byte abyte0[]) {
		int i = 0;
		for (int j = 0; j < abyte0.length; j++)
			i += abyte0[j] & 255;

		return i;
	}
}