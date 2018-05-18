package com.lotus.transaction.test;
import static org.junit.Assert.*;

import com.lotus.database.DBService;
import com.lotus.database.MysqlService;
import com.lotus.database.test.BeanInsertSqlTest;
import com.lotus.transaction.SingleDatabaseTransaction;
import com.lotus.transaction.Transaction;
import org.junit.Test;
public class MutiTransactionTest {
    DBService service = new MysqlService();
    @Test
    public void testNestTransaction(){
        SingleDatabaseTransaction transaction = new SingleDatabaseTransaction();
        String updateSql = "update teapot set volume = ? where id = ?";
        transaction.begin();//外层事务
            transaction.begin();//内层事务1
            service.excute(updateSql, new Object[] {444, BeanInsertSqlTest.ID});
            transaction.commit();

            transaction.begin();//内层事务2
            service.excute(updateSql, new Object[] {555, BeanInsertSqlTest.ID});
            transaction.rollback();
        transaction.commit();
    }
}
