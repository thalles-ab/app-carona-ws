package br.uvv.wscarona.webservice.util;

import com.google.gson.annotations.Expose;

public class MessageException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MessageException(String message) {
		super();
		this.message = message;
	}
	
	@Expose
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
