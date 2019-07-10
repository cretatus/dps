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
@Table(name = "element")
public class Element extends ApplicationStereotypicalObject {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id", nullable = false)
	private ElementType type;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "area_id", nullable = false)
	private Area area;

	@Column(name = "size", nullable = true)
	private Integer size;

	@Column(name = "accuracy", nullable = true)
	private Integer accuracy;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reference_entity_id", nullable = true)
	private AppEntity referenceEntity;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "structure_id", nullable = true)
	private Structure structure;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "key_element_id", nullable = true)
	private Element keyElement;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "value_element_id", nullable = true)
	private Element valueElement;

	public String getDetail() {
		if ("string".equals(type.getName())) {
			return "string (" + getSize() + ")";
		}
		else if ("numeric".equals(type.getName())) {
			return "numeric (" + getSize() + ", " + getAccuracy() + ")";
		}
		else if ("reference".equals(type.getName()) && (getReferenceEntity() != null)) {
			return "reference to entity [" + getReferenceEntity().getName() + "]";
		}
		else if("collection".equals(type.getName()) && (getValueElement() != null)){
			return "collection of elements [" + getValueElement().getName() + "]";
		}
		else if("map".equals(type.getName()) && (getKeyElement() != null) && (getValueElement() != null)){
			return "map (key = [" + getKeyElement().getName() + "], value = [" + getValueElement().getName() + "])";
		}
		else if("structure".equals(type.getName()) && (getStructure() != null)){
			return "structure [" + getStructure().getName() + "]";
		}
		else {
			return type.getName();
		}
	}

	public void setDetail(String detail) {

	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public ElementType getType() {
		return type;
	}

	public void setType(ElementType type) {
		this.type = type;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(Integer accuracy) {
		this.accuracy = accuracy;
	}

	public AppEntity getReferenceEntity() {
		return referenceEntity;
	}

	public void setReferenceEntity(AppEntity referenceEntity) {
		this.referenceEntity = referenceEntity;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public Element getKeyElement() {
		return keyElement;
	}

	public void setKeyElement(Element keyElement) {
		this.keyElement = keyElement;
	}

	public Element getValueElement() {
		return valueElement;
	}

	public void setValueElement(Element valueElement) {
		this.valueElement = valueElement;
	}

}
