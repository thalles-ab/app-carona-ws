package br.uvv.wscarona.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "TBL_TOKEN")
public class Token extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "ID_STUDENT")
	@OneToOne(fetch = FetchType.LAZY)
	private Student student;

	@Column(name = "DS_TOKEN")
	@Expose(deserialize = false, serialize = true)
	private String token;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "DT_EXPIRATION")
	private Date expirationDate;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	
}
