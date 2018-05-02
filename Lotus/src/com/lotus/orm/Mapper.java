package com.lotus.orm;

import java.util.List;

import com.lotus.model.Property;

public interface Mapper {
	
	String getEntityName();
	
	String getTableName();
	
	List<Property> getAllProperties();
	
	Property getProperty(String propertyName);
}
