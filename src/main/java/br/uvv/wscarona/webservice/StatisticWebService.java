package br.uvv.wscarona.webservice;

import br.uvv.wscarona.dao.RideDAO;
import br.uvv.wscarona.webservice.util.ListMessageException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by luizguilhermepicorelli on 15/05/16.
 */
public class StatisticWebService extends BaseWebService{
    @Inject
    private RideDAO rideDAO;

//    @GET
//    @Path("/ride/{id}")
//    public Response rideStats(@PathParam("id") long id) {
//        try{
//
//            return successRequest();
//        }catch(ListMessageException list){
//            return badRequest(list);
//        }
//    }
}
