package br.uvv.wscarona.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.uvv.wscarona.model.User;

@Stateless
public class UserDAO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<User> getUsers(){
		StringBuilder hql = new StringBuilder("SELECT u FROM User u");
		Query query = this.entityManager.createQuery(hql.toString());
		return (List<User>) query.getResultList();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public User save(User user){
		this.entityManager.persist(user);
		return user;
	}
}
