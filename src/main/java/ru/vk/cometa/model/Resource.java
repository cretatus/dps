package ru.vk.cometa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="resource")
public class Resource extends ApplicationObject{

	@Column(name = "text", nullable = false)
	private String text;

	@Column(name = "format", nullable = false)
	private String format;

	@Column(name = "encoding", nullable = false)
	private String encoding;

	@Column(name = "owner", nullable = false)
	private String owner;

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}