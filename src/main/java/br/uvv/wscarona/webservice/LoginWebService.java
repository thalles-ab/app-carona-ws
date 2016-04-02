package br.uvv.wscarona.webservice;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.uvv.wscarona.dao.LoginDAO;
import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.model.Token;
import br.uvv.wscarona.webservice.util.AuthenticatorUtil;
import br.uvv.wscarona.webservice.util.ListMessageException;

@Path("/login")
@RequestScoped
public class LoginWebService extends BaseWebService{
	@Inject private LoginDAO loginDAO;
	
	@POST
	public Response login(String json){
		try{
	 		Student student = loginDAO.getStudentLogin(gson.fromJson(json, Student.class));
			if(student == null){
				return responseBadRequest("error.login.password.invalid");
			}
			
			Token token = AuthenticatorUtil.generateToken(student.getCode());
			token.setStudent(student);
			loginDAO.saveOrUpdateToken(token);
			
			return responseOk(token);
		}catch(ListMessageException list){
			return responseBadRequest(list);
		}
	}
}
