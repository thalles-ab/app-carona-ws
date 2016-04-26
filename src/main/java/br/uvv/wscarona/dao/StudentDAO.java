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
import com.mysql.jdbc.StringUtils;

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

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Student SaveOrUpdate(Student student) throws ListMessageException{
        try{
            fullValidation(student);
            this.throwErros();
            this.merge(student);
        }catch(NoResultException e){
            return null;
        }
        return student;
    }

    public void fullValidation(Student student){
        if(StringUtils.isEmptyOrWhitespaceOnly(student.getCode())){
            MessageBundle.addRquiredField("attr.student.code", erros);
        }
        if(student.getCode().length()>255){
            MessageBundle.addMaxLengthError("attr.student.code", erros);
        }
        if(StringUtils.isEmptyOrWhitespaceOnly(student.getName())){
            MessageBundle.addRquiredField("attr.student.name", erros);
        }
        if(student.getName().length()>255){
            MessageBundle.addMaxLengthError("attr.student.name", erros);
        }
        if(student.getPhoto()!=null){
            if(StringUtils.isEmptyOrWhitespaceOnly(student.getPhoto())){
                MessageBundle.addRquiredField("attr.student.photo", erros);
            }
            if(student.getPhoto().length()>255){
                MessageBundle.addMaxLengthError("attr.student.photo", erros);
            }
        }
        if(StringUtils.isEmptyOrWhitespaceOnly(student.getPassword())){
            MessageBundle.addRquiredField("attr.student.password", erros);
        }
        if(student.getPassword().length()>255) {
            MessageBundle.addMaxLengthError("attr.password", erros);
        }
        if(student.getPassword().length()<6) {
            MessageBundle.addMinLengthError("attr.password", erros);
        }
    }
}
