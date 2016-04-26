package br.uvv.wscarona.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.mysql.jdbc.StringUtils;

import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.model.Token;
import br.uvv.wscarona.webservice.util.AuthenticatorUtil;
import br.uvv.wscarona.webservice.util.ListMessageException;
import br.uvv.wscarona.webservice.util.MessageBundle;

@Stateless
public class LoginDAO extends GenericDAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SELECT_STUDENT_FOR_LOGIN = "SELECT st FROM Student st WHERE st.code = :code AND st.password = :password";
	private static final String SELECT_TOKEN_FOR_STUDENT = "SELECT tk FROM Token tk WHERE tk.student.id = :idStudent ";
	private static final String SELECT_STUDENT_TOKEN = "SELECT st FROM Token tk JOIN tk.student st WHERE tk.token = :token ";

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Student getStudentLogin(Student student) throws ListMessageException {
		try{
			// VALIDAÇÕES
			if (student == null) {
				MessageBundle.addRquiredField("attr.student.code", erros);
				MessageBundle.addRquiredField("attr.password", erros);
			} else {
				if (student.getCode() == null) {
					MessageBundle.addRquiredField("attr.student.code", erros);
				}
				if (StringUtils.isNullOrEmpty(student.getPassword())) {
					MessageBundle.addRquiredField("attr.password", erros);
				}
			}
			this.throwErros(); // LANÇA OS ERROS CASO EXISTAM
	
			StringBuilder hql = new StringBuilder(SELECT_STUDENT_FOR_LOGIN);
			Query query = this.entityManager.createQuery(hql.toString());
			query.setParameter("code", student.getCode());
			query.setParameter("password", AuthenticatorUtil.getPassword(student.getPassword()));
			return (Student) query.getSingleResult();
		}catch(NoResultException e){
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Token saveOrUpdateToken(Token token) throws ListMessageException {
		try{
			// VALIDAÇÕES
			if (token == null) {
				MessageBundle.addError("error.no.token", erros);
			} else {
				if(token.getStudent() == null){
					MessageBundle.addError("error.no.student", erros);
				}
				else if(token.getStudent().getCode() == null){
					MessageBundle.addError("error.no.code", erros);
				}
			}
			this.throwErros(); // LANÇA OS ERROS CASO EXISTAM
	
			StringBuilder hql = new StringBuilder(SELECT_TOKEN_FOR_STUDENT);
			Query query = this.entityManager.createQuery(hql.toString());
			query.setParameter("idStudent", token.getStudent().getId());
			Token aux = (Token) query.getSingleResult();
			
			this.entityManager.detach(aux);
			aux.setToken(token.getToken());
			aux.setExpirationDate(token.getExpirationDate());
			token = aux;
		}catch(NoResultException e){
		}
		return (Token) merge(token);
	}

	public Student validateToken(String token) throws ListMessageException {
		try{
			// VALIDAÇÕES
			if (token == null) {
				MessageBundle.addError("error.no.token", erros);
			} 
			this.throwErros(); // LANÇA OS ERROS CASO EXISTAM
	
			StringBuilder hql = new StringBuilder(SELECT_STUDENT_TOKEN);
			Query query = this.entityManager.createQuery(hql.toString());
			query.setParameter("token", token);
			return (Student) query.getSingleResult();
		}catch(NoResultException e){
			MessageBundle.addError("error.invalid.token", erros);
			this.throwErros();
			return null;
		}
	}
}
