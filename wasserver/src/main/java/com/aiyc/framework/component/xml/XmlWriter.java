package com.aiyc.framework.component.xml;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Stack;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlWriter {

	private void print(String s) throws IOException {
		linebeak = false;
		if (writerBuffer != null)
			writerBuffer.append(s);
		else if (writer != null)
			writer.write(s);
	}

	private void print(char c) throws IOException {
		if (writerBuffer != null)
			writerBuffer.append(c);
		else if (writer != null)
			writer.write(c);
	}

	private void println() throws IOException {
		if (!linebeak) {
			print(NL);
			linebeak = true;
		}
	}

	public XmlWriter(StringBuffer stringbuffer) {
		writer = null;
		writerBuffer = null;
		stack = new Stack();
		closed = true;
		encoding = "GB2312";
		writerBuffer = stringbuffer;
	}

	public XmlWriter(Writer writer1) {
		writer = null;
		writerBuffer = null;
		stack = new Stack();
		closed = true;
		encoding = "GB2312";
		writer = writer1;
	}

	public XmlWriter(String s, String s1) throws IOException {
		this(((OutputStream) (new FileOutputStream(s))), s1);
	}

	public XmlWriter(OutputStream outputstream, String s) throws IOException {
		writer = null;
		writerBuffer = null;
		stack = new Stack();
		closed = true;
		encoding = "GB2312";
		if (s != null)
			encoding = s;
		writer = new OutputStreamWriter(outputstream, encoding);
	}

	public XmlWriter(String s) throws IOException {
		writer = null;
		writerBuffer = null;
		stack = new Stack();
		closed = true;
		encoding = "GB2312";
		writer = new FileWriter(s);
	}

	public void dataElement(String s, String s1) throws IOException {
		startElement(s);
		closeOpeningTag();
		writeEscapeXml(s1);
		endElement();
	}

	private void fillBlank() throws IOException {
		if (linebeak) {
			for (int i = 0; i < stack.size(); i++)
				print(elementSpan);

		}
	}

	public XmlWriter startElement(String s) throws IOException {
		closeOpeningTag();
		println();
		closed = false;
		stack.add(s);
		fillBlank();
		print("<");
		print(s);
		empty = true;
		return this;
	}

	public void closeOpeningTag() throws IOException {
		if (!closed) {
			addAttributes();
			closed = true;
			print(">");
		}
	}

	private void addAttributes() throws IOException {
		empty = false;
	}

	public XmlWriter addAttribute(String s, String s1) throws IOException {
		if (s != null && s1 != null) {
			print(" ");
			print(s);
			print("=\"");
			writeEscapeXml(s1);
			print("\"");
		}
		return this;
	}

	public XmlWriter endElement() throws IOException {
		if (stack.empty())
			throw new IOException("Called endElement too many times. ");
		if (!empty)
			fillBlank();
		String s = (String) stack.pop();
		if (s != null) {
			if (empty) {
				addAttributes();
				print("/>");
			} else {
				print("</");
				print(s);
				print(">");
			}
			empty = false;
			closed = true;
			println();
		}
		return this;
	}

	public void close() throws IOException {
		if (writer != null)
			try {
				writer.close();
			} catch (Exception exception) {
			}
	}

	public XmlWriter startDocument(String s, boolean flag) throws IOException {
		print("<?xml ");
		addAttribute("version", "1.0");
		if (s != null)
			addAttribute("encoding", s);
		if (flag)
			addAttribute("standalone", "yes");
		print("?>");
		println();
		return this;
	}

	public XmlWriter characters(String s) throws IOException {
		closeOpeningTag();
		empty = false;
		writeEscapeXml(s);
		return this;
	}

	public XmlWriter raw(String s) throws IOException {
		print(s);
		return this;
	}

	public XmlWriter CDATA(String s) throws IOException {
		closeOpeningTag();
		print("<![CDATA[");
		print(s);
		print("]]>");
		empty = false;
		return this;
	}

	public XmlWriter comment(String s) throws IOException {
		closeOpeningTag();
		print("<!--");
		print(s);
		print("-->");
		empty = false;
		return this;
	}

	public XmlWriter processingInstruction(String s, String s1)
			throws IOException {
		closeOpeningTag();
		if (s != null && s1 != null) {
			print("<?");
			print(s);
			print(" ");
			print(s1);
			print("?>");
		}
		empty = false;
		return this;
	}

	public XmlWriter reference(String s) throws IOException {
		closeOpeningTag();
		print("&");
		print(s);
		print(";");
		empty = false;
		return this;
	}

	public static String xmlEncode(String s) {
		StringBuffer stringbuffer = new StringBuffer();
		XmlWriter xmlwriter = new XmlWriter(stringbuffer);
		try {
			xmlwriter.writeEscapeXml(s);
		} catch (Exception exception) {
		}
		return stringbuffer.toString();
	}

	public void writeEscapeXml(String s) throws IOException {
		int j = s.length();
		for (int i = 0; i < j; i++) {
			char c = s.charAt(i);
			switch (c) {
			case 39: // '\''
				print("&apos;");
				break;

			case 34: // '"'
				print("&quot;");
				break;

			case 60: // '<'
				print("&lt;");
				break;

			case 62: // '>'
				print("&gt;");
				break;

			case 38: // '&'
				print("&amp;");
				break;

			default:
				print(c);
				break;
			}
		}

	}

	public void endDocument() throws IOException {
		println();
		if (!stack.empty())
			throw new IOException((new StringBuilder()).append(
					"Tags are not all closed. Possibly, ").append(stack.pop())
					.append(" is unclosed. ").toString());
		else
			return;
	}

	public void writeNode(Node node) throws IOException {
		short word0 = node.getNodeType();
		switch (word0) {
		case 2: // '\002'
		case 6: // '\006'
		default:
			break;

		case 9: // '\t'
			writeNode(((Node) (((Document) node).getDocumentElement())));
			break;

		case 1: // '\001'
			startElement(node.getNodeName());
			NamedNodeMap namednodemap = node.getAttributes();
			for (int i = 0; i < namednodemap.getLength(); i++) {
				Node node1 = namednodemap.item(i);
				addAttribute(node1.getNodeName(), node1.getNodeValue());
			}

			NodeList nodelist = node.getChildNodes();
			int j = nodelist.getLength();
			for (int k = 0; k < j; k++)
				writeNode(nodelist.item(k));

			endElement();
			break;

		case 5: // '\005'
			reference(node.getNodeName());
			break;

		case 4: // '\004'
			CDATA(node.getNodeValue());
			break;

		case 3: // '\003'
			String s = node.getNodeValue().trim();
			if (s.length() > 0)
				characters(s);
			break;

		case 7: // '\007'
			processingInstruction(node.getNodeName(), node.getNodeValue());
			break;

		case 8: // '\b'
			comment(node.getNodeValue());
			break;
		}
	}

	public String getEncoding() {
		return encoding;
	}

	public static String NL = System.getProperty("line.separator");
	public static String elementSpan = " ";
	private Writer writer;
	private StringBuffer writerBuffer;
	private Stack stack;
	private boolean empty;
	private boolean closed;
	private String encoding;
	private boolean linebeak;

}
