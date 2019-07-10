package ru.vk.cometa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="version")
public class Version extends ApplicationObject {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "module_id", nullable = false)
	private AppModule module;

	@Column(name = "number", nullable = false)
	private Integer number;
	
	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "name", nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "major_version_id", nullable = false)
	private MajorVersion majorVersion;

	@Column(name = "status", nullable = false)
	private String status;
	public static final String STATUS_OPENED = "OPENED";
	public static final  String STATUS_CLOSED = "CLOSED";
/*
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToMany
    @JoinTable(name = "assembly_has_version", joinColumns = @JoinColumn(name = "version_id"), inverseJoinColumns = @JoinColumn(name = "assembly_id"))
    private Set<Assembly> assemblies = new HashSet<Assembly>();
	
	public Set<Assembly> getAssemblies() {
		return assemblies;
	}

	public void setAssemblies(Set<Assembly> assemblies) {
		this.assemblies = assemblies;
	}
*/
	public String getLabel() {
		return module.getName() + " (" + getName() + ")";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MajorVersion getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(MajorVersion majorVersion) {
		this.majorVersion = majorVersion;
	}

}
