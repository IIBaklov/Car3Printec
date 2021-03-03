package com.ivo.cars.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Ownership implements Serializable {
	@Id
	int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCarRegNumber() {
		return CarRegNumber;
	}

	public void setCarRegNumber(String carRegNumber) {
		CarRegNumber = carRegNumber;
	}

	@Column(name ="Owner")
	@JsonProperty("owner")
	@NotNull(message = "Owner can not be null.")
    @NotEmpty(message = "Owner can not be empty.")
	String owner;
	
	public Ownership() {
		super();
		// TODO Auto-generated constructor stub
	}

	@JsonProperty("CarRegNumber")
	@NotNull(message = "Car must be registrate.")
    @NotEmpty(message = "Car must be registrate.")
	String CarRegNumber;


}
