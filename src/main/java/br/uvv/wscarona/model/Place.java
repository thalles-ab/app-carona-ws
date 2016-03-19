package br.uvv.wscarona.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.google.gson.annotations.Expose;
import br.uvv.wscarona.model.enumerator.TypePlace;

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

	@Column(name = "DS_GEOLOCALIZATION")
	@Expose(deserialize = true, serialize = true)
	private String geoLocalization;

	@Column(name = "TP_PLACE")
	@Expose(deserialize = true, serialize = true)
	private TypePlace placeType;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getGeoLocalization() {
		return geoLocalization;
	}

	public void setGeoLocalization(String geoLocalization) {
		this.geoLocalization = geoLocalization;
	}

	public TypePlace getPlaceType() {
		return placeType;
	}

	public void setPlaceType(TypePlace placeType) {
		this.placeType = placeType;
	}
}
