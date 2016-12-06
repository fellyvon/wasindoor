package com.aiyc.framework.component;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.aiyc.framework.component.xml.XmlResultExport;
import com.aiyc.framework.utils.DateUtil;


public class ResultExport extends XmlResultExport
{

    public ResultExport()
    {
        isInBuffer = false;
        writerBuffer = new StringBuffer();
        writeRecordCount = true;
        dataSetName = "ROWS";
        rowName = "ROW";
    }

    private void writeMetaData(ResultSetMetaData resultsetmetadata)
        throws IOException, SQLException
    {
        columnCount = resultsetmetadata.getColumnCount();
        columnNames = new String[columnCount];
        for(int i = 0; i < columnCount; i++)
            columnNames[i] = resultsetmetadata.getColumnName(i + 1);

    }

    private int writeRowData(ResultSet resultset1, int i)
        throws IOException, SQLException
    {
        writer.startElement(dataSetName);
        int j = 0;
        do
        {
            if(!resultset1.next())
                break;
            writer.startElement(rowName);
            for(int k = 0; k < columnCount; k++)
            {
                Object obj = resultset1.getObject(k + 1);
                writer.startElement(columnNames[k]);
                if(obj != null)
                {
                    String s;
                    if(java.util.Date.class.isInstance(obj))
                        s = DateUtil.DateTimeToStr((Date)obj, '-', ' ', ':');
                    else
                        s = obj.toString();
                    writer.characters("");
                    writer.raw(s);
                }
                writer.endElement();
            }

            writer.endElement();
            j++;
        } while(i <= 0 || j < i);
        if(writeRecordCount)
        {
            writer.startElement("RECORDCOUNT");
            writer.characters(String.valueOf(j));
            writer.endElement();
        }
        writer.endElement();
        return j;
    }

    public void close()
        throws IOException
    {
        writer.close();
    }

    public void writeResultset(ResultSet resultset1)
        throws IOException, SQLException
    {
        writeResultset(resultset1, -1);
    }

    public int writeResultset(ResultSet resultset1, int i)
        throws IOException, SQLException
    {
        resultset = resultset1;
        writer.startDocument(dataEncoding, true);
        writer.startElement("RESULT");
        writeMetaData(resultset1.getMetaData());
        int j = writeRowData(resultset1, i);
        writer.endElement();
        writer.close();
        return j;
    }

    public String getDataSetName()
    {
        return dataSetName;
    }

    public String getRowName()
    {
        return rowName;
    }

    public boolean isWriteRecordCount()
    {
        return writeRecordCount;
    }

    public void setDataSetName(String s)
    {
        dataSetName = s;
    }

    public void setRowName(String s)
    {
        rowName = s;
    }

    public void setWriteRecordCount(boolean flag)
    {
        writeRecordCount = flag;
    }

    private String columnNames[];
    private int columnCount;
    private boolean isInBuffer;
    private ResultSet resultset;
    private StringBuffer writerBuffer;
    private boolean writeRecordCount;
    private String dataSetName;
    private String rowName;
}

