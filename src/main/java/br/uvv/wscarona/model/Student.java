package br.uvv.wscarona.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "TBL_STUDENT")
public class Student extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "DS_LOGIN")
	@Expose(deserialize = true, serialize = true)
	private String login;

	@Column(name = "DS_PASSWORD")
	@Expose(deserialize = true, serialize = false)
	private String password;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
