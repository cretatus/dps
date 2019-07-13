package ru.vk.cometa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="build")
public class Build extends ApplicationObject{
	@Column(name = "on_time", nullable = false)
	private Date onTime;

	@Column(name = "number", nullable = false)
	private Integer number;

	@Column(name = "description", nullable = true)
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "assembly_id", nullable = true)
	private Assembly assembly;

	@Column(name = "path", nullable = false)
	private String path;

	public String getLabel() {
		return getAssembly().getApplication().getSysname() + "." + getAssembly().getSysname() + "." + getNumber();
	}

	public void setLabel(String label) {
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Assembly getAssembly() {
		return assembly;
	}

	public void setAssembly(Assembly assembly) {
		this.assembly = assembly;
	}

	public Date getOnTime() {
		return onTime;
	}

	public void setOnTime(Date onTime) {
		this.onTime = onTime;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
