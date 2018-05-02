package com.lotus.database.sql;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lotus.model.Property;
import com.lotus.orm.Filter;
import com.lotus.orm.Mapper;
import com.lotus.orm.Selector;

public abstract class QuerySql {
	/**
	 * 查询语句的字段重命名关系记录，比如select name as username form user;
	 * 此时需要将name和username对应关系记录下来，以便于对查询结果进行解析
	 * 存储的键是as后的名称，值为数据库字段名称。fieldMap.put("username","name");
	 */
	protected Map<String, String> fieldMap = new HashMap<>();
	protected Mapper mapper;
	protected Object[] params;
	protected Selector selectors;
	protected Filter filter;

	public QuerySql(Mapper mapper, Selector selectors, Filter filter) {
		this.mapper = mapper;
		this.selectors = selectors;
		this.filter = filter;
	}

	private StringBuffer translateFilter(Filter filter) {
		StringBuffer filterSql = new StringBuffer();
		Property property = mapper.getProperty(filter.getName());
		filterSql.append(mapper.getTableName()).append('.');
		filterSql.append(property.getDbName());
		filterSql.append(filter.getOperator());
		filterSql.append(" ? ");
		return filterSql;
	}

	public String toSql() {
		// 拼接select头
		StringBuffer mainSql = new StringBuffer();
		mainSql.append(" select ");
		List<String> dbFields = new ArrayList<>();
		for (String field : selectors.getSelectors()) {
			Property property = mapper.getProperty(field);
			if (property == null) {
				throw new RuntimeException(String.format("实体【%s】中不存在名为【%s】的字段", mapper.getEntityName(), field));
			}
			dbFields.add(property.getDbName());
			fieldMap.put(property.getDbName(), field);
		}
		mainSql.append(String.join(",", dbFields));
		mainSql.append(" from ");
		mainSql.append(mapper.getTableName());
		mainSql.append(" where ");
		mainSql.append(translateFilter(filter));
		return mainSql.toString();
	}
	
	public Object[] getParams() {
		return new Object[] { filter.getValue() };
	}

	public abstract <T extends Object> List<T> parse(ResultSet rs);
}
