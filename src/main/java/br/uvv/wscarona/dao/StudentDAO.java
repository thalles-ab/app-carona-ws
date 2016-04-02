package br.uvv.wscarona.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.webservice.util.ListMessageException;
import br.uvv.wscarona.webservice.util.MessageBundle;


@Stateless
public class StudentDAO extends GenericDAO {
	/**
	 * 
	 */
	private static int NameMinLenght;
	private static int NameMaxLenght;
	static {
		NameMinLenght = 1;
		NameMaxLenght = 255;
	}
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Student> getUsers() {
		StringBuilder hql = new StringBuilder("SELECT s FROM Student s");
		Query query = this.entityManager.createQuery(hql.toString());
		return (List<Student>) query.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Student Save(Student entity) throws ListMessageException {
		//TODO - Validações
		try{
			entity.setName(entity.getName().trim());
			entity.setPhoto(entity.getPhoto().trim());
			ValidateName(entity.getName());
			throwErros();
			entityManager.merge(entity);
			return entity;
		}
		catch (NoResultException e){
			return null;
		}
	}

	private void ValidateName(String name){
		if(name.length()< NameMinLenght ){
			MessageBundle.addRquiredField("Tamanho mínimo não foi alcançado.", erros);
		}
		if(name.length()> NameMaxLenght){
			MessageBundle.addRquiredField("Tamanho mínimo não foi alcançado.", erros);
		}
	}
}
