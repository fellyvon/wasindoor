 
package com.aiyc.server.standalone.db.homes;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.logging.Level;

import com.aiyc.server.standalone.core.Map;
import com.aiyc.server.standalone.db.HomeFactory;

 
public class MapHome extends EntityHome<Map> {
	
	public static final String[] TableCols = {"mapName", "mapURL"};
	public static final String TableName = "map"; 
	private static final String TableIdCol = "mapId";
	


	public MapHome() {
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
	 * @see EntityHome#parseResultRow(ResultSet, int)
	 */
	@Override
	public Map parseResultRow(final ResultSet rs, int fromIndex) throws SQLException{
		Map map = new Map();
		
		try {
			map.setId(rs.getInt(fromIndex));
			map.setMapName(rs.getString(fromIndex + 1));
			map.setMapURL(rs.getString(fromIndex + 2));
		
		} catch (SQLException e) {
			log.log(Level.SEVERE, "parseResultRow failed: " + e.getMessage(), e);
			throw e;
		}
		
		return map;
	}

	/**
	 * @see EntityHome#remove(String)
	 */
	@Override
	public boolean remove(String constraint) {
		// remove all locations and fingerprints for the map, then remove map
		String mapCnst = (constraint != null && constraint.length() > 0) ? constraint : "1=1";
		String locationCnst = HomeFactory.getLocationHome().getTableIdCol() + " IN (SELECT " + HomeFactory.getLocationHome().getTableIdCol() + 
																				 " FROM " + HomeFactory.getLocationHome().getTableName() + 
																				 " WHERE " + mapCnst + ")";
		String fingerprintsCnst = HomeFactory.getFingerprintHome().getTableIdCol() + " IN (SELECT " + HomeFactory.getFingerprintHome().getTableIdCol() + 
																					   " FROM " + HomeFactory.getFingerprintHome().getTableName() +
																					   " WHERE (" + locationCnst + ")) ";
		String measurementsCnst = HomeFactory.getMeasurementHome().getTableIdCol() + " IN (SELECT " + HomeFactory.getFingerprintHome().getTableCols()[1] + 
																					 " FROM " + HomeFactory.getFingerprintHome().getTableName() + 
																					 " WHERE (" + fingerprintsCnst + ")) ";
		String readingInMeasurementCnst = " IN (SELECT readingId FROM readinginmeasurement WHERE (" + measurementsCnst + ")) ";
		
		
		String sql_m = " DELETE FROM " + HomeFactory.getMeasurementHome().getTableName() + " WHERE " + measurementsCnst;
		String sql_wifi = " DELETE FROM " + HomeFactory.getWiFiReadingHome().getTableName() + 
						  " WHERE " + HomeFactory.getWiFiReadingHome().getTableIdCol() + readingInMeasurementCnst;
		String sql_gsm = " DELETE FROM " + HomeFactory.getGSMReadingHome().getTableName() + 
		  				 " WHERE " + HomeFactory.getGSMReadingHome().getTableIdCol() + readingInMeasurementCnst;
		String sql_bluetooth = " DELETE FROM " + HomeFactory.getBluetoothReadingHome().getTableName() + 
		  					   " WHERE " + HomeFactory.getBluetoothReadingHome().getTableIdCol() + readingInMeasurementCnst;
		 
		String sql_rinm = "DELETE FROM readinginmeasurement WHERE " + measurementsCnst;
		String sql_fp = "DELETE FROM " + HomeFactory.getFingerprintHome().getTableName() + " WHERE " + locationCnst;
		
		String sql_l = "DELETE FROM " + HomeFactory.getLocationHome().getTableName() + " WHERE " + mapCnst;
		String sql_map = "DELETE FROM " + getTableName() + " WHERE " + mapCnst;
		Statement stat = null;
		
		log.finest(sql_wifi);
		log.finest(sql_gsm);
		log.finest(sql_bluetooth);
		log.finest(sql_rinm);
		log.finest(sql_m);
		log.finest(sql_fp);
		log.finest(sql_l);
		log.finest(sql_map);
		try {
			int res = -1;
			db.getConnection().setAutoCommit(false);
			stat = db.getConnection().createStatement();
			if (db.getConnection().getMetaData().supportsBatchUpdates()) {
				stat.addBatch(sql_wifi);
				stat.addBatch(sql_gsm);
				stat.addBatch(sql_bluetooth);
				stat.addBatch(sql_rinm);
				stat.addBatch(sql_fp);
				stat.addBatch(sql_m);
				stat.addBatch(sql_l);
				stat.addBatch(sql_map);
				int results[] = stat.executeBatch();
				if (results != null && results.length > 0) {
					res = results[results.length - 1];
				}
			} else {
				stat.executeUpdate(sql_wifi);
				stat.executeUpdate(sql_gsm);
				stat.executeUpdate(sql_bluetooth);
				stat.executeUpdate(sql_rinm);
				stat.executeUpdate(sql_fp);
				stat.executeUpdate(sql_m);
				stat.executeUpdate(sql_l);
				res = stat.executeUpdate(sql_map);
			}
			db.getConnection().commit();
			return res > 0;
		} catch (SQLException e) {
			log.log(Level.SEVERE, "remove map failed: " + e.getMessage(), e);
		} finally {
			try {
				db.getConnection().setAutoCommit(true);
				if (stat != null) stat.close();
			} catch (SQLException es) {
				log.log(Level.WARNING, "failed to close statement: " + es.getMessage(), es);
			}
		}
		return false;

	}

	/**
	 * @see EntityHome#fillInStatement(PreparedStatement, com.aiyc.server.standalone.db.IEntity, int)
	 */
	@Override
	public int fillInStatement(PreparedStatement ps, Map t, int fromIndex)
			throws SQLException {
		return fillInStatement(ps, new Object[] {t.getMapName(), t.getMapURL()}, new int[]{Types.VARCHAR, Types.VARCHAR}, fromIndex);
	}


}
