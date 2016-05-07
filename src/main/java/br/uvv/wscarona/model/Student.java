package br.uvv.wscarona.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "TBL_STUDENT")
public class Student extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NM_STUDENT")
	@Expose(deserialize = true, serialize = true)
	private String name;

	@Column(name = "CD_STUDENT")
	@Expose(deserialize = true, serialize = true)
	private String code;

	@Column(name = "DS_PASSWORD")
	@Expose(deserialize = true, serialize = false)
	private String password;

	@Column(name = "DS_PHOTO")
	@Expose(deserialize = true, serialize = true)
	private String photo;

	@Column(name = "DS_EMAIL")
	@Expose(deserialize = true, serialize = true)
	private String email;
	
	@Column(name = "DS_CELL_PHONE")
	@Expose(deserialize = true, serialize = true)
	private String cellPhone;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
	private List<Ride> listRide;

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public List<Ride> getListRide() {
		return listRide;
	}

	public void setListRide(List<Ride> listRide) {
		this.listRide = listRide;
	}
}
