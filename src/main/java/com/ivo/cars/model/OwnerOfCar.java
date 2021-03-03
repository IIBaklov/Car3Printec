package com.ivo.cars.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class OwnerOfCar implements Serializable {
	@Id
	String EGN;
	
	@Column(name ="FullName")
	@JsonProperty("fullname")
	@NotNull(message = "Owner name can not be null.")
    @NotEmpty(message = "Owner name can not be empty.")
	String name;
	
	public OwnerOfCar() {
		super();
		// TODO Auto-generated constructor stub
	}

	@OneToMany(mappedBy = "owner")
	private List<Car> cars = new ArrayList<Car>();
	
	@JsonProperty("Address")
	@NotNull(message = "Owner must be have address.")
    @NotEmpty(message = "Owner must be have address.")
	String Address;
	
	String Phone;

	public String getEGN() {
		return EGN;
	}

	public void setEGN(String eGN) {
		EGN = eGN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

}
