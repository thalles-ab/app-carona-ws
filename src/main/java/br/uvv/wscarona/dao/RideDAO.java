package br.uvv.wscarona.dao;

import br.uvv.wscarona.model.Ride;
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
            FullValidation(ride);
            this.throwErros();
            this.entityManager.merge(ride);
        }
        catch (NoResultException e) {
            return null;
        }
        return ride;
    }


    public void FullValidation(Ride ride){
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
