package com.lotus.transaction.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.lotus.common.test.Teapot;
import com.lotus.database.BeanQuery;
import com.lotus.database.DBService;
import com.lotus.database.MysqlService;
import com.lotus.database.test.BeanInsertSqlTest;
import com.lotus.orm.Filter;
import com.lotus.orm.Selector;
import com.lotus.transaction.SingleDatabaseTransaction;
import com.lotus.transaction.Transaction;

public class SimpleTransactionTest {

	DBService service = new MysqlService();
	String updateSql = "update teapot set volume = ? where id = ?";

	@Test
	public void testCommit() {
		Transaction transaction =new SingleDatabaseTransaction();
		transaction.begin();
		service.excute(updateSql, new Object[] {500,BeanInsertSqlTest.ID});
		transaction.commit();
		Teapot teapot = this.getTeapot();
		assertEquals(0,Double.compare(500L,teapot.getVolume()));
	}
	@Test
	public void testRollback() {
		Transaction transaction =new SingleDatabaseTransaction();
		transaction.begin();
		service.excute(updateSql, new Object[] {850,BeanInsertSqlTest.ID});
		transaction.rollback();
		
		Teapot teapot = this.getTeapot();
		assertEquals(true,Double.compare(850L,teapot.getVolume())!=0);
	}
	@Test
	public void testNestTransaction(){
		SingleDatabaseTransaction transaction = new SingleDatabaseTransaction();
		transaction.begin();//外层事务

		transaction.begin();//内层事务1
		service.excute(updateSql, new Object[] {444, BeanInsertSqlTest.ID});
		transaction.commit();

		service.excute(updateSql, new Object[] {111, BeanInsertSqlTest.ID});//隶属于外层事务，不会被回滚

		transaction.begin();//内层事务2
		service.excute(updateSql, new Object[] {555, BeanInsertSqlTest.ID});
		transaction.rollback();

		transaction.commit();

		Teapot teapot = getTeapot();
		assertEquals(true,Double.compare(111L,teapot.getVolume())==0);
	}

	@Test
	public void testNestRollback(){
		SingleDatabaseTransaction transaction = new SingleDatabaseTransaction();
		service.excute(updateSql, new Object[] {333, BeanInsertSqlTest.ID});

		transaction.begin();//外层事务

		transaction.begin();//内层事务1
		service.excute(updateSql, new Object[] {852, BeanInsertSqlTest.ID});
		transaction.commit();

		service.excute(updateSql, new Object[] {314, BeanInsertSqlTest.ID});//隶属于外层事务

		transaction.begin();//内层事务2
		service.excute(updateSql, new Object[] {267, BeanInsertSqlTest.ID});
		transaction.commit();

		transaction.rollback();

		Teapot teapot = getTeapot();
		assertEquals(true,Double.compare(333L,teapot.getVolume())==0);
	}
	private Teapot getTeapot() {
		BeanQuery query = new BeanQuery();
		List<Teapot> results= query.query("teapot", new Selector("id","volume"), new Filter("id",Filter.equals,BeanInsertSqlTest.ID));
		return results.get(0);
	}

}
