package br.uvv.wscarona.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.uvv.wscarona.model.Ride;
import br.uvv.wscarona.model.SolicitationRide;
import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.model.StudentRide;
import br.uvv.wscarona.webservice.util.ListMessageException;

@Stateless
public class StudentRideDAO extends GenericDAO {
    /**
    *
    */
   private static final long serialVersionUID = 1L;
   private static final String SELECT_STUDENT_SOLICITATION = "SELECT sr FROM SolicitationRide sr JOIN sr.student st JOIN sr.ride rd WHERE sr.id = :idSolicitationRide"; 
   
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public StudentRide saveOrUpdate(SolicitationRide solicitationParam) throws ListMessageException{
       try{
           fullValidation(solicitationParam);
           this.throwErros();
           
           Query query = this.entityManager.createQuery(SELECT_STUDENT_SOLICITATION);
           query.setParameter("idSolicitationRide", solicitationParam.getId());
           SolicitationRide solicitation = (SolicitationRide) query.getSingleResult();
           this.entityManager.detach(solicitation);
           
           StudentRide studentRide = new StudentRide();
           studentRide.setStudent(solicitation.getStudent());
           studentRide.setRide(solicitation.getRide());
           return this.entityManager.merge(studentRide);
       }
       catch (NoResultException e) {
           return null;
       }
   }
   
   public void updateShowCellPhone(StudentRide studentRideParam) throws ListMessageException{
		if(studentRideParam == null || studentRideParam.getId() == null){
			this.erros.addRquiredField("attr.ride");
		}
		this.throwErros();
		
		StudentRide studentRide = (StudentRide) this.searchById(studentRideParam);
		this.entityManager.detach(studentRide);
		studentRide.setShowCellPhone(studentRideParam.isShowCellPhone());
		System.out.println(studentRideParam.isShowCellPhone());
		this.entityManager.merge(studentRide);
   }

   public void fullValidation(SolicitationRide solicitation){
       if(solicitation.getStudent() == null || solicitation.getStudent().getId() == null && solicitation.getStudent().getId() <= 0){
           this.erros.addRquiredField("attr.student");
       }
       if(solicitation.getRide() == null || solicitation.getRide().getId() == null && solicitation.getRide().getId() <= 0){
           this.erros.addRquiredField("attr.ride");
       }
   }
}
