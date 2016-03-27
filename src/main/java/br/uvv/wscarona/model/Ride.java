package br.uvv.wscarona.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.annotations.Expose;

import br.uvv.wscarona.model.enumerator.TypeDay;

@Entity
@Table(name = "TBL_RIDE")
public class Ride extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "ID_STUDENT")
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Student.class)
	@Expose(deserialize = true, serialize = true)
	private Student student;

	@Column(name = "TP_DAY")
	@Expose(deserialize = true, serialize = true)
	private TypeDay dayType;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "DT_CREATION")
	@Expose(deserialize = true, serialize = true)
	private Date creationDate;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "DT_EXPIRATION")
	@Expose(deserialize = true, serialize = true)
	private Date expirationDate;

	@Column(name = "QT_PASSENGERS")
	@Expose(deserialize = true, serialize = true)
	private int quantityPassengers;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public TypeDay getDayType() {
		return dayType;
	}

	public void setDayType(TypeDay dayType) {
		this.dayType = dayType;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public int getQuantityPassengers() {
		return quantityPassengers;
	}

	public void setQuantityPassengers(int quantityPassengers) {
		this.quantityPassengers = quantityPassengers;
	}
}
