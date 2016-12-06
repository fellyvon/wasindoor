 
package com.aiyc.server.standalone.db.homes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;

import com.aiyc.server.standalone.core.measure.GSMReading;

 
public class GSMReadingHome extends EntityHome<GSMReading> {
	
	private static final String[] TableCols = {"cellId", "areaId", "signalStrength", "MCC", "MNC","networkName"};
	private static final String TableName = "gsmreading"; 
	private static final String TableIdCol = "gsmReadingId";
	
	
	public GSMReadingHome() {
		super();
	}
	
	/**
	 * @see EntityHome#getTableName()
	 */
	@Override
	protected String getTableName() {
		return TableName;
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
	 * @see EntityHome#parseResultRow(ResultSet)
	 */
	@Override
	public GSMReading parseResultRow(final ResultSet rs, int fromIndex) throws SQLException{
		GSMReading reading = new GSMReading();
		
		try {
			reading.setId(rs.getInt(fromIndex));
			reading.setCellId(rs.getString(fromIndex + 1));
			reading.setAreaId(rs.getString(fromIndex + 2));
			reading.setSignalStrength(rs.getString(fromIndex + 3));
			reading.setMCC(rs.getString(fromIndex + 4));
			reading.setMNC(rs.getString(fromIndex + 5));
			reading.setNetworkName(rs.getString(fromIndex + 6));
			
		
		} catch (SQLException e) {
			log.log(Level.SEVERE, "parseResultRow failed: " + e.getMessage(), e);
			throw e;
		}
		
		return reading;
	}

	/**
	 * @see EntityHome#fillInStatement(PreparedStatement, org.icscn.server.standalone.db.IEntity, int)
	 */
	@Override
	public int fillInStatement(PreparedStatement ps, GSMReading t, int fromIndex)
			throws SQLException {
		return fillInStatement(ps, new Object[] {t.getCellId(), t.getAreaId(), t.getSignalStrength(), t.getMCC(), t.getMNC(), t.getNetworkName()},   
				new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR},
				fromIndex);
	}


	
	
}
