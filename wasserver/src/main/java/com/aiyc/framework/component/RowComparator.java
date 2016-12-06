package com.aiyc.framework.component;

import java.util.Comparator;

import com.aiyc.framework.utils.StringUtils;


public class RowComparator
    implements Comparator
{

    public void setNullIsBigger(boolean flag)
    {
        nullIsBigger = flag;
    }

    public boolean isNullIsBigger()
    {
        return nullIsBigger;
    }

    public RowComparator(int i, boolean flag)
    {
        nullIsBigger = true;
        byte byte0;
        if(flag)
            byte0 = 2;
        else
            byte0 = 1;
        compareColumns = (new CompareItem[] {
            new CompareItem(i, byte0)
        });
    }

    public RowComparator(CompareItem acompareitem[])
    {
        nullIsBigger = true;
        compareColumns = acompareitem;
    }

    public int compare(Object obj, Object obj1)
        throws ClassCastException
    {
        CachedRow cachedrow = (CachedRow)obj;
        CachedRow cachedrow1 = (CachedRow)obj1;
        for(int i = 0; i < compareColumns.length; i++)
        {
            CompareItem compareitem = compareColumns[i];
            if(compareitem == null)
                continue;
            int j = compareitem.getDirection();
            int k = 0;
            try
            {
                Object obj2 = compareitem.getColumnData(cachedrow);
                Object obj3 = compareitem.getColumnData(cachedrow1);
                if(obj3 == null)
                {
                    if(obj2 != null)
                        if(nullIsBigger)
                            k = -1;
                        else
                            k = 1;
                } else
                if(obj2 == null)
                {
                    if(nullIsBigger)
                        k = 1;
                    else
                        k = -1;
                } else
                if( String.class.isInstance(obj2))
                    k = StringUtils.compareString(obj2.toString(), obj3.toString(), "GBK");
                else
                    k = ((Comparable)obj2).compareTo(obj3);
            }
            catch(Exception exception)
            {
                throw new RuntimeException("Data compare error!", exception);
            }
            if(k == 0)
                continue;
            if(j == 2)
                k *= -1;
            return k;
        }

        return 0;
    }

    private boolean nullIsBigger;
    private CompareItem compareColumns[];
}
