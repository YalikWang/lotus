package com.lotus.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlService implements DBService {

	@Override
	public ResultSet query(String sql, Object[] params) {
		ConnectionPool cp = new ConnectionPool();
		try {
			Connection connection = cp.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					//设置SQL参数竟然从1开始，不能忍
					prepareStatement.setObject(i+1, params[i]);
				}
			}
			return prepareStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void excute(String sql, Object[] params) {
		ConnectionPool cp = new ConnectionPool();
		try {
			Connection connection = cp.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					//设置SQL参数竟然从1开始，不能忍
					prepareStatement.setObject(i+1, params[i]);
				}
			}
			System.out.println(sql);
			prepareStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
