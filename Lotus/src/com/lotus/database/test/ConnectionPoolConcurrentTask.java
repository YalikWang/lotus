package com.lotus.database.test;

import com.lotus.database.ConnectionPool;
import com.lotus.test.concurrent.AbstractConcurrentTask;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolConcurrentTask extends AbstractConcurrentTask {
    @Override
    public void doTask() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection c = null;
        try {
            c = connectionPool.getConnectionWithPool();
            System.out.println(Thread.currentThread().getName() + "获取一个连接");
            Thread.currentThread().sleep(2000L);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        connectionPool.releaseConnectionToPool(c);
        System.out.println(Thread.currentThread().getName() + "释放连接");
    }
}
