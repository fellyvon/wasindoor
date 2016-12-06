package com.aiyc.server.standalone.db;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aiyc.framework.component.CachedRowSet;
import com.aiyc.framework.utils.DateUtils;
 
public class DaoUtil {

	public  static String makePix(int num) {
		String s = "";
		for (int i = 0; i < num; i++) {
			s += "?,";
		}
		if (num > 0)
			return s.substring(0, s.length() - 1);
		return s;
	}

	public synchronized  static ResultSet queryData(String sql, Object[] paras)
			throws SQLException {
		if (sql != null)
			sql = sql.toLowerCase();
		Connection conn = DatabaseConnection.getInstance().getConnection();
		java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
		for (int i = 1; i <= paras.length; i++) {
			if (paras[i - 1] instanceof Integer) {
				stmt.setInt(i, ((Integer) paras[i - 1]).intValue());
			} else if (paras[i - 1] instanceof Double) {

				stmt.setDouble(i, ((Double) paras[i - 1]).doubleValue());
			} else if (paras[i - 1] instanceof Long) {

				stmt.setLong(i, ((Long) paras[i - 1]).longValue());
			} else if (paras[i - 1] instanceof Float) {

				stmt.setFloat(i, ((Float) paras[i - 1]).floatValue());
			} else if (paras[i - 1] instanceof Byte) {

				stmt.setByte(i, ((Byte) paras[i - 1]).byteValue());
			} else if (paras[i - 1] instanceof java.sql.Date) {

				stmt.setDate(i, ((java.sql.Date) paras[i - 1]));
			} else if (paras[i - 1] instanceof java.util.Date) {
				stmt.setDate(i, DateUtils
						.toSqlDate((java.util.Date) paras[i - 1]));
			} else if (paras[i - 1] instanceof BigDecimal) {
				stmt.setLong(i, ((BigDecimal) paras[i - 1]).longValue());

			} else if (paras[i - 1] instanceof Blob) {
				stmt.setBlob(i, (Blob) paras[i - 1]);
			} else if (paras[i - 1] instanceof Clob) {
				stmt.setClob(i, (Clob) paras[i - 1]);
			}

			else {
				stmt.setString(i, (String) paras[i - 1]);
			}

		}

		ResultSet rs = stmt.executeQuery();
		ResultSet localResultSet = rs;
		try {
			CachedRowSet localCachedRowSet = new CachedRowSet();
			localCachedRowSet.populate(localResultSet);
			return localCachedRowSet;
		} finally {
			close(localResultSet);
			close(stmt);
			close(conn);
		}
		// close(stmt);

	}

	public synchronized static int executeQuery(java.sql.PreparedStatement stmt, String sql,
			Object[] paras) throws SQLException {
		if (sql != null)
			sql = sql.toLowerCase();
		for (int i = 1; i <= paras.length; i++) {
			if (paras[i - 1] instanceof Integer) {
				stmt.setInt(i, ((Integer) paras[i - 1]).intValue());
			} else if (paras[i - 1] instanceof Double) {

				stmt.setDouble(i, ((Double) paras[i - 1]).doubleValue());
			} else if (paras[i - 1] instanceof Long) {

				stmt.setLong(i, ((Long) paras[i - 1]).longValue());
			} else if (paras[i - 1] instanceof Float) {

				stmt.setFloat(i, ((Float) paras[i - 1]).floatValue());
			} else if (paras[i - 1] instanceof Byte) {

				stmt.setByte(i, ((Byte) paras[i - 1]).byteValue());
			} else if (paras[i - 1] instanceof java.sql.Date) {

				stmt.setDate(i, ((java.sql.Date) paras[i - 1]));
			} else if (paras[i - 1] instanceof java.util.Date) {
				stmt.setDate(i, DateUtils
						.toSqlDate((java.util.Date) paras[i - 1]));
			} else if (paras[i - 1] instanceof BigDecimal) {
				stmt.setLong(i, ((BigDecimal) paras[i - 1]).longValue());

			} else if (paras[i - 1] instanceof Blob) {
				stmt.setBlob(i, (Blob) paras[i - 1]);
			} else if (paras[i - 1] instanceof Clob) {
				stmt.setClob(i, (Clob) paras[i - 1]);
			}

			else {
				stmt.setString(i, (String) paras[i - 1]);
			}

		}

		int result = -1;
		result = stmt.executeUpdate();

		close(stmt);
		return result;

	}

	public synchronized static int executeQuery(String sql, Object[] paras)
			throws SQLException {
		if (sql != null)
			sql = sql.toLowerCase();
		Connection conn = DatabaseConnection.getInstance().getConnection();
		java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
		for (int i = 1; i <= paras.length; i++) {
			if (paras[i - 1] instanceof Integer) {
				stmt.setInt(i, ((Integer) paras[i - 1]).intValue());
			} else if (paras[i - 1] instanceof Double) {

				stmt.setDouble(i, ((Double) paras[i - 1]).doubleValue());
			} else if (paras[i - 1] instanceof Long) {

				stmt.setLong(i, ((Long) paras[i - 1]).longValue());
			} else if (paras[i - 1] instanceof Float) {

				stmt.setFloat(i, ((Float) paras[i - 1]).floatValue());
			} else if (paras[i - 1] instanceof Byte) {

				stmt.setByte(i, ((Byte) paras[i - 1]).byteValue());
			} else if (paras[i - 1] instanceof java.sql.Date) {

				stmt.setDate(i, ((java.sql.Date) paras[i - 1]));
			} else if (paras[i - 1] instanceof java.util.Date) {
				stmt.setDate(i, DateUtils
						.toSqlDate((java.util.Date) paras[i - 1]));
			} else if (paras[i - 1] instanceof BigDecimal) {
				stmt.setLong(i, ((BigDecimal) paras[i - 1]).longValue());

			} else if (paras[i - 1] instanceof Blob) {
				stmt.setBlob(i, (Blob) paras[i - 1]);
			} else if (paras[i - 1] instanceof Clob) {
				stmt.setClob(i, (Clob) paras[i - 1]);
			}

			else {
				stmt.setString(i, (String) paras[i - 1]);
			}

		}

		int result = -1;
		result = stmt.executeUpdate();
		close(conn);
		close(stmt);
		return result;
	}

	public synchronized static void close(ResultSet rs) throws SQLException {
		rs.close();
	}

	public synchronized static void close(PreparedStatement rs) throws SQLException {
		rs.close();
	}

	public synchronized  static void close(Connection rs) throws SQLException {
		rs.close();
	}

}
