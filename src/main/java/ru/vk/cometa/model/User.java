package ru.vk.cometa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="user")
public class User implements Identified{
	@Id @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "login", nullable = false)
	private String login;

	@Column(name = "email", nullable = true)
	private String email;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "password", nullable = false)
	private String password;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "current_application_id", nullable = true)
	private Application currentApplication;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "current_version_id", nullable = true)
	private Version currentVersion;

	@Column(name = "current_permission", nullable = true)
	private String currentPermission;
	
	public String getCurrentPermission() {
		return currentPermission;
	}

	public void setCurrentPermission(String currentPermission) {
		this.currentPermission = currentPermission;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Version getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(Version currentVersion) {
		this.currentVersion = currentVersion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Application getCurrentApplication() {
		return currentApplication;
	}

	public void setCurrentApplication(Application currentApplication) {
		this.currentApplication = currentApplication;
	}
	
}
