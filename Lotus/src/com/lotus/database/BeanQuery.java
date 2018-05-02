package com.lotus.database;

import java.sql.ResultSet;
import java.util.List;

import com.lotus.database.sql.BeanQuerySql;
import com.lotus.orm.AnnotationMapperContext;
import com.lotus.orm.Filter;
import com.lotus.orm.Selector;

public class BeanQuery implements BeanQueryService {

	@Override
	public <T> List<T> query(String entity, Selector selector, Filter filter) {
		AnnotationMapperContext context = new AnnotationMapperContext();
		BeanQuerySql querySql = new BeanQuerySql(context.getMapper(entity), selector, filter);
		String sql = querySql.toSql();
		DBService service = new MysqlService();
		ResultSet resultSet = service.query(sql, querySql.getParams());
		return querySql.parse(resultSet);
	}

}
