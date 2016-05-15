package br.uvv.wscarona.webservice;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.uvv.wscarona.dao.SolicitationRideDAO;
import br.uvv.wscarona.model.SolicitationRide;
import br.uvv.wscarona.webservice.util.ListMessageException;

@Path("/solicitationRide")
@RequestScoped
public class SolicitationRideWebService extends BaseWebService{
    @Inject
    private SolicitationRideDAO solicitationRideDAO;
	
	@POST
	public Response create(String json) {
		try{
			SolicitationRide solicitationRide = gson.fromJson(json, SolicitationRide.class);
            solicitationRide.setStudent(BaseWebService.getStudentContext());
            solicitationRideDAO.create(solicitationRide);
			return successRequest();
		}catch(ListMessageException list){
			return badRequest(list);
		}
	}

    @PUT
    public Response accept(String json) {
        try{
            SolicitationRide solicitationRide = gson.fromJson(json, SolicitationRide.class);
            solicitationRideDAO.accept(solicitationRide);
            return successRequest();
        }catch(ListMessageException list){
            return badRequest(list);
        }
    }

    @DELETE
    public Response reject(String json) {
        try{
            SolicitationRide solicitationRide = gson.fromJson(json, SolicitationRide.class);
            solicitationRideDAO.reject(solicitationRide);
            return successRequest();
        }catch(ListMessageException list){
            return badRequest(list);
        }
    }
}
