package com.lotus.database;

import java.sql.ResultSet;
/**
 * 数据库服务接口
 * @author yalik_wang
 *
 */
public interface DBService {
	
	ResultSet query(String sql,Object[] params);
	
	void excute(String sql,Object[] params);
}
