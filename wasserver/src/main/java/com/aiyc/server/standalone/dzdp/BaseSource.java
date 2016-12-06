package com.aiyc.server.standalone.dzdp;

public class BaseSource implements Source {

	public INet getSource(String source) throws Exception {
		 
		return (INet)Class.forName(source).newInstance() ;
	}

}
