package br.uvv.wscarona.webservice.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class ListMessageException extends Exception {
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
		this.addError(error);
	}

	@Expose
	private List<MessageException> erros;

	public void addError(String key) {
		String mensagem = MessageBundle.getMessage(key);
		this.erros.add(new MessageException(mensagem));
	}

	public void addError(String key, Object... params) {
		String mensagem = MessageBundle.getMessage(key, params);
		this.erros.add(new MessageException(mensagem));
	}

	public void addRquiredField(String keyField) {
		this.addError("error.required.field", MessageBundle.getMessage(keyField));
	}

	public void addMaxLengthError(String keyField) {
		this.addError("error.maxlength.field", MessageBundle.getMessage(keyField));
	}

	public void addMinLengthError(String keyField) {
		this.addError("error.minlength.field", MessageBundle.getMessage(keyField));
	}
	
	public void clear(){
		this.erros = new ArrayList<>();
	}

	public List<MessageException> getErros() {
		return erros;
	}
}
