package com.aiyc.framework.component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class BlobOutputStream extends ByteArrayOutputStream
{

    public BlobOutputStream(BlobField blobfield)
    {
        field = blobfield;
    }

    public void close()
        throws IOException
    {
        field.setBytes(toByteArray());
    }

    private BlobField field;
}
