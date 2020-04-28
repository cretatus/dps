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
@Table(name="transformation")
public class Transformation extends ApplicationNamedObject{
	@Column(name = "source_protocol", nullable = false)
	private String sourceProtocol;

	@Column(name = "source_reader", nullable = false)
	private String sourceReader;

	@Column(name = "source_parameters", nullable = false)
	private String sourceParameters;

	@Column(name = "target_protocol", nullable = false)
	private String targetProtocol;

	@Column(name = "target_writer", nullable = false)
	private String targetWriter;

	@Column(name = "target_parameters", nullable = false)
	private String targetParameters;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "transforming_resource_id", nullable = false)
	private Resource transformingResource;

	public Resource getTransformingResource() {
		return transformingResource;
	}

	public void setTransformingResource(Resource transformerResource) {
		this.transformingResource = transformerResource;
	}

	public String getSourceProtocol() {
		return sourceProtocol;
	}

	public void setSourceProtocol(String sourceProtocol) {
		this.sourceProtocol = sourceProtocol;
	}

	public String getSourceReader() {
		return sourceReader;
	}

	public void setSourceReader(String sourceReader) {
		this.sourceReader = sourceReader;
	}

	public String getSourceParameters() {
		return sourceParameters;
	}

	public void setSourceParameters(String sourceParameters) {
		this.sourceParameters = sourceParameters;
	}

	public String getTargetProtocol() {
		return targetProtocol;
	}

	public void setTargetProtocol(String targetProtocol) {
		this.targetProtocol = targetProtocol;
	}

	public String getTargetWriter() {
		return targetWriter;
	}

	public void setTargetWriter(String targetWriter) {
		this.targetWriter = targetWriter;
	}

	public String getTargetParameters() {
		return targetParameters;
	}

	public void setTargetParameters(String targetParameters) {
		this.targetParameters = targetParameters;
	}

}
