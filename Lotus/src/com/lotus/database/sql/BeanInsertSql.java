package com.lotus.database.sql;

import java.util.ArrayList;
import java.util.List;

import com.lotus.model.Property;
import com.lotus.orm.AnnotationMapperContext;
import com.lotus.orm.Mapper;

public class BeanInsertSql extends Sql {
	private Object obj;

	public BeanInsertSql(Object insertObjet) {
		obj = insertObjet;
	}

	public Object[] getParams() {
		return params;
	}

	@Override
	public String toSql() {
		AnnotationMapperContext context = new AnnotationMapperContext();
		Mapper mapper = context.getMapper(obj.getClass());
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into ");
		insertSql.append(mapper.getTableName()).append(" (");
		List<String> fields = new ArrayList<>();
		List<String> questionMarks = new ArrayList<>();
		List<Property> allProperties = mapper.getAllProperties();
		params = new Object[allProperties.size()];
		for (int i = 0; i < allProperties.size(); i++) {
			Property p = allProperties.get(i);
			fields.add(p.getDbName());
			questionMarks.add("?");
			params[i] = p.getValue(obj);
		}
		insertSql.append(String.join(",", fields)).append(") ");
		insertSql.append("values (").append(String.join(",", questionMarks)).append(")");
		return insertSql.toString();
	}
}
