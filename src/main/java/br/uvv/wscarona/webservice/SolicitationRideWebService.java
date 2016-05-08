package br.uvv.wscarona.webservice;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
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
	public Response saveOrUpdate(String json) {
		try{
			SolicitationRide solicitationRide = gson.fromJson(json, SolicitationRide.class);
			return successRequest(solicitationRideDAO.saveOrUpdate(solicitationRide));
		}catch(ListMessageException list){
			return badRequest(list);
		}
	}
}
