 
package com.waspring.wasservice.net.busii.loc;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.aiyc.server.standalone.core.Fingerprint;
import com.aiyc.server.standalone.core.Location;
import com.aiyc.server.standalone.core.Measurement;
import com.aiyc.server.standalone.core.measure.WiFiReading;
import com.aiyc.server.standalone.db.HomeFactory;
import com.aiyc.server.standalone.db.homes.FingerprintHome;
import com.aiyc.server.standalone.db.homes.MeasurementHome;
import com.aiyc.server.standalone.locator.ILocator;
import com.aiyc.server.standalone.locator.MeasurementComparator;
import com.aiyc.server.standalone.util.Log;

public class MapLocator implements ILocator {

	MeasurementHome measurementHome = null;
	FingerprintHome fingerprintHome = null;
	Logger log;
	Level loglevel = Level.FINEST;

	private static double ID_POS_CONTRIBUTION = 1;
	private static double ID_NEG_CONTRIBUTION = -0.4;

	public static double SIGNAL_CONTRIBUTION = 1;
	public static double SIGNAL_PENALTY_THRESHOLD = 10;
	public static double SIGNAL_GRAPH_LEVELING = 0.2;

	/* accuracy level */
	public static final int LOCATION_KNOWN = 10;
	public static final int LOCATION_UNKNOWN = 0;
	public static int LOCATION_THRESHOLD = 2;

	public static boolean debug = false;

	private String mapId;

	public MapLocator(String mapId) {
		this.mapId = mapId;
		measurementHome = HomeFactory.getMeasurementHome();
		fingerprintHome = HomeFactory.getFingerprintHome();
		log = Log.getLogger();

		loadParameters();
	}

	public void loadParameters() {
		Properties p = new Properties();
		File f = new File("redpinlocator.properties");
		if (f.exists()) {
			try {
				FileInputStream reader = new FileInputStream(f);
				p.load(reader);

				debug = Boolean.valueOf(
						p.getProperty("debug", Boolean.valueOf(debug)
								.toString())).booleanValue();

				LOCATION_THRESHOLD = Integer.parseInt(p.getProperty(
						"LOCATION_THRESHOLD", new Integer(LOCATION_THRESHOLD)
								.toString()));
				ID_POS_CONTRIBUTION = Double.parseDouble(p.getProperty(
						"ID_POS_CONTRIBUTION", new Double(ID_POS_CONTRIBUTION)
								.toString()));
				ID_NEG_CONTRIBUTION = Double.parseDouble(p.getProperty(
						"ID_NEG_CONTRIBUTION", new Double(ID_NEG_CONTRIBUTION)
								.toString()));
				SIGNAL_CONTRIBUTION = Double.parseDouble(p.getProperty(
						"SIGNAL_CONTRIBUTION", new Double(SIGNAL_CONTRIBUTION)
								.toString()));
				SIGNAL_PENALTY_THRESHOLD = Double.parseDouble(p.getProperty(
						"SIGNAL_PENALTY_THRESHOLD", new Double(
								SIGNAL_PENALTY_THRESHOLD).toString()));
				SIGNAL_GRAPH_LEVELING = Double.parseDouble(p.getProperty(
						"SIGNAL_GRAPH_LEVELING", new Double(
								SIGNAL_GRAPH_LEVELING).toString()));

			} catch (Exception e) {
				Log.getLogger().log(
						Level.WARNING,
						"RedpinLocator Config initialization failed: "
								+ e.getMessage(), e);

			}
		}
	}

