package com.aiyc.framework.component;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;


public class SerialBlob
    implements Blob, Serializable
{

    public SerialBlob(Blob blob)
        throws SQLException
    {
        buffer = null;
        length = 0L;
        length = blob.length();
        buffer = new byte[(int)length];
        BufferedInputStream bufferedinputstream = new BufferedInputStream(blob.getBinaryStream());
        try
        {
            int i = 0;
            int j = 0;
            do
            {
                i = bufferedinputstream.read(buffer, j, (int)(length - (long)j));
                j += i;
            } while(i > 0 && (long)j < length);
        }
        catch(IOException ioexception)
        {
            throw new SQLException((new StringBuilder()).append("SerialBlob: ").append(ioexception.getMessage()).toString());
        }
    }

    public InputStream getBinaryStream()
        throws SQLException
    {
        return new ByteArrayInputStream(buffer);
    }

    public byte[] getBytes(long l, int i)
        throws SQLException
    {
        if(l < 0L || (long)i > length || l + (long)i > length)
        {
            throw new SQLException("Invalid Arguments");
        } else
        {
            byte abyte0[] = new byte[i];
            System.arraycopy(buffer, (int)l, abyte0, 0, i);
            return abyte0;
        }
    }

    public byte[] getBytes()
    {
        return buffer;
    }

    public long length()
        throws SQLException
    {
        return length;
    }

    public long position(Blob blob, long l)
        throws SQLException
    {
        return position(blob.getBytes(0L, (int)blob.length()), l);
    }
    public long position(byte abyte0[], long l)
    {
    	return 0;
    }
//    public long position(byte abyte0[], long l)
//        throws SQLException
//    {
//        int i;
//        long l1;
//        if(l < 0L || l > length || l + (long)abyte0.length > length)
//            throw new SQLException("Invalid Arguments");
//        i = (int)(l - 1L);
//        boolean flag = false;
//        l1 = abyte0.length;
//        if(l < 0L || l > length)
//            return -1L;
//_L2:
//        int j;
//        long l2;
//        if((long)i >= length)
//            break MISSING_BLOCK_LABEL_127;
//        j = 0;
//        l2 = i + 1;
//_L4:
//        if(abyte0[j++] != buffer[i++]) goto _L2; else goto _L1
//_L1:
//        if((long)j != l1) goto _L4; else goto _L3
//_L3:
//        return l2;
//        return -1L;
//    }

    public OutputStream setBinaryStream(long l)
        throws SQLException
    {
        throwUnsupportedFeatureSqlException();
        return null;
    }

    public int setBytes(long l, byte abyte0[])
        throws SQLException
    {
        throwUnsupportedFeatureSqlException();
        return -1;
    }

    public int setBytes(long l, byte abyte0[], int i, int j)
        throws SQLException
    {
        throwUnsupportedFeatureSqlException();
        return -1;
    }

    public void truncate(long l)
        throws SQLException
    {
        throwUnsupportedFeatureSqlException();
    }

    public static void throwUnsupportedFeatureSqlException()
        throws SQLException
    {
        throw new SQLException("\u4E0D\u652F\u6301\u8BE5\u9879\u64CD\u4F5C");
    }

    public void free()
        throws SQLException
    {
    }

    public InputStream getBinaryStream(long l, long l1)
        throws SQLException
    {
        return new ByteArrayInputStream(buffer, (int)l, (int)l1);
    }

    private static final long serialVersionUID = 1L;
    private byte buffer[];
    private long length;
}