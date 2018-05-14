package com.lotus.transaction;

import java.sql.Connection;
import java.util.List;

public class TransactionContext {
	private TransactionContext parent;
	private List<Connection> holdingConns;
	private Connection conn;
	public TransactionContext getParent() {
		return parent;
	}
	public void setParent(TransactionContext parent) {
		this.parent = parent;
	}
	public List<Connection> getHoldingConns() {
		return holdingConns;
	}
	public void setHoldingConns(List<Connection> holdingConns) {
		this.holdingConns = holdingConns;
	}
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	// private SavePoint
}
