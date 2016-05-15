package br.uvv.wscarona.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.mysql.jdbc.StringUtils;

import br.uvv.wscarona.model.InfoUser;
import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.webservice.util.AuthenticatorUtil;
import br.uvv.wscarona.webservice.util.ListMessageException;;

@Stateless
public class LoginDAO extends GenericDAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SELECT_STUDENT_FOR_LOGIN = "SELECT st FROM Student st WHERE st.code = :code AND (st.password = :password OR st.lostPassword = :password) ";
	private static final String SELECT_TOKEN_FOR_STUDENT = "SELECT tk FROM InfoUser tk WHERE tk.student.id = :idStudent ";
	private static final String SELECT_STUDENT_TOKEN = "SELECT st FROM InfoUser tk JOIN tk.student st WHERE tk.token like(:token) ";

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Student getStudentLogin(Student student) throws ListMessageException {
		try{
			// VALIDAÇÕES
			if (student == null) {
				this.erros.addRquiredField("attr.student.code");
				this.erros.addRquiredField("attr.password");
			} else {
				if (student.getCode() == null) {
					this.erros.addRquiredField("attr.student.code");
				}
				if (StringUtils.isNullOrEmpty(student.getPassword())) {
					this.erros.addRquiredField("attr.password");
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
	public InfoUser saveOrUpdateToken(InfoUser token) throws ListMessageException {
		try{
			// VALIDAÇÕES
			if (token == null) {
				this.erros.addError("error.no.token");
			} else {
				if(token.getStudent() == null){
					this.erros.addError("error.no.student");
				}
				else if(StringUtils.isNullOrEmpty(token.getStudent().getCode())){
					this.erros.addRquiredField("attr.student.code");
				}
				else if(StringUtils.isNullOrEmpty(token.getStudent().getPassword())){
					this.erros.addRquiredField("attr.student.password");
				}
			}
			this.throwErros(); // LANÇA OS ERROS CASO EXISTAM
	
			StringBuilder hql = new StringBuilder(SELECT_TOKEN_FOR_STUDENT);
			Query query = this.entityManager.createQuery(hql.toString());
			query.setParameter("idStudent", token.getStudent().getId());
			InfoUser aux = (InfoUser) query.getSingleResult();
			
			this.entityManager.detach(aux);
			aux.setToken(token.getToken());
			aux.setExpirationToken(token.getExpirationToken());
			token = aux;
		}catch(NoResultException e){
		}
		return (InfoUser) merge(token);
	}

	public Student validateToken(String token) throws ListMessageException {
		try{
			// VALIDAÇÕES
			if (token == null) {
				this.erros.addError("error.no.token");
			} 
			this.throwErros(); // LANÇA OS ERROS CASO EXISTAM
	
			StringBuilder hql = new StringBuilder(SELECT_STUDENT_TOKEN);
			Query query = this.entityManager.createQuery(hql.toString());
			query.setParameter("token", token);
			return (Student) query.getSingleResult();
		}catch(NoResultException e){
			this.erros.addError("error.invalid.token");
			this.throwErros();
			return null;
		}
	}
}
