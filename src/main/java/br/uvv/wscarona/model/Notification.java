package br.uvv.wscarona.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "TBL_NOTIFICATION")
public class Notification extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "ID_STUDENT")
	@ManyToOne(fetch = FetchType.LAZY)
	@Expose(deserialize = true, serialize = true)
	private Student student;

	@Column(name = "DS_DISPOSITIVE")
	@Expose(deserialize = true, serialize = true)
	private String dispositive;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getDispositive() {
		return dispositive;
	}

	public void setDispositive(String dispositive) {
		this.dispositive = dispositive;
	}
}
