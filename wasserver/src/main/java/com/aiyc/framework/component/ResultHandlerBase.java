package com.aiyc.framework.component;

import java.util.ArrayList;

import org.xml.sax.AttributeList;
import org.xml.sax.SAXException;

import com.aiyc.framework.component.xml.XmlColumnDefine;
import com.aiyc.framework.component.xml.XmlResultExport;
import com.aiyc.framework.component.xml.XmlResultHandlerBase;
import com.aiyc.framework.component.xml.XmlResultSet;
import com.aiyc.framework.component.xml.XmlResultSetMetaData;


public class ResultHandlerBase extends XmlResultHandlerBase
{

    public ResultHandlerBase()
    {
        level = 0;
        isinit = false;
        row = new ArrayList();
        columnNames = new ArrayList();
        columnValue = null;
        characters = null;
        index = 0;
        length = -1;
        curIndex = -1;
        curLength = -1;
        rootBeginTag = "<RESULT>";
        rootEndTag = "</RESULT>";
    }

    public byte[] getXmlCharacter()
    {
        return rootEndTag.getBytes();
    }

    public String getXmlType()
    {
        return "RESULT";
    }

    public void startElement(String s, AttributeList attributelist)
        throws SAXException
    {
        level++;
        if(level == 4)
        {
            columnValue = null;
            curIndex = -1;
            curLength = -1;
        }
    }

    public void endElement(String s)
        throws SAXException
    {
        if(level == 3)
        {
            if(!isinit)
            {
                int i = columnNames.size();
                for(int j = 0; j < i; j++)
                {
                    XmlColumnDefine xmlcolumndefine = ((XmlResultSetMetaData)resultSetMetaData).add();
                    xmlcolumndefine.setColumnName((String)columnNames.get(j));
                    xmlcolumndefine.setColumnType(12);
                    xmlcolumndefine.setColumnDisplaySize(1024);
                    xmlcolumndefine.setColumnClass( String.class);
                }

                isinit = true;
            }
            if(row.size() > 0)
            {
                Object aobj[] = new Object[row.size()];
                row.toArray(aobj);
                ((XmlResultSet)resultSet).addRow(aobj);
                row.clear();
            }
        }
        if(level == 4)
        {
            if(!isinit)
                columnNames.add(s);
            if(columnValue == null)
            {
                String s1 = (new StringBuilder()).append("<").append(s).append(">").toString();
                String s2 = (new StringBuilder()).append("</").append(s).append(">").toString();
                int k = (new String(characters, index, 20)).indexOf(s1);
                byte byte0 = -1;
                if(k != -1)
                {
                    int l = (new String(characters)).indexOf(s2, index);
                    if(l - k != s1.length())
                    {
                        if(index == -1)
                            curIndex = (new String(characters)).indexOf(rootBeginTag, k);
                        else
                            curIndex = (new String(characters)).indexOf(rootBeginTag, index);
                        curLength = (new String(characters)).indexOf(rootEndTag, curIndex);
                        columnValue = new String(characters, curIndex, (curLength - curIndex) + rootEndTag.length());
                    }
                }
            }
            row.add(columnValue);
        }
        level--;
    }

    public void characters(char ac[], int i, int j)
        throws SAXException
    {
        if(characters == null)
            characters = ac;
        if(level == 4)
        {
            columnValue = new String(ac, i, j);
            if(columnValue.toString().replaceAll(" ", "").equals(""))
            {
                index = index;
                columnValue = null;
            } else
            {
                index = i;
            }
            j = j;
        }
    }

    protected XmlResultExport getXmlResultExport()
    {
        return new ResultExport();
    }

    private int level;
    private boolean isinit;
    private ArrayList row;
    private ArrayList columnNames;
    private Object columnValue;
    private char characters[];
    private int index;
    private int length;
    private int curIndex;
    private int curLength;
    private String rootBeginTag;
    private String rootEndTag;
}
