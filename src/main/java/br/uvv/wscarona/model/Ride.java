package br.uvv.wscarona.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.google.gson.annotations.Expose;
import br.uvv.wscarona.model.enumerator.DayType;

@Entity
@Table(name = "TBL_RIDE")
public class Ride extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_STUDENT")
	@Expose(deserialize = true, serialize = true)
	private Student student;
	
	@Column(name = "TP_DAY")
	@Expose(deserialize = true, serialize = true)
	private DayType dayType;
	
	@Column(name = "DT_CREATION")
	@Expose(deserialize = true, serialize = true)
	private Date creationDate;
	
	@Column(name = "DT_EXPIRATION")
	@Expose(deserialize = true, serialize = true)
	private Date expirationDate;
	
	@Column(name = "QT_PASSENGERS")
	@Expose(deserialize = true, serialize = true)
	private int quantityPassengers;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public DayType getDayType() {
		return dayType;
	}

	public void setDayType(DayType dayType) {
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
}
