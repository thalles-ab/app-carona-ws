package br.uvv.wscarona.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.webservice.util.ListMessageException;

@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class BaseWebService {
	protected Gson gson;
	protected ListMessageException erros;
	protected static Student studentContext;

	public BaseWebService() {
		this.gson = getGsonInstance();
		this.erros = new ListMessageException();
	}

	protected Response successRequest() {
		return Response.ok().build();
	}
	
	protected Response successRequest(Object object) {
		return Response.ok(gson.toJson(object)).build();
	}
	
	protected Response badRequest() {
		return badRequest(this.erros);
	}
	
	protected Response badRequest(ListMessageException erros) {
		return Response.status(Status.BAD_REQUEST).entity(gson.toJson(erros)).build();
	}
	
	public static Gson getGsonInstance() {
		final GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		return builder.create();
	}

	public static Student getStudentContext() {
		return studentContext;
	}

	public static void setStudentContext(Student studentContext) {
		BaseWebService.studentContext = studentContext;
	}
}
