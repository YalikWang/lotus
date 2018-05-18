package com.lotus.database;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.lotus.database.DataSourceInfo.DatabaseType;
import com.lotus.transaction.Transaction;
import com.lotus.transaction.TransactionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ConnectionPool implements DataSource {
    private static final int MAX_CONNECTION = 10;
    private static Stack<Connection> availableConnections = new Stack<>();
    private static Stack<Connection> busyConnections = new Stack<>();
    private static Connection con;
    private static DataSourceInfo dataSourceInfo;

    private static ConnectionPool cp;
    public static ConnectionPool getInstance(){
        if(cp==null){
            cp = new ConnectionPool();
        }
        return cp;
    }
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
            if (dataSourceInfo == null) {
                dataSourceInfo = this.getDatabaseSetting();
            }
            try {
                String driverName = "com.mysql.jdbc.Driver";
                Class.forName(driverName);
                String baseInfo = String.format("jdbc:mysql://%s:%s/%s", dataSourceInfo.getUrl(), dataSourceInfo.getPort(),
                        dataSourceInfo.getDatabase());
                con = DriverManager.getConnection(baseInfo + dataSourceInfo.getOtherSetting(), dataSourceInfo.getUser(), dataSourceInfo.getPassword());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return con;
    }

    public static int count = 0;
    public synchronized Connection getConnectionWithPool() throws SQLException{
        /**测试代码*/
        if(availableConnections.size()==0){
            if(busyConnections.size()==MAX_CONNECTION){
                //没有空闲连接且连接数已经达到系统设置上限，等待一定时间
                try {
                    System.out.println(Thread.currentThread().getName()+"申请不到连接，等待");
                    this.wait(2000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(availableConnections.size()>0){
                    Connection pop = availableConnections.pop();
                    busyConnections.push(pop);
                    return pop;
                }else{
                    throw new RuntimeException("连接超时，无法申请到可用的数据库连接！");
                }
            }else{
                System.out.println("新建一个连接"+(++count));
                //当前没有空闲连接，但是连接数还没有达到上限，新建一个连接
                Connection connection = this.builtConnection();
                busyConnections.push(connection);
                return connection;
            }
        }else{
            Connection pop = availableConnections.pop();
            busyConnections.push(pop);
            return pop;
        }
    }

    public synchronized void releaseConnectionToPool(Connection connection){
        busyConnections.remove(connection);
        availableConnections.push(connection);
        this.notify();
    }

    /**
     * 新建一个连接
     * @return
     * @throws SQLException
     */
    private Connection builtConnection() throws SQLException{
        if (dataSourceInfo == null) {
            dataSourceInfo = this.getDatabaseSetting();
        }
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName);
            String baseInfo = String.format("jdbc:mysql://%s:%s/%s", dataSourceInfo.getUrl(), dataSourceInfo.getPort(),
                    dataSourceInfo.getDatabase());
            return DriverManager.getConnection(baseInfo + dataSourceInfo.getOtherSetting(), dataSourceInfo.getUser(), dataSourceInfo.getPassword());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private DataSourceInfo getDatabaseSetting() {
        //获取系统配置文件（lotus.xml）的路径
        URL resource = ConnectionPool.class.getResource("/");
        String currentPath = ConnectionPool.class.getResource("/").getPath();
        File mainDir = new File(currentPath+"/com/lotus");
        File[] systemXmls = mainDir.listFiles(file -> {
            if (file.isFile()) {
                String name = file.getName();
                if ("lotus.xml".equalsIgnoreCase(name)) {
                    return true;
                }
            }
            return false;
        });
        if (systemXmls.length == 0) {
            throw new RuntimeException("找不到系统配置文件！");
        }
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(systemXmls[0].getAbsolutePath());
            Element rootElement = document.getRootElement();
            Element properties = rootElement.element("properties");
            Element databaseSetting = properties.element("databasesetting");
            DataSourceInfo dataSourceInfo = new DataSourceInfo();
            dataSourceInfo.setDriver(databaseSetting.element("driver").getText());
            dataSourceInfo.setDatabase(databaseSetting.element("database").getText());
            dataSourceInfo.setUrl(databaseSetting.element("host").getText());
            dataSourceInfo.setPort(databaseSetting.element("port").getText());
            dataSourceInfo.setUser(databaseSetting.element("user").getText());
            dataSourceInfo.setPassword(databaseSetting.element("password").getText());
            String otherSetting = "?serverTimezone=" + databaseSetting.element("serverTimezone").getText() + "&useUnicode=" + databaseSetting.element("useUnicode").getText()
                    + "&characterEncoding=" + databaseSetting.element("characterEncoding").getText();
            dataSourceInfo.setOtherSetting(otherSetting);
            return dataSourceInfo;
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

}
