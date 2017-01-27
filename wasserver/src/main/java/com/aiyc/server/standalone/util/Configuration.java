package com.aiyc.server.standalone.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;

import com.aiyc.server.standalone.svm.SVMSupport;
import com.aiyc.server.standalone.svm.TrainSVMTimerTask;
import com.waspring.wasdbtools.DatabaseConnection;

/**
 * Configuration class which represents all configuration settings. Before the
 * first access to a property, all properties are read from the properties file
 * and are initialized accordingly. If the database schema is not already set
 * up, this is also done.
 * 
 * @author felly
 * 
 */
public class Configuration {

	public static final String ANNO = "anno";
	public static final String FILE = "file";

	/**
	 * Different {@link DatabaseTypes} which are supported by the server
	 * 
	 * @author felly
	 * 
	 */
	public enum DatabaseTypes {
		SQLITE, MYSQL
	};

	public static final String ResourcesDir = "/resources/";
	private static final String SQLite_Schema = ResourcesDir
			+ "waspring_sqlite.sql";

	public enum LoggerFormat {
		PLAIN, XML
	};

	// default settings
	public static Integer ServerPort = 1968;

	public static Integer LongServerPort = 1969;

	public static String ImageUploadPath = "mapuploads/";

	public static String indoor_location = "indoor";

	public static LoggerFormat LogFormat = LoggerFormat.PLAIN; // or xml
	public static Level LogLevel = Level.WARNING;
	public static String LogFile = "waspring.log";
	public static String handers = "com.waspring.wasservice.net.busii.*";

	public static boolean LogRequests = false;
	public static String LogRequestPath = "requests/";

	public static DatabaseTypes DatabaseType = DatabaseTypes.MYSQL;
	public static String DatabaseLocation = "//localhost:3336/indoor?user=waspring&password=waspring";
	public static String DatabaseDriver = "com.mysql.jdbc.Driver";

	public static String handerType = "anno";

	public static String LibSVMDirectory = "libsvm-2.9";
	public static long SVMTrainRate = TrainSVMTimerTask.DEFAULT_TRAIN_RATE;

	private static String generateTrainScript(String dir) {
		return "#!/bin/sh \n" + dir + "/svm-scale -l -1 -u 1 -s "
				+ SVMSupport.RANGE + " " + SVMSupport.TRAIN + " > "
				+ SVMSupport.TRAIN_SCALE + "$1\n" + dir
				+ "/svm-train -c 512 -t 0 -q " + SVMSupport.TRAIN_SCALE + "$1";
	}

	public static String get_coupon_url = "http://api.dianping.com/v1/coupon/get_single_coupon";

	public static String get_deal_url = "http://api.dianping.com/v1/deal/get_single_deal";

	public static String find_businesses_url = "http://api.dianping.com/v1/business/find_businesses";
	public static String get_businesses_url = "http://api.dianping.com/v1/business/get_batch_businesses_by_id";

	public static int max_page = 2000;
	public static int search_radais = 1000;// m

	public static String appkey, secret;

	public static String hander_indoor_libdir = "wasservice-v0.0.1.jar";// /ҵ��ӿڿ�

	public static String calc_indoor_libdir = "wasindoor-v0.0.1.jar";// /�㷨��

	public static String calc_indoor_path = "com.waspring.wasindoor.locale.IndoorTrainTask";// /�㷨����

