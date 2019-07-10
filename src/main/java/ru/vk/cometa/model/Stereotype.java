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
@Table(name="stereotype")
public class Stereotype extends ApplicationNamedObject{

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subtype_code", nullable = false)
	private Subtype subtype;

	@Column(name = "is_default", nullable = false)
	private Boolean isDefault;
	
	public Subtype getSubtype() {
		return subtype;
	}

	public void setSubtype(Subtype subtype) {
		this.subtype = subtype;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = Boolean.valueOf(true).equals(isDefault);
	}

	@Override
	public String getLabel() {
		return getVersion().getModule().getName() + "(" + getVersion().getName() + "): " + getName();
	}

}
