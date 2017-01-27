 
package com.aiyc.server.standalone.svm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import org.libsvm.core.svm_predict;
import org.libsvm.core.svm_scale;
import org.libsvm.core.svm_train;

import com.aiyc.server.standalone.core.Fingerprint;
import com.aiyc.server.standalone.core.Location;
import com.aiyc.server.standalone.core.Measurement;
import com.aiyc.server.standalone.core.measure.WiFiReading;
import com.aiyc.server.standalone.db.HomeFactory;
import com.aiyc.server.standalone.util.Log;
import com.waspring.wasdbtools.DaoUtil;

/**
 * 
 * @author  felly
 * 
 */
public class MapSVMSupport {

	public static String TRAIN = "train.1";
	public static String TEST = "test.1";
	public static String TEMP = "temp";
	public static String TRAIN_SCALE = "train.1.scale";
	public static String TEST_SCALE = "test.1.scale";
	public static String RANGE = "range1";
	public static String OUT = "out";
	public static String MODEL_EXT = ".model";
	public static String TRAIN_SCRIPT = "train.sh";
	private static int ACTIVE_MODEL = 0;

	public static void main(String dfsfpp[]) throws Exception {
		train("1");
	}

	public static void initParams(String mapId) {
		String pre = File.separator;
		String train = "train";
		File f = new File(train);
		if (!f.exists()) {
			f.mkdir();
		}
		String file = "train" + pre + mapId;
		f = new File(file);
		if (!f.exists()) {
			f.mkdir();
		}
		TRAIN = file + pre + "train.1";
		TEST = file + pre + "test.1";
		TEMP = file + pre + "temp";
		TRAIN_SCALE = file + pre + "train.1.scale";
		TEST_SCALE = file + pre + "test.1.scale";
		RANGE = file + pre + "range1";
		OUT = file + pre + "out";

		TRAIN_SCRIPT = file + pre + "train.sh";
	}

	/**
	 * Train SVM
	 */
	public synchronized static void train(String mapId) {
		initParams(mapId);

		Log.getLogger().log(Level.FINE, "Starting SVM train.");
		Log.getLogger().log(Level.FINE, "Building categories...");
		MapCategorizerFactory.BSSIDCategorizer(mapId).clear();
		MapCategorizerFactory.buildCategories(mapId);

		List<Measurement> setupdata = HomeFactory.getMeasurementHome().getAll(
				mapId);
		if (setupdata == null || setupdata.size() == 0)
			return;

		Log.getLogger().log(Level.FINE,
				"Transforming data to the format of an SVM package...");
		transformToSVMFormat(setupdata, TRAIN, false,mapId);

		Log.getLogger().log(Level.FINE, "Creating the model...");
		int nextModel = Math.abs(ACTIVE_MODEL - 1);
		if (!runScript(TRAIN_SCRIPT, new String[] { nextModel + "" })) {
			String[] scaleargs = { "-l", "-1", "-u", "1", "-s", RANGE, TRAIN };
			String[] args = { "-t", "0", "-c", "512", "-q",
					TRAIN_SCALE + nextModel,
					TRAIN_SCALE + nextModel + MODEL_EXT };
			svm_train t = new svm_train();
			svm_scale s = new svm_scale();
			try {
				s.run(scaleargs, TRAIN_SCALE + nextModel);
				t.run(args);
			} catch (IOException e) {
				Log.getLogger().log(Level.SEVERE,
						"Failed to create SVM model: " + e.getMessage());
			}
		}
		synchronized (MapSVMSupport.class) {
			ACTIVE_MODEL = nextModel;
		}
		Log.getLogger().log(Level.FINE, "SVM train finished..");
		saveModel(TRAIN_SCALE + nextModel + MODEL_EXT, mapId, nextModel);

	}

