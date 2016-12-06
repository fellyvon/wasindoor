package com.aiyc.framework.component;

import java.util.HashMap;

import org.xml.sax.AttributeList;

import com.aiyc.framework.component.xml.XmlColumnDefine;
import com.aiyc.framework.component.xml.XmlElementHandler;
import com.aiyc.framework.component.xml.XmlResultExport;
import com.aiyc.framework.component.xml.XmlResultHandlerBase;


public class DataPackageHandlerBase extends XmlResultHandlerBase
{
    private class FieldElementHandler
        implements XmlElementHandler
    {

        public void startElement(AttributeList attributelist)
            throws Exception
        {
            XmlColumnDefine xmlcolumndefine = xmlResultSetMetaData.add();
            String s = attributelist.getValue("fieldtype");
            String s1 = attributelist.getValue("attrname");
            String s2 = attributelist.getValue("SUBTYPE");
            String s3 = attributelist.getValue("WIDTH");
            String s4 = attributelist.getValue("required");
            String s5 = attributelist.getValue("readonly");
            xmlcolumndefine.setColumnName(s1);
            xmlcolumndefine.setColumnLabel(s1);
            if(s3 != null)
            {
                int i = Integer.parseInt(s3);
                xmlcolumndefine.setColumnDisplaySize(i);
            }
            if(s4 != null)
                xmlcolumndefine.setNullable(0);
            else
                xmlcolumndefine.setNullable(1);
            if(s5 != null)
                xmlcolumndefine.setReadOnly(Boolean.getBoolean(s5));
            Object aobj[] = (Object[])(Object[])DataPackageHandlerBase.dataTypeMap.get(s);
            if(aobj == null)
            {
                throw new UnsupportedOperationException((new StringBuilder()).append("unsupport Type=").append(s).toString());
            } else
            {
                xmlcolumndefine.setColumnType(((int[])(int[])aobj[1])[0]);
                xmlcolumndefine.setColumnClassName(((Class)aobj[0]).getName());
                xmlcolumndefine.setColumnClass((Class)aobj[0]);
                return;
            }
        }

        final DataPackageHandlerBase this$0;

        private FieldElementHandler()
        { super();
            this$0 = DataPackageHandlerBase.this;
           
        }

    }

    private class RowElementHandler
        implements XmlElementHandler
    {

        public void startElement(AttributeList attributelist)
            throws Exception
        {
            int i = xmlResultSetMetaData.getColumnCount();
            String as[] = new String[i];
            for(int j = 0; j < i; j++)
                as[j] = attributelist.getValue(resultSetMetaData.getColumnName(j + 1));

            xmlResultSet.addRow(as);
        }

        final DataPackageHandlerBase this$0;

        private RowElementHandler()
        {super();
            this$0 = DataPackageHandlerBase.this;
            
        }

    }


    public DataPackageHandlerBase()
    {
        register("ROW", new RowElementHandler());
        register("FIELD", new FieldElementHandler());
    }

    public byte[] getXmlCharacter()
    {
        return "</DATAPACKET>".getBytes();
    }

    public String getXmlType()
    {
        return "DATAPACKET";
    }

    protected XmlResultExport getXmlResultExport()
    {
        return new DataPackageExport();
    }

    private static HashMap dataTypeMap;

    static 
    {
        dataTypeMap = null;
        dataTypeMap = new HashMap();
        dataTypeMap.put("r8", ((Object) (new Object[] {
            java.lang.Double.class, new int[] {
                8
            }
        })));
        dataTypeMap.put("i4", ((Object) (new Object[] {
            java.lang.Integer.class, new int[] {
                4
            }
        })));
        dataTypeMap.put("dateTime", ((Object) (new Object[] {
            java.sql.Timestamp.class, new int[] {
                93
            }
        })));
        dataTypeMap.put("string", ((Object) (new Object[] {
            java.lang.String.class, new int[] {
                12
            }
        })));
    }

}