	public static int calcSort = 1;// ///1 wifi, 2 �شţ�3 ��ʹ��
	public static Properties p;
	// initialization
	static {
		p = new Properties();
		File f = new File("waspring.properties");
		System.out.println("path=" + f.getAbsolutePath());
		if (f.exists()) {
			try {
				FileInputStream reader = new FileInputStream(f);
				p.load(reader);

				try {
					ServerPort = new Integer(p.getProperty("port",
							ServerPort.toString()));
					LongServerPort = new Integer(p.getProperty("longport",
							LongServerPort.toString()));
				} catch (NumberFormatException e) {
				}

				ImageUploadPath = p.getProperty("image.upload.path",
						ImageUploadPath);
				if (ImageUploadPath.charAt(ImageUploadPath.length() - 1) == '/') {
					ImageUploadPath = ImageUploadPath.substring(0,
							ImageUploadPath.length() - 1);
				}
				LogFile = p.getProperty("log.file", LogFile);

				String format = "";
				try {
					format = p.getProperty("log.format", LogFormat.name());
					LogFormat = LoggerFormat.valueOf(format.toUpperCase());

				} catch (IllegalArgumentException e) {
					Log.getLogger().log(
							Level.CONFIG,
							"No such log format type " + format + ": "
									+ e.getMessage(), e);
				}

				String level = "";
				try {
					level = p.getProperty("log.level", LogLevel.getName());
					LogLevel = Level.parse(level);
				} catch (IllegalArgumentException e) {
					Log.getLogger().log(
							Level.CONFIG,
							"No such log format type " + format + ": "
									+ e.getMessage(), e);
				}

				LogRequests = Boolean.valueOf(
						p.getProperty("requests.log",
								Boolean.valueOf(LogRequests).toString()))
						.booleanValue();
				LogRequestPath = p.getProperty("requests.log.path",
						LogRequestPath);

				if (LogRequestPath.charAt(LogRequestPath.length() - 1) == '/') {
					LogRequestPath = LogRequestPath.substring(0,
							LogRequestPath.length() - 1);
				}

				if (LogRequests) {
					File lrp = new File(LogRequestPath);
					if (!lrp.exists()) {
						lrp.mkdirs();
					}
				}

				String type = "";
				try {
					type = p.getProperty("db.type", DatabaseType.name());
					DatabaseType = DatabaseTypes.valueOf(type.toUpperCase());

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					Log.getLogger().log(
							Level.CONFIG,
							"No such database type " + type + ": "
									+ e.getMessage(), e);
				}

				if (DatabaseType == DatabaseTypes.SQLITE) {
					DatabaseLocation = p.getProperty("db.location",
							DatabaseLocation);
					DatabaseDriver = "org.sqlite.JDBC";

				}

				if (DatabaseType == DatabaseTypes.MYSQL) {
					DatabaseDriver = "com.mysql.jdbc.Driver";
					DatabaseLocation = p.getProperty("db.location",
							DatabaseLocation);
				}

				LibSVMDirectory = p.getProperty("svm.libdir", LibSVMDirectory);
				try {
					SVMTrainRate = Long.parseLong(p.getProperty(
							"svm.trainrate", SVMTrainRate + ""));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				Log.getLogger().log(Level.SEVERE,
						"Config initialization failed: " + e.getMessage(), e);
				e.printStackTrace();
			}

		}

		File dir = new File(ImageUploadPath);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				Log.getLogger().log(Level.WARNING,
						"Image Upload Path could not be created");
			} else {
				Log.getLogger().fine(
						"No image upload dir found, now creating "
								+ ImageUploadPath);
			}
		}

		if ((DatabaseType == DatabaseTypes.SQLITE)
				&& (!new File(DatabaseLocation).exists())) {
			Log.getLogger().fine(
					"No database file found, now importing database schema");
			importSQLiteSchema();
		}

		dir = new File(LibSVMDirectory);
		if (dir.isDirectory() && dir.exists()) {

			File trainPl = new File(SVMSupport.TRAIN_SCRIPT);
			try {
				if (!trainPl.exists()) {
					Writer w = new FileWriter(trainPl);
					w.write(generateTrainScript(LibSVMDirectory));
					w.flush();
					w.close();
					trainPl.setExecutable(true);
				}
			} catch (Exception e) {
				Log.getLogger().fine(
						"could not create " + SVMSupport.TRAIN_SCRIPT);
			}
		}

		handers = p.getProperty("hander.pacakges", handers);
		handerType = p.getProperty("hander.type", handerType);

		get_coupon_url = p.getProperty("net.get_coupon_url", get_coupon_url);
		get_deal_url = p.getProperty("net.get_deal_url", get_deal_url);

		calcSort = Integer.parseInt(p.getProperty("calcSort", calcSort + ""));

		find_businesses_url = p.getProperty("net.find_businesses_url",
				find_businesses_url);

		get_businesses_url = p.getProperty("net.get_businesses_url",
				get_businesses_url);

		search_radais = Integer.parseInt(p.getProperty("net.search_radais",
				search_radais + ""));

		appkey = p.getProperty("net.appkey", appkey);
		secret = p.getProperty("net.secret", secret);

		hander_indoor_libdir = p.getProperty("hander.indoor.libdir",
				hander_indoor_libdir);

		calc_indoor_libdir = p.getProperty("calc.indoor.libdir",
				calc_indoor_libdir);

		indoor_location = p.getProperty("indoor.location", indoor_location);

		calc_indoor_path = p.getProperty("calc.indoor.path", calc_indoor_path);

		File indoorLocationFile = new File(indoor_location);
		if (!indoorLocationFile.exists()) {
			indoorLocationFile.mkdir();
		}
	}

	/**
	 * Import the sqlite database schema
	 */
	private static void importSQLiteSchema() {

		InputStream is = ClassLoader.class.getResourceAsStream(SQLite_Schema);
		if (is != null) {

			try {

				BufferedReader bf = new BufferedReader(
						new InputStreamReader(is));

				String sql = "";
				String line = "";
				while (true) {

					line = bf.readLine();

					if (line == null)
						break;

					line = line.trim();

					if (line.endsWith(";")) {
						sql += line;

						Connection conn = DatabaseConnection.getInstance()
								.getConnection();
						Statement stat = conn.createStatement();
						Log.getLogger().finest("import table " + sql);
						stat.executeUpdate(sql);

						sql = "";
						line = "";
					} else {
						sql += line;
					}

				}

			} catch (FileNotFoundException e) {
				Log.getLogger().log(Level.WARNING,
						"schema file missing: " + e.getMessage(), e);
			} catch (SQLException e) {
				Log.getLogger().log(Level.WARNING,
						"schema file import failed: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.getLogger().log(Level.WARNING,
						"schema file ioerror: " + e.getMessage(), e);
			}
		} else {
			Log.getLogger().log(Level.WARNING, "schema file missing");
		}

	}
}
