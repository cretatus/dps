package ru.vk.cometa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="assembly")
public class Assembly extends ApplicationObject{
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "sysname", nullable = false)
	private String sysname;

	@Column(name = "description", nullable = true)
	private String description;

	@ManyToMany
    @JoinTable(name = "assembly_has_version", joinColumns = @JoinColumn(name = "assembly_id"), inverseJoinColumns = @JoinColumn(name = "version_id"))
    private Set<Version> versions = new HashSet<Version>();
	
	public Set<Version> getVersions() {
		return versions;
	}

	public void setVersions(Set<Version> versions) {
		this.versions = versions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSysname() {
		return sysname;
	}

	public void setSysname(String sysname) {
		this.sysname = sysname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
