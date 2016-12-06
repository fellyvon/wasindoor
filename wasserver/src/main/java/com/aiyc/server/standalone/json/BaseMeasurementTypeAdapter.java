 
package com.aiyc.server.standalone.json;

import java.lang.reflect.Type;


import com.aiyc.server.standalone.core.Measurement;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 
 */
public class BaseMeasurementTypeAdapter implements JsonSerializer<com.aiyc.base.core.Measurement>, JsonDeserializer<com.aiyc.base.core.Measurement>{
	
	/**
	 * @see JsonSerializer#serialize(Object, Type, JsonSerializationContext)
	 */
 
	public JsonElement serialize(com.aiyc.base.core.Measurement src, Type typeOfSrc,
			JsonSerializationContext context) {
		
		return context.serialize(src, Measurement.class);
	}

	/**
	 * @see JsonDeserializer#deserialize(JsonElement, Type, JsonDeserializationContext)
	 */
 
	public Measurement deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
	
		return context.deserialize(json, Measurement.class);
	}


}
