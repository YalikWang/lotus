package com.lotus.orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lotus.database.annotation.Column;
import com.lotus.database.annotation.ColumnIgnore;
import com.lotus.database.annotation.Entity;
import com.lotus.model.BeanProperty;
import com.lotus.model.Property;

public class AnnotationMapperContext extends MapperContext {

	public Mapper getMapper(Class<?> clazz) {
		if (!clazz.isAnnotationPresent(Entity.class)) {
			throw new RuntimeException(String.format("类【%s】未配置Entity注解，无法进行字段映射解析", clazz.getName()));
		}
		Entity entityConfig = clazz.getAnnotation(Entity.class);
		String entityName = entityConfig.name();
		if (Entity.DEFAULT_ENTITY_NAME.equalsIgnoreCase(entityName)) {
			entityName = clazz.getSimpleName().toLowerCase();
		}
		return this.getMapper(entityName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T createEntity(String entity) {
		BeanMapper mapper = getMapper(entity);
		Class<?> clazz = mapper.getClazz();
		try {
			return (T) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Mapper> scanConfiguration() {
		Map<String, Mapper> mapperContext = new HashMap<>();
		// 扫描package，获取所有的类
		List<Class<?>> classes = ClassUtil.getClasses("com.lotus");
		for (Class<?> clazz : classes) {
			// 有Entity注解标识的即为实体
			if (!clazz.isAnnotationPresent(Entity.class)) {
				continue;
			}
			// 通过注解来配置ORM，则其类型一定是BeanMapper
			BeanMapper mapper = new BeanMapper();
			mapper.setClazz(clazz);
			Entity entity = clazz.getAnnotation(Entity.class);
			String entityName = entity.name().toLowerCase();
			if (Entity.DEFAULT_ENTITY_NAME.equalsIgnoreCase(entityName)) {
				// 如果默认没有显式配置实体名称，则以类名为实体名
				entityName = clazz.getSimpleName().toLowerCase();
			}
			mapper.setEntity(entityName);
			String tableName = entity.table().toLowerCase();
			if (Entity.DEFAULT_TABLE_NAME.equalsIgnoreCase(tableName)) {
				// 如果默认没有显式配置数据库表名，则以实体名为表名
				tableName = entityName;
			}
			mapper.setTableName(tableName);
			mapper.setProperties(getProperties(clazz, entityName));
			mapperContext.put(entityName, mapper);
		}
		return mapperContext;
	}

	private List<Property> getProperties(Class<?> clazz, String entityName) {
		Entity entityConfig = clazz.getAnnotation(Entity.class);
		boolean isDefaultColumn = entityConfig.defaultColumn();
		// 获取字段配置信息
		Field[] fields = clazz.getDeclaredFields();
		Field.setAccessible(fields, true);
		List<Property> properties = new ArrayList<>();
		for (Field f : fields) {
			// 先设置Property的基本信息，但是注意该Property不一定有对应数据库字段
			BeanProperty property = new BeanProperty();
			property.setField(f);
			property.setType(f.getType());
			if (f.isAnnotationPresent(Column.class)) {
				// 如果配置了Column注解，则以注解为准
				property.setEntityName(entityName);
				property.setName(f.getName());
				Column column = f.getAnnotation(Column.class);
				String dbName = column.name();
				if (Column.DEFAULT_DB_NAME.equalsIgnoreCase(dbName)) {
					dbName = f.getName();
				}
				property.setDbName(dbName);
				properties.add(property);
			} else {
				// 没有配置Column注解的情况下,实体配置按照自动规则映射且字段上没有配置ColumnIgnore注解
				if (isDefaultColumn) {
					if (!f.isAnnotationPresent(ColumnIgnore.class)) {
						property.setEntityName(entityName);
						property.setName(f.getName());
						property.setDbName(f.getName());
						properties.add(property);
					}
				}
			}
		}
		return properties;
	}
}
