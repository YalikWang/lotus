package com.lotus.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;
import java.util.Random;

/**
 * 单库事务
 *
 * @author yalik_wang
 */
public class SingleDatabaseTransaction implements Transaction {

    public SingleDatabaseTransaction() {

    }

    public void begin() {
        TransactionContext transactionContext = CONTEXT_HOLDER.get();
        if (transactionContext == null) {
            transactionContext = new TransactionContext();
            CONTEXT_HOLDER.set(transactionContext);
        } else {
            TransactionContext newContxet = new TransactionContext();
            newContxet.setParent(transactionContext);
            Connection connection = transactionContext.getConn();
            if(connection!=null){
                try {
                    transactionContext.getSavepoints().add(connection.setSavepoint("" + new Random().nextInt(1000)));
                } catch (SQLException e) {
                   throw new RuntimeException(e);
                }
            }
            CONTEXT_HOLDER.set(newContxet);
        }
    }

    @Override
    public void commit() {
        TransactionContext transactionContext = CONTEXT_HOLDER.get();
        if (transactionContext != null) {
            CONTEXT_HOLDER.remove();
            Connection connection = transactionContext.getConn();
            TXState state = transactionContext.getState();
            if (connection != null && TXState.RUNING == state) {
                TransactionContext parent = transactionContext.getParent();
                if (parent != null) {
                    CONTEXT_HOLDER.set(parent);
                }else {
                    //没有上级事务则直接提交
                    try {
                        connection.commit();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }else{
            throw  new RuntimeException("当前线程无正在运行的事务！");
        }
    }

    @Override
    public void rollback() {
        TransactionContext transactionContext = CONTEXT_HOLDER.get();
        if (transactionContext != null) {
            CONTEXT_HOLDER.remove();
            Connection connection = transactionContext.getConn();
            TXState state = transactionContext.getState();
            if (connection != null && TXState.RUNING == state) {
                TransactionContext parent = transactionContext.getParent();
                Savepoint savepoint = null;
                if (parent != null) {
                    CONTEXT_HOLDER.set(parent);
                    //如果有上级事务，则回滚到上一个Savepoint
                    List<Savepoint> savepoints = parent.getSavepoints();
                    if (!savepoints.isEmpty())
                        savepoint = savepoints.get(savepoints.size() - 1);
                }
                try {
                    if (savepoint != null)
                        connection.rollback(savepoint);
                    else
                        connection.rollback();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }else{
            throw  new RuntimeException("当前线程无正在运行的事务！");
        }
    }

    @Override
    public void rollback(String point) {

        throw new RuntimeException("暂不支持SavePoint");
    }
}
