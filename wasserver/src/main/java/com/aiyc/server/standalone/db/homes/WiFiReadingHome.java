 
package com.aiyc.server.standalone.db.homes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;

import com.aiyc.server.standalone.core.measure.WiFiReading;

/**
 
 */
public class WiFiReadingHome extends EntityHome<WiFiReading> {

	
	private static final String[] TableCols = {"bssid", "ssid", "rssi", "wepEnabled", "isInfrastructure"};
	private static final String TableName = "wifireading"; 
	private static final String TableIdCol = "wifiReadingId";
	
	
	public WiFiReadingHome() {
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
	public synchronized WiFiReading parseResultRow(final ResultSet rs, int fromIndex) throws SQLException{
		WiFiReading reading = new WiFiReading();
		
		try {
			reading.setId(rs.getInt(fromIndex));
			reading.setBssid(rs.getString(fromIndex + 1));
			reading.setSsid(rs.getString(fromIndex + 2));
			reading.setRssi(rs.getInt(fromIndex + 3));
			reading.setWepEnabled(rs.getBoolean(fromIndex + 4));
			reading.setInfrastructure(rs.getBoolean(fromIndex + 5));
		
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
	public int fillInStatement(PreparedStatement ps, WiFiReading t, int fromIndex)
			throws SQLException {
		return fillInStatement(ps, new Object[] {t.getBssid(), t.getSsid(), t.getRssi(), t.isWepEnabled(), t.isInfrastructure()},   
				new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.TINYINT, Types.TINYINT}, fromIndex);
	}

	
	
	

}
