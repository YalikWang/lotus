package com.lotus.database.test;

import com.lotus.database.ConnectionPool;
import com.lotus.test.concurrent.AbstractConcurrentTask;
import com.lotus.test.concurrent.ConcurrentTaskHelper;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnectionPool {
    @Test
    public void testGetConnectionWithMutiThreads() {
        ConcurrentTaskHelper.simulate(ConnectionPoolConcurrentTask.class, 15);
    }

    public static void main(String args[]){
        //尽然成功了，哈哈哈，不敢相信
        ConcurrentTaskHelper.simulate(ConnectionPoolConcurrentTask.class, 15);
    }
}
