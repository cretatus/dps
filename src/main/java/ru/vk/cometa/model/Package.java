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
@Table(name="package")
public class Package extends ApplicationNamedObject {
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_package_id", nullable = true)
	private Package parent;

	@Column(name = "file_name_template", nullable = false)
	private String fileNameTemplate;

	public String getFileNameTemplate() {
		return fileNameTemplate;
	}

	public void setFileNameTemplate(String fileNameTemplate) {
		this.fileNameTemplate = fileNameTemplate;
	}

	public String findPath(Package pack) {
		if(pack == null) {
			return "";
		}
		if(pack.getParent() == null) {
			return pack.getSysname();
		}
		return findPath(pack.getParent()) + "." + pack.getSysname();
	}

	public String getPath() {
		return findPath(this);
	}

	public void setPath(String path) {
	}

	public Package getParent() {
		return parent;
	}

	public void setParent(Package parent) {
		this.parent = parent;
	}
	
}
