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

	@Test
	public void testCommit() {
		DBService service = new MysqlService();
		String updateSql = "update teapot set volume = ? where id = ?";
		Transaction transaction =new SingleDatabaseTransaction();
		service.excute(updateSql, new Object[] {500,BeanInsertSqlTest.ID});
		transaction.commit();
		transaction.end();
		Teapot teapot = this.getTeapot();
		assertEquals(0,Double.compare(500L,teapot.getVolume()));
	}

	@Test
	public void testRollback() {
		DBService service = new MysqlService();
		String updateSql = "update teapot set volume = ? where id = ?";
		Transaction transaction =new SingleDatabaseTransaction();
		service.excute(updateSql, new Object[] {850,BeanInsertSqlTest.ID});
		transaction.rollback();
		transaction.end();
		
		Teapot teapot = this.getTeapot();
		assertEquals(true,Double.compare(850L,teapot.getVolume())!=0);
	}
	
	private Teapot getTeapot() {
		BeanQuery query = new BeanQuery();
		List<Teapot> results= query.query("teapot", new Selector("id","volume"), new Filter("id",Filter.equals,BeanInsertSqlTest.ID));
		return results.get(0);
	}

}
