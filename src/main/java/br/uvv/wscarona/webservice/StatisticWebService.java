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
    public Response stats() {
        try{
            Long amountGivenRides = rideDAO.amountGivenRides(studentContext.getId());
            Long amountTakenRides = rideDAO.amountTakenRides(studentContext.getId());

            StatisticUtil statisticUtil = new StatisticUtil();
            statisticUtil.setAmountGivenRides(amountGivenRides);
            statisticUtil.setAmountTakenRides(amountTakenRides);

            return successRequest(statisticUtil);
        }catch(Exception e){
            return badRequest();
        }
    }
}
