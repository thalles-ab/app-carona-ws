package br.uvv.wscarona.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import br.uvv.wscarona.model.Student;

@Stateless
public class StudentDAO extends GenericDAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Student> getUsers() {
		StringBuilder hql = new StringBuilder("SELECT s FROM Student s");
		Query query = this.entityManager.createQuery(hql.toString());
		return (List<Student>) query.getResultList();
	}

}
