package br.uvv.wscarona.webservice;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.uvv.wscarona.dao.PlaceDAO;
import br.uvv.wscarona.model.Place;
import br.uvv.wscarona.model.enumerator.TypeSituation;
import br.uvv.wscarona.webservice.util.ListMessageException;
import com.google.gson.reflect.TypeToken;

@Path("/place")
@RequestScoped
public class PlaceWebService extends BaseWebService {
	@Inject private PlaceDAO placeDAO;

    @POST
    public Response SaveOrUpdate(String request) throws ListMessageException {
        try{
            Place place = gson.fromJson(request, Place.class);
            if(place.getId() == null){
                place.setStudent(studentContext);
                place.setSituation(TypeSituation.ENABLE);
                place = placeDAO.create(place);

                Place placeAux = new Place();
                placeAux.setId(place.getId());
                return successRequest(placeAux);
            }
            place = placeDAO.update(place);
            return successRequest();
        }
        catch (ListMessageException e){
            return badRequest(erros);
        }
    }

	@GET
	public Response getAll() {
		List<Place> placesList = placeDAO.getPlaces(studentContext);
        return successRequest(placesList);
	}

    @GET
    @Path("/{id}")
    public Response getSpecific(@PathParam("id") String id) throws ListMessageException {
        Place place= placeDAO.getPlace(id);
        return successRequest(place);
    }

    @GET
    @Path("/search/{geoLocalization}")
    public Response getByGeolocalization(@PathParam("geoLocalization") String geoLocalization) throws ListMessageException {
        try {
            List<Place> results = placeDAO.getPlaceByGeo(geoLocalization);
            return successRequest(results);
        }
        catch (Exception e){
            return badRequest(erros);
        }
    }

    @POST
    @Path("/delete/")
    public Response delete(String json){
        try{
            Type type = new TypeToken<List<Place>>() {}.getType();
            List<Place> places = gson.fromJson(json, type);

            for (Place place : places) {
                place.setStudent(studentContext);
                this.placeDAO.delete(place);
            }
            return successRequest();
        }
        catch (ListMessageException list){
            return badRequest(list);
        }
    }
}
