package br.uvv.wscarona.webservice;

import br.uvv.wscarona.dao.LoginDAO;
import br.uvv.wscarona.model.InfoUser;
import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.webservice.util.AuthenticatorUtil;
import br.uvv.wscarona.webservice.util.ListMessageException;
import com.mysql.jdbc.StringUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/login")
@RequestScoped
public class LoginWebService extends BaseWebService{
	@Inject private LoginDAO loginDAO;

	@POST
	public Response login(String json){
		String tokenHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
		if(StringUtils.isEmptyOrWhitespaceOnly(tokenHeader)){
			try{
				Student student = loginDAO.getStudentLogin(gson.fromJson(json, Student.class));
				if(student == null){
					this.erros.addError("error.login.password.invalid");
					throw erros;
				}

				InfoUser token = AuthenticatorUtil.generateToken(student.getCode());
				token.setStudent(student);
				loginDAO.saveOrUpdateToken(token);

				return successRequest(token);
			}catch(ListMessageException list){
				return badRequest(list);
			}
		}else{
			try {
				loginDAO.validateToken(tokenHeader);
				return successRequest();
			} catch (ListMessageException list) {
				return badRequest(list);
			}
		}
	}
}
