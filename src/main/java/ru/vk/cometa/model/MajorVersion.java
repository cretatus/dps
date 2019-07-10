package ru.vk.cometa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="major_version")
public class MajorVersion extends ApplicationObject{

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "module_id", nullable = false)
	private AppModule module;

	@Column(name = "number", nullable = false)
	private Integer number;
	
	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "status", nullable = false)
	private String status;

	public static String STATUS_OPENED = "OPENED";
	public static String STATUS_CLOSED = "CLOSED";
	public static String STATUS_PLANNED = "PLANNED";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AppModule getModule() {
		return module;
	}

	public void setModule(AppModule module) {
		this.module = module;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}


}