package com.aiyc.framework.component;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

public class BlobField
implements Serializable
{

public BlobField(int i)
{
    length = 0;
    buffer = null;
    length = i;
    buffer = new byte[i];
}

public BlobField(byte abyte0[])
{
    length = 0;
    buffer = null;
    buffer = abyte0;
    length = abyte0.length;
}

public BlobField(String s)
{
    length = 0;
    buffer = null;
    try
    {
        byte abyte0[] = s.getBytes(BLOB_ENCODING);
        buffer = abyte0;
        length = abyte0.length;
    }
    catch(Exception exception)
    {
        exception.printStackTrace();
    }
}

public BlobField(Blob blob)
    throws SQLException
{
    length = 0;
    buffer = null;
    length = (int)blob.length();
    buffer = new byte[length];
    BufferedInputStream bufferedinputstream = new BufferedInputStream(blob.getBinaryStream());
    try
    {
        int i = 0;
        int j = 0;
        do
        {
            i = bufferedinputstream.read(buffer, j, (int)((long)length - (long)j));
            j += i;
        } while(i > 0 && j < length);
    }
    catch(IOException ioexception)
    {
        throw new SQLException((new StringBuilder()).append("SerialBlob: ").append(ioexception.getMessage()).toString());
    }
}

public BlobField(InputStream inputstream)
{
    length = 0;
    buffer = null;
    try
    {
        length = inputstream.available();
        buffer = new byte[length];
        inputstream.read(buffer);
    }
    catch(Exception exception)
    {
        exception.printStackTrace();
    }
}

public InputStream getInputStream()
{
    return new ByteArrayInputStream(buffer);
}

public OutputStream getOutputStream()
{
    return new BlobOutputStream(this);
}

public void setBytes(byte abyte0[])
{
    buffer = abyte0;
    length = abyte0.length;
}

public long length()
{
    return (long)length;
}

public String toString()
{
    try
    {
        return new String(buffer, BLOB_ENCODING);
    }
    catch(Exception exception)
    {
        exception.printStackTrace();
    }
    return null;
}

private static final long serialVersionUID = 1L;
public static String BLOB_ENCODING = "GBK";
private int length;
private byte buffer[];

}