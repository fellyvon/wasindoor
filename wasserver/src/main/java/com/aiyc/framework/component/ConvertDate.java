package com.aiyc.framework.component;

import java.lang.reflect.Constructor;
import java.sql.Timestamp;
import java.util.Date;
import java.util.StringTokenizer;

public class ConvertDate extends ConvertObject {

	private Object createDate(Long long1) {
		if (dateConstructor != null)
			try {
				return dateConstructor.newInstance(new Object[] { long1 });
			} catch (Exception exception) {
			}
		return null;
	}

	public ConvertDate(Class class1, Object obj) {
		super(class1, obj);
		dateConstructor = null;
		try {
			dateConstructor = class1.getConstructor(new Class[] { Long.TYPE });
		} catch (Exception exception) {
			dateConstructor = null;
		}
	}public static Date StrToDate(String s)
    {
        int k1 = 0;
        int l1 = 0;
        int i2 = 0;
        if(s == null)
           return null;
        int j;
        int l;
        int j1;
        StringTokenizer stringtokenizer;
        try
        {
            char c = s.charAt(4);
            if(c >= '0' && c <= '9')
            {
                int i = parseInt(s, 0, 3) - 1900;
                int k = parseInt(s, 4, 5) - 1;
                int i1 = parseInt(s, 6, 7);
                k1 = parseInt(s, 9, 10);
                l1 = parseInt(s, 12, 13);
                i2 = parseInt(s, 15, 16);
                return new Date(i, k, i1, k1, l1, i2);
            }
        }
        catch( Exception exception1)
        {
            throw new RuntimeException((new StringBuilder()).append("To date error:").append(s).toString());
        }
        
       
        stringtokenizer = new StringTokenizer(s, " \t\n\r\f,-/:.", false);
        j = Integer.parseInt(stringtokenizer.nextToken()) - 1900;
        l = Integer.parseInt(stringtokenizer.nextToken()) - 1;
        j1 = Integer.parseInt(stringtokenizer.nextToken());
        k1 = 0;
        l1 = 0;
        i2 = 0;
        if(stringtokenizer.hasMoreTokens())
        {
            k1 = Integer.parseInt(stringtokenizer.nextToken());
            if(stringtokenizer.hasMoreTokens())
            {
                l1 = Integer.parseInt(stringtokenizer.nextToken());
                if(stringtokenizer.hasMoreTokens())
                    i2 = Integer.parseInt(stringtokenizer.nextToken());
            }
        }
        return new Date(j, l, j1, k1, l1, i2);
       
    }
    static int parseInt(String s, int i, int j)
    {
        int k = 0;
        int l = s.length();
        if(i >= l || j >= l)
            return 0;
        for(int i1 = i; i1 <= j; i1++)
        {
            int j1 = s.charAt(i1) - 48;
            if(j1 >= 0 && j1 <= 9)
                k = k * 10 + j1;
        }

        return k;
    }
	public Object doCoverit(Object obj) throws Exception {
		Date date = null;
		if ( String.class.isInstance(obj))
			date =  StrToDate((String) obj);
		else if ( Date.class.isInstance(obj))
			date = (Date) obj;
		if (date != null)
			return createDate(new Long(date.getTime()));
		else
			return date;
	}

	private Constructor dateConstructor;
}
