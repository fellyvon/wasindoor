package com.aiyc.server.standalone.json;

import java.lang.reflect.Type;
import java.util.Collection;

import com.aiyc.server.standalone.core.Measurement;
import com.aiyc.server.standalone.core.Vector;
import com.aiyc.server.standalone.core.measure.BluetoothReading;
import com.aiyc.server.standalone.core.measure.GSMReading;
import com.aiyc.server.standalone.core.measure.WiFiReading;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

/**
 * Custom adapter for {@link Measurement}s in order to deserialize the
 * {@link Vector}s
 * 
 * @see JsonDeserializer
 */
public class MeasurementTypeAdapter implements JsonDeserializer<Measurement> {

	/**
	 * @see JsonDeserializer#deserialize(JsonElement, Type,
	 *      JsonDeserializationContext)
	 */

	public Measurement deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {

		// get all json elements in order to deserialize them separately
		JsonObject obj = json.getAsJsonObject();
		JsonElement json_timestamp = obj.get("timestamp");
		JsonElement json_wifi = obj.get("wifiReadings");
		JsonElement json_gsm = obj.get("gsmReadings");
		JsonElement json_bluetooth = obj.get("bluetoothReadings");

		// init vectors
		Vector<WiFiReading> wifi = new Vector<WiFiReading>();
		Vector<GSMReading> gsm = new Vector<GSMReading>();
		Vector<BluetoothReading> bluetooth = new Vector<BluetoothReading>();

		// deserialize reading vectors
		Type listType;
		if (json_wifi != null) {
			listType = new TypeToken<Vector<WiFiReading>>() {
			}.getType();
			Collection<WiFiReading> wificol = context.deserialize(json_wifi,
					listType);
			wifi.addAll(wificol);
		}

		if (json_gsm != null) {
			listType = new TypeToken<Vector<GSMReading>>() {
			}.getType();
			Collection<GSMReading> gsmcol = context.deserialize(json_gsm,
					listType);
			gsm.addAll(gsmcol);
		}

		if (json_bluetooth != null) {
			listType = new TypeToken<Vector<BluetoothReading>>() {
			}.getType();
			Collection<BluetoothReading> bluetoothcol = context.deserialize(
					json_bluetooth, listType);
			bluetooth.addAll(bluetoothcol);
		}
		// create deserialized measurement
		Measurement m = new Measurement(gsm, wifi, bluetooth);
		if (json_timestamp != null) {
			m.setTimestamp((Long) context.deserialize(json_timestamp,
					Long.class));
		}

		return m;
	}

}
