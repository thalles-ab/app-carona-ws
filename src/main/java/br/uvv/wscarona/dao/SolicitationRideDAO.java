package br.uvv.wscarona.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.uvv.wscarona.model.Ride;
import br.uvv.wscarona.model.SolicitationRide;
import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.model.enumerator.TypeSituation;
import br.uvv.wscarona.webservice.util.ListMessageException;

@Stateless
public class SolicitationRideDAO extends GenericDAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public SolicitationRide saveOrUpdate(SolicitationRide solicitationRide) throws ListMessageException{
        
            FullValidation(solicitationRide);
            this.throwErros();
            
            Ride ride = (Ride) searchById(solicitationRide.getRide());
            Student student = (Student) searchById(solicitationRide.getStudent());
            
            if(ride == null){
            	this.erros.addError("error.invalid.student");
            }
            if(student == null){
            	this.erros.addError("error.invalid.student");
            }
            this.throwErros();
            
            solicitationRide.setSituation(TypeSituation.Enable);
            return this.entityManager.merge(solicitationRide);
        
    }
    
    public void FullValidation(SolicitationRide solicitationRide){
        if(solicitationRide.getStudent() == null || solicitationRide.getStudent().getId() == null && solicitationRide.getStudent().getId() <= 0){
            this.erros.addRquiredField("attr.student");
        }
        if(solicitationRide.getRide() == null || solicitationRide.getRide().getId() == null && solicitationRide.getRide().getId() <= 0){
            this.erros.addRquiredField("attr.ride");
        }
    }
}
