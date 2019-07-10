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
@Table(name="dependency")
public class Dependency extends ApplicationObject {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "influencer_version_id", nullable = false)
	private Version influencerVersion;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dependent_version_id", nullable = false)
	private Version dependentVersion;

	@Column(name = "description", nullable = true)
	private String description;


	public Version getInfluencerVersion() {
		return influencerVersion;
	}

	public void setInfluencerVersion(Version influencerVersion) {
		this.influencerVersion = influencerVersion;
	}

	public Version getDependentVersion() {
		return dependentVersion;
	}

	public void setDependentVersion(Version dependentVersion) {
		this.dependentVersion = dependentVersion;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
