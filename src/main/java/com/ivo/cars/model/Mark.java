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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@Entity
public class Mark implements Serializable {
	@Id
	int id;
	
	public Mark() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Column(name ="MarkName")
	@JsonProperty("markName")
	@NotNull(message = "Mark name can not be null.")
    @NotEmpty(message = "Mark name can not be empty.")
	String markName;
	
	@OneToMany(mappedBy="mark")
	private List<Model> models=new ArrayList<Model>();
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMarkName() {
		return markName;
	}

	public void setMarkName(String markName) {
		this.markName = markName;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	
	@Column(name ="create_date")
	@JsonProperty("createDate")
	@JsonFormat(pattern="dd-MM-yyyy")
	LocalDate createDate;
}
