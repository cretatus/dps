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
@Table(name="build_log")
public class BuildLog extends ApplicationObject{
	@Column(name = "path", nullable = false)
	private String path;

	@Column(name = "file", nullable = false)
	private String file;

	@Column(name = "is_directory", nullable = false)
	private Boolean isDirectory;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "build_id", nullable = true)
	private Build build;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "generator_id", nullable = false)
	private Generator generator;

	@Column(name = "metaobject", nullable = false)
	private String metaobject;

	@Column(name = "object_id", nullable = false)
	private Integer objectId;

	public String getLocalPath() {
		return getPath().substring(getBuild().getPath().length());
	}

	public void setLocalPath(String localPath) {
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Boolean getIsDirectory() {
		return isDirectory;
	}

	public void setIsDirectory(Boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public Build getBuild() {
		return build;
	}

	public void setBuild(Build build) {
		this.build = build;
	}

	public Generator getGenerator() {
		return generator;
	}

	public void setGenerator(Generator generator) {
		this.generator = generator;
	}

	public String getMetaobject() {
		return metaobject;
	}

	public void setMetaobject(String metaobject) {
		this.metaobject = metaobject;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

}
