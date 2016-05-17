package br.uvv.wscarona.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import br.uvv.wscarona.model.enumerator.TypeSituation;

@Entity
@Table(name = "TBL_SOLICITATION_RIDE")
public class SolicitationRide  extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JoinColumn(name = "ID_STUDENT")
	@ManyToOne(fetch = FetchType.EAGER)
	@Expose(deserialize = true, serialize = true)
	private Student student;
	
	@JoinColumn(name = "ID_RIDE")
	@ManyToOne(fetch = FetchType.EAGER)
	@Expose(deserialize = true, serialize = true)
	private Ride ride;

	@JoinColumn(name = "ID_PLACE")
	@Expose(deserialize = true, serialize = true)
	private Place place;
	
	@Column(name = "TP_SITUATION")
	private TypeSituation situation;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Ride getRide() {
		return ride;
	}

	public void setRide(Ride ride) {
		this.ride = ride;
	}

	public TypeSituation getSituation() {
		return situation;
	}

	public void setSituation(TypeSituation situation) {
		this.situation = situation;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}
}
