package com.aiyc.framework.component;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class ObjectInputStreamForContext extends ObjectInputStream
{

    public ObjectInputStreamForContext(InputStream inputstream)
        throws IOException
    {
        this(inputstream, null);
    }

    public ObjectInputStreamForContext(InputStream inputstream, ClassLoader classloader)
        throws IOException
    {
        super(inputstream);
       
            classLoader = classloader;
    }

    protected Class resolveClass(ObjectStreamClass objectstreamclass)
        throws IOException, ClassNotFoundException
    {
        String s = objectstreamclass.getName();
        try
        {
            return Class.forName(s, false, classLoader);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            Class class1 = super.resolveClass(objectstreamclass);
            if(class1 != null)
                return class1;
            else
                throw classnotfoundexception;
        }
    }

    private ClassLoader classLoader;
}

