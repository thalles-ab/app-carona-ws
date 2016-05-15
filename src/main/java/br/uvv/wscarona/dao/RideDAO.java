package br.uvv.wscarona.dao;

import br.uvv.wscarona.model.Ride;
import br.uvv.wscarona.model.enumerator.TypeSituation;
import br.uvv.wscarona.webservice.util.ListMessageException;
import com.mysql.jdbc.StringUtils;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
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
    private static final long serialVersionUID = 1L;
    private static final StringBuilder SELECT_RIDES_BY_LAT_LONG = new StringBuilder("SELECT ride FROM Ride ride ")
            .append("JOIN ride.startPoint startPoint ")
            .append("JOIN ride.endPoint endPoint ")
            .append("WHERE startPoint.latitude like :startPointLat ")
            .append("and startPoint.longitude like :startPointLong ")
            .append("and endPoint.latitude like :endPointLat ")
            .append("and endPoint.longitude like :endPointLong ")
            .append("and ride.student.id != :studentId");

    public List<Ride> searchRidesWithSameLatLong(Ride ride){
        StringBuilder hql = new StringBuilder(SELECT_RIDES_BY_LAT_LONG);
        Query query = this.entityManager.createQuery(hql.toString());
        query.setParameter("startPointLat", ride.getStartPoint().getLatitude());
        query.setParameter("startPointLong", ride.getStartPoint().getLongitude());
        query.setParameter("endPointLat", ride.getEndPoint().getLatitude());
        query.setParameter("endPointLong", ride.getEndPoint().getLongitude());
        query.setParameter("studentId", ride.getStudent().getId());
        List<Ride> results = query.getResultList();
        return results;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Ride SaveOrUpdate(Ride ride) throws ListMessageException{
        try{
            fullValidation(ride);
            this.throwErros();
            ride.setSituation(TypeSituation.Enable);
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
