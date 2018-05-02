package com.lotus.database.sql;

import java.util.ArrayList;
import java.util.List;

import com.lotus.model.Property;
import com.lotus.orm.AnnotationMapperContext;
import com.lotus.orm.Mapper;

public class BeanInsertSql {
	
	public String toInsertSql(Object obj) {
		AnnotationMapperContext context = new AnnotationMapperContext();
		Mapper mapper = context.getMapper(obj.getClass());
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into ");
		insertSql.append(mapper.getTableName()).append(" (");
		List<String> fields = new ArrayList<>();
		List<String> questionMarks = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		List<Property> allProperties = mapper.getAllProperties();
		for (Property p : allProperties) {
			fields.add(p.getDbName());
			questionMarks.add("?");
			values.add(p.getValue(obj));
		}
		insertSql.append(String.join(",", fields)).append(") ");
		insertSql.append("values (").append(String.join(",", questionMarks)).append(")");
		return insertSql.toString();
	}
}
