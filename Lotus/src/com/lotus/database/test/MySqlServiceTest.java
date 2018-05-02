package com.lotus.database.test;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.lotus.database.MysqlService;

public class MySqlServiceTest {
	MysqlService service = new MysqlService();

	@Test
	public void testQuery() {
		this.testExcute();
		String querySql = "select * from LotusTestTable where age = ?";
		ResultSet result = service.query(querySql, new Object[] { 23 });
		try {
			assertEquals(result.next(), true);
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
			fail("ResultSet抛出异常");
		}
	}

	@Test
	public void testExcute() {
		String dropsql = "drop table if exists LotusTestTable";
		service.excute(dropsql, null);

		String createSql = "create table LotusTestTable( name varchar(30),age int" + ")";
		service.excute(createSql, null);

		String insertSql = "insert into LotusTestTable(name,age) values('小强',23)";
		service.excute(insertSql, null);

		String updateSql = "update LotusTestTable set name='大哥强' where age = ?";
		service.excute(updateSql, new Object[] { 23 });
	}

}
