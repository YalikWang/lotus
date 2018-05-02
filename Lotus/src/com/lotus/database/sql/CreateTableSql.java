package com.lotus.database.sql;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lotus.model.Property;
import com.lotus.orm.AnnotationMapperContext;
import com.lotus.orm.Mapper;
import com.lotus.orm.MapperContext;

public class CreateTableSql {

	public String toDropSql(String entity) {
		MapperContext mc = new AnnotationMapperContext();
		Mapper mapper = mc.getMapper(entity);
		String tableName = mapper.getTableName();
		String dropTable = "drop table if exists " + tableName;
		return dropTable;
	}

	public String toCreateSql(String entity) {
		MapperContext mc = new AnnotationMapperContext();
		Mapper mapper = mc.getMapper(entity);
		String tableName = mapper.getTableName();
		StringBuffer sql = new StringBuffer();
		sql.append("create table " + tableName + " (");
		List<Property> allProperties = mapper.getAllProperties();
		List<String> fieldConfigs = new ArrayList<>();
		for (Property p : allProperties) {
			String fieldExp = String.format("%s %s", p.getDbName(), getSQLType(p.getType()));
			fieldConfigs.add(fieldExp);
		}
		sql.append(String.join(",", fieldConfigs));
		sql.append(")");
		return sql.toString();
	}

	public String toConstraintSql(String entity) {
		MapperContext mc = new AnnotationMapperContext();
		Mapper mapper = mc.getMapper(entity);
		String tableName = mapper.getTableName();
		String PKSql = "alter table " + tableName + " add constraint " + tableName + "_PK " + "primary key (id)";
		return PKSql;
	}

	private String getSQLType(Class<?> type) {
		Map<Class<?>, String> typeMapper = new HashMap<>();
		typeMapper.put(long.class, "bigint");
		typeMapper.put(Long.class, "bigint");
		typeMapper.put(int.class, "int");
		typeMapper.put(Integer.class, "int");
		typeMapper.put(float.class, "float");
		typeMapper.put(Float.class, "float");
		typeMapper.put(double.class, "double");
		typeMapper.put(Double.class, "double");
		typeMapper.put(boolean.class, "tinyint");
		typeMapper.put(Boolean.class, "tinyint");
		typeMapper.put(String.class, "varchar(50)");
		typeMapper.put(BigDecimal.class, "decimal");
		typeMapper.put(java.util.Date.class, "datetime");
		typeMapper.put(java.sql.Date.class, "datetime");
		return typeMapper.get(type);
	}
}
