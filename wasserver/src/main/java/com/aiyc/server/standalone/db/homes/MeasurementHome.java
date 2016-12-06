 
package com.aiyc.server.standalone.db.homes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.logging.Level;

import com.aiyc.server.standalone.core.Measurement;
import com.aiyc.server.standalone.core.Vector;
import com.aiyc.server.standalone.db.HomeFactory;

public class MeasurementHome extends EntityHome<Measurement> {

	private static final String[] TableCols = {"timestamp"};
	private static final String TableName = "measurement"; 
	private static final String TableIdCol = "measurementId";
	private static final String selectMeasurements = " SELECT " + HomeFactory.getMeasurementHome().getTableColNames() + ", " +
					 								 " readinginmeasurement.readingClassName, " + HomeFactory.getWiFiReadingHome().getTableColNames() + ", " + 
					 								 HomeFactory.getGSMReadingHome().getTableColNames() + ", " + HomeFactory.getBluetoothReadingHome().getTableColNames()  +
					 								 " FROM measurement INNER JOIN readinginmeasurement ON readinginmeasurement.measurementId = measurement.measurementId " +
					 								 " LEFT OUTER JOIN wifireading ON wifireading.wifiReadingId = readinginmeasurement.readingId " +
					 								 " LEFT OUTER JOIN gsmreading ON gsmreading.gsmReadingId = readinginmeasurement.readingId " +
					 								 " LEFT OUTER JOIN bluetoothreading ON bluetoothreading.bluetoothReadingId = readinginmeasurement.readingId ";
	private static final String orderMeasurements = " measurement.measurementId, readinginmeasurement.readingClassName ";
	
