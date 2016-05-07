package br.uvv.wscarona.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "CRZ_STUDENT_RIDE")
public class StudentRide  extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JoinColumn(name = "ID_STUDENT")
	@ManyToOne(fetch = FetchType.LAZY)
	@Expose(deserialize = true, serialize = true)
	private Student student;
	
	@JoinColumn(name = "ID_RIDE")
	@ManyToOne(fetch = FetchType.LAZY)
	@Expose(deserialize = true, serialize = true)
	private Ride ride;

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
	
}
