package br.uvv.wscarona.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import br.uvv.wscarona.model.enumerator.TypePlace;
import br.uvv.wscarona.model.enumerator.TypeSituation;

@Entity
@Table(name = "TBL_PLACE")
public class Place extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "ID_STUDENT")
	@ManyToOne(fetch = FetchType.LAZY)
	@Expose(deserialize = true, serialize = true)
	private Student student;
	
	@Column(name = "DS_LATITUDE")
	@Expose(deserialize = true, serialize = true)
	private String latitude;
	
	@Column(name = "DS_LONGITUDE")
	@Expose(deserialize = true, serialize = true)
	private String longitude;
	
	@Column(name = "DS_DESCRIPTION")
	@Expose(deserialize = true, serialize = true)
	private String description;

	@Column(name = "TP_PLACE")
	@Expose(deserialize = true, serialize = true)
	private TypePlace placeType;
	
	@Column(name = "TP_SITUATION")
	private TypeSituation situation;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TypePlace getPlaceType() {
		return placeType;
	}

	public void setPlaceType(TypePlace placeType) {
		this.placeType = placeType;
	}

	public TypeSituation getSituation() {
		return situation;
	}

	public void setSituation(TypeSituation situation) {
		this.situation = situation;
	}	
}
