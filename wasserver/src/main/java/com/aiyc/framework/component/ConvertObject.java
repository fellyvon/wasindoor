package com.aiyc.framework.component;


public abstract class ConvertObject
{

    public ConvertObject(Class class1, Object obj)
    {
        destClass = class1;
        defaultValue = obj;
    }

    public Object covert(Object obj)
    {
        if(obj != null)
            try
            {
                Object obj1 = doCoverit(obj);
                if(obj1 != null)
                    return obj1;
            }
            catch(Exception exception)
            {
                if(defaultValue != null)
                    exception.printStackTrace();
            }
        return defaultValue;
    }

    public abstract Object doCoverit(Object obj)
        throws Exception;

    protected Object defaultValue;
    protected Class destClass;
}