	/**
	 * ����ģ���ļ�
	 */
	private static void saveModel(String fileName, String mapId, int nextModel) {
		if (!isTrained(mapId)) {
			String sql = "insert into  svmmodel(mapID,filePath,loationCount,traindate)values(?,?,?,now())";
			try {
				DaoUtil.executeUpdate(sql, new Object[] { mapId, fileName,
						20 });
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String sql = "update  svmmodel set traindate=now(),filePath=? where mapId=?";
			try {
				DaoUtil.executeUpdate(sql, new Object[] { fileName, mapId });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �õ�ģ���ļ�
	 */
	public static String getModelFile(String mapId) throws SQLException {
		String sql = "select  filePath from svmmodel where mapID=?    ";
		ResultSet rs = DaoUtil.queryData(sql, new Object[] { mapId });
		if (rs.next()) {
			return rs.getString(1);
		}
		return TRAIN_SCALE + ACTIVE_MODEL + MODEL_EXT;
	}

	/**
	 * Predict
	 * 
	 * @param m
	 *            {@link Measurement}
	 * @return path to result file
	 */
	public static synchronized String predict(String mapId, final Measurement m)
			throws Exception {
		initParams(mapId);
		File modelfile = new File(getModelFile(mapId));
		File outputfile = new File(OUT);
		Vector<Measurement> testMeasurements = new Vector<Measurement>();
		testMeasurements.add(m);
		transformToSVMFormat(testMeasurements, TEST, true,mapId);

		try {
			String[] scaleargs = { "-r", RANGE, TEST };
			svm_scale s = new svm_scale();
			s.run(scaleargs, TEST_SCALE);

			String[] args = { TEST_SCALE, modelfile + "", outputfile + "" };
			svm_predict.main(args);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.getLogger().log(
					Level.SEVERE,
					"predict failed due to FileNotFoundException: "
							+ e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Log.getLogger().log(Level.SEVERE,
					"predict failed due to IOException: " + e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
	 
		}

		return OUT;
	}

	public static boolean isTrained(String mapId) {
		try {
			String sql = "select  1 from  svmmodel  where mapID=?";
			return DaoUtil.queryData(sql, new Object[] { mapId }).next();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Function transforms data (measurements) to the format of an SVM package.
	 * Each measurement is represented as a vector of real numbers.
	 * 
	 * @param data -
	 *            list of measurements
	 * @param fileName -
	 *            destination file name
	 */
	public synchronized static void transformToSVMFormat(
			final List<Measurement> data, String fileName, boolean isNew,String mapId) {

		File testfile = new File(fileName);
		try {
			BufferedWriter writertest = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(testfile)));
			Hashtable<Integer, Integer> rssis = new Hashtable<Integer, Integer>();
			Vector<Integer> sarray = new Vector<Integer>();
			for (Measurement m : data) {
				Integer categoryID = -1;
				if (!isNew) {
					categoryID = getLocationCategory(m,mapId);
					if (categoryID == -1)
						continue;
				}
				StringBuffer line = new StringBuffer();
				line.append(categoryID);
				for (WiFiReading r : m.getWiFiReadings()) {
					if (r != null && r.getBssid() != null) {
						Integer id = MapCategorizerFactory.BSSIDCategorizer(mapId)
								.GetCategoryID(r.getBssid());
						if (id != -1 && !rssis.contains(id)) {
							rssis.put(id, r.getRssi());
							sarray.add(id);
						}
					}
				}
				Collections.sort(sarray);
				for (int i = 0; i < sarray.size(); i++) {
					if (rssis.get(sarray.get(i)) != null)
						line.append(" " + sarray.get(i) + ":"
								+ rssis.get(sarray.get(i)));
				}
				line.append("\n");
				writertest.write(line.toString());
				rssis.clear();
				sarray.clear();
			}
			writertest.close();

		} catch (FileNotFoundException e) {
			Log.getLogger().log(
					Level.SEVERE,
					"transformToSVMFormat failed due to FileNotFoundException: "
							+ e.getMessage());
		} catch (IOException e) {
			Log.getLogger().log(
					Level.SEVERE,
					"transformToSVMFormat failed due to IOException: "
							+ e.getMessage());
		}
	}

	/**
	 * Runs a shell script
	 * 
	 * @param scriptName
	 * @return true in case of a successful run
	 */
	private static synchronized boolean runScript(String scriptName,
			String[] args) {
		String command = "./" + scriptName;
		for (String arg : args) {
			command += " " + arg;
		}
		try {
			Process p = Runtime.getRuntime().exec(command);
			int exitvalue = p.waitFor();
			if (exitvalue == 0)
				return true;
		} catch (InterruptedException e) {
		 
			Log.getLogger().log(
					Level.INFO,
					"runScript failed due to InterruptedException: "
							+ e.getMessage());
		} catch (IOException e) {
		 
			Log.getLogger().log(Level.INFO,
					"runScript failed due to IOException: " + e.getMessage());
		}
		return false;
	}

	/**
	 * Returns location category
	 * 
	 * @param m
	 *            {@link Measurement}
	 * @return Location category id
	 */
	private synchronized static Integer getLocationCategory(Measurement m,String mapId) {
		if (m == null || m.getId() == null)
			return -1;

		Fingerprint f = HomeFactory.getFingerprintHome().getByMeasurementId(
				m.getId());

		if (f != null) {
			Location l = (Location) f.getLocation();
			if (l != null && l.getId() != null) {
				return MapCategorizerFactory.LocationCategorizer(mapId)
						.GetCategoryID(l.getMapXcord() + "" + l.getMapYcord());
			}
		}
		return -1;
	}
}
