package com.aiyc.server.standalone.json;

import java.lang.reflect.Type;

import com.aiyc.server.standalone.core.Fingerprint;
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
public class BaseFingerprintTypeAdapter implements
		JsonSerializer<com.aiyc.base.core.Fingerprint>,
		JsonDeserializer<com.aiyc.base.core.Fingerprint> {

	/**
	 * @see JsonSerializer#serialize(Object, Type, JsonSerializationContext)
	 */

	public JsonElement serialize(com.aiyc.base.core.Fingerprint src,
			Type typeOfSrc, JsonSerializationContext context) {
		return context.serialize(src, Fingerprint.class);
	}

	/**
	 * @see JsonDeserializer#deserialize(JsonElement, Type,
	 *      JsonDeserializationContext)
	 */

	public Fingerprint deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		return context.deserialize(json, Fingerprint.class);
	}

}
