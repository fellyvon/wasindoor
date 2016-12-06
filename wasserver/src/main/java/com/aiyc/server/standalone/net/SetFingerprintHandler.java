 
package com.aiyc.server.standalone.net;


import com.aiyc.server.standalone.core.Fingerprint;
import com.aiyc.server.standalone.core.Location;
import com.aiyc.server.standalone.core.Measurement;
import com.aiyc.server.standalone.db.HomeFactory;
import com.aiyc.server.standalone.db.homes.FingerprintHome;
import com.aiyc.server.standalone.json.GsonFactory;
import com.aiyc.server.standalone.net.Response.Status;
import com.aiyc.server.standalone.svm.SVMSupport;
import com.aiyc.server.standalone.util.Log;
import com.google.gson.JsonElement;

/**
 * @see IHandler
 * @author felly
 */
public class SetFingerprintHandler implements IHandler {

	private static final int INSTANT_TRAIN_THREASHOLD = 20;
	
	FingerprintHome fingerprintHome;
	
	public SetFingerprintHandler() {
		fingerprintHome = HomeFactory.getFingerprintHome();
	}
		
	/**
	 * @see IHandler#handle(JsonElement)
	 */
 
	public Response handle(JsonElement data) {
		Response res;
		
		Fingerprint fprint = GsonFactory.getGsonInstance().fromJson(data, Fingerprint.class);
		if (fprint.getLocation() != null && ((Location)fprint.getLocation()).getId() != null && ((Location)fprint.getLocation()).getId().intValue() != -1) {
			Location l = HomeFactory.getLocationHome().getLocation(((Location)fprint.getLocation()).getId(), null);
			fprint = new Fingerprint(l,(Measurement)fprint.getMeasurement());
		}
		fprint = fingerprintHome.add(fprint);
		
		if(fprint == null) {
			res = new Response(Status.failed, "could not add to database", null);
			Log.getLogger().fine("fingerpint could not be added to the database");
		} else {
			res = new Response(Status.ok, null, fprint);
			Log.getLogger().finer("fingerprint set: " + fprint);
			
			
			Location loc = (Location)fprint.getLocation();
			int count = fingerprintHome.getCount(loc);
			
			if (count < INSTANT_TRAIN_THREASHOLD) {
				Log.getLogger().fine("Training model (fp count for loc " + loc.getSymbolicID() + ": " + count);
				Thread trainer = new Thread(new Runnable() {				
				 
					public void run() {
						SVMSupport.train();					
					}
				});
				trainer.start();			
			}
			
		}
		
				
		
		return res;
	}

}
