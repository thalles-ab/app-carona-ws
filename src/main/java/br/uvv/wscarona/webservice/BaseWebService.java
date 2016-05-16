package br.uvv.wscarona.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.webservice.util.ListMessageException;

@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON+"; chatset=UTF-8"})
public class BaseWebService {
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZZ";
	protected Gson gson;
	protected ListMessageException erros;
	protected static Student studentContext;
	@Context
	HttpHeaders headers;

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
		builder.setDateFormat(DATE_FORMAT).create();
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
