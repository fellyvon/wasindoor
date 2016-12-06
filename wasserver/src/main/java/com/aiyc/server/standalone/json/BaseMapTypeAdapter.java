 
package com.aiyc.server.standalone.json;

import java.lang.reflect.Type;




import com.aiyc.server.standalone.core.Map;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 
 *
 */
public class BaseMapTypeAdapter implements JsonSerializer<com.aiyc.base.core.Map>,
		JsonDeserializer<com.aiyc.base.core.Map> {

	/**
	 * @see JsonSerializer#serialize(Object, Type, JsonSerializationContext)
	 */
 
	public JsonElement serialize(com.aiyc.base.core.Map src, Type typeOfSrc,
			JsonSerializationContext context) {
		return context.serialize(src, Map.class);
	}

	/**
	 * @see JsonDeserializer#deserialize(JsonElement, Type, JsonDeserializationContext)
	 */
	 
	public Map deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		return context.deserialize(json, Map.class);
	}

}
