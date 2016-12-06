package com.aiyc.framework.component.xml;

import java.io.CharArrayReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlHandlerBase extends HandlerBase {

	public XmlHandlerBase() {
		elementList = new LinkedList();
		tagListenerMap = new HashMap();
	}

	public String getElementPath() {
		StringBuffer stringbuffer = new StringBuffer();
		Iterator iterator = elementList.iterator();
		boolean flag = false;
		for (; iterator.hasNext(); stringbuffer.append(iterator.next()))
			stringbuffer.append("/");

		return stringbuffer.toString();
	}

	public int getLevel() {
		return elementList.size();
	}

	public int getElementLevel(String s) {
		Iterator iterator = elementList.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			String s1 = (String) iterator.next();
			if (s.equals(s1))
				return i;
		}

		return -1;
	}

	public void parseFile(String s, String s1) throws Exception {
		FileInputStream fileinputstream = new FileInputStream(s);
		parseStream(fileinputstream, s1);
	}

	public void parseFile(String s) throws Exception {
		FileInputStream fileinputstream = new FileInputStream(s);
		parseStream(fileinputstream, null);
	}

	public void parseStream(InputStream inputstream) throws Exception {
		parseStream(inputstream, null);
	}

	public void parseStream(InputStream inputstream, String s) throws Exception {
		SAXParserFactory saxparserfactory = SAXParserFactory.newInstance();
		saxparserfactory.setNamespaceAware(false);
		saxparserfactory.setValidating(false);
		SAXParser saxparser = saxparserfactory.newSAXParser();
		InputSource inputsource = new InputSource(inputstream);
		if (s != null)
			inputsource.setEncoding(s);
		saxparser.parse(inputsource, this);
	}

	public void parseString(String s) throws Exception {
		SAXParserFactory saxparserfactory = SAXParserFactory.newInstance();
		saxparserfactory.setNamespaceAware(false);
		saxparserfactory.setValidating(false);
		SAXParser saxparser = saxparserfactory.newSAXParser();
		CharArrayReader chararrayreader = new CharArrayReader(s.toCharArray());
		InputSource inputsource = new InputSource(chararrayreader);
		saxparser.parse(inputsource, this);
	}

	public void startDocument() throws SAXException {
		documentStart = true;
	}

	public void endDocument() throws SAXException {
		documentEnd = true;
	}

	public void startElement(String s, AttributeList attributelist)
			throws SAXException {
		elementList.addLast(s);
		XmlElementHandler xmlelementhandler = (XmlElementHandler) tagListenerMap
				.get(s);
		try {
			if (xmlelementhandler != null)
				xmlelementhandler.startElement(attributelist);
		} catch (Exception exception) {
			if ( SAXException.class.isInstance(exception))
				throw (SAXException) exception;
			else
				throw new SAXException(exception);
		}
	}

	public void endElement(String s) throws SAXException {
		elementList.removeLast();
	}

	public void register(String s, XmlElementHandler xmlelementhandler) {
		tagListenerMap.put(s, xmlelementhandler);
	}

	private boolean documentEnd;
	private boolean documentStart;
	private LinkedList elementList;
	private HashMap tagListenerMap;
}
