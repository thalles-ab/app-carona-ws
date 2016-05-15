package br.uvv.wscarona.webservice;

import br.uvv.wscarona.dao.RideDAO;
import br.uvv.wscarona.model.Ride;
import br.uvv.wscarona.model.enumerator.TypeSituation;
import br.uvv.wscarona.webservice.util.ListMessageException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

/**
 * Created by Capucho on 07/05/2016.
 */
@Path("/ride")
@RequestScoped
public class RideWebService extends BaseWebService {
    @Inject
    private RideDAO rideDAO;

    @POST
    public Response save(String json){
        Ride ride = this.gson.fromJson(json, Ride.class);
        try {
            ride.setStudent(this.studentContext);
            ride.setCreationDate(new Date());
            ride.setSituation(TypeSituation.ENABLE);
            rideDAO.SaveOrUpdate(ride);
            return successRequest(ride);
        } catch (ListMessageException list) {
            return badRequest(list);
        }
    }

    @POST
    @Path("/list")
    public Response list(String json){
        Ride ride = this.gson.fromJson(json, Ride.class);
        ride.setStudent(getStudentContext());
        List<Ride> listRides = rideDAO.searchRidesWithSameLatLong(ride);
        return successRequest(listRides);
    }

    @POST
    @Path("/changeShowCellPhone")
    public Response changeShowCellPhone(String json){
        try {
            Ride ride = this.gson.fromJson(json, Ride.class);
            rideDAO.updateShowCellPhone(ride);
            return successRequest();
        } catch (ListMessageException list) {
            return badRequest(list);
        }
    }
}