	public MeasurementHome() {
		super();
	}
	
	
	public  List<Measurement> getAll(String mapId){
		String where=" exists(select 1 from  location,fingerprint where location.locationId=fingerprint.locationId and location.mapId='"+mapId+"'" +
				"  and  fingerprint.measurementId=measurement.measurementId)";
	return get(where);
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
	
	@Override
	public synchronized Measurement parseResultRow(final ResultSet rs, int fromIndex)
			throws SQLException {
		Measurement m = new Measurement();
		
		try {
			if (!rs.isAfterLast()) {
				int mId = rs.getInt(fromIndex);
				m.setId(mId);
				m.setTimestamp(rs.getLong(fromIndex + 1));
				String readingClassName = rs.getString(fromIndex + 2);
				
				if (readingClassName == null) {
					// there are no readings in measurement
					rs.next();
				} else {	
					while(!rs.isAfterLast() && mId == rs.getInt(fromIndex) ) {
						readingClassName = rs.getString(fromIndex + 2);
						if (HomeFactory.getWiFiReadingVectorHome().getContainedObjectClassName().equals(readingClassName)) {
							m.setWiFiReadings(HomeFactory.getWiFiReadingVectorHome().parseResultRow(rs, fromIndex + 3));
						} else if (HomeFactory.getGSMReadingVectorHome().getContainedObjectClassName().equals(readingClassName)) {
							m.setGSMReadings(HomeFactory.getGSMReadingVectorHome().parseResultRow(rs, fromIndex + 3 + HomeFactory.getWiFiReadingHome().getTableCols().length + 1));
						} else if (HomeFactory.getBluetoothReadingVectorHome().getContainedObjectClassName().equals(readingClassName)) {
							m.setBluetoothReadings(HomeFactory.getBluetoothReadingVectorHome().parseResultRow(rs, fromIndex + 3 + HomeFactory.getGSMReadingHome().getTableCols().length + 1 + HomeFactory.getWiFiReadingHome().getTableCols().length + 1));
						} else {
							log.fine("Result row has no matching readingClassName " + readingClassName);
							rs.next();
						}
					}
					/*
					 * adjust pointer to row because the last VectorHome read one row to far to see if readings are finished
					 * Unfortunately not possible because SQLite JDBC driver only supports forward cursors.
					 * See comment in FingerprintHome#get(String)
					 * 
					 * rs.previous();
					 */
					
				}
			}
		} catch (SQLException e) {
			log.log(Level.SEVERE, "parseResultRow failed: " + e.getMessage(), e);
			throw e;
		}
		
		return m;

	}
	
	
	

	@Override
	public Measurement getById(Integer id) {
		String constraint = getTableName() + "." + getTableIdCol() + " = " + id;
		List<Measurement> list = get(constraint);
		if (list.size() == 0) {
			return null;
		}
		return list.get(0);
		
	}

	@Override
	public Measurement add(Measurement m) {
		Connection conn = db.getConnection();
		Vector<PreparedStatement> vps = new Vector<PreparedStatement>();
		ResultSet rs = null;
		
		try {

			conn.setAutoCommit(false);
			
			int measurementId = HomeFactory.getMeasurementHome().executeInsertUpdate(vps, m);
			// wifi
			HomeFactory.getWiFiReadingVectorHome().executeUpdate(vps, m.getWiFiReadings(), measurementId);
			// gsm
			HomeFactory.getGSMReadingVectorHome().executeUpdate(vps, m.getGsmReadings(), measurementId);			
			// bluetooth
			HomeFactory.getBluetoothReadingVectorHome().executeUpdate(vps, m.getBluetoothReadings(), measurementId);
		
			
			
			conn.commit();
			
			return getById(measurementId);
		
		} catch (SQLException e) {
			log.log(Level.SEVERE, "add fingerprint failed: " + e.getMessage(), e);
		} finally {
			try {
				conn.setAutoCommit(true);
				if (rs != null) rs.close();
				for(PreparedStatement p : vps) {
					if (p != null) p.close();
				}
			} catch (SQLException es) {
				log.log(Level.WARNING, "failed to close statement: " + es.getMessage(), es);
			}
		}
		return null;
	}
	
	


	@Override
	protected String getSelectSQL() {
		return selectMeasurements;
	}
	
	@Override
	protected String getOrder() {
		return orderMeasurements;
	}

	
	

	@Override
	protected boolean remove(String constrain) {
		String measurementsCnst = (constrain != null && constrain.length() > 0) ? constrain : "1=1";
		
		String readingInMeasurementCnst = " IN (SELECT readingId FROM readinginmeasurement WHERE (" + measurementsCnst + ")) ";
		
		
		String sql_m = " DELETE FROM " + HomeFactory.getMeasurementHome().getTableName() + " WHERE " + measurementsCnst;
		String sql_wifi = " DELETE FROM " + HomeFactory.getWiFiReadingHome().getTableName() + 
						  " WHERE " + HomeFactory.getWiFiReadingHome().getTableIdCol() + readingInMeasurementCnst;
		String sql_gsm = " DELETE FROM " + HomeFactory.getGSMReadingHome().getTableName() + 
		  				 " WHERE " + HomeFactory.getGSMReadingHome().getTableIdCol() + readingInMeasurementCnst;
		String sql_bluetooth = " DELETE FROM " + HomeFactory.getBluetoothReadingHome().getTableName() + 
		  					   " WHERE " + HomeFactory.getBluetoothReadingHome().getTableIdCol() + readingInMeasurementCnst;
		 
		String sql_rinm = "DELETE FROM readinginmeasurement WHERE " + measurementsCnst;
		
		Statement stat = null;
		
		log.finest(sql_wifi);
		log.finest(sql_gsm);
		log.finest(sql_bluetooth);
		log.finest(sql_rinm);
		log.finest(sql_m);

		try {
			int res = -1;
			db.getConnection().setAutoCommit(false);
			stat = db.getConnection().createStatement();
			if (db.getConnection().getMetaData().supportsBatchUpdates()) {
				stat.addBatch(sql_wifi);
				stat.addBatch(sql_gsm);
				stat.addBatch(sql_bluetooth);
				stat.addBatch(sql_rinm);
				stat.addBatch(sql_m);
				int results[] = stat.executeBatch();
				if (results != null && results.length > 0) {
					res = results[results.length - 1];
				}
			} else {
				stat.executeUpdate(sql_wifi);
				stat.executeUpdate(sql_gsm);
				stat.executeUpdate(sql_bluetooth);
				stat.executeUpdate(sql_rinm);
				res = stat.executeUpdate(sql_m);
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

	@Override
	public int fillInStatement(PreparedStatement ps, Measurement t, int fromIndex)
			throws SQLException {
		return fillInStatement(ps, new Object[] {t.getTimestamp()}, new int[]{ Types.BIGINT}, fromIndex);
	}

	
}
