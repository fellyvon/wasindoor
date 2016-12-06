 
package com.aiyc.server.standalone.svm;

import java.util.Hashtable;

/**
 *  
 * @author  felly
 *
 */
public class Categorizer {
	protected Hashtable<String, Integer> categoryToIdDictionary = new Hashtable<String, Integer>();
	protected Integer ID = 0;
	private Hashtable<Integer, String> idToCategoryDictionary = new Hashtable<Integer, String>();
	
	public void clear() {
		ID = 0;
		categoryToIdDictionary.clear();
		idToCategoryDictionary.clear();
	}
	
	public Integer AddCategory(String name) {
		if (categoryToIdDictionary.containsKey(name)) return categoryToIdDictionary.get(name);
		ID++;
		categoryToIdDictionary.put(name, ID);
		idToCategoryDictionary.put(ID, name);
		return ID;
	}
	
	public String GetCategory(Integer id) {
		return idToCategoryDictionary.get(id);
	}
	
	public Integer GetCategoryID(String name) {
		if (categoryToIdDictionary.containsKey(name)) return categoryToIdDictionary.get(name);
		return -1;
	}
	
	
	
}

