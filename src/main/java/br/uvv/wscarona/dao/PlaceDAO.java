package br.uvv.wscarona.dao;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import br.uvv.wscarona.model.enumerator.TypeSituation;
import com.mysql.jdbc.StringUtils;

import br.uvv.wscarona.model.Place;
import br.uvv.wscarona.model.Ride;
import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.webservice.util.ListMessageException;


@Stateless
public class PlaceDAO extends GenericDAO {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @SuppressWarnings("unchecked")

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Place> getPlaces(Student student) {
        StringBuilder hql = new StringBuilder("SELECT p FROM Place p WHERE p.student.id = :studentId and p.situation = :situation");
        Query query = this.entityManager.createQuery(hql.toString());
        query.setParameter("studentId", student.getId());
        query.setParameter("situation", TypeSituation.Enable);
        return (List<Place>)query.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Place getPlace(String id) {
        Place place = new Place();
        place.setId(Long.parseLong(id));
        return (Place)searchById(place);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Place> getPlaceByGeo(String geoLocalization) {
        StringBuilder hql = new StringBuilder("SELECT p FROM Place p WHERE p.geoLocalization = :geoLocalization");
        Query query = this.entityManager.createQuery(hql.toString());
        query.setParameter("geoLocalization", geoLocalization);
        return (List<Place>)query.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Place saveOrUpdate(Place place) throws ListMessageException{
    	fullValidation(place);
    	this.throwErros();
        return entityManager.merge(place);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(String id){
        Place placeToDelete = getPlace(id);
        placeToDelete.setSituation(TypeSituation.Disable);
        entityManager.merge(placeToDelete);
    }
    
    public void fullValidation(Place place){
        if(place.getLatitude() == null){
        	this.erros.addRquiredField("attr.place.latitude");
        }
        if(place.getLongitude() == null){
        	this.erros.addRquiredField("attr.place.longitude");
        }
        if(place.getDescription() == null){
        	this.erros.addRquiredField("attr.place.description");
        }
    }
}