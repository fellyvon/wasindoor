package com.aiyc.framework.component.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.RowId;
import java.sql.SQLXML;

import java.sql.SQLWarning;
 
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
 
import java.sql.SQLException;
 
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;

import com.aiyc.framework.component.BlobField;
import com.aiyc.framework.component.CachedRow;
import com.aiyc.framework.component.CompareItem;
import com.aiyc.framework.component.DBSetHandlerBase;
import com.aiyc.framework.component.DataPackageHandlerBase;
import com.aiyc.framework.component.ResultHandlerBase;
import com.aiyc.framework.component.RowComparator;
import com.aiyc.framework.utils.ByteArrayUtils;
import com.aiyc.framework.utils.PropertyFieldUtils;

public class XmlResultSet implements ResultSet, Serializable, Cloneable {

	
	public NClob getNClob(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}





	public NClob getNClob(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}





	public RowId getRowId(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}





	public RowId getRowId(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}





	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}





	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}





	public void updateNClob(int columnIndex, NClob clob) throws SQLException {
		// TODO Auto-generated method stub
		
	}





	public void updateNClob(String columnLabel, NClob clob) throws SQLException {
		// TODO Auto-generated method stub
		
	}





	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		// TODO Auto-generated method stub
		
	}





	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		// TODO Auto-generated method stub
		
	}





	public void updateSQLXML(int columnIndex, SQLXML xmlObject)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}





	public void updateSQLXML(String columnLabel, SQLXML xmlObject)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}





	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

 
 


	public Reader getNCharacterStream(int i) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Reader getNCharacterStream(String s) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
//	public NClob getNClob(int i) throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	
//	public NClob getNClob(String s) throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
	public String getNString(int i) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getNString(String s) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

//	
//	public RowId getRowId(int i) throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	
//	public RowId getRowId(String s) throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	
//	public SQLXML getSQLXML(int i) throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	
//	public SQLXML getSQLXML(String s) throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void updateAsciiStream(int i, InputStream inputstream, long l)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateAsciiStream(int i, InputStream inputstream)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateAsciiStream(String s, InputStream inputstream, long l)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateAsciiStream(String s, InputStream inputstream)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateBinaryStream(int i, InputStream inputstream, long l)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateBinaryStream(int i, InputStream inputstream)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateBinaryStream(String s, InputStream inputstream, long l)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateBinaryStream(String s, InputStream inputstream)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateBlob(int i, InputStream inputstream, long l)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateBlob(int i, InputStream inputstream) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateBlob(String s, InputStream inputstream, long l)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateBlob(String s, InputStream inputstream)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateCharacterStream(int i, Reader reader, long l)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateCharacterStream(int i, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateCharacterStream(String s, Reader reader, long l)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateCharacterStream(String s, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateClob(int i, Reader reader, long l) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateClob(int i, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateClob(String s, Reader reader, long l) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateClob(String s, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateNCharacterStream(int i, Reader reader, long l)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateNCharacterStream(int i, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateNCharacterStream(String s, Reader reader, long l)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateNCharacterStream(String s, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

