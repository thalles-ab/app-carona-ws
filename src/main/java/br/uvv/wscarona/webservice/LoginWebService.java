package br.uvv.wscarona.webservice;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.uvv.wscarona.dao.LoginDAO;
import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.model.InfoUser;
import br.uvv.wscarona.webservice.util.AuthenticatorUtil;
import br.uvv.wscarona.webservice.util.ListMessageException;
import br.uvv.wscarona.webservice.util.MessageBundle;

@Path("/login")
@RequestScoped
public class LoginWebService extends BaseWebService{
	@Inject private LoginDAO loginDAO;

	@POST
	public Response login(String json){
		try{
			Student student = loginDAO.getStudentLogin(gson.fromJson(json, Student.class));
			if(student == null){
				MessageBundle.addError("error.login.password.invalid", this.erros);
				throw erros;
			}

			InfoUser token = AuthenticatorUtil.generateToken(student.getCode());
			token.setStudent(student);
			loginDAO.saveOrUpdateToken(token);
			
			return successRequest(token);
		}catch(ListMessageException list){
			return badRequest(list);
		} 
	}
}
