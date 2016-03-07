package br.uvv.wscarona.webservice;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.uvv.wscarona.dao.UserDAO;
import br.uvv.wscarona.model.User;

@Path("/helloworld")
@RequestScoped
public class HelloWorld extends BaseWebService{
	@Inject private UserDAO userDAO; 
	
    @GET
    public Response getHelloWorld() {
    	List<User> list = userDAO.getUsers();
        return Response.status(200).entity(this.gson.toJson(list)).build();
    }
}
