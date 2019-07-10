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
@Table(name="attribute")
public class Attribute extends ApplicationStereotypicalObject implements Detail {
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "structure_id", nullable = false)
	private Structure structure;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "element_id", nullable = false)
	private Element element;

	@Column(name = "number", nullable = false)
	private Integer number;

	@Column(name = "is_nullable", nullable = false)
	private Boolean isNullable = false;

	@Column(name = "is_reference", nullable = false)
	private Boolean isReference = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", nullable = false)
	private Attribute parent;

	public void setContainer(Container parent) {
		this.structure = (Structure)parent;
	}

	public void copyFrom(Detail child) {
		Attribute from = (Attribute)child;
		super.copyFrom(from);
		setElement(from.getElement());
		setNumber(from.getNumber());
		setIsNullable(from.getIsNullable());
	}
	
	public Boolean getIsNullable() {
		if(isNullable == null) {
			return false;
		}
		return isNullable;
	}

	public void setIsNullable(Boolean isNullable) {
		this.isNullable = Boolean.valueOf(true).equals(isNullable);
	}

	public Boolean getIsReference() {
		return (getElement() != null) && (getElement().getReferenceEntity() != null);
	}

	public void setIsReference(Boolean isReference) {
		
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Attribute getParent() {
		return parent;
	}

	public void setParent(Attribute parent) {
		this.parent = parent;
	}

}
