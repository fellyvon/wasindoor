package com.aiyc.framework.component;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.aiyc.framework.component.xml.XmlResultExport;
import com.aiyc.framework.utils.DateUtil;

public class DataPackageExport extends XmlResultExport
{

    public DataPackageExport()
    {
        isInBuffer = false;
        writerBuffer = new StringBuffer();
    }

    private void writeMetaData(ResultSetMetaData resultsetmetadata)
        throws IOException, SQLException
    {
        columnCount = resultsetmetadata.getColumnCount();
        writer.startElement("METADATA");
        writer.startElement("FIELDS");
        columnNames = new String[columnCount];
        for(int i = 0; i < columnCount; i++)
        {
            boolean flag = resultsetmetadata.isReadOnly(i + 1);
            int j = resultsetmetadata.isNullable(i + 1);
            int k = resultsetmetadata.getColumnType(i + 1);
            String s = resultsetmetadata.getColumnName(i + 1);
            columnNames[i] = s;
            String s1 = null;
            String s2 = null;
            int l = -1;
            switch(k)
            {
            case -6: 
            case -5: 
            case 4: // '\004'
            case 5: // '\005'
                s1 = "i4";
                if(resultsetmetadata.isAutoIncrement(i + 1))
                    s2 = "Autoinc";
                break;

            case 1: // '\001'
            case 12: // '\f'
                s1 = "string";
                l = resultsetmetadata.getColumnDisplaySize(i + 1);
                break;

            case -7: 
            case 16: // '\020'
                s1 = "boolean";
                break;

            case 2: // '\002'
            case 3: // '\003'
            case 6: // '\006'
            case 7: // '\007'
            case 8: // '\b'
                s1 = "r8";
                if(resultsetmetadata.isCurrency(i + 1))
                    s2 = "Money";
                break;

            case 91: // '['
            case 92: // '\\'
            case 93: // ']'
                s1 = "dateTime";
                break;

            case -4: 
            case -3: 
            case -2: 
            case 2004: 
                s1 = "bin.hex";
                s2 = "Binary";
                break;

            case -1: 
            case 2005: 
                s1 = "bin.hex";
                s2 = "Text";
                break;

            default:
                s1 = "string";
                l = resultsetmetadata.getColumnDisplaySize(i + 1);
                break;
            }
            writer.startElement("FIELD");
            writer.addAttribute("attrname", s);
            writer.addAttribute("fieldtype", s1);
            if(s2 != null)
                writer.addAttribute("SUBTYPE", s2);
            if(l > 0)
                writer.addAttribute("WIDTH", String.valueOf(l));
            ResultSetMetaData _tmp = resultsetmetadata;
            if(j == 0)
                writer.addAttribute("required", "true");
            if(flag)
                writer.addAttribute("readonly", "true");
            writer.endElement();
        }

        writer.endElement();
        writer.endElement();
    }

    private int writeRowData(ResultSet resultset1, int i)
        throws IOException, SQLException
    {
        writer.startElement("ROWDATA");
        int j = 0;
        do
        {
            if(!resultset1.next())
                break;
            writer.startElement("ROW");
            for(int k = 0; k < columnCount; k++)
            {
                Object obj = resultset1.getObject(k + 1);
                if(obj == null)
                    continue;
                String s;
                if(java.util.Date.class.isInstance(obj))
                    s = DateUtil.DateTimeToISOStr((Date)obj);
                else
                    s = obj.toString();
                writer.addAttribute(columnNames[k], s);
            }

            writer.endElement();
            j++;
        } while(i <= 0 || j < i);
        writer.endElement();
        return j;
    }

    public int writeResultset(ResultSet resultset1, int i)
        throws IOException, SQLException
    {
        resultset = resultset1;
        writer.startDocument(dataEncoding, true);
        writer.startElement("DATAPACKET");
        writer.addAttribute("Version", "2.0");
        writeMetaData(resultset1.getMetaData());
        int j = writeRowData(resultset1, i);
        writer.endElement();
        writer.close();
        return j;
    }

    private String columnNames[];
    private int columnCount;
    private boolean isInBuffer;
    private ResultSet resultset;
    private StringBuffer writerBuffer;
}
