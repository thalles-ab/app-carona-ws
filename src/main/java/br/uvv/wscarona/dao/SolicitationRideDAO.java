package br.uvv.wscarona.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.uvv.wscarona.model.Ride;
import br.uvv.wscarona.model.SolicitationRide;
import br.uvv.wscarona.model.Student;
import br.uvv.wscarona.model.StudentRide;
import br.uvv.wscarona.model.enumerator.TypeSituation;
import br.uvv.wscarona.webservice.util.ListMessageException;

import java.util.List;

@Stateless
public class SolicitationRideDAO extends GenericDAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @Inject
    private StudentRideDAO studentRideDAO;

    private static final String SELECT_COUNT_STUDENT_RIDE_BY_RIDE = "SELECT COUNT(sr.id) FROM StudentRide sr WHERE sr.ride.id =:rideId";
    private static final String SELECT_EXISTS_STUDENT_RIDE_BY_STUDENT = "SELECT SYSDATE FROM StudentRide sr WHERE sr.student.id =:studentId AND sr.ride.id =:rideId";
    private static final String SELECT_SOLICITATION_RIDE = "SELECT sr FROM SolicitationRide sr JOIN Student st ON sr.student.id = st.id WHERE sr.id =:solicitationRideId";
	
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void create(SolicitationRide solicitationRide) throws ListMessageException{
        try{
            fullValidation(solicitationRide);
            Ride ride = (Ride) searchById(solicitationRide.getRide());
            if(ride == null){
                this.erros.addError("error.invalid.ride");
            }
            this.throwErros();
            solicitationRide.setSituation(TypeSituation.PENDING);
            this.entityManager.merge(solicitationRide);
        }catch(NoResultException e){}
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void accept(SolicitationRide solicitationRide) throws ListMessageException {
        try {
            Student student = (Student) searchById(solicitationRide.getStudent());
            Ride ride = (Ride) searchById(solicitationRide.getRide());

            StringBuilder hqlStudentRide = new StringBuilder(SELECT_EXISTS_STUDENT_RIDE_BY_STUDENT);
            Query queryStudentRide = this.entityManager.createQuery(hqlStudentRide.toString());
            queryStudentRide.setParameter("studentId", student.getId());
            queryStudentRide.setParameter("rideId", ride.getId());

            try {
                queryStudentRide.getSingleResult();
                this.entityManager.detach(solicitationRide);
                StudentRide studentRide = new StudentRide();
                studentRide.setStudent(student);
                studentRide.setRide(ride);
                this.studentRideDAO.entityManager.merge(studentRide);
                solicitationRide.setSituation(TypeSituation.DISABLE);
                this.entityManager.merge(solicitationRide);
            }catch (NoResultException e){
                throw new ListMessageException("error.student.has.found.ride");
            }
        } catch (NoResultException e) {}
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void reject(SolicitationRide solicitationRide) throws ListMessageException {
        try{
            solicitationRide.setSituation(TypeSituation.DISABLE);
            this.entityManager.merge(solicitationRide);
        }catch (NoResultException e){}
    }
    
    public void fullValidation(SolicitationRide solicitationRide){
        if(solicitationRide.getStudent() == null || solicitationRide.getStudent().getId() == null && solicitationRide.getStudent().getId() <= 0){
            this.erros.addRquiredField("attr.student");
        }
        if(solicitationRide.getRide() == null || solicitationRide.getRide().getId() == null && solicitationRide.getRide().getId() <= 0){
            this.erros.addRquiredField("attr.ride");
        }
    }
}
