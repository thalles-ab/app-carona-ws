package br.uvv.wscarona.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.mysql.jdbc.StringUtils;

import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.webservice.util.AuthenticatorUtil;
import br.uvv.wscarona.webservice.util.ListMessageException;

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
    public Student saveOrUpdate(Student student) throws ListMessageException{
        try{
            fullValidation(student);
            this.throwErros();
            this.merge(student);
        }catch(NoResultException e){
            return null;
        }
        return student;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void lostPassword(Student studentParam) throws ListMessageException{
	    if(studentParam == null || studentParam.getId() == null){
	    	this.erros.addRquiredField("attr.student");
	    }
	    
	    this.throwErros();
	    
		String newPassword = AuthenticatorUtil.getPasswordRandom();
		studentParam.setLostPassword(AuthenticatorUtil.getDbPassword(newPassword));
		this.merge(studentParam);
		System.out.println(AuthenticatorUtil.hashString(newPassword, "MD5"));
    }

    public void fullValidation(Student student){
        if(StringUtils.isEmptyOrWhitespaceOnly(student.getCode())){
            this.erros.addRquiredField("attr.student.code");
        }
        if(student.getCode().length()>255){
            this.erros.addMaxLengthError("attr.student.code");
        }
        if(StringUtils.isEmptyOrWhitespaceOnly(student.getName())){
            this.erros.addRquiredField("attr.student.name");
        }
        if(student.getName().length()>255){
            this.erros.addMaxLengthError("attr.student.name");
        }
        if(student.getPhoto()!=null){
            if(StringUtils.isEmptyOrWhitespaceOnly(student.getPhoto())){
                this.erros.addRquiredField("attr.student.photo");
            }
            if(student.getPhoto().length()>255){
                this.erros.addMaxLengthError("attr.student.photo");
            }
        }
        if(StringUtils.isEmptyOrWhitespaceOnly(student.getPassword())){
            this.erros.addRquiredField("attr.student.password");
        }
        if(student.getPassword().length()>255) {
            this.erros.addMaxLengthError("attr.password");
        }
        if(student.getPassword().length()<6) {
            this.erros.addMinLengthError("attr.password");
        }
    }
}
