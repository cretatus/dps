package ru.vk.cometa.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="component")
public class Component extends ApplicationNamedObject {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "platform_id", nullable = false)
	private Platform platform;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "package_id", nullable = false)
	private Package pack;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "metatype_code", nullable = false)
	private Metatype metatype;

	public Metatype getMetatype() {
		return metatype;
	}

	public void setMetatype(Metatype metatype) {
		this.metatype = metatype;
	}

	public Package getPack() {
		return pack;
	}

	public void setPack(Package pack) {
		this.pack = pack;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	
}
