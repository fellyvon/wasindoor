package com.aiyc.framework.component;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


public class ColumnDefine
    implements Serializable
{

    public ColumnDefine()
    {
    }

    public int getColumnDisplaySize()
    {
        return columnDisplaySize;
    }

    public int isNullable()
    {
        return nullable;
    }

    public boolean isCurrency()
    {
        return currency;
    }

    public int getPrecision()
    {
        return precision;
    }

    public boolean isReadOnly()
    {
        return readOnly;
    }

    public boolean isDefinitelyWritable()
    {
        return definitelyWritable;
    }

    public String getColumnTypeName()
    {
        return columnTypeName;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public int getScale()
    {
        return scale;
    }

    public boolean isSigned()
    {
        return signed;
    }

    public int getColumnType()
    {
        return columnType;
    }

    public boolean isSearchable()
    {
        return searchable;
    }

    public boolean isCaseSensitive()
    {
        return caseSensitive;
    }

    public String getColumnClassName()
    {
        return columnClassName;
    }

    public String getCatalogName()
    {
        return catalogName;
    }

    public String getColumnLabel()
    {
        return columnLabel;
    }

    public String getTableName()
    {
        return tableName;
    }

    public String getSchemaName()
    {
        return SchemaName;
    }

    public boolean isAutoIncrement()
    {
        return autoIncrement;
    }

    public void setWritable(boolean flag)
    {
        writable = flag;
    }

    public void setColumnDisplaySize(int i)
    {
        columnDisplaySize = i;
    }

    public void setNullable(int i)
    {
        nullable = i;
    }

    public void setCurrency(boolean flag)
    {
        currency = flag;
    }

    public void setPrecision(int i)
    {
        precision = i;
    }

    public void setReadOnly(boolean flag)
    {
        readOnly = flag;
    }

    public void setDefinitelyWritable(boolean flag)
    {
        definitelyWritable = flag;
    }

    public void setColumnTypeName(String s)
    {
        columnTypeName = s;
    }

    public void setColumnName(String s)
    {
        columnName = s;
        if(columnLabel == null)
            columnLabel = s;
    }

    public void setScale(int i)
    {
        scale = i;
    }

    public void setSigned(boolean flag)
    {
        signed = flag;
    }

    public void setColumnType(int i)
    {
        columnType = i;
    }

    public void setSearchable(boolean flag)
    {
        searchable = flag;
    }

    public void setCaseSensitive(boolean flag)
    {
        caseSensitive = flag;
    }

    public void setColumnClassName(String s)
    {
        columnClassName = s;
    }

    public void setCatalogName(String s)
    {
        catalogName = s;
    }

    public void setColumnLabel(String s)
    {
        columnLabel = s;
    }

    public void setTableName(String s)
    {
        tableName = s;
    }

    public void setSchemaName(String s)
    {
        SchemaName = s;
    }

    public void setAutoIncrement(boolean flag)
    {
        autoIncrement = flag;
    }

    public void setColumnClass(Class class1)
    {
        if(columnClassName == null)
            columnClassName = class1.getName();
        columnClass = class1;
    }

    public boolean isWritable()
    {
        return writable;
    }

    public boolean isCalculate()
    {
        return false;
    }

    private Class getColumnClass(int i)
    {
        Class class1 =  String.class;
        switch(i)
        {
        case -6: 
        case -5: 
        case 4: // '\004'
        case 5: // '\005'
            class1 =  Integer.class;
            break;

        case 1: // '\001'
        case 12: // '\f'
            class1 =  String.class;
            break;

        case -7: 
        case 16: // '\020'
            class1 =  Boolean.class;
            break;

        case 2: // '\002'
        case 3: // '\003'
        case 6: // '\006'
        case 7: // '\007'
        case 8: // '\b'
            class1 =  Double.class;
            break;

        case 91: // '['
            class1 =  Date.class;
            break;

        case 92: // '\\'
            class1 =  Time.class;
            break;

        case 93: // ']'
            class1 =  Timestamp.class;
            break;

        case -4: 
        case -3: 
        case -2: 
        case 2004: 
           // class1 = [B;
            break;

        case -1: 
        case 2005: 
            class1 =  Blob.class;
            break;
        }
        return class1;
    }

    public Class getColumnClass()
    {
        if(columnClass == null)
        {
            if(columnClassName != null)
                try
                {
                    columnClass = Class.forName(columnClassName);
                }
                catch(ClassNotFoundException classnotfoundexception) { }
            if(columnClass == null)
            {
                columnClass = getColumnClass(columnType);
                if(columnClass != null && columnClassName == null)
                    columnClassName = columnClass.getName();
            }
        }
        return columnClass;
    }

    public int getNullable()
    {
        return nullable;
    }

    private static final long serialVersionUID = 1L;
    private boolean autoIncrement;
    private boolean caseSensitive;
    private boolean searchable;
    private boolean currency;
    private int nullable;
    private boolean signed;
    private int columnDisplaySize;
    private String columnLabel;
    private String columnName;
    private String SchemaName;
    private int precision;
    private int scale;
    private String tableName;
    private String catalogName;
    private int columnType;
    private String columnTypeName;
    private boolean readOnly;
    private boolean writable;
    private boolean definitelyWritable;
    private String columnClassName;
    private Class columnClass;
}
