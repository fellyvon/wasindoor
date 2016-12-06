 
package com.aiyc.server.standalone.json;



import java.lang.reflect.Type;


import com.aiyc.server.standalone.core.Location;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 *  
 * @see JsonSerializer
 * @see JsonDeserializer
 * @author felly
 */
public class BaseLocationTypeAdapter implements JsonSerializer<com.aiyc.base.core.Location>,
		JsonDeserializer<com.aiyc.base.core.Location> {

	/**
	 * @see JsonSerializer#serialize(Object, Type, JsonSerializationContext)
	 */
 
	public JsonElement serialize(com.aiyc.base.core.Location src, Type typeOfSrc,
			JsonSerializationContext context) {
		
		return context.serialize(src, Location.class);
	}

	/**
	 * @see JsonDeserializer#deserialize(JsonElement, Type, JsonDeserializationContext)
	 */
 
	public Location deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {

		return context.deserialize(json, Location.class);
	}

}
