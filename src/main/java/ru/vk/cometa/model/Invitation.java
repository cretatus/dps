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

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="invitation")
public class Invitation implements Identified {
	@Id @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "permission", nullable = false)
	private String permission;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "application_id", nullable = false)
	private Application application;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reciever_user_id", nullable = true)
	private User recieverUser;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sender_user_id", nullable = false)
	private User senderUser;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "description", nullable = true)
	private String description;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public User getRecieverUser() {
		return recieverUser;
	}

	public void setRecieverUser(User recieverUser) {
		this.recieverUser = recieverUser;
	}

	public User getSenderUser() {
		return senderUser;
	}

	public void setSenderUser(User senderUser) {
		this.senderUser = senderUser;
	}


}
