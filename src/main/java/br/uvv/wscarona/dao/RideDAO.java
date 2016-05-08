package br.uvv.wscarona.dao;

import br.uvv.wscarona.model.Ride;
import br.uvv.wscarona.model.enumerator.TypeSituation;
import br.uvv.wscarona.webservice.util.ListMessageException;
import com.mysql.jdbc.StringUtils;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import java.util.Date;

/**
 * Created by Capucho on 07/05/2016.
 */
@Stateless
public class RideDAO extends GenericDAO {
    /**
     *
     */
    private static final long serialVersionUID = 1L;


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
