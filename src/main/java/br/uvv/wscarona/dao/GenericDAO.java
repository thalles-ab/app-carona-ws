package br.uvv.wscarona.dao;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.uvv.wscarona.webservice.util.ListMessageException;

public class GenericDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected ListMessageException erros; 

	@PersistenceContext
	protected EntityManager entityManager;
	 
	public GenericDAO() {
		erros = new ListMessageException();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Object merge(Object entity) {
		this.entityManager.merge(entity);
		return entity;
	}
	
	public void throwErros() throws ListMessageException{
		if(erros == null){
			return ;
		}
		if(erros.getErros().isEmpty() == false){
			ListMessageException aux = new ListMessageException(erros.getErros());
			erros.setErros(new ArrayList<>());
			throw aux;
		}
	}

}
