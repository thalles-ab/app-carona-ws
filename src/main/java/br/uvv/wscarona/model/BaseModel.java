package br.uvv.wscarona.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose(deserialize = true, serialize = true)
	@Column(name = "ID")
	private Long id;

	@Version
	@Column(name = "VERSION")
	@Expose(deserialize = false, serialize = false)
	private Long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
