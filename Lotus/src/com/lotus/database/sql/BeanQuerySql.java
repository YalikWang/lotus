package com.lotus.database.sql;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lotus.model.BeanProperty;
import com.lotus.model.Property;
import com.lotus.orm.AnnotationMapperContext;
import com.lotus.orm.Filter;
import com.lotus.orm.Mapper;
import com.lotus.orm.MapperContext;
import com.lotus.orm.Selector;

public class BeanQuerySql extends QuerySql {

	public BeanQuerySql(Mapper mapper, Selector selectors, Filter filter) {
		super(mapper, selectors, filter);
	}

	@Override
	public <T> List<T> parse(ResultSet rs) {
		List<T> result = new ArrayList<>();
		MapperContext mc = new AnnotationMapperContext();
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			List<Property> allProperties = mapper.getAllProperties();
			while (rs.next()) {
				// 获取一个Bean的实例
				T entityInstance = mc.createEntity(mapper.getEntityName());
				Field[] fields = entityInstance.getClass().getFields();
				Field.setAccessible(fields, true);
				// 为字段设值
				for (int i = 1; i <= columnCount; i++) {
					Object columnValue = rs.getObject(i);
					String columnName = metaData.getColumnName(i);
					String dbFieldName = this.fieldMap.get(columnName);
					boolean match = false;
					for (Property p : allProperties) {
						if (p.getDbName().equals(dbFieldName)) {
							BeanProperty bp = (BeanProperty) p;
							Field field = bp.getField();
							field.set(entityInstance, columnValue);
							match = true;
							break;
						}
					}
					if (!match) {
						throw new RuntimeException("实体【" + mapper.getEntityName() + "】中不存在【" + dbFieldName + "】对应的字段");
					}
				}
				result.add(entityInstance);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

}
