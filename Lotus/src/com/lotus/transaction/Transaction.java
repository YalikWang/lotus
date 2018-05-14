package com.lotus.transaction;

public interface Transaction {
	
	public static final ThreadLocal<TransactionContext> CONTEXT_HOLDER = new ThreadLocal<>();

	void commit();

	void commit(String point);

	void rollback();

	void rollback(String point);
	
	void end();
}
