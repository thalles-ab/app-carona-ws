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
import br.uvv.wscarona.webservice.util.MessageBundle;

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
	
	protected void addError(String key){
		MessageBundle.addError(key, this.erros);
	}
	
	protected void addRquiredField(String key){
		MessageBundle.addRquiredField(key, this.erros);
	}
	
	protected Response response(Status status, Object entity){
		return Response.status(status).entity(gson.toJson(entity)).build();
	}
	
	protected Response responseOk(Object entity){
		return Response.ok(gson.toJson(entity)).build();
	}
	
	protected Response responseBadRequest(Object entity){
		return response(Status.BAD_REQUEST, entity);
	}
	
	protected Response responseBadRequest(String key){
		addError(key);
		return responseBadRequest();
	}
	
	protected Response responseBadRequest(){
		return response(Status.BAD_REQUEST, erros);
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
