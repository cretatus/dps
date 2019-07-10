package ru.vk.cometa.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class ApplicationStereotypicalObject extends ApplicationNamedObject{

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stereotype_id", nullable = false)
	private Stereotype stereotype;

	@Column(name = "glossary", nullable = true)
	private String glossary;
	
	public void copyFrom(ApplicationStereotypicalObject from) {
		setApplication(from.getApplication());
		setDescription(from.getDescription());
		setGlossary(from.getGlossary());
		setName(from.getName());
		setStereotype(from.getStereotype());
		setSysname(from.getSysname());
		setVersion(from.getVersion());
	}
	
	public String getGlossary() {
		return glossary;
	}

	public void setGlossary(String glossary) {
		this.glossary = glossary;
	}

	public Stereotype getStereotype() {
		return stereotype;
	}

	public void setStereotype(Stereotype stereotype) {
		this.stereotype = stereotype;
	}

}
