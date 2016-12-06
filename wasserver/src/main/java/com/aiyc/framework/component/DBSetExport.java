package com.aiyc.framework.component;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.aiyc.framework.component.xml.XmlResultExport;
import com.aiyc.framework.component.xml.XmlWriter;
import com.aiyc.framework.utils.DateUtil;

public class DBSetExport extends XmlResultExport {

	public DBSetExport() {
		rowBuffer = new StringBuffer();
		columnCount = 0;
		columnNames = null;
		writeRowCount = true;
		DBSetNodeName = "DBSET";
		rowNodeName = "ROW";
		colNodeName = "COL";
		colName = "NAME";
	}

	private void writeMetaData(ResultSetMetaData resultsetmetadata)
			throws IOException, SQLException {
		columnCount = resultsetmetadata.getColumnCount();
		columnNames = new String[columnCount];
		for (int i = 0; i < columnCount; i++)
			columnNames[i] = resultsetmetadata.getColumnName(i + 1);

	}

	private int writeRowData(ResultSet resultset1, int i) throws IOException,
			SQLException {
		int j = 0;
		XmlWriter xmlwriter = new XmlWriter(rowBuffer);
		do {
			if (!resultset1.next())
				break;
			xmlwriter.startElement(rowNodeName);
			for (int k = 0; k < columnCount; k++) {
				Object obj = resultset1.getObject(k + 1);
				xmlwriter.startElement(colNodeName);
				xmlwriter.addAttribute(colName, columnNames[k]);
				if (obj != null) {
					xmlwriter.characters("");
					xmlwriter.raw(parseValue(obj));
				}
				xmlwriter.endElement();
			}

			xmlwriter.endElement();
			j++;
		} while (i <= 0 || j < i);
		return j;
	}

	private String parseValue(Object obj) {
		String s = null;
		try {
			if (java.util.Date.class.isInstance(obj))
				s = DateUtil.DateTimeToStr((Date) obj, '-', ' ', ':');
			else
				s = obj.toString();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return s;
	}

	public int writeResultset(ResultSet resultset1, int i) throws IOException,
			SQLException {
		int j = 0;
		resultset = resultset1;
		writer.startDocument(dataEncoding, true);
		writer.startElement(DBSetNodeName);
		writeMetaData(resultset1.getMetaData());
		j = writeRowData(resultset1, i);
		if (writeRowCount)
			writer.addAttribute("RESULT", String.valueOf(j));
		writer.characters("");
		writer.raw(rowBuffer.toString());
		writer.endElement();
		writer.close();
		return j;
	}

	private ResultSet resultset;
	private StringBuffer rowBuffer;
	private int columnCount;
	private String columnNames[];
	private boolean writeRowCount;
	private String DBSetNodeName;
	private String rowNodeName;
	private String colNodeName;
	private String colName;
}
