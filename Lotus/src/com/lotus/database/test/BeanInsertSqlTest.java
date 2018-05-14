package com.lotus.database.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.lotus.common.test.Teapot;
import com.lotus.database.DBService;
import com.lotus.database.MysqlService;
import com.lotus.database.sql.BeanInsertSql;
import com.lotus.orm.AnnotationMapperContext;
import com.lotus.orm.Mapper;
import com.lotus.orm.MapperContext;

public class BeanInsertSqlTest {

	public static final long ID = 152334118944795L;

	@Test
	public void testToInsertSql() {
		DBService service = new MysqlService();

		MapperContext mapperContext = new AnnotationMapperContext();
		Mapper mapper = mapperContext.getMapper("teapot");
		String tableName = mapper.getTableName();
		String deleteSql = " delete from " + tableName + " where id = ?";
		service.excute(deleteSql, new Object[] { ID });
		
		Teapot teapot = new Teapot();
		teapot.setCeramics(true);
		teapot.setCreateTime(new Date());
		teapot.setManufacturer("大清国景德镇御用瓷器烧造局");
		teapot.setPrice(new BigDecimal(123.45));
		teapot.setTeacupCount(2);
		teapot.setVolume(250);
		teapot.setId(ID);

		BeanInsertSql beanInsertSql = new BeanInsertSql(teapot);
		String insertSql = beanInsertSql.toSql();

		service.excute(insertSql, beanInsertSql.getParams());
	}

}
