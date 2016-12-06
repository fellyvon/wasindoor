package com.aiyc.framework.component;

import java.sql.Blob;


public class ConvertBlobField extends ConvertObject
{

    public ConvertBlobField(Class class1, Object obj)
    {
        super(class1, obj);
    }

    public Object doCoverit(Object obj)
        throws Exception
    {
        if(BlobField.class.isInstance(obj))
            return new BlobField((byte[])(byte[])obj);
        if( String.class.isInstance(obj))
            return new BlobField((String)obj);
        if( Blob.class.isInstance(obj))
        {
            java.io.InputStream inputstream = null;
            try
            {
                inputstream = ((Blob)obj).getBinaryStream();
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
            if(inputstream != null)
                return new BlobField(inputstream);
        }
        return null;
    }
}

