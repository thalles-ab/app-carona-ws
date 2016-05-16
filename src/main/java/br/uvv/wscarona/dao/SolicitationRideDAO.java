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
    @Inject private StudentRideDAO studentRideDAO;

    private static final String SELECT_COUNT_STUDENT_RIDE_BY_RIDE = "SELECT COUNT(sr.id) FROM StudentRide sr WHERE sr.ride.id =:rideId";
    private static final String SELECT_STUDENT_RIDE_BY_STUDENT = "SELECT sr FROM StudentRide sr WHERE sr.student.id =:studentId AND sr.ride.id =:rideId";
    private static final String SELECT_SOLICITATION_RIDE = "SELECT sr FROM SolicitationRide sr JOIN Student st ON sr.student.id = st.id WHERE sr.id =:solicitationRideId";
    private static final String SELECT_SOLICITATION_RIDE_BY_RIDE_AND_STUDENT = "SELECT sr FROM SolicitationRide sr WHERE sr.student.id =:studentId AND sr.ride.id =:rideId";
	
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void create(SolicitationRide solicitationRide) throws ListMessageException{
        try{
            fullValidation(solicitationRide);
            Ride ride = (Ride) searchById(solicitationRide.getRide());
            try{
                StringBuilder hqlSolicitationRide= new StringBuilder(SELECT_SOLICITATION_RIDE_BY_RIDE_AND_STUDENT);
                Query querySolicitationRide = this.entityManager.createQuery(hqlSolicitationRide.toString());
                querySolicitationRide.setParameter("studentId", solicitationRide.getStudent().getId());
                querySolicitationRide.setParameter("rideId", solicitationRide.getRide().getId());
                querySolicitationRide.getSingleResult();
                this.erros.addError("error.solicitation.ride.has.found");
            }catch (NoResultException e){
                solicitationRide.setSituation(TypeSituation.PENDING);
                this.entityManager.merge(solicitationRide);
            }
        }catch(NoResultException e){
            this.erros.addError("error.invalid.ride");
        }
        this.throwErros();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void accept(SolicitationRide solicitationRide) throws ListMessageException {
        try {
            Student student = (Student) searchById(solicitationRide.getStudent());
            Ride ride = (Ride) searchById(solicitationRide.getRide());
            try{
                StringBuilder hqlStudentRide = new StringBuilder(SELECT_STUDENT_RIDE_BY_STUDENT);
                Query queryStudentRide = this.entityManager.createQuery(hqlStudentRide.toString());
                queryStudentRide.setParameter("studentId", student.getId());
                queryStudentRide.setParameter("rideId", ride.getId());
                queryStudentRide.getSingleResult();
                this.erros.addError("error.student.has.found.ride");
            } catch (NoResultException e) {
                StudentRide studentRide = new StudentRide();
                studentRide.setStudent(student);
                studentRide.setRide(ride);
                this.entityManager.merge(studentRide);

                SolicitationRide solicitationRideAux = (SolicitationRide) searchById(solicitationRide);
                solicitationRideAux.setSituation(TypeSituation.DISABLE);
                this.entityManager.merge(solicitationRideAux);
            }
        }catch (NoResultException e){
            this.erros.addError("error.invalid.student.or.ride");
        }
        this.throwErros();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void reject(SolicitationRide solicitationRide) throws ListMessageException {
        try{
            SolicitationRide solicitationRideAux = (SolicitationRide) searchById(solicitationRide);

            if(solicitationRideAux == null){
                this.erros.addError("error.invalid.solicitation.ride");
            }
            this.throwErros();

            solicitationRideAux.setSituation(TypeSituation.DISABLE);
            this.entityManager.merge(solicitationRideAux);
        }catch (NoResultException e){}
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SolicitationRide> getList(Long id)throws ListMessageException {
        try{
            StringBuilder hql = new StringBuilder("SELECT sr FROM SolicitationRide sr WHERE sr.student.id = :studentId");
            Query query = this.entityManager.createQuery(hql.toString());
            query.setParameter("studentId", id);
            return (List<SolicitationRide>) query.getResultList();
        }catch (NoResultException e){
            return null;
        }
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
