package br.uvv.wscarona.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import br.uvv.wscarona.webservice.util.MessageBundle;
import com.mysql.jdbc.StringUtils;

import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.webservice.util.AuthenticatorUtil;
import br.uvv.wscarona.webservice.util.ListMessageException;

@Stateless
public class StudentDAO extends GenericDAO {
	/**
	 * 
	 */
    private static final String SELECT_STUDENT_BY_EMAIL = "SELECT st FROM Student st WHERE st.email like :email";
    private static final String SELECT_STUDENT_BY_CODE = "SELECT st FROM Student st WHERE st.code like :code";
//    private static final String SELECT_STUDENT_BY_EMAIL_OR_CODE = "SELECT st FROM Student st WHERE (st.email like :email OR st.code like :code)";
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Student> getUsers() {
		StringBuilder hql = new StringBuilder("SELECT s FROM Student s");
		Query query = this.entityManager.createQuery(hql.toString());
		return (List<Student>) query.getResultList();
	}

    public Student getStudent(Long id) throws ListMessageException {
        if(id==null || id<=0){
            this.erros.addRquiredField("attr.student.id");
        }
        this.throwErros();
        Student student = new Student();
        student.setId(id);
        student = (Student)searchById(student);
        return student;
    }

    public Boolean isUniqueCode(Student student){
        try{
            StringBuilder hql = new StringBuilder(SELECT_STUDENT_BY_CODE);
            Query query = this.entityManager.createQuery(hql.toString());
            query.setParameter("code", student.getCode());
            Student studentResult = (Student)query.getSingleResult();
            if( studentResult.getId() == student.getId() ){
                return true;
            }
            return false;
        }
        catch (NoResultException e){
            return true;
        }
        catch (NonUniqueResultException e){
            return false;
        }
    }

    public Boolean isUniqueEmail(Student student){
        try{
            StringBuilder hql = new StringBuilder(SELECT_STUDENT_BY_EMAIL);
            Query query = this.entityManager.createQuery(hql.toString());
            query.setParameter("email", student.getEmail());
            Student studentResult = (Student)query.getSingleResult();
            if( studentResult.getId() == student.getId() ){
                return true;
            }
            return false;
        }
        catch (NoResultException e){
            return true;
        }
        catch (NonUniqueResultException e){
            return false;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Student saveOrUpdate(Student student) throws ListMessageException{
        fullValidation(student);
        this.throwErros();
        if (isUniqueEmail(student) == false) {
            this.erros.addError("error.invalid.email");
        }
        if(student.getId()==0) {
            if(isUniqueCode(student)== false){
                this.erros.addError("error.invalid.code");
            }
        }
        this.throwErros();
        return (Student)this.merge(student);
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
        if(StringUtils.isEmptyOrWhitespaceOnly(student.getEmail())){
            this.erros.addRquiredField("attr.student.email");
        }
        if(student.getEmail().length()>255){
            this.erros.addMaxLengthError("attr.student.email");
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
