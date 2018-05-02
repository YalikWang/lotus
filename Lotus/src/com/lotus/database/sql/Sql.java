package com.lotus.database.sql;

public abstract class Sql {
	protected String sql;
	protected Object[] params;
	public abstract String toSql();
	public abstract Object[] getParams();
}
