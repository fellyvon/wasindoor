package com.waspring.wasservice.net.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.waspring.wasdbtools.DaoUtil;
import com.waspring.wasdbtools.DatabaseConnection;
import com.waspring.wasservice.net.model.SetModelReqMessage;

public class ModelDao {

	public synchronized int saveModel(SetModelReqMessage model)
			throws Exception {
		// /����λ����Ϣ
		int reulst = 0;
		int locationId = getPrimaryKeyId("location", "locationId");
		int measurementId = getPrimaryKeyId("measurement", "measurementId");
		int wifiReadingId = getPrimaryKeyId("wifireading", "wifiReadingId");

		Connection conn = DatabaseConnection.getInstance().getConnection();
		conn.setAutoCommit(false);
		try {

			String sql = "insert into  location(locationId,symbolicId,mapId,mapXCord,mapYCord,accuracy)"
					+ "values(?,?,?,?,?,?)";

			String uuid = UUID.randomUUID().toString();
			java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
			DaoUtil.executeUpdate(stmt, sql, new Object[] { locationId,
					model.MESSAGE.LOCATION.X + model.MESSAGE.LOCATION.Y,
					model.MESSAGE.MAP_NO,  model.MESSAGE.LOCATION.X,
					 model.MESSAGE.LOCATION.Y, "0" });

			sql = "insert into measurement(measurementId,timestamp)values(?,?)";
			stmt = conn.prepareStatement(sql);

			DaoUtil.executeUpdate(stmt, sql, new Object[] { measurementId,
					System.currentTimeMillis() });

			sql = "insert into fingerprint(locationId,measurementId)values(?,?)";
			stmt = conn.prepareStatement(sql);

			DaoUtil.executeUpdate(stmt, sql, new Object[] { locationId,
					measurementId

			});

			List<SetModelReqMessage.Message.AP_INFO> readings = model.MESSAGE.AP_INFO;
			Iterator<SetModelReqMessage.Message.AP_INFO> it = readings
					.iterator();

			while (it.hasNext()) {
				SetModelReqMessage.Message.AP_INFO ap = it.next();

				sql = " insert into wifireading(wifiReadingId"
						+ ",bssid,ssid,rssi,wepEnabled,"
						+ "isInfrastructure)values(?,?,?,?,?,?)";
				stmt = conn.prepareStatement(sql);
				String wep = ap.WEPENABLED;
				if (wep == null || "".equals(wep)
						|| "false".equalsIgnoreCase(wep)) {
					wep = "0";
				} else if ("true".equalsIgnoreCase(wep)) {
					wep = "1";
				} else {
					wep = "0";
				}
				String us = ap.ISINFRASTRUCTURE;
				if (us == null || "".equals(us) || "false".equalsIgnoreCase(us)) {
					us = "0";
				} else if ("true".equalsIgnoreCase(us)) {
					us = "1";
				} else {
					us = "0";
				}

				DaoUtil.executeUpdate(stmt, sql, new Object[] { wifiReadingId,
						ap.AP_MAC, ap.AP_NAME, ap.AP_VALUE, wep, us

				});

				sql = "insert into readinginmeasurement(measurementId,readingId,readingClassName) values(?,?,?)";
				stmt = conn.prepareStatement(sql);
				DaoUtil.executeUpdate(stmt, sql, new Object[] { measurementId,
						wifiReadingId, "WiFiReading"

				});

				sql = "select 1 from  macadresslist where mac=?   ";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, ap.AP_MAC);
				ResultSet rs = stmt.executeQuery();
				if (!rs.next()) {

					sql = "insert into `macadresslist`(`MAC`,`MAP_ID`) values(?,?)";

					stmt = conn.prepareStatement(sql);
					DaoUtil.executeUpdate(stmt, sql, new Object[] { ap.AP_MAC,
							model.MESSAGE.MAP_NO

					});
				}

				wifiReadingId = wifiReadingId + 1;

			}

			conn.commit();
			reulst = 1;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			reulst = 0;
		} finally {

			conn.close();
			return reulst;
		}

	}

	/**
	 * �õ�λ������
	 */
	public int getCountLocation(String mapId, String x, String y)
			throws Exception {
		String sql = "select count(1) from location,fingerprint where location.locationId"
				+ "=fingerprint.locationId and location.mapId=? and "
				+ " location.mapXCord=? and  location.mapYCord=? ";

		ResultSet rs = DaoUtil.queryData(sql, new Object[] { mapId, x, y });

		if (rs.next()) {
			return rs.getInt(1);
		}

		return 0;
	}

	/**
	 * 
	 * @return next primary key for the entry
	 */
	public synchronized int getPrimaryKeyId(String tableName, String key)
			throws Exception {
		int primaryKeyId = -1;

		String sql = "select max(" + key + ") from  " + tableName + "";
		ResultSet rs = DaoUtil.queryData(sql, new Object[] {});
		if (rs.next()) {
			primaryKeyId = rs.getInt(1);
		}
		primaryKeyId++;
		return primaryKeyId;
	}

	public boolean haveMap(String mapId) throws Exception {
		String sql = "select 1 from map where mapId=? ";

		return DaoUtil.queryData(sql, new Object[] { mapId }).next();
	}

	/**
	 * ��ȡ��ͼ�϶��ٸ���λ������²�����ѵ���㷨
	 */
	public static final int MAX_TRAIN_POINT_LOC = 20;

	public int getMaxTrinPointLoc(String mapId) throws Exception {
		String sql = "select loationCount from svmmodel  where  mapID =? ";
		ResultSet rs = DaoUtil.queryData(sql, new Object[] { mapId });
		int max = MAX_TRAIN_POINT_LOC;
		if (rs.next()) {
			max = rs.getInt("loationCount");
		}
		return max;

	}

}
