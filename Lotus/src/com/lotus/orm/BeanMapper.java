package com.lotus.orm;

import java.util.List;

import com.lotus.model.Property;

public class BeanMapper implements Mapper {
	private String entity;
	private Class<?> clazz;

	private String tableName;
	private List<Property> properties;

	@Override
	public String getEntityName() {
		return entity;
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public List<Property> getAllProperties() {
		return properties;
	}

	public Class<?> getClazz() {
		return clazz;
	}
	@Override
	public Property getProperty(String propertyName) {
		for (Property p : properties) {
			if (p.getEntityName().equals(propertyName)) {
				return p;
			}
		}
		return null;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

}
