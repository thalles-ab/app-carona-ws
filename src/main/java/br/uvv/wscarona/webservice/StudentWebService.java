package br.uvv.wscarona.webservice;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import br.uvv.wscarona.dao.StudentDAO;
import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.webservice.util.ListMessageException;

@Path("/student")
@RequestScoped
public class StudentWebService extends BaseWebService {
	@Inject
	private StudentDAO studentDAO;

	@GET
	public Response get() {
		List<Student> list = studentDAO.getUsers();
		return successRequest(list);
	}

	@POST
	public Response save(String json) {
		Student user = this.gson.fromJson(json, Student.class);
		Student contextUser = StudentWebService.studentContext;
		try{
			contextUser.setPhoto(user.getPhoto());
			contextUser.setName(user.getName());
			studentDAO.SaveOrUpdate(contextUser);
			return successRequest(contextUser);
		}catch(ListMessageException list){
			return badRequest(list);
		}
	}
}
