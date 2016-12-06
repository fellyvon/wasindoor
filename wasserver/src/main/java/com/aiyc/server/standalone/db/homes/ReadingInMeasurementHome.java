package com.aiyc.server.standalone.db.homes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;

import com.aiyc.server.standalone.core.ReadingInMeasurement;

public class ReadingInMeasurementHome extends EntityHome<ReadingInMeasurement> {
	
	private static final String[] TableCols = {"measurementId", "readingId", "readingClassName"};
	private static final String TableName = "readinginmeasurement"; 
	private static final String TableIdCol = "id";
	
	public ReadingInMeasurementHome() {
		super();
	}
	
	/**
	 * @see EntityHome#getTableIdCol()
	 */
	@Override
	protected String getTableIdCol() {
		return TableIdCol;
	}

	/**
	 * @see EntityHome#getTableCols()
	 */
	@Override
	protected String[] getTableCols() {
		return TableCols;
	}

	/**
	 * @see EntityHome#getTableName()
	 */
	@Override
	protected String getTableName() {
		return TableName;
	}

	/**
	 * @see EntityHome#parseResultRow(ResultSet, int)
	 */
	@Override
	public ReadingInMeasurement parseResultRow(ResultSet rs, int fromIndex)
			throws SQLException {
		ReadingInMeasurement rinm = new ReadingInMeasurement();
		
		try {
			if (!rs.isAfterLast()) {
				rinm.setId(rs.getInt(fromIndex));
				rinm.setMeasurementId(rs.getInt(fromIndex + 1));
				rinm.setReadingId(rs.getInt(fromIndex + 2));
				rinm.setReadingClassName(rs.getString(fromIndex + 3));
			}
		} catch (SQLException e) {
			log.log(Level.SEVERE, "parseResultRow failed: " + e.getMessage(), e);
			throw e;
		}
		
		return rinm;
	}

	@Override
	public int fillInStatement(PreparedStatement ps, ReadingInMeasurement t, int fromIndex) throws SQLException {
		return fillInStatement(ps, new Object[] {t.getMeasurementId(), t.getReadingId(), t.getReadingClassName()}, new int[]{Types.INTEGER, Types.INTEGER, Types.VARCHAR}, fromIndex);
	}
	

}
