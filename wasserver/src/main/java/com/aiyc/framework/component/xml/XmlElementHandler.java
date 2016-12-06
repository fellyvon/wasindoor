package com.aiyc.framework.component.xml;

import org.xml.sax.AttributeList;

public interface XmlElementHandler
{

    public abstract void startElement(AttributeList attributelist)
        throws Exception;
}