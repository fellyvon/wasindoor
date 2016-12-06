package com.aiyc.framework.component.xml;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

 


public class XmlResultHandlerBase extends XmlHandlerBase
{

    public XmlResultHandlerBase()
    {
    }

    public void setResultSet(ResultSet resultset)
    {
        resultSet = resultset;
        try
        {
            resultSetMetaData = resultset.getMetaData();
        }
        catch(Exception exception)
        {
            resultSetMetaData = null;
        }
        if( XmlResultSet.class.isInstance(resultset))
            xmlResultSet = (XmlResultSet)resultset;
        if( XmlResultSetMetaData.class.isInstance(resultSetMetaData))
            xmlResultSetMetaData = (XmlResultSetMetaData)resultSetMetaData;
    }

    protected XmlResultExport getXmlResultExport()
    {
        return null;
    }

    public byte[] getXmlCharacter()
    {
        return null;
    }

    public String getXmlType()
    {
        return null;
    }

    public int writeXML(XmlWriter xmlwriter, int i)
        throws IOException, SQLException
    {
        int j;
        j = 0;
        XmlResultExport xmlresultexport;
        try
        {
            xmlresultexport = getXmlResultExport();
            if(xmlresultexport == null)
                return 0;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            if( IOException.class.isInstance(exception))
                throw (IOException)exception;
            else
                throw new RuntimeException(exception);
        }
        xmlresultexport.setWriter(xmlwriter);
        j = xmlresultexport.writeResultset(resultSet, i);
        xmlresultexport.close();
        return j;
    }

    protected ResultSetMetaData resultSetMetaData;
    protected ResultSet resultSet;
    protected XmlResultSetMetaData xmlResultSetMetaData;
    protected XmlResultSet xmlResultSet;
}
