package br.uvv.wscarona.dao;

import br.uvv.wscarona.model.Place;
import br.uvv.wscarona.model.Ride;
import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.model.StudentRide;
import br.uvv.wscarona.model.enumerator.TypeDay;
import br.uvv.wscarona.model.enumerator.TypeSituation;
import br.uvv.wscarona.webservice.util.ListMessageException;
import com.mysql.jdbc.StringUtils;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Capucho on 07/05/2016.
 */
@Stateless
public class RideDAO extends GenericDAO {
    /**
     *
     */
    @Inject
    private StudentDAO studentDAO;
    private static final String SELECT_RIDE = "Select rd from Ride rd join rd.studentList sList WHERE rd.id = :idRide";
    private static final String DELETE_RIDE = "Select rd from Ride rd WHERE rd.id =:id ";
    private static final long serialVersionUID = 1L;
    private static final String SELECT_RIDES_FROM_USER = "Select rd from Ride rd where rd.student.id = :id";
    private static final StringBuilder SELECT_RIDES_BY_LAT_LONG = new StringBuilder("SELECT ")
        .append("ride.ID, ride.ID_STUDENT, ride.TP_DAY, ride.DT_CREATION, ride.DT_EXPIRATION, ride.QT_PASSENGERS, ")
        .append("ride.DS_ROUTE_GOOGLE_FORMAT, ride.ID_PLACE_START, ride.ID_PLACE_END, ride.VERSION, ride.TP_SITUATION, ride.FL_SHOW_CELL_PHONE ")
        .append("FROM TBL_RIDE ride ")
        .append("JOIN TBL_PLACE startPoint on startPoint.ID = ride.ID_PLACE_START ")
        .append("JOIN TBL_PLACE endPoint on endPoint.ID = ride.ID_PLACE_END ")
//        .append("JOIN CRZ_STUDENT_RIDE crz on crz.ID_RIDE = ride.ID ")
        .append("JOIN TBL_STUDENT student on student.ID = ride.ID_STUDENT ")
        .append("WHERE student.ID != ?1 ")
//        .append("AND  crz.ID_STUDENT != ?1 ")
        .append("AND  ride.DT_EXPIRATION >= NOW() ")
        .append("AND _GetKmDistance(startPoint.DS_LATITUDE, startPoint.DS_LONGITUDE, ?2, ?3) <= 0.2 ")
        .append("AND _GetKmDistance(endPoint.DS_LATITUDE, endPoint.DS_LONGITUDE, ?4, ?5) <= 0.2");

    public void delete(Long id) throws ListMessageException{
        Ride ride = new Ride();
        ride.setId(id);
        ride = (Ride)searchById(ride);
        if(ride==null){
            this.erros.addError("error.invalid.ride");
            this.throwErros();
        }
        ride.setSituation(TypeSituation.DISABLE);
        merge(ride);
    }

    public List<Ride> myRides(Student student){
        StringBuilder hql = new StringBuilder(SELECT_RIDES_FROM_USER);
        Query query = this.entityManager.createQuery(hql.toString());
        query.setParameter("id", student.getId());
        try{
            return query.getResultList();
        }
        catch (Exception e){
            throw e;
        }
    }

    public Ride getStudents(Ride ride) throws ListMessageException{
        try{
            StringBuilder hql = new StringBuilder(SELECT_RIDE);
            Query query = this.entityManager.createQuery(hql.toString());
            query.setParameter("idRide", ride.getId());
            return (Ride)query.getSingleResult();
        }
        catch (NoResultException e){
            return ride;
        }
    }

    public List<Ride> searchRidesWithSameLatLong(Ride ride){
        Query query = this.entityManager.createNativeQuery(SELECT_RIDES_BY_LAT_LONG.toString());
        query.setParameter("1", ride.getStudent().getId());
        query.setParameter("2", ride.getStartPoint().getLatitude());
        query.setParameter("3", ride.getStartPoint().getLongitude());
        query.setParameter("4", ride.getEndPoint().getLatitude());
        query.setParameter("5", ride.getEndPoint().getLongitude());
        List<Object[]> results = query.getResultList();
        List<Ride> rideList = new ArrayList<>();
        Student student = new Student();
        Place place = new Place();
        for(Object[] item : results){
            Ride tempRide = new Ride();
            tempRide.setId((Long)item[0]);
            student.setId((Long)item[1]);
            tempRide.setStudent((Student)this.searchById(student));
            tempRide.setCreationDate((Date)item[3]);
            tempRide.setExpirationDate((Date)item[4]);
            tempRide.setQuantityPassengers((int)item[5]);
            tempRide.setRouteGoogleFormat((String)item[6]);
            place.setId((Long)item[7]);
            tempRide.setStartPoint((Place)searchById(place));
            place.setId((Long)item[8]);
            tempRide.setEndPoint((Place)searchById(place));
            tempRide.setSituation(TypeSituation.value((Integer) item[10]));
            tempRide.setShowCellPhone((boolean)item[11]);
            rideList.add(tempRide);
        }
        return rideList;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Ride SaveOrUpdate(Ride ride) throws ListMessageException{
        try{
            fullValidation(ride);
            this.throwErros();
            ride.setSituation(TypeSituation.ENABLE);
            this.entityManager.merge(ride);
        }
        catch (NoResultException e) {
            return null;
        }
        return ride;
    }

    public void updateShowCellPhone(Ride rideParam) throws ListMessageException{
		if(rideParam == null || rideParam.getId() == null){
			this.erros.addRquiredField("attr.ride");
		}
		this.throwErros();
		
		Ride ride = (Ride) this.searchById(rideParam);
		this.entityManager.detach(ride);
		ride.setShowCellPhone(rideParam.isShowCellPhone());
		System.out.println(rideParam.isShowCellPhone());
		this.entityManager.merge(ride);
    }

    public Long amountGivenRides(Long id){
        StringBuilder hql = new StringBuilder("SELECT COUNT(r.id) FROM Ride r WHERE r.student.id = :studentId");
        Query query = this.entityManager.createQuery(hql.toString());
        query.setParameter("studentId", id);
        return (Long) query.getSingleResult();
    }

    public Long amountTakenRides(Long id){
        StringBuilder hql = new StringBuilder("SELECT COUNT(r.id) FROM StudentRide r WHERE r.student.id = :studentId");
        Query query = this.entityManager.createQuery(hql.toString());
        query.setParameter("studentId", id);
        return (Long) query.getSingleResult();
    }

    public void fullValidation(Ride ride){
        if(ride.getExpirationDate() == null){
            this.erros.addRquiredField("attr.ride.expiration.date");
        }
        if(ride.getExpirationDate().before(new Date())){
            this.erros.addError("error.invalid.expiration.date");
        }
        if(StringUtils.isNullOrEmpty(ride.getRouteGoogleFormat())){
            this.erros.addRquiredField("attr.ride.route.google.format");
        }
        if(ride.getStartPoint() == null){
            this.erros.addRquiredField("attr.ride.start.point");
        }
        if(ride.getEndPoint() == null){
            this.erros.addRquiredField("attr.ride.end.point");
        }
    }
}
