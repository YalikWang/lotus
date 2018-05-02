package com.lotus.model;
/**
 * 属性
 * @author yalik_wang
 *
 */
public class Property {
	private String entityName;
	private String name;
	private String localeName;
	private String dbName;
	private Class<?> type;
	
	public <T extends Object> T getValue(Object obj) {
		return null;
	}
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocaleName() {
		return localeName;
	}
	public void setLocaleName(String localeName) {
		this.localeName = localeName;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public Class<?> getType() {
		return type;
	}
	public void setType(Class<?> type) {
		this.type = type;
	}
}