	public Location locate(Measurement currentMeasurement) {

		if (debug) {
			loadParameters();
		}

		Location loc = null;

		// get all measurement in database
		List<Measurement> list = measurementHome.getAll(mapId);
		/* check for similarity */
		TreeSet<Measurement> hits = new TreeSet<Measurement>(
				new MeasurementComparator(currentMeasurement));

		for (Measurement m : list) {
			int level = measurementSimilarityLevel(m, currentMeasurement);

			if (debug) {
				// debug
				Fingerprint fp = fingerprintHome.getByMeasurementId(m.getId());

				if (fp != null) {
					Location l = (Location) fp.getLocation();
					if (l != null) {
						log.log(Level.FINE, "location \""
								+ l.getMap().getMapName() + l.getSymbolicID()
								+ "\" achieved similarity level " + level);
					}
				}
				// end debug
			}

			if (level > LOCATION_THRESHOLD) {
				hits.add(m);
			}
		}

		if (hits.size() > 0) {

			Measurement bestMatch = hits.first();

			Fingerprint f = fingerprintHome.getByMeasurementId(bestMatch
					.getId());

			if (f != null) {
				loc = (Location) f.getLocation();
				loc.setAccuracy(measurementSimilarityLevel(bestMatch,
						currentMeasurement));

				if (debug) {
					log.log(Level.FINE, "Best match: \""
							+ loc.getMap().getMapName() + loc.getSymbolicID()
							+ "\" achieved similarity level "
							+ loc.getAccuracy());
				}
			}

		}

		return loc;
	}

	@SuppressWarnings("unchecked")
	public int measurementSimilarityLevel(com.aiyc.base.core.Measurement t,
			com.aiyc.base.core.Measurement o) {

		Location mloc = null;
		if (debug) {
			Fingerprint tf = fingerprintHome
					.getByMeasurementId(((Measurement) t).getId());

			if (tf != null) {
				mloc = (Location) tf.getLocation();
			}

			if (mloc != null) {
				log.log(loglevel,
						"Calculating similarity Level between current measurement and "
								+ mloc.getMap().getMapName()
								+ mloc.getSymbolicID());
			} else {
				log.log(loglevel,
						"Calculating similarity Level between current Measurements and #"
								+ ((Measurement) t).getId());
			}
		} else {
			log.log(loglevel,
					"Calculating similarity Level between current Measurements and #"
							+ ((Measurement) t).getId());
		}

		/* total amount of credit that can be achieved */
		double totalCredit = 0;

		/* account that holds the achieved credit */
		double account = 0;

		/* counts the nr of positive matches of reading ID's */
		int matches;

		/*
		 * log.log(loglevel, "WiFi ID penalty: (" + readings + " read - " holds
		 * the max nr of measured readings. max of reference measurement and
		 * current measurement
		 */
		int readings;

		java.util.Vector<WiFiReading> this_vect = t.getWiFiReadings();
		java.util.Vector<WiFiReading> other_vect = o.getWiFiReadings();

		/* check WiFiReadings */
		// Vector wifiReadings1 = this.wifiReadings;
		// Vector wifiReadings2 = m.getWiFiReadings();
		matches = 0;

		for (int i = 0; i < this_vect.size(); i++) {
			WiFiReading this_wifi = this_vect.elementAt(i);
			for (int j = 0; j < other_vect.size(); j++) {
				WiFiReading other_wifi = other_vect.elementAt(j);

				/*
				 * bssid match: add ID contribution and signal strength
				 * contribution
				 */
				if (this_wifi != null && this_wifi.getBssid() != null
						&& other_wifi != null && other_wifi.getBssid() != null
						&& this_wifi.getBssid().equals(other_wifi.getBssid())) {

					// log.log(loglevel, "WiFi + ID + SC");

					account += ID_POS_CONTRIBUTION;
					account += signalContribution(this_wifi.getRssi(),
							other_wifi.getRssi());
					matches++;
				}
			}
		}

		/*
		 * penalty if for each net that did not match
		 */
		readings = Math.max(this_vect.size(), other_vect.size());
		account += (readings - matches) * ID_NEG_CONTRIBUTION;

		log.log(loglevel, "WiFi ID penalty: (" + readings + " read - "
				+ matches + " matches) * " + ID_NEG_CONTRIBUTION + " = "
				+ (readings - matches) * ID_NEG_CONTRIBUTION);

		/*
		 * get the total credit for this measurement.
		 */
		totalCredit += this_vect.size() * ID_POS_CONTRIBUTION;

		totalCredit += this_vect.size() * SIGNAL_CONTRIBUTION;

		/* get accuracy level defined by bounds */
		int factor = LOCATION_KNOWN - LOCATION_UNKNOWN;

		/* a negative account results immediately in accuracy equals zero */
		int accuracy = 0;
		if (account > 0) {
			/*
			 * compute percentage of account from totalCredit -> [0,1]; stretch
			 * by accuracy span -> [0,MAX]; and in case min accuracy would not
			 * be zero, add this offset
			 */
			double a = (account / totalCredit) * factor + LOCATION_UNKNOWN;

			/* same as Math.round */
			accuracy = (int) Math.floor(a + 0.5d);
		}
		String logmsg = "Comparing measurements..."
				+ "\n"
				+ "-> Testing Measur: "
				+ (mloc == null ? ((Measurement) t).getId() : mloc.getMap()
						.getMapName()
						+ mloc.getSymbolicID()) + "\n" + "-> Credit achieved: "
				+ account + "\n" + "-> Total Credit possible: " + totalCredit
				+ "\n" + "-> Accuracy achieved: " + accuracy;
		log.log(loglevel, logmsg);

		return accuracy;
	}

