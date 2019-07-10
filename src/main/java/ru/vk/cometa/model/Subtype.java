package ru.vk.cometa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="subtype")
public class Subtype {
	public static final String PRIMARY = "pk";
	public static final String UNIQUE = "uq";
	public static final String INDEX = "index";
	public static final String ELEMENT = "element";
	public static final String KEY_STRUCTURE = "key structure";
	
	@Id
	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "metaobject", nullable = false)
	private String metaobject;

	public String getLabel() {
		return metaobject + "(" + code + ")";
	}

	public void setLabel(String label) {

	}

	public String getMetaobject() {
		return metaobject;
	}

	public void setMetaobject(String metaobject) {
		this.metaobject = metaobject;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
