package com.lotus.transaction;

public interface Transaction {
	
	public static final ThreadLocal<TransactionContext> CONTEXT_HOLDER = new ThreadLocal<>();

	void begin();

	void commit();

	void rollback();

	void rollback(String point);
}
