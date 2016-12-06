package com.aiyc.framework.component;

import java.util.ArrayList;

import org.xml.sax.AttributeList;
import org.xml.sax.SAXException;

import com.aiyc.framework.component.xml.XmlColumnDefine;
import com.aiyc.framework.component.xml.XmlResultExport;
import com.aiyc.framework.component.xml.XmlResultHandlerBase;
import com.aiyc.framework.component.xml.XmlResultSet;
import com.aiyc.framework.component.xml.XmlResultSetMetaData;


public class DBSetHandlerBase extends XmlResultHandlerBase
{

    public DBSetHandlerBase()
    {
        level = 0;
        initColumn = false;
        rowValues = new ArrayList();
        columnNames = new ArrayList();
        value = null;
        columnName = null;
        characters = null;
        index = -1;
        length = -1;
        curIndex = -1;
        curLength = -1;
        rootBeginTag = "<DBSET";
        rootEndTag = "</DBSET>";
    }

    public byte[] getXmlCharacter()
    {
        return "</DBSET>".getBytes();
    }

    public String getXmlType()
    {
        return "DBSET";
    }

    public void startElement(String s, AttributeList attributelist)
        throws SAXException
    {
        level++;
        if(level == 2 && !initColumn)
        {
            int i = columnNames.size();
            if(i > 0)
            {
                for(int j = 0; j < i; j++)
                {
                    XmlColumnDefine xmlcolumndefine = ((XmlResultSetMetaData)resultSetMetaData).add();
                    xmlcolumndefine.setColumnName((String)columnNames.get(j));
                    xmlcolumndefine.setColumnType(12);
                    xmlcolumndefine.setColumnDisplaySize(1024);
                    xmlcolumndefine.setColumnClass(String.class);
                }

                initColumn = true;
            }
        }
        if(level == 3)
        {
            value = null;
            curIndex = -1;
            curLength = -1;
            columnName = attributelist.getValue("NAME");
            if(!initColumn)
                columnNames.add(columnName);
        }
    }

    public void endElement(String s)
        throws SAXException
    {
        if(level == 2 && rowValues.size() > 0)
        {
            Object aobj[] = new Object[rowValues.size()];
            rowValues.toArray(aobj);
            ((XmlResultSet)resultSet).addRow(aobj);
            rowValues.clear();
        }
        if(level == 3)
        {
            if(value == null)
            {
                String s1 = (new StringBuilder()).append("<COL NAME=\"").append(columnName).append("\">").toString();
                String s2 = "</COL>";
                int i = (new String(characters)).indexOf(s1, index);
                System.out.println(columnName);
                byte byte0 = -1;
                if(i != -1)
                {
                    int j = (new String(characters)).indexOf(s2, index);
                    if(j - i != s1.length())
                    {
                        if(index == -1)
                            curIndex = (new String(characters)).indexOf(rootBeginTag, i);
                        else
                            curIndex = (new String(characters)).indexOf(rootBeginTag, index);
                        curLength = (new String(characters)).indexOf(rootEndTag, curIndex);
                        value = new String(characters, curIndex, (curLength - curIndex) + rootEndTag.length());
                    }
                }
            }
            rowValues.add(value);
        }
        level--;
    }

    public void characters(char ac[], int i, int j)
        throws SAXException
    {
        if(characters == null)
            characters = ac;
        if(level == 3)
        {
            value = new String(ac, i, j);
            if(value.toString().replaceAll(" ", "").equals(""))
            {
                index = index;
                value = null;
            } else
            {
                index = i;
            }
            j = j;
        }
    }

    protected XmlResultExport getXmlResultExport()
    {
        return new DBSetExport();
    }

    private int level;
    private boolean initColumn;
    private ArrayList rowValues;
    private ArrayList columnNames;
    private Object value;
    private String columnName;
    private char characters[];
    private int index;
    private int length;
    private int curIndex;
    private int curLength;
    private String rootBeginTag;
    private String rootEndTag;
}
