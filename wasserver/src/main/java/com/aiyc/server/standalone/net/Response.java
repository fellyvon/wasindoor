 
package com.aiyc.server.standalone.net;
/**
 * Class representing a response from the server. 
 * 
 * @author felly
 */
public class Response {
	
	/**
	 * Different status the server reports back to the client
	  
	 */
	public enum Status {
		ok,
		failed,
		warning,
		jsonError;
	}
	
	
	private Status status;
	private String message;
	private Object data;
	
	
	public Response() {}
	
	
	public Response(Status status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	/**
	 * 
	 * @param status {@link Status} to be sent to the client
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	
	/**
	 * 
	 * @return {@link Status} to be sent to the client
	 */
	public Status getStatus() {
		return status;
	}
	
	/**
	 * 
	 * @param message Message to be sent to the client
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 
	 * @return Message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * 
	 * @param data Data to be sent to the client
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
	/**
	 * 
	 * @return Data
	 */
	public Object getData() {
		return data;
	}
	
}
