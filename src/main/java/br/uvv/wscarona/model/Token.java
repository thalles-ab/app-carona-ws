package br.uvv.wscarona.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

	@Column(name = "DT_EXPIRATION")
	@Expose(deserialize = true, serialize = true)
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
}
