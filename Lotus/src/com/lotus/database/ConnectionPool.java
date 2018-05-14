package com.lotus.database;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.lotus.database.DataSourceInfo.DatabaseType;
import com.lotus.transaction.Transaction;
import com.lotus.transaction.TransactionContext;

public class ConnectionPool implements DataSource {

	private static Connection con;

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		//支持事务
		TransactionContext transactionContext = Transaction.CONTEXT_HOLDER.get();
		if (transactionContext != null) {
			Connection conn = transactionContext.getConn();
			if (conn == null) {
				Connection connection = this.getConnectionWithNoTransaction();
				connection.setAutoCommit(false);
				transactionContext.setConn(connection);
				return connection;
			} else {
				return conn;
			}
		} else {
			return getConnectionWithNoTransaction();
		}
	}

	private Connection getConnectionWithNoTransaction() throws SQLException {
		if (con == null) {
			DataSourceInfo info = new DataSourceInfo();
			info.setUrl("localhost");
			info.setPort(3306);
			info.setUser("root");
			info.setPassword("root");
			info.setType(DatabaseType.MySql);
			info.setDatabase("lotus");
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				String baseInfo = String.format("jdbc:mysql://%s:%s/%s", info.getUrl(), info.getPort(),
						info.getDatabase());
				String setting = "?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8";
				con = DriverManager.getConnection(baseInfo + setting, info.getUser(), info.getPassword());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return con;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
