package com.dd.danmaku.user.bean;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Transient
	private static final int STATUS_ENABLE = 1;
	@Transient
	private static final int STATUS_DISABLE = 0;
	@Transient
	private static final String LEVEL_NORMAL = "normal";
	@Transient
	private static final String LEVEL_VIP = "vip";

	@Id
	private String id;
	private String name;
	private String password;
	private String mail;
	private int status;
	private String level;
	
	public User() {
	}
	
	
	public User(String name, String password, String mail) {
		this.name = name;
		this.password = password;
		this.mail = mail;
		this.status = STATUS_ENABLE;
		this.level = LEVEL_NORMAL;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}
	
	
}
