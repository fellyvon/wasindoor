 
package com.aiyc.server.standalone.svm;

import java.util.List;

import com.aiyc.server.standalone.core.Fingerprint;
import com.aiyc.server.standalone.core.Location;
import com.aiyc.server.standalone.core.Measurement;
import com.aiyc.server.standalone.core.measure.WiFiReading;
import com.aiyc.server.standalone.db.HomeFactory;
import com.aiyc.server.standalone.util.ConsoleData;

/**
 *  
 * @author  felly
 *
 */
public class MapCategorizerFactory {

 

	public synchronized static Categorizer LocationCategorizer(String mapId) {
		if(ConsoleData.instance().getCachedValue(mapId) == null) {
			ConsoleData.instance().addCached(mapId ,new Categorizer());
		}		
		return (Categorizer)ConsoleData.instance().getCachedValue(mapId);
	}
	
	public synchronized static Categorizer BSSIDCategorizer(String mapId) {
		 return LocationCategorizer(mapId);
	}
	
	/**
	 * SVM requires all attributes as real numbers. This function 
	 * converts location tags and bssids into numeric data. 
	 * @param dataset
	 */
	public synchronized static void buildCategories(String mapId){
		List<Fingerprint> dataset = HomeFactory.getFingerprintHome().getAll(mapId);
		for (Fingerprint f : dataset) {
			if (f == null || f.getLocation() == null || f.getMeasurement() == null) continue;
			
			Location  id = ((Location)f.getLocation());
			if (id == null) continue;
			
		 
			LocationCategorizer(mapId).AddCategory(id.getMapXcord()+""+id.getMapYcord());
			Measurement m = (Measurement)f.getMeasurement();
			for (WiFiReading r : m.getWiFiReadings()) {
				if (r != null && r.getBssid() != null) {
					BSSIDCategorizer(mapId).AddCategory(r.getBssid());
				}
			}
		}
	}
}
