 
package com.aiyc.server.standalone.locator;

import com.aiyc.server.standalone.svm.SVMSupport;

/**
 * Factory for {@link ILocator}
 * 
 * @author felly 
 */
public class LocatorHome {
	
	private static ILocator locator = null;
	
	public synchronized static ILocator getLocator() {
		if(locator == null) {
			locator = new BaseLocator();
		}
		
		if (locator instanceof BaseLocator) {
			if (SVMSupport.isTrained()) {
				locator = new SVMLocator();		//locator is changed only once 
			}
		}
		
		return locator;
	}
	
	
	
}
