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
@Table(name="entity")
public class AppEntity extends ApplicationStereotypicalObject implements Container {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "structure_id", nullable = false)
	private Structure structure;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pk_attribute_id", nullable = false)
	private Attribute pkAttribute;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id", nullable = true)
	private AppEntity parent;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "area_id", nullable = false)
	private Area area;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY, mappedBy = "entity")
	private List<Key> keys;

	public List<Key> getKeys() {
		return keys;
	}

	public void setKeys(List<Key> keys) {
		this.keys = keys;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public Attribute getPkAttribute() {
		return pkAttribute;
	}

	public void setPkAttribute(Attribute pkAttribute) {
		this.pkAttribute = pkAttribute;
	}

	public AppEntity getParent() {
		return parent;
	}

	public void setParent(AppEntity parent) {
		this.parent = parent;
	}

	@Override
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public List<Detail> getDetails() {
		return new ArrayList<Detail>(keys);
	}

	@Override
	public void setDetails(List<Detail> children) {
		if(children != null) {
			this.keys = new ArrayList<Key>();
			for(Detail child : children) {
				this.keys.add((Key)child);
			}
		}
		else {
			this.keys = null;
		}
	}

}
