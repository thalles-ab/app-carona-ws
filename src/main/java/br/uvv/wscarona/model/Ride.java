package br.uvv.wscarona.model;


import java.util.Date;

import javax.persistence.*;

import com.google.gson.annotations.Expose;

import br.uvv.wscarona.model.enumerator.TypeDay;
import br.uvv.wscarona.model.enumerator.TypeSituation;

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
	
	@Column(name = "DS_ROUTE_GOOGLE_FORMAT")
	@Expose(deserialize = true, serialize = true)
	private String routeGoogleFormat;
	
	@JoinColumn(name = "ID_PLACE_START")
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Place.class)
	@Expose(deserialize = true, serialize = true)
	private Place startPoint;

	@JoinColumn(name = "ID_PLACE_END")
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Place.class)
	@Expose(deserialize = true, serialize = true)
	private Place endPoint;
	
	@Column(name = "FL_SHOW_CELL_PHONE")
	@Expose(deserialize = true, serialize = true)
	private boolean showCellPhone;
	
	@Column(name = "TP_SITUATION")
	@Expose(deserialize = true, serialize = true)
	@Enumerated(EnumType.ORDINAL)
	private TypeSituation situation;

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

	public String getRouteGoogleFormat() {
		return routeGoogleFormat;
	}

	public void setRouteGoogleFormat(String routeGoogleFormat) {
		this.routeGoogleFormat = routeGoogleFormat;
	}

	public Place getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Place startPoint) {
		this.startPoint = startPoint;
	}

	public Place getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Place endPoint) {
		this.endPoint = endPoint;
	}

	public boolean isShowCellPhone() {
		return showCellPhone;
	}

	public void setShowCellPhone(boolean showCellPhone) {
		this.showCellPhone = showCellPhone;
	}

	public TypeSituation getSituation() {
		return situation;
	}

	public void setSituation(TypeSituation situation) {
		this.situation = situation;
	}
}
