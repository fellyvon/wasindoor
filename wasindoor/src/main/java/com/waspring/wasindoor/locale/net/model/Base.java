package com.waspring.wasindoor.locale.net.model;

import com.google.gson.Gson;

/**
 * 
 * @author felly
 * 
 */
public abstract class Base implements IMessage {

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
