package com.lotus.database.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lotus.database.DBService;
import com.lotus.database.MysqlService;
import com.lotus.database.sql.CreateTableSql;
import com.lotus.orm.AnnotationMapperContext;
import com.lotus.orm.Mapper;
import com.lotus.orm.MapperContext;

public class CreateTableSqlTest {
	CreateTableSql createTableSql = new CreateTableSql();
	@Test
	public void testCreateTable() {
		String testEntity = "teapot";
		DBService service = new MysqlService();
		String dropSql = createTableSql.toDropSql(testEntity);
		service.excute(dropSql, null);
		String createSql = createTableSql.toCreateSql(testEntity);
		service.excute(createSql, null);
		String constraintSql = createTableSql.toConstraintSql(testEntity);
		service.excute(constraintSql, null);
	}

}
