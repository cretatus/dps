package ru.vk.cometa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Entity
@Table(name = "entity_key")
public class Key extends ApplicationStereotypicalObject implements Detail{

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "entity_id", nullable = false)
	private AppEntity entity;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "structure_id", nullable = false)
	private Structure structure;

	@Column(name = "metatype_code", nullable = false)
	private String metatypeCode;

	public String getMetatypeCode() {
		return metatypeCode;
	}

	public void setMetatypeCode(String metatypeCode) {
		this.metatypeCode = metatypeCode;
	}

	public AppEntity getEntity() {
		return entity;
	}

	public void setEntity(AppEntity entity) {
		this.entity = entity;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	@Override
	public void copyFrom(Detail child) {
		Key from = (Key)child;
		super.copyFrom(from);
		setEntity(from.getEntity());
		setStructure(from.getStructure());
	}

	@Override
	public void setContainer(Container parent) {
		this.entity = (AppEntity)parent;
	}
}
