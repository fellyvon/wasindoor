package com.aiyc.framework.component;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class CalculateColumn extends ColumnDefine
    implements Serializable, Cloneable
{

    public CalculateColumn()
    {
    }

    public void setResultSet(ResultSet resultset)
    {
        resultSet = resultset;
    }

    public ResultSet getResultSet()
    {
        return resultSet;
    }

    public boolean isCalculate()
    {
        return true;
    }

    public Object clone()
    {
        return SerializableObject.clone(this);
    }

    public abstract Object getObject()
        throws SQLException;

    private static final long serialVersionUID = 1L;
    private transient ResultSet resultSet;
}