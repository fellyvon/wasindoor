 
package com.aiyc.server.standalone.db.homes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.logging.Level;

import com.aiyc.server.standalone.core.Location;
import com.aiyc.server.standalone.core.Map;
import com.aiyc.server.standalone.db.HomeFactory;

 
public class LocationHome extends EntityHome<Location> {
	
	
	private static final String[] TableCols = {"symbolicId", "mapId", "mapXCord", "mapYCord", "accuracy"};
	private static final String TableName = "location"; 
	private static final String TableIdCol = "locationId";
	
	public LocationHome() {
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
	public Location parseResultRow(final ResultSet rs, int fromIndex) throws SQLException {
		Location loc = new Location();
		
		try {
			loc.setId(rs.getInt(fromIndex));
			loc.setSymbolicID(rs.getString(fromIndex + 1));
			loc.setMapXcord(rs.getString(fromIndex + 3));
			loc.setMapYcord(rs.getString(fromIndex + 4));
			loc.setAccuracy(rs.getInt(fromIndex + 5));
			Map map = HomeFactory.getMapHome().parseResultRow(rs, fromIndex + 6);
			loc.setMap(map);
		} catch (SQLException e) {
			log.log(Level.SEVERE, "parseResultRow failed: " + e.getMessage(), e);
			throw e;
		}
		
		return loc;
	}
	
	/**
	 * 
	 * @param id {@link Map} primary key
	 * @return {@link List} of {@link Location} for the {@link Map} with id; or all locations if map id is -1
	 */
	public List<Location> getListByMapId(Integer id) {	
		String constraint = ""; 
		if (id != -1) constraint += " location.mapId = " + id;
		return get(constraint);
	}
	
	/**
	 * @see LocationHome#getListByMapId(Integer)
	 * @param m {@link Map}
	 * @return {@link List} of {@link Location} for the {@link Map}
	 */
	public List<Location> getListByMap(Map m) {	
		return getListByMapId(m.getId());
	}

	/**
	 * @see EntityHome#getAll()
	 */
	@Override
	public List<Location> getAll() {
		return getListByMapId(-1);
	}

	/**
	 * get fingerprints depending on different contraints.
	 * -1 is used for no constraint
	 * 
	 * @param locationId {@link Location} primary key
	 * @param symbolicId Symbolic ID of {@link Location}
	 * @return {@link Location} either by primary key or by symbolicId 
	 */
	public Location getLocation(Integer locationId, String symbolicId) {
		List<Location> list;
		
		if (locationId != -1) {
			list =  get("locationId = " + locationId);
			return list.size() == 0 ? null : list.get(0);
		} else if (symbolicId != null && symbolicId.length() > 0) {
			list = get("symbolicId = '" + symbolicId + "'");
			return list.size() == 0 ? null : list.get(0);
		} else {
			return null;
		}
		
		
	}

	/**
	 * @see EntityHome#fillInStatement(PreparedStatement, com.aiyc.server.standalone.db.IEntity, int)
	 */
	@Override
	public int fillInStatement(PreparedStatement ps, Location t, int fromIndex)
			throws SQLException {
		return fillInStatement(ps, new Object[] {t.getSymbolicID(), ((Map)t.getMap()).getId(), t.getMapXcord(), t.getMapYcord(), t.getAccuracy()},   
				new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER},
				fromIndex);
		
	}
	
	
	/**
	 * @see EntityHome#getSelectSQL()
	 */
	@Override
	protected String getSelectSQL() {
		return "SELECT " + getTableColNames() + ", " + HomeFactory.getMapHome().getTableColNames() + " FROM " + getTableName() + " INNER JOIN map ON location.mapId = map.mapId ";
	}

	
	/**
	 * @see EntityHome#remove(String)
	 */
	@Override
	public boolean remove(String constrain) {
		// remove all fingerprints for the location, then remove location
		String locationCnst = (constrain != null && constrain.length() > 0) ? constrain : "1=1";
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
	
		String sql_loc = "DELETE FROM " + getTableName() + " WHERE " + locationCnst;
		Statement stat = null;
		
		log.finest(sql_wifi);
		log.finest(sql_gsm);
		log.finest(sql_bluetooth);
		log.finest(sql_rinm);
		log.finest(sql_m);
		log.finest(sql_fp);
		log.finest(sql_loc);
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
				stat.addBatch(sql_loc);
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
				res = stat.executeUpdate(sql_loc);
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
}
