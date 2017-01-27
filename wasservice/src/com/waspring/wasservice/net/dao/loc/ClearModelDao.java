package com.waspring.wasservice.net.dao.loc;

import com.waspring.wasdbtools.DaoUtil;

public class ClearModelDao {

	/**
	 * 
	 * @param mapNo
	 * @throws Exception
	 */
	public void clearModel(String mapNo) throws Exception {
		String sql = "delete\n"
				+ "  from wifireading\n"
				+ " where wifiReadingId in\n"
				+ "       (select readingId\n"
				+ "          from readinginmeasurement\n"
				+ "         where\n"
				+ "          measurementId in\n"
				+ "                (select  measurementId\n"
				+ "                   from fingerprint\n"
				+ "                  where  locationId in\n"
				+ "                        (select locationId from location n where mapId = ?)))";

		DaoUtil.executeUpdate(sql, new Object[] { mapNo });

		sql = "delete\n"
				+ "  from readinginmeasurement\n"
				+ " where measurementId in\n"
				+ "       (select measurementId\n"
				+ "          from fingerprint\n"
				+ "         where locationId in\n"
				+ "               (select locationId from location where mapId = ?));";
		DaoUtil.executeUpdate(sql, new Object[] { mapNo });

		sql = "delete\n" + "  from fingerprint\n"
				+ " where locationId in  (select locationId\n"
				+ "          from location\n" + "         where  mapId = ?)";
		DaoUtil.executeUpdate(sql, new Object[] { mapNo });

		sql = "delete from location where mapId = ?";

		DaoUtil.executeUpdate(sql, new Object[] { mapNo });

		sql = "delete from `macadresslist` where MAP_ID = ?";

		DaoUtil.executeUpdate(sql, new Object[] { mapNo });

	}
}
