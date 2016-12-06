 
package com.aiyc.server.standalone.locator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.aiyc.server.standalone.core.Location;
import com.aiyc.server.standalone.core.Measurement;
import com.aiyc.server.standalone.db.HomeFactory;
import com.aiyc.server.standalone.svm.CategorizerFactory;
import com.aiyc.server.standalone.svm.SVMSupport;
import com.aiyc.server.standalone.util.Log;

/**
 * Locator that uses support vector machines (SVM) to estimate location
 *  
 * @author felly
 *
 */
public class SVMLocator implements ILocator {
	
	Logger log;
	
	public SVMLocator() {
		log = Log.getLogger();
	}
	
	 
	public Location locate(Measurement currentMeasurement) 
	{
		Location l = null;
		
		try {
			
			String outputfile = SVMSupport.predict(currentMeasurement);		
			
			BufferedReader outputreader = new BufferedReader(new InputStreamReader(new FileInputStream(outputfile)));
			String outputline = outputreader.readLine();
			if (outputline != null) {
				double locationTagIDScaled = Double.parseDouble(outputline);
				int locationTagID = (int)locationTagIDScaled;
				l = getByLocationTag(CategorizerFactory.LocationCategorizer().GetCategory(locationTagID));
			}		
			
		} catch (FileNotFoundException e) {
			log.log(Level.SEVERE, "locate failed due to FileNotFoundException: " + e.getMessage());
		} catch (IOException e) {
			log.log(Level.SEVERE, "locate failed due to IOException: " + e.getMessage());
		} catch (NumberFormatException e) {
			log.log(Level.SEVERE, "locate failed due to NumberFormatException: " + e.getMessage());
		}


		return l;
	}	
	
	
	private Location getByLocationTag(String tag) {
		if (tag == null) return null;
		try {
			int id = Integer.parseInt(tag);
			
			return HomeFactory.getLocationHome().getById(id);
			
		} catch (NumberFormatException e) {
			log.log(Level.WARNING, "getByLocationTag failed: " +e.getMessage());
		}
		return null;
		
	}
	
 
	public int measurementSimilarityLevel(com.aiyc.base.core.Measurement t,
			com.aiyc.base.core.Measurement o) {
		return 0;
	}

 
	public Boolean measurmentAreSimilar(com.aiyc.base.core.Measurement t,
			com.aiyc.base.core.Measurement o) {
		return null;
	}

}
