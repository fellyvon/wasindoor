package com.aiyc.server.standalone.longwait;

public class ExcepitonTrace {
	public static  String getExceptionTraceInfo(Exception e){
		   
		StackTraceElement ep[]=  e.getStackTrace();
		String exp=e.getMessage()+"\r\n";
		for(int i=0;i<ep.length;i++){
	 
              exp+="\tat " + ep[i]+"\r\n";

          
		  
		}
		  Throwable ourCause = e.getCause();
          if (ourCause != null)
          {
        	  ep=  ourCause.getStackTrace();
        	  
        	  for(int i=0;i<ep.length;i++){
        			 
                  exp+="\tat " + ep[i]+"\r\n";

              
    		  
    		}
          }
          
          
          return exp;
	}
}
