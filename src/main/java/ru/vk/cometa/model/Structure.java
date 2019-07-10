package ru.vk.cometa.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="structure")
public class Structure extends ApplicationStereotypicalObject implements Container {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "area_id", nullable = false)
	private Area area;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", nullable = false)
	private Structure parent;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY, mappedBy = "structure")
	private List<Attribute> attributes;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public List<Detail> getDetails() {
		return new ArrayList<Detail>(attributes);
	}
	
	public void setDetails(List<Detail> children) {
		if(children != null) {
			this.attributes = new ArrayList<Attribute>();
			for(Detail child : children) {
				this.attributes.add((Attribute)child);
			}
		}
		else {
			this.attributes = null;
		}
	}

	public Structure getParent() {
		return parent;
	}

	public void setParent(Structure parnet) {
		this.parent = parnet;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
}
