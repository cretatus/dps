package ru.vk.cometa.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class ApplicationNamedObject extends ApplicationObject{

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "sysname", nullable = false)
	private String sysname;

	@Column(name = "description", nullable = true)
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "version_id", nullable = false)
	private Version version;

	public String getLabel() {
		return version.getModule().getName() + "(" + version.getName() + "): " + name;
	}

	public void setLabel(String label) {

	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
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
