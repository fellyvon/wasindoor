package com.aiyc.framework.component;

import java.io.Serializable;
import java.sql.SQLException;

import com.aiyc.framework.component.xml.XmlResultSet;


public class CachedRow
    implements Serializable
{

    public CachedRow(XmlResultSet xmlresultset, Object aobj[])
    {
        columnData = aobj;
        resultSet = xmlresultset;
    }

    public Object[] getColumnData()
    {
        return columnData;
    }

    public Object getColumn(int i)
    {
        return columnData[i - 1];
    }

    public Object getColumn(String s)
        throws SQLException
    {
        return getColumn(resultSet.findColumn(s));
    }

    public void setColumnData(Object aobj[])
    {
        columnData = aobj;
    }

    private static final long serialVersionUID = 1L;
    private Object columnData[];
    private XmlResultSet resultSet;
}
