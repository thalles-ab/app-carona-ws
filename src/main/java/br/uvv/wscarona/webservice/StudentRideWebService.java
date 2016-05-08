package br.uvv.wscarona.webservice;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.uvv.wscarona.dao.StudentRideDAO;
import br.uvv.wscarona.model.SolicitationRide;
import br.uvv.wscarona.model.StudentRide;
import br.uvv.wscarona.webservice.util.ListMessageException;

@Path("/studentRide")
@RequestScoped
public class StudentRideWebService extends BaseWebService{
    @Inject
    private StudentRideDAO studentRideDAO;
	
	@POST
	public Response saveOrUpdate(String json) {
		try{
			SolicitationRide solicitationRide = gson.fromJson(json, SolicitationRide.class);
			return successRequest(studentRideDAO.saveOrUpdate(solicitationRide));
		}catch(ListMessageException list){
			return badRequest(list);
		}
	}
	
    @POST
    @Path("/changeShowCellPhone")
    public Response changeShowCellPhone(String json){
        try {
            StudentRide studentRide = this.gson.fromJson(json, StudentRide.class);
            studentRideDAO.updateShowCellPhone(studentRide);
            return successRequest();
        } catch (ListMessageException list) {
            return badRequest(list);
        }
    }
}
