package br.uvv.wscarona.webservice;

import br.uvv.wscarona.dao.RideDAO;
import br.uvv.wscarona.webservice.util.ListMessageException;
import br.uvv.wscarona.webservice.util.StatisticUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by luizguilhermepicorelli on 15/05/16.
 */
@Path("/stats")
@RequestScoped
public class StatisticWebService extends BaseWebService{
    @Inject
    private RideDAO rideDAO;

    @GET
    @Path("/amountGivenRides")
    public Response amountGivenRides() {
        try{
            Long count = rideDAO.amountGivenRides(studentContext.getId());
            StatisticUtil statisticUtil = new StatisticUtil();
            statisticUtil.setCount(count);
            return successRequest(statisticUtil);
        }catch(Exception e){
            return badRequest();
        }
    }

    @GET
    @Path("/amountTakenRides")
    public Response amountTakenRides() {
        try{
            Long count = rideDAO.amountTakenRides(studentContext.getId());
            StatisticUtil statisticUtil = new StatisticUtil();
            statisticUtil.setCount(count);
            return successRequest(statisticUtil);
        }catch(Exception e){
            return badRequest();
        }
    }
}
