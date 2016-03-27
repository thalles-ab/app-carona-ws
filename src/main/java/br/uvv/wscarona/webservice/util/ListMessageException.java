package br.uvv.wscarona.webservice.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class ListMessageException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ListMessageException() {
		super();
		this.erros = new ArrayList<>();
	} 
	
	public ListMessageException(List<MessageException> erros) {
		super();
		this.erros = erros;
	}
	
	public ListMessageException(String error) {
		this();
		this.erros.add(new MessageException(error));
	}


	@Expose
	private List<MessageException> erros;

	public List<MessageException> getErros() {
		return erros;
	}

	public void setErros(List<MessageException> erros) {
		this.erros = erros;
	}
}