	public Boolean measurmentAreSimilar(com.aiyc.base.core.Measurement t,
			com.aiyc.base.core.Measurement o) {

		if (measurementSimilarityLevel(t, o) > LOCATION_THRESHOLD) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * computes the credit contributed by the received signal strength of any
	 * wireless scan
	 */
	private double signalContribution(double rssi1, double rssi2) {

		/*
		 * we take the reference value of the rssi as base for further
		 * computations
		 */
		double base = rssi1;

		log.log(loglevel, "  Base: " + base);

		/*
		 * in order that +20 and -20 dB are treated the same, the penalty
		 * function uses the difference of the rssi's.
		 */
		double diff = Math.abs(rssi1 - rssi2);

		log.log(loglevel, "  Diff: " + diff);

		/* get percentage of error */
		double x = diff / base;

		log.log(loglevel, "  Diff percents of base: " + x);

		/* prevent division by zero */
		if (x > 0.0) {
			/*
			 * small error should result in a high contribution, big error in a
			 * small -> reciprocate (1/x) MIN = 1, MAX = infinity
			 */
			double y = 1 / x;

			log.log(loglevel, "  Current Contribution: " + y);

			/*
			 * compute percentage of treshold regarding the current base
			 */
			double t = SIGNAL_PENALTY_THRESHOLD / base;

			/*
			 * shift down the resulting graph. the root (zero) will then be
			 * exactly at x = treshold for every base, e.g. measurement, and
			 * signal differences above the treshold will result in a negative
			 * contribution
			 */
			y -= 1 / t;

			/*
			 * graph increases fast, so that a difference of 15 still results in
			 * a maximal contribution. With this adjustment, the graph gets flat
			 * and this has also an impact on the penalty (difference to big)
			 */
			y = y * SIGNAL_GRAPH_LEVELING;

			log.log(loglevel, "  Shifted Contribution: " + y + " (shifted by "
					+ t + ")");

			if ((-1 * SIGNAL_CONTRIBUTION <= y) && (y <= SIGNAL_CONTRIBUTION)) {

				log.log(loglevel, "  Returned: " + y);
				return y;
			} else {

				log.log(loglevel, "  Returned: " + SIGNAL_CONTRIBUTION
						+ " (CUTOFF, y was " + y + ")");

				/* don't exceed the max possible credits/penalty */
				return SIGNAL_CONTRIBUTION;
			}
		} else {

			log.log(loglevel, "  Returned: " + SIGNAL_CONTRIBUTION
					+ " (diff was zero)");
			return SIGNAL_CONTRIBUTION;
		}
	}

}
