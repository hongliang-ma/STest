package com.aliyun.yun.plugin.database.utils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("database")
public class DBConfigurationInfo {

	@XStreamAsAttribute
	private String name;

	private String dbType;

	private String url;

	private String username;

	private String password;

	public String getDbType() {
		return dbType;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
