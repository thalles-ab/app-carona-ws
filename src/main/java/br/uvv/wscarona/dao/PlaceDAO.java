package br.uvv.wscarona.dao;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import br.uvv.wscarona.model.Place;
import br.uvv.wscarona.model.Student;


@Stateless
public class PlaceDAO extends GenericDAO {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @SuppressWarnings("unchecked")

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Place> getPlaces(Student student) {
        StringBuilder hql = new StringBuilder("SELECT p FROM Place p WHERE p.student.id = :studentId");
        Query query = this.entityManager.createQuery(hql.toString());
        query.setParameter("studentId", student.getId());
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
    public Place saveOrUpdate(Place place){
        //Validar
        return entityManager.merge(place);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(String id){
        Place placeToDelete = getPlace(id);
        entityManager.remove(placeToDelete);
    }
}