//	
//	public void updateNClob(int i, NClob nclob) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}

	
	public void updateNClob(int i, Reader reader, long l) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateNClob(int i, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
//	public void updateNClob(String s, NClob nclob) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}

	
	public void updateNClob(String s, Reader reader, long l)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateNClob(String s, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateNString(int i, String s) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void updateNString(String s, String s1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
//	public void updateRowId(int i, RowId rowid) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	
//	public void updateRowId(String s, RowId rowid) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	
//	public void updateSQLXML(int i, SQLXML sqlxml) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	
//	public void updateSQLXML(String s, SQLXML sqlxml) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}

	public XmlResultSet() {
		autoCoverDataType = false;
		rowCount = -1;
		presentRow = 0;
		resultSetMetaData = new XmlResultSetMetaData();
		rowCount = 0;
		rowList = new ArrayList();
	}

	public XmlResultSet(ResultSetMetaData resultsetmetadata) {
		autoCoverDataType = false;
		rowCount = -1;
		presentRow = 0;
		try {
			resultSetMetaData = new XmlResultSetMetaData(resultsetmetadata);
		} catch (SQLException sqlexception) {
			resultSetMetaData = new XmlResultSetMetaData();
		}
		rowCount = 0;
		rowList = new ArrayList();
	}

	private void initColumnNames() throws SQLException {
		int i = resultSetMetaData.getColumnCount();
		if (columnNames == null || columnNames.length != i) {
			columnNames = new String[i];
			for (int j = 0; j < i; j++)
				columnNames[j] = resultSetMetaData.getColumnName(j + 1);

		}
	}

	public XmlResultSet(InputStream inputstream,
			XmlResultHandlerBase xmlresulthandlerbase) {
		autoCoverDataType = false;
		rowCount = -1;
		presentRow = 0;
		resultSetMetaData = new XmlResultSetMetaData();
		rowCount = 0;
		rowList = new ArrayList();
		xmlStream = inputstream;
		xmlresulthandlerbase.setResultSet(this);
		try {
			xmlresulthandlerbase.parseStream(inputstream);
			initColumnNames();
			presentRow = 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		autoCoverDataType = true;
	}

	private static XmlResultHandlerBase getHander(String s) {
		XmlResultHandlerBase xmlresulthandlerbase = null;
		try {
			Class class1 = (Class) xmlParseHandleClassMap.get(s);
			if (class1 == null)
				throw new RuntimeException((new StringBuilder()).append(
						"Unsupport xmlDataType :").append(s).toString());
			xmlresulthandlerbase = (XmlResultHandlerBase) class1.newInstance();
		} catch (Exception exception) {
			throw new RuntimeException("Create handler Error!!", exception);
		}
		return xmlresulthandlerbase;
	}

	public XmlResultSet(InputStream inputstream, String s) {
		this(inputstream, getHander(s));
	}

	public XmlResultSet(InputStream inputstream) {
		this(inputstream, "DATAPACKET");
	}

	public void close() {
		if (xmlStream != null)
			try {
				xmlStream.close();
			} catch (Exception exception) {
			}
		rowList.clear();
	}

	public int getRowCount() {
		return rowCount;
	}

	public boolean isAutoCoverDataType() {
		return autoCoverDataType;
	}

	public int getRecordCount() {
		return rowCount;
	}

	public boolean next() throws SQLException {
		boolean flag = false;
		presentRow++;
		boolean flag1 = presentRow <= rowCount;
		if (flag1)
			columnValues = (Object[]) (Object[]) rowList.get(presentRow - 1);
		return flag1;
	}

	public void addRow(Object aobj[]) {
		rowList.add(((Object) (aobj)));
		rowCount++;
		presentRow = rowCount;
	}

	public String getString(int i) throws SQLException {
		Object obj = getOriginalObject(i);
		if (obj == null)
			return (String) obj;
		if (obj instanceof String)
			return (String) obj;
		if (obj instanceof BigDecimal)
			return obj.toString();
		if (obj instanceof byte[])
			return new String((byte[]) (byte[]) obj);
		if (obj instanceof SerialClob) {
			SerialClob serialclob = (SerialClob) obj;
 
			if((int) serialclob.length()>0)
			return serialclob.getSubString(1L, (int) serialclob.length());
			else
				return "";
		}
		if (obj instanceof SerialBlob) {
			SerialBlob serialblob = (SerialBlob) obj;
			if((int) serialblob.length()>0)
			return new String(serialblob
					.getBytes(1L, (int) serialblob.length()));
			else {
				return "";
			}
		} else {
			return (String) PropertyFieldUtils.coverTo(obj, String.class);
		}
	}

	public boolean wasNull() throws SQLException {
		return lastReadIsNull;
	}

	public boolean getBoolean(int i) throws SQLException {
		Boolean boolean1 = (Boolean) getObject(i, Boolean.class);
		return boolean1 != null ? boolean1.booleanValue() : false;
	}

	public byte getByte(int i) throws SQLException {
		Object obj = getOriginalObject(i);
		if (obj == null)
			return 0;
		if (obj instanceof Byte)
			return ((Byte) obj).byteValue();
		if (obj instanceof BigDecimal)
			return ((BigDecimal) obj).toString().getBytes()[0];
		if (obj instanceof String)
			return ((String) obj).getBytes()[0];
		if (obj instanceof SerialBlob) {
			SerialBlob serialblob = (SerialBlob) obj;
			return serialblob.getBytes(0L, 1)[0];
		}
		if (obj instanceof SerialClob) {
			SerialClob serialclob = (SerialClob) obj;
			return serialclob.getSubString(0L, 1).getBytes()[0];
		} else {
			throw new SQLException("Fail to convert to internal representation");
		}
	}

	public short getShort(int i) throws SQLException {
		Short short1 = (Short) getObject(i, Short.class);
		if (short1 == null)
			return 0;
		else
			return short1.shortValue();
	}

	public int getInt(int i) throws SQLException {
		Integer integer = (Integer) getObject(i, Integer.class);
		if (integer == null)
			return 0;
		else
			return integer.intValue();
	}

	public long getLong(int i) throws SQLException {
		Long long1 = (Long) getObject(i, Long.class);
		if (long1 == null)
			return 0L;
		else
			return long1.longValue();
	}

	public float getFloat(int i) throws SQLException {
		Float float1 = (Float) getObject(i, Float.class);
		if (float1 == null)
			return 0.0F;
		else
			return float1.floatValue();
	}

	public double getDouble(int i) throws SQLException {
		Double double1 = (Double) getObject(i, Double.class);
		if (double1 == null)
			return 0.0D;
		else
			return double1.doubleValue();
	}

	public BigDecimal getBigDecimal(int i, int j) throws SQLException {
		return getBigDecimal(i);
	}

	public byte[] getBytes(int i) throws SQLException {
		Object obj = getOriginalObject(i);
		if (obj == null)
			return (byte[]) (byte[]) obj;
		if (obj instanceof byte[])
			return (byte[]) (byte[]) obj;
		if (obj instanceof String)
			return ((String) obj).getBytes();
		if (obj instanceof BigDecimal)
			return ((BigDecimal) obj).toString().getBytes();
		if (obj instanceof SerialBlob) {
			SerialBlob serialblob = (SerialBlob) obj;
			return serialblob.getBytes(0L, (int) serialblob.length());
		}
		if (obj instanceof SerialClob) {
			SerialClob serialclob = (SerialClob) obj;
			return serialclob.getSubString(0L, (int) serialclob.length())
					.getBytes();
		} else {
			throw new SQLException("Fail to convert to internal representation");
		}
	}

	public Date getDate(int i) throws SQLException {
		Date date = (Date) getObject(i, java.sql.Date.class);
		return date;
	}

	public Time getTime(int i) throws SQLException {
		Time time = (Time) getObject(i, java.sql.Time.class);
		return time;
	}

	public Timestamp getTimestamp(int i) throws SQLException {
		Timestamp timestamp = (Timestamp) getObject(i, Timestamp.class);
		return timestamp;
	}

	private synchronized InputStream getStream(int i) throws SQLException {
		Object obj = getOriginalObject(i);
		if (obj == null)
			return null;
		if (obj instanceof InputStream)
			return (InputStream) obj;
		if (obj instanceof String)
			return new ByteArrayInputStream(((String) obj).getBytes());
		if (obj instanceof byte[])
			return new ByteArrayInputStream((byte[]) (byte[]) obj);
		if (obj instanceof SerialClob)
			return ((SerialClob) obj).getAsciiStream();
		if (obj instanceof SerialBlob)
			return ((SerialBlob) obj).getBinaryStream();
		else
			throw new SQLException(
					"Could not convert the column into a stream type");
	}

	public InputStream getAsciiStream(int i) throws SQLException {
		return getStream(i);
	}

	public InputStream getUnicodeStream(int i) throws SQLException {
		return getAsciiStream(i);
	}

	public InputStream getBinaryStream(int i) throws SQLException {
		return getAsciiStream(i);
	}

	private int getColumnIndex(String s) throws SQLException {
		initColumnNames();
		int i = -1;
		for (int j = 0; j < columnNames.length; j++)
			if (s.equalsIgnoreCase(columnNames[j]))
				i = j;

		if (i == -1)
			throw new SQLException((new StringBuilder()).append("Column ")
					.append(s).append(" not found.").toString());
		else
			return i + 1;
	}

	public String getString(String s) throws SQLException {
		return getString(getColumnIndex(s));
	}

	public boolean getBoolean(String s) throws SQLException {
		return getBoolean(getColumnIndex(s));
	}

	public byte getByte(String s) throws SQLException {
		return getByte(getColumnIndex(s));
	}

	public short getShort(String s) throws SQLException {
		return getShort(getColumnIndex(s));
	}

	public int getInt(String s) throws SQLException {
		return getInt(getColumnIndex(s));
	}

	public long getLong(String s) throws SQLException {
		return getLong(getColumnIndex(s));
	}

	public float getFloat(String s) throws SQLException {
		return getFloat(getColumnIndex(s));
	}

	public double getDouble(String s) throws SQLException {
		return getDouble(getColumnIndex(s));
	}

	public BigDecimal getBigDecimal(String s, int i) throws SQLException {
		return getBigDecimal(s);
	}

	public byte[] getBytes(String s) throws SQLException {
		return getBytes(getColumnIndex(s));
	}

	public Date getDate(String s) throws SQLException {
		return getDate(getColumnIndex(s));
	}

	public Time getTime(String s) throws SQLException {
		return getTime(getColumnIndex(s));
	}

	public Timestamp getTimestamp(String s) throws SQLException {
		return getTimestamp(getColumnIndex(s));
	}

	public InputStream getAsciiStream(String s) throws SQLException {
		return getAsciiStream(getColumnIndex(s));
	}

	public InputStream getUnicodeStream(String s) throws SQLException {
		return getAsciiStream(s);
	}

	public InputStream getBinaryStream(String s) throws SQLException {
		return getAsciiStream(s);
	}

 

 


 



 
 


 

 

 



 





	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}





	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.clearWarnings() unsupported");
	}

	public String getCursorName() throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getCursorName() unsupported");
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		if (resultSetMetaData == null)
			resultSetMetaData = new XmlResultSetMetaData();
		return resultSetMetaData;
	}

	private Object getOriginalObject(int i) throws SQLException {
		Object obj = null;
		if (columnValues != null) {
			if (i > 0 && i <= resultSetMetaData.getColumnCount())
				if (i <= columnValues.length)
					obj = columnValues[i - 1];
				else
					resultSetMetaData.getCalculateValue(i);
			return obj;
		} else {
			throw new SQLException("No data !call next fisrt");
		}
	}

	private Object getObject(int i, Class class1) throws SQLException {
		Object obj = getOriginalObject(i);
		if (obj != null) {
			if (autoCoverDataType && class1 == null)
				class1 = resultSetMetaData.getColumnClass(i);
			if (class1 != null)
				try {
					obj = PropertyFieldUtils.coverTo(obj, class1);
				} catch (Exception exception) {
					// logger.debug("cover error", exception);
					exception.printStackTrace();
					return null;
				}
		}
		return obj;
	}

	public Object getObject(int i) throws SQLException {
		return getObject(i, (Class) null);
	}

	public Object getObject(String s) throws SQLException {
		return getObject(getColumnIndex(s));
	}

	public int findColumn(String s) throws SQLException {
		return getColumnIndex(s);
	}

	public Reader getCharacterStream(int i) throws SQLException {
		String s = getString(i);
		return s != null ? new StringReader(s) : null;
	}

	public Reader getCharacterStream(String s) throws SQLException {
		return getCharacterStream(getColumnIndex(s));
	}

	public BigDecimal getBigDecimal(int i) throws SQLException {
		BigDecimal bigdecimal = (BigDecimal) getObject(i, BigDecimal.class);
		return bigdecimal;
	}

	public BigDecimal getBigDecimal(String s) throws SQLException {
		return getBigDecimal(getColumnIndex(s));
	}

	public boolean isBeforeFirst() throws SQLException {
		return presentRow == 0;
	}

	public boolean isAfterLast() throws SQLException {
		return presentRow == rowCount + 1;
	}

	public boolean isFirst() throws SQLException {
		return presentRow == 1;
	}

	public boolean isLast() throws SQLException {
		return presentRow == rowCount;
	}

	public void beforeFirst() throws SQLException {
		presentRow = 0;
		columnValues = null;
	}

	public void afterLast() throws SQLException {
		presentRow = rowCount + 1;
	}

	public boolean first() throws SQLException {
		return absolute(1);
	}

	public boolean last() throws SQLException {
		return absolute(rowCount);
	}

	public int getRow() throws SQLException {
		if (presentRow > rowCount)
			return rowCount;
		else
			return presentRow;
	}

	public boolean absolute(int i) throws SQLException {
		if (i == 0 || Math.abs(i) > rowCount) {
			return false;
		} else {
			presentRow = i < 0 ? rowCount + i + 1 : i;
			columnValues = (Object[]) (Object[]) rowList.get(presentRow - 1);
			return true;
		}
	}

	public boolean relative(int i) throws SQLException {
		return absolute(presentRow + i);
	}

	public boolean previous() throws SQLException {
		if (presentRow > 0) {
			presentRow--;
			absolute(presentRow);
		}
		return false;
	}

	public void setFetchDirection(int i) throws SQLException {
		if (1000 != i)
			throw new UnsupportedOperationException(
					"ResultSet.setFetchDirection(int) unsupported");
		else
			return;
	}

	public int getFetchDirection() throws SQLException {
		return 1000;
	}

	public void setFetchSize(int i) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.setFetchSize(int) unsupported");
	}

	public int getFetchSize() throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getFetchSize() unsupported");
	}

	public int getType() throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getType() unsupported");
	}

	public int getConcurrency() throws SQLException {
		return 1007;
	}

	public boolean rowUpdated() throws SQLException {
		return false;
	}

	public boolean rowInserted() throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.rowInserted() unsupported");
	}

	public boolean rowDeleted() throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.rowDeleted() unsupported");
	}

	private void checkUpdate(int i) throws SQLException {
		if (columnValues != null) {
			int j = resultSetMetaData.getColumnCount();
			if (i < 1 || i > j)
				throw new SQLException((new StringBuilder()).append(
						"Index Error").append(i).toString());
			if (i > columnValues.length) {
				int k = rowList.indexOf(((Object) (columnValues)));
				if (k >= 0) {
					Object aobj[] = new Object[j];
					System.arraycopy(((Object) (columnValues)), 0,
							((Object) (aobj)), 0, columnValues.length);
					columnValues = aobj;
					rowList.set(k, ((Object) (aobj)));
				}
			}
		} else {
			throw new SQLException("next /prev  first");
		}
	}

	public void updateNull(int i) throws SQLException {
		checkUpdate(i);
		columnValues[i - 1] = null;
	}

	public void updateBoolean(int i, boolean flag) throws SQLException {
		checkUpdate(i);
		columnValues[i - 1] = Boolean.valueOf(flag);
	}

	public void updateByte(int i, byte byte0) throws SQLException {
		checkUpdate(i);
		columnValues[i - 1] = new Byte(byte0);
	}

	public void updateShort(int i, short word0) throws SQLException {
		checkUpdate(i);
		columnValues[i - 1] = new Short(word0);
	}

	public void updateInt(int i, int j) throws SQLException {
		checkUpdate(i);
		columnValues[i - 1] = new Integer(j);
	}

	public void updateLong(int i, long l) throws SQLException {
		checkUpdate(i);
		columnValues[i - 1] = new Long(l);
	}

	public void updateFloat(int i, float f) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.updateFloat(int, float) unsupported");
	}

	public void updateDouble(int i, double d) throws SQLException {
		checkUpdate(i);
		columnValues[i - 1] = new Double(d);
	}

	public void updateBigDecimal(int i, BigDecimal bigdecimal)
			throws SQLException {
		checkUpdate(i);
		columnValues[i - 1] = bigdecimal;
	}

	public void updateString(int i, String s) throws SQLException {
		checkUpdate(i);
		columnValues[i - 1] = s;
	}

	public void updateBytes(int i, byte abyte0[]) throws SQLException {
		checkUpdate(i);
		columnValues[i - 1] = abyte0;
	}

	public void updateDate(int i, Date date) throws SQLException {
		checkUpdate(i);
		columnValues[i - 1] = date;
	}

	public void updateTime(int i, Time time) throws SQLException {
		checkUpdate(i);
		columnValues[i - 1] = time;
	}

	public void updateTimestamp(int i, Timestamp timestamp) throws SQLException {
		checkUpdate(i);
		columnValues[i - 1] = timestamp;
	}

	public void updateAsciiStream(int i, InputStream inputstream, int j)
			throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.updateAsciiStream (int, InputStream, int) unsupported");
	}

	public void updateBinaryStream(int i, InputStream inputstream, int j)
			throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.updateBinaryStream(int, InputStream, int) unsupported");
	}

	public void updateCharacterStream(int i, Reader reader, int j)
			throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.updateCharacterStream(int, Reader, int) unsupported");
	}

	public void updateObject(int i, Object obj, int j) throws SQLException {
		checkUpdate(i);
		columnValues[i - 1] = obj;
	}

	public void updateObject(int i, Object obj) throws SQLException {
		checkUpdate(i);
		columnValues[i - 1] = obj;
	}

	public void updateNull(String s) throws SQLException {
		updateNull(getColumnIndex(s));
	}

	public void updateBoolean(String s, boolean flag) throws SQLException {
		updateBoolean(getColumnIndex(s), flag);
	}

	public void updateByte(String s, byte byte0) throws SQLException {
		updateByte(getColumnIndex(s), byte0);
	}

	public void updateShort(String s, short word0) throws SQLException {
		updateShort(getColumnIndex(s), word0);
	}

	public void updateInt(String s, int i) throws SQLException {
		updateInt(getColumnIndex(s), i);
	}

	public void updateLong(String s, long l) throws SQLException {
		updateLong(getColumnIndex(s), l);
	}

	public void updateFloat(String s, float f) throws SQLException {
		updateFloat(getColumnIndex(s), f);
	}

	public void updateDouble(String s, double d) throws SQLException {
		updateDouble(getColumnIndex(s), d);
	}

	public void updateBigDecimal(String s, BigDecimal bigdecimal)
			throws SQLException {
		updateBigDecimal(getColumnIndex(s), bigdecimal);
	}

	public void updateString(String s, String s1) throws SQLException {
		updateString(getColumnIndex(s), s1);
	}

	public void updateBytes(String s, byte abyte0[]) throws SQLException {
		updateBytes(getColumnIndex(s), abyte0);
	}

	public void updateDate(String s, Date date) throws SQLException {
		updateDate(getColumnIndex(s), date);
	}

	public void updateTime(String s, Time time) throws SQLException {
		updateTime(getColumnIndex(s), time);
	}

	public void updateTimestamp(String s, Timestamp timestamp)
			throws SQLException {
		updateTimestamp(getColumnIndex(s), timestamp);
	}

	public void updateAsciiStream(String s, InputStream inputstream, int i)
			throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.updateAsciiStream(String, InputStream, int) unsupported");
	}

	public void updateBinaryStream(String s, InputStream inputstream, int i)
			throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.updateBinaryStream(String, InputStream, int) unsupported");
	}

	public void updateCharacterStream(String s, Reader reader, int i)
			throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.updateCharacterStream(String, Reader, int) unsupported");
	}

	public void updateObject(String s, Object obj, int i) throws SQLException {
		updateObject(getColumnIndex(s), obj);
	}

	public void updateObject(String s, Object obj) throws SQLException {
		updateObject(getColumnIndex(s), obj);
	}

	public void insertRow() throws SQLException {
		addRow(columnValues);
	}

	public void updateRow() throws SQLException {
	}

	public void deleteRow() throws SQLException {
		rowList.remove(((Object) (columnValues)));
		rowCount = rowList.size();
	}

	public void refreshRow() throws SQLException {
	}

	public void cancelRowUpdates() throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.cancelRowUpdates() unsupported");
	}

	public void moveToInsertRow() throws SQLException {
		columnValues = new Object[getMetaData().getColumnCount()];
	}

	public void moveToCurrentRow() throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.moveToeCurrentRow() unsupported");
	}

	public Statement getStatement() throws SQLException {
		return null;
	}

	public Object getObject(int i, Map map) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getObject(int, Map) unsupported");
	}

	public Ref getRef(int i) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getRef(int) unsupported");
	}

	public Blob getBlob(int i) throws SQLException {
		Object obj = getOriginalObject(i);
		if (obj != null && Blob.class.isInstance(obj))
			return (Blob) obj;
		else
			return null;
	}

	public Clob getClob(int i) throws SQLException {
		Object obj = getOriginalObject(i);
		if (obj != null && Clob.class.isInstance(obj))
			return (Clob) obj;
		else
			return null;
	}

	public Array getArray(int i) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getArray(int) unsupported");
	}

	public Object getObject(String s, Map map) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getObject(String, Map) unsupported");
	}

	public Ref getRef(String s) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getRef(String) unsupported");
	}

	public Blob getBlob(String s) throws SQLException {
		return getBlob(getColumnIndex(s));
	}

	public Clob getClob(String s) throws SQLException {
		return getClob(getColumnIndex(s));
	}

	public Array getArray(String s) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getArray(String) unsupported");
	}

	public Date getDate(int i, Calendar calendar) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getDate(int, Calendar) unsupported");
	}

	public Date getDate(String s, Calendar calendar) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getDate(String, Calendar) unsupported");
	}

	public Time getTime(int i, Calendar calendar) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getTime(int, Calendar) unsupported");
	}

	public Time getTime(String s, Calendar calendar) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getTime(String, Calendar) unsupported");
	}

	public Timestamp getTimestamp(int i, Calendar calendar) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getTimestamp(int, Calendar) unsupported");
	}

	public Timestamp getTimestamp(String s, Calendar calendar)
			throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getTimestamp(String, Calendar) unsupported");
	}

	public URL getURL(int i) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getURL(int) unsupported");
	}

	public URL getURL(String s) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.getURL(String) unsupported");
	}

	public void updateRef(int i, Ref ref) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.updateRef(int,java.sql.Ref) unsupported");
	}

	public void updateRef(String s, Ref ref) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.updateRef(String,java.sql.Ref) unsupported");
	}

	public void updateBlob(int i, Blob blob) throws SQLException {
		Object obj = null;
		if (blob != null) {
			SerialBlob serialblob;
			if (SerialBlob.class.isInstance(blob))
				serialblob = (SerialBlob) blob;
			else
				serialblob = new SerialBlob(blob);
			updateObject(i, serialblob);
		}
	}

	public void updateBlob(String s, Blob blob) throws SQLException {
		updateBlob(getColumnIndex(s), blob);
	}

	public void updateClob(int i, Clob clob) throws SQLException {
		Object obj = null;
		if (clob != null) {
			SerialClob serialclob;
			if (SerialClob.class.isInstance(clob))
				serialclob = (SerialClob) clob;
			else
				serialclob = new SerialClob(clob);
			updateObject(i, serialclob);
		}
	}

	public void updateClob(String s, Clob clob) throws SQLException {
		updateClob(getColumnIndex(s), clob);
	}

	public void updateArray(int i, Array array) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.updateArray(int,java.sql.Array) unsupported");
	}

	public void updateArray(String s, Array array) throws SQLException {
		throw new UnsupportedOperationException(
				"ResultSet.updateArray(String,java.sql.Array) unsupported");
	}

	private static int writeResult(String s, XmlWriter xmlwriter,
			ResultSet resultset, int i) throws IOException, SQLException {
		XmlResultHandlerBase xmlresulthandlerbase = getHander(s);
		xmlresulthandlerbase.setResultSet(resultset);

		return xmlresulthandlerbase.writeXML(xmlwriter, i);
	}

	public static String exportToString(String s, ResultSet resultset)
			throws IOException, SQLException {
		StringBuffer stringbuffer = new StringBuffer();
		XmlWriter xmlwriter = new XmlWriter(stringbuffer);
		writeResult(s, xmlwriter, resultset, -1);
		return stringbuffer.toString();
	}

	public static String exportToString(ResultSet resultset)
			throws IOException, SQLException {
		return exportToString("DATAPACKET", resultset);
	}

	public static int exportToStream(ResultSet resultset,
			OutputStream outputstream, int i) throws IOException, SQLException {
		return exportToStream("DATAPACKET", resultset, outputstream, null, i);
	}

	public static void exportToStream(ResultSet resultset,
			OutputStream outputstream, String s) throws IOException,
			SQLException {
		exportToStream("DATAPACKET", resultset, outputstream, s, -1);
	}

	public static int exportToStream(ResultSet resultset,
			OutputStream outputstream, String s, int i) throws IOException,
			SQLException {
		return exportToStream("DATAPACKET", resultset, outputstream, s, i);
	}

	public static int exportToStream(String s, ResultSet resultset,
			OutputStream outputstream, String s1, int i) throws IOException,
			SQLException {
		XmlWriter xmlwriter = new XmlWriter(outputstream, s1);
		return writeResult(s, xmlwriter, resultset, i);
	}

	public synchronized void populate(ResultSet resultset) throws SQLException {
		resultSetMetaData = new XmlResultSetMetaData(resultset.getMetaData());
		if (XmlResultSet.class.isInstance(resultset))
			setAutoCoverDataType(((XmlResultSet) resultset).autoCoverDataType);
		else
			setAutoCoverDataType(false);
		rowCount = 0;
		rowList = new ArrayList();
		int i = resultSetMetaData.getColumnCount();
		boolean aflag[] = new boolean[i + 1];
		for (int j = 0; j < i; j++)
			aflag[j + 1] = "java.sql.Timestamp".equals(resultSetMetaData
					.getColumnClassName(j + 1));

		Object aobj[];
		for (; resultset.next(); addRow(aobj)) {
			aobj = new Object[i];
			for (int k = 0; k < i; k++) {
				if (aflag[k + 1])
					aobj[k] = resultset.getTimestamp(k + 1);
				else
					aobj[k] = resultset.getObject(k + 1);
				if (aobj[k] instanceof Blob) {
					if (!BlobField.class.isInstance(aobj[k]))
						aobj[k] = new SerialBlob((Blob) aobj[k]);
					continue;
				}
				if (aobj[k] instanceof Clob)
					aobj[k] = new SerialClob((Clob) aobj[k]);
			}

		}

		beforeFirst();
	}

	public CachedRow[] getRows() {
		int i = rowList.size();
		CachedRow acachedrow[] = new CachedRow[i];
		for (int j = 0; j < i; j++)
			acachedrow[j] = new CachedRow(this, (Object[]) (Object[]) rowList
					.get(j));

		return acachedrow;
	}

	public void addRows(CachedRow acachedrow[]) {
		int i = acachedrow.length;
		for (int j = 0; j < i; j++)
			rowList.add(((Object) (acachedrow[j].getColumnData())));

	}

	public void truncate() {
		rowList.clear();
	}

	public void sortBy(RowComparator rowcomparator) throws SQLException {
		CachedRow acachedrow[] = getRows();
		Arrays.sort(acachedrow, 0, acachedrow.length, rowcomparator);
		truncate();
		addRows(acachedrow);
	}

	public void sortBy(int i, boolean flag) throws SQLException {
		int j = getMetaData().getColumnCount();
		if (i < 1 || i > j) {
			throw new SQLException((new StringBuilder()).append(
					"column not Finded!no=").append(i).toString());
		} else {
			sortBy(new RowComparator(i, flag));
			return;
		}
	}

	public void sortBy(String s) throws SQLException {
		StringTokenizer stringtokenizer = new StringTokenizer(s.toUpperCase(),
				" \t\n\r\f,", false);
		ArrayList arraylist = new ArrayList();
		Object obj = null;
		boolean flag = false;
		int i = -1;
		byte byte0 = 1;
		while (stringtokenizer.hasMoreTokens()) {
			String s1 = stringtokenizer.nextToken();
			if (s1.equals("ASC"))
				byte0 = 1;
			else if (s1.equals("DESC")) {
				byte0 = 2;
			} else {
				if (i != -1) {
					CompareItem compareitem = new CompareItem(i, byte0);
					arraylist.add(compareitem);
				}
				i = getColumnIndex(s1);
				byte0 = 1;
			}
		}
		if (i != -1) {
			CompareItem compareitem1 = new CompareItem(i, byte0);
			arraylist.add(compareitem1);
		}
		CompareItem acompareitem[] = new CompareItem[arraylist.size()];
		arraylist.toArray(acompareitem);
		RowComparator rowcomparator = new RowComparator(acompareitem);
		sortBy(rowcomparator);
	}

	public boolean deleteRow(String s, Object obj) throws SQLException {
		if (find(new String[] { s }, new Object[] { obj })) {
			deleteRow();
			return true;
		} else {
			return false;
		}
	}

	public boolean find(String s, Object obj) throws SQLException {
		return find(new String[] { s }, new Object[] { obj });
	}

	public boolean find(String as[], Object aobj[]) throws SQLException {
		CompareItem acompareitem[] = new CompareItem[as.length];
		RowComparator rowcomparator = new RowComparator(acompareitem);
		Object aobj1[] = new Object[resultSetMetaData.getColumnCount()];
		for (int i = 0; i < as.length; i++) {
			int j = findColumn(as[i]);
			aobj1[j - 1] = aobj[i];
			acompareitem[i] = new CompareItem(j, 1);
		}

		beforeFirst();
		CachedRow cachedrow = new CachedRow(this, columnValues);
		CachedRow cachedrow1 = new CachedRow(this, aobj1);
		while (next()) {
			cachedrow.setColumnData(columnValues);
			if (rowcomparator.compare(cachedrow, cachedrow1) == 0)
				return true;
		}
		return false;
	}

	public synchronized void union(ResultSet resultset) throws SQLException {
		ResultSetMetaData resultsetmetadata = resultset.getMetaData();
		int i = resultsetmetadata.getColumnCount();
		boolean aflag[] = new boolean[i + 1];
		int ai[] = new int[i + 1];
		for (int j = 1; j <= i; j++) {
			aflag[j] = "java.sql.Timestamp".equals(resultSetMetaData
					.getColumnClassName(j));
			try {
				ai[j] = getColumnIndex(resultSetMetaData.getColumnName(j));
			} catch (Exception exception) {
				ai[j] = -1;
			}
		}

		Object obj = null;
		for (; resultset.next(); insertRow()) {
			moveToInsertRow();
			for (int k = 1; k <= i; k++) {
				int l = ai[k];
				if (l <= 0)
					continue;
				Object obj1 = null;
				try {
					if (aflag[k])
						obj1 = resultset.getTimestamp(k);
					else
						obj1 = resultset.getObject(k);
					if (obj1 instanceof Blob) {
						if (!BlobField.class.isInstance(obj1))
							obj1 = new SerialBlob((Blob) obj1);
					} else if (obj1 instanceof Clob)
						obj1 = new SerialClob((Clob) obj1);
				} catch (Exception exception1) {
					obj1 = resultset.getObject(k);
				} catch (AbstractMethodError abstractmethoderror) {
					obj1 = resultset.getObject(k);
				}
				updateObject(l, obj1);
			}

		}

	}

	public void setReadOnly(boolean flag) throws SQLException {
	}

	public boolean getReadOnly() throws SQLException {
		return false;
	}

	public static String checkXmlType(byte abyte0[]) {
		String s = null;
		try {
			Object aobj[] = xmlParseHandleClassMap.values().toArray();
			Object obj = null;
			Object obj1 = null;
			int i = 0;
			do {
				if (i >= aobj.length)
					break;
				Class class1 = (Class) aobj[i];
				XmlResultHandlerBase xmlresulthandlerbase = (XmlResultHandlerBase) class1
						.newInstance();
				if (ByteArrayUtils.indexOf(abyte0, xmlresulthandlerbase
						.getXmlCharacter()) != -1) {
					s = xmlresulthandlerbase.getXmlType();
					break;
				}
				i++;
			} while (true);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return s;
	}

	public static void registerXmlResultHandler(String s, Class class1) {
		if (xmlParseHandleClassMap != null)
			xmlParseHandleClassMap.put(s, class1);
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public void setAutoCoverDataType(boolean flag) {
		autoCoverDataType = flag;
	}

	private static final long serialVersionUID = 1L;
	private boolean autoCoverDataType;
	public static String dataEncoding = "GB2312";
	protected XmlResultSetMetaData resultSetMetaData;
	protected int rowCount;
	protected transient InputStream xmlStream;
	public static final String DATAPACKET_TYPE = "DATAPACKET";
	public static final String RESULT_TYPE = "RESULT";
	public static final String DBSET_TYPE = "DBSET";

	private static Map xmlParseHandleClassMap;
	private int presentRow;
	private ArrayList rowList;
	private Object columnValues[];
	private String columnNames[];
	private boolean lastReadIsNull;

	static {
		xmlParseHandleClassMap = new HashMap();
		xmlParseHandleClassMap.put("DATAPACKET", DataPackageHandlerBase.class);
		xmlParseHandleClassMap.put("RESULT", ResultHandlerBase.class);
		xmlParseHandleClassMap.put("DBSET", DBSetHandlerBase.class);
	}

	
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
