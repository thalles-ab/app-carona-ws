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

@Path("/student")
@RequestScoped
public class StudentWebService extends BaseWebService {
	@Inject
	private StudentDAO studentDAO;

	@GET
	public Response getHelloWorld() {
		List<Student> list = studentDAO.getUsers();
		return Response.status(200).entity(this.gson.toJson(list)).build();
	}

	@POST
	public Response save(String json) {
		Student user = this.gson.fromJson(json, Student.class);
		user = (Student) studentDAO.save(user);
		return Response.status(200).entity(this.gson.toJson(user)).build();
	}
}
