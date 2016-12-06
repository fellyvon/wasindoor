 
package com.aiyc.server.standalone.json;



import com.aiyc.server.standalone.core.Measurement;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Factory for {@link Gson} instances.
 
 */
public class GsonFactory {
	
	private static Gson gson;
	
	/**
	 * Gets a configured {@link Gson} instance
	 *  
	 * @return {@link Gson} instance
	 */
	public synchronized static Gson getGsonInstance() {
		if(gson == null) {
			GsonBuilder builder = new GsonBuilder();
			
			//needed to get proper sub type after deserialization
			builder.registerTypeAdapter(com.aiyc.base.core.Fingerprint.class, new BaseFingerprintTypeAdapter());
			builder.registerTypeAdapter(com.aiyc.base.core.Location.class, new BaseLocationTypeAdapter());
			builder.registerTypeAdapter(com.aiyc.base.core.Map.class, new BaseMapTypeAdapter());
			builder.registerTypeAdapter(com.aiyc.base.core.Measurement.class, new BaseMeasurementTypeAdapter());
			
			//needed in order to deserialize proper the measurement vectors
			builder.registerTypeAdapter(Measurement.class, new MeasurementTypeAdapter());
	
			//builder.setPrettyPrinting();
			gson = builder.create();

		}
		
		return gson;
	}
	
}
