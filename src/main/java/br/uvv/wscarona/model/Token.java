package br.uvv.wscarona.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "TBL_TOKEN")
public class Token extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_TOKEN")
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
