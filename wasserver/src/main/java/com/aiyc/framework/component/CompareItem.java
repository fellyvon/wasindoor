package com.aiyc.framework.component;

import com.aiyc.framework.utils.PropertyFieldUtils;


public class CompareItem
{

    public void setCompareClass(Class class1)
    {
        compareClass = class1;
    }

    public void setDefaultValue(Object obj)
    {
        defaultValue = obj;
    }

    public CompareItem()
    {
        compareClass = null;
        defaultValue = null;
        direction = 1;
    }

    public CompareItem(int i, int j)
    {
        compareClass = null;
        defaultValue = null;
        column = i;
        direction = j;
    }

    public int getColumn()
    {
        return column;
    }

    public Object getColumnData(CachedRow cachedrow)
    {
        Object obj = defaultValue;
        if(cachedrow != null)
            obj = cachedrow.getColumn(column);
        if(compareClass != null && obj != null && !compareClass.isInstance(obj))
            try
            {
                obj = PropertyFieldUtils.coverTo(obj, compareClass);
            }
            catch(Exception exception)
            {
                obj = null;
            }
        return obj;
    }

    public int getDirection()
    {
        return direction;
    }

    public Class getCompareClass()
    {
        return compareClass;
    }

    public Object getDefaultValue()
    {
        return defaultValue;
    }

    public static final int ASC = 1;
    public static final int DESC = 2;
    private int column;
    private int direction;
    private Class compareClass;
    private Object defaultValue;
}


