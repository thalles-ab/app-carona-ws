package br.uvv.wscarona.webservice;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.uvv.wscarona.dao.UserDAO;
import br.uvv.wscarona.model.User;

@Path("/user")
@RequestScoped
public class UserWebService extends BaseWebService{
	@Inject private UserDAO userDAO; 
	
    @GET
    public Response getHelloWorld() {
    	List<User> list = userDAO.getUsers();
        return Response.status(200).entity(this.gson.toJson(list)).build();
    }
    
    @POST
    public Response saveUser(String json) {
    	User user = this.gson.fromJson(json, User.class);
    	user = userDAO.save(user);
        return Response.status(200).entity(this.gson.toJson(user)).build();
    }
}
