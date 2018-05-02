package com.lotus.database;
/**
 * 数据源信息
 * @author yalik_wang
 *
 */
public class DataSourceInfo {
	public enum DatabaseType{
		MySql,
		Oracal,
		SqlServer,
		MongoDB,
		OceanBase
	}
	private DatabaseType type;
	private String url;
	private int port;
	private String user;
	private String password;
	private String database;
	
	public DatabaseType getType() {
		return type;
	}
	public void setType(DatabaseType type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	

}
