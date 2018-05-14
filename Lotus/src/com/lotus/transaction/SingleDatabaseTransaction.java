package com.lotus.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 单库事务
 * 
 * @author yalik_wang
 *
 */
public class SingleDatabaseTransaction implements Transaction {

	public SingleDatabaseTransaction() {
		TransactionContext transactionContext = CONTEXT_HOLDER.get();
		if (transactionContext == null) {
			transactionContext = new TransactionContext();
			CONTEXT_HOLDER.set(transactionContext);
		} else {
			TransactionContext newContxet = new TransactionContext();
			newContxet.setParent(transactionContext);
			CONTEXT_HOLDER.set(newContxet);
		}
	}

	@Override
	public void commit() {
		Connection connection = this.getTransactionConnection();
		if (connection != null) {
			try {
				connection.commit();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}

	}

	@Override
	public void commit(String point) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback() {
		Connection connection = this.getTransactionConnection();
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void rollback(String point) {

	}

	@Override
	public void end() {
		TransactionContext transactionContext = CONTEXT_HOLDER.get();
		if (transactionContext != null) {
			Connection connection = transactionContext.getConn();
			if (connection != null) {
				try {
					connection.commit();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			CONTEXT_HOLDER.remove();
			TransactionContext parent = transactionContext.getParent();
			if (parent != null) {
				CONTEXT_HOLDER.set(parent);
			}
		}
	}

	private Connection getTransactionConnection() {
		TransactionContext transactionContext = CONTEXT_HOLDER.get();
		if (transactionContext != null) {
			return transactionContext.getConn();
		}
		return null;
	}

}
