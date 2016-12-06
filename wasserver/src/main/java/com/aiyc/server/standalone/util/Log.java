 
package com.aiyc.server.standalone.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.XMLFormatter;

import com.aiyc.server.standalone.util.Configuration.LoggerFormat;

/**
 * Log class which provides access to the logger
 * 
 * @author  felly
 *
 */
public class Log {
	
	private static Logger logger;
	
	/**
	 * Get an instance of a {@link Logger} configured according to the settings
	 * 
	 * @return {@link Logger}
	 */
	public synchronized static Logger getLogger() {
		
		if(logger == null) {
			try {
				logger = Logger.getLogger("IcscnLogger");
				FileHandler fh = new FileHandler(Configuration.LogFile);
				
				if(Configuration.LogFormat == LoggerFormat.PLAIN) {
					fh.setFormatter(new SimpleFormatter());
				} else if(Configuration.LogFormat == LoggerFormat.XML) {
					fh.setFormatter(new XMLFormatter());
				} else {
					//default
					fh.setFormatter(new SimpleFormatter());					
				}
				
				logger.addHandler(fh);
				logger.setLevel(Configuration.LogLevel);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		} 
		
		return logger;
		
	}
	

	
}
