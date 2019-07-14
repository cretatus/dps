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
@Table(name="generator")
public class Generator extends ApplicationNamedObject {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "platform_id", nullable = false)
	private Platform platform;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stereotype_id", nullable = false)
	private Stereotype stereotype;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "resource_id", nullable = false)
	private Resource resource;

	@Column(name = "extension", nullable = false)
	private String extension;

	@Column(name = "encoding", nullable = false)
	private String encoding;

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Integer getResourceId() {
		return resource.getId();
	}

	public void setResourceId(Integer resourceId) {
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Stereotype getStereotype() {
		return stereotype;
	}

	public void setStereotype(Stereotype stereotype) {
		this.stereotype = stereotype;
	}

}
