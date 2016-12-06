package com.aiyc.framework.component.xml;

import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.aiyc.framework.component.CalculateColumn;
import com.aiyc.framework.component.ColumnDefine;

 


public class XmlResultSetMetaData
    implements ResultSetMetaData, Serializable
{


	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

 
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public XmlResultSetMetaData()
    {
        this(incColumnCount);
    }

    public XmlResultSetMetaData(int i)
    {
        columnCount = 0;
        maxColumnCount = i;
        fields = new XmlColumnDefine[i];
    }

    public XmlResultSetMetaData(ResultSetMetaData resultsetmetadata)
        throws SQLException
    {
        columnCount = resultsetmetadata.getColumnCount();
        maxColumnCount = columnCount;
        fields = new XmlColumnDefine[maxColumnCount];
        for(int i = 0; i < columnCount; i++)
        {
            fields[i] = new XmlColumnDefine();
            ColumnDefine columndefine = fields[i];
            columndefine.setSearchable(resultsetmetadata.isSearchable(i + 1));
            columndefine.setCaseSensitive(resultsetmetadata.isCaseSensitive(i + 1));
            columndefine.setReadOnly(resultsetmetadata.isReadOnly(i + 1));
            columndefine.setNullable(resultsetmetadata.isNullable(i + 1));
            columndefine.setSigned(resultsetmetadata.isSigned(i + 1));
            columndefine.setColumnDisplaySize(resultsetmetadata.getColumnDisplaySize(i + 1));
            int j = resultsetmetadata.getColumnType(i + 1);
            columndefine.setColumnType(j);
            columndefine.setColumnLabel(resultsetmetadata.getColumnLabel(i + 1));
            columndefine.setColumnName(resultsetmetadata.getColumnName(i + 1));
            columndefine.setSchemaName(resultsetmetadata.getSchemaName(i + 1));
            if(j == 2 || j == -5 || j == 3 || j == 8 || j == 6 || j == 4)
            {
                columndefine.setPrecision(resultsetmetadata.getPrecision(i + 1));
                columndefine.setScale(resultsetmetadata.getScale(i + 1));
            } else
            {
                columndefine.setPrecision(0);
                columndefine.setScale(0);
            }
            columndefine.setTableName(resultsetmetadata.getTableName(i + 1));
            columndefine.setColumnTypeName(resultsetmetadata.getColumnTypeName(i + 1));
            columndefine.setWritable(resultsetmetadata.isWritable(i + 1));
            columndefine.setDefinitelyWritable(resultsetmetadata.isDefinitelyWritable(i + 1));
            columndefine.setCurrency(resultsetmetadata.isCurrency(i + 1));
            columndefine.setAutoIncrement(resultsetmetadata.isAutoIncrement(i + 1));
            columndefine.setCatalogName(resultsetmetadata.getCatalogName(i + 1));
            columndefine.setColumnClassName(resultsetmetadata.getColumnClassName(i + 1));
        }

    }

    public XmlColumnDefine add()
    {
        XmlColumnDefine xmlcolumndefine = new XmlColumnDefine();
        addColumn(xmlcolumndefine);
        return xmlcolumndefine;
    }

    public void addColumn(XmlColumnDefine xmlcolumndefine)
    {
        if(columnCount >= maxColumnCount)
        {
            int i = maxColumnCount + incColumnCount;
            XmlColumnDefine axmlcolumndefine[] = new XmlColumnDefine[i];
            System.arraycopy(fields, 0, axmlcolumndefine, 0, maxColumnCount);
            maxColumnCount = i;
            fields = axmlcolumndefine;
        }
        fields[columnCount] = xmlcolumndefine;
        columnCount++;
    }

    public int getColumnCount()
        throws SQLException
    {
        return columnCount;
    }

    public boolean isAutoIncrement(int i)
        throws SQLException
    {
        return fields[i - 1].isAutoIncrement();
    }

    public boolean isCaseSensitive(int i)
        throws SQLException
    {
        return fields[i - 1].isCaseSensitive();
    }

    public boolean isSearchable(int i)
        throws SQLException
    {
        return fields[i - 1].isSearchable();
    }

    public boolean isCurrency(int i)
        throws SQLException
    {
        return fields[i - 1].isCurrency();
    }

    public int isNullable(int i)
        throws SQLException
    {
        return fields[i - 1].isNullable();
    }

    public boolean isSigned(int i)
        throws SQLException
    {
        return fields[i - 1].isSigned();
    }

    public int getColumnDisplaySize(int i)
        throws SQLException
    {
        return fields[i - 1].getColumnDisplaySize();
    }

    public String getColumnLabel(int i)
        throws SQLException
    {
        return fields[i - 1].getColumnLabel();
    }

    public String getColumnName(int i)
        throws SQLException
    {
        return fields[i - 1].getColumnName();
    }

    public String getSchemaName(int i)
        throws SQLException
    {
        return fields[i - 1].getSchemaName();
    }

    public int getPrecision(int i)
        throws SQLException
    {
        return fields[i - 1].getPrecision();
    }

    public int getScale(int i)
        throws SQLException
    {
        return fields[i - 1].getScale();
    }

    public String getTableName(int i)
        throws SQLException
    {
        return fields[i - 1].getTableName();
    }

    public String getCatalogName(int i)
        throws SQLException
    {
        return fields[i - 1].getCatalogName();
    }

    public int getColumnType(int i)
        throws SQLException
    {
        return fields[i - 1].getColumnType();
    }

    public String getColumnTypeName(int i)
        throws SQLException
    {
        return fields[i - 1].getColumnTypeName();
    }

    public boolean isReadOnly(int i)
        throws SQLException
    {
        return fields[i - 1].isReadOnly();
    }

    public boolean isWritable(int i)
        throws SQLException
    {
        return fields[i - 1].isWritable();
    }

    public boolean isDefinitelyWritable(int i)
        throws SQLException
    {
        return fields[i - 1].isDefinitelyWritable();
    }

    public String getColumnClassName(int i)
        throws SQLException
    {
        return fields[i - 1].getColumnClassName();
    }

    public Class getColumnClass(int i)
        throws SQLException
    {
        return fields[i - 1].getColumnClass();
    }

    public boolean isCalculate(int i)
        throws SQLException
    {
        return fields[i - 1].isCalculate();
    }

    public Object getCalculateValue(int i)
        throws SQLException
    {
        if(CalculateColumn.class.isInstance(fields[i - 1]))
            return ((CalculateColumn)(CalculateColumn)fields[i - 1]).getObject();
        else
            return null;
    }

    private static final long serialVersionUID = 1L;
    private int columnCount;
    private int maxColumnCount;
    private static int incColumnCount = 128;
    private ColumnDefine fields[];

}

