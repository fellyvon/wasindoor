 
package com.aiyc.server.standalone.svm;

import java.util.List;

import com.aiyc.server.standalone.core.Fingerprint;
import com.aiyc.server.standalone.core.Location;
import com.aiyc.server.standalone.core.Measurement;
import com.aiyc.server.standalone.core.measure.WiFiReading;
import com.aiyc.server.standalone.db.HomeFactory;

/**
 *  
 * @author felly
 *
 */
public class CategorizerFactory {

	private static Categorizer pLocationCategorizer; 
	private static Categorizer pBSSIDCategorizer;

	public synchronized static Categorizer LocationCategorizer() {
		if(pLocationCategorizer == null) {
			pLocationCategorizer = new Categorizer();
		}		
		return pLocationCategorizer;
	}
	
	public synchronized static Categorizer BSSIDCategorizer() {
		if(pBSSIDCategorizer == null) {
			pBSSIDCategorizer = new Categorizer();
		}		
		return pBSSIDCategorizer;
	}
	
	/**
	 * SVM requires all attributes as real numbers. This function 
	 * converts location tags and bssids into numeric data. 
	 * @param dataset
	 */
	public synchronized static void buildCategories(){
		List<Fingerprint> dataset = HomeFactory.getFingerprintHome().getAll();
		for (Fingerprint f : dataset) {
			if (f == null || f.getLocation() == null || f.getMeasurement() == null) continue;
			
			Integer id = ((Location)f.getLocation()).getId();
			if (id == null) continue;
			
			String locationTag = id.toString();
			LocationCategorizer().AddCategory(locationTag);
			Measurement m = (Measurement)f.getMeasurement();
			for (WiFiReading r : m.getWiFiReadings()) {
				if (r != null && r.getBssid() != null) {
					BSSIDCategorizer().AddCategory(r.getBssid());
				}
			}
		}
	}
}
