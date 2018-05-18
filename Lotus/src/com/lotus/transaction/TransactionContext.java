package com.lotus.transaction;

import java.sql.Connection;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

public class TransactionContext {

	/**
	 * 上层事务
	 */
	private TransactionContext parent;

	/**
	 * 数据库连接
	 */
	private Connection conn;

	/**
	 * 当前事务状态
	 */
	private TXState state = TXState.RUNING;

	/**
	 * 当前事务的保存节点
	 */
	private List<Savepoint> savepoints = new ArrayList<>();

	public TransactionContext getParent() {
		return parent;
	}

	public void setParent(TransactionContext parent) {
		this.parent = parent;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
		while(parent!=null&&parent.getConn()==null){
			parent.setConn(conn);
		}
	}

	public TXState getState() {
		return state;
	}

	public void setState(TXState state) {
		this.state = state;
	}

	public List<Savepoint> getSavepoints() {
		return savepoints;
	}

	public void setSavepoints(List<Savepoint> savepoints) {
		this.savepoints = savepoints;
	}
}
