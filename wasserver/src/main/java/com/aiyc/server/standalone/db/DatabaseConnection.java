 
package com.aiyc.server.standalone.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;


import com.aiyc.server.standalone.util.Configuration;
import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * Provides basic actions and access to a database.
 * 
 * @author  felly
 * 
 */
public class DatabaseConnection {
	private static DataSource ds = null;

	public static Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	private DatabaseConnection() {
		try {

			ds = DruidDataSourceFactory.createDataSource(Configuration.p);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static class DatabaseConnectionImpl {
		private static DatabaseConnection db = new DatabaseConnection();
	}

	public static DatabaseConnection getInstance() {
		return DatabaseConnectionImpl.db;
	}

}
