package com.lhl.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author lhl
 * @version 1.0 2010/10/23
 * @description 
 *
 */
public class ConnManage {
	
	/**
	 * 日志处理对象
	 */
	private static final Log logger = LogFactory.getLog(ConnManage.class);
	
	/**
	 * 唯一实例
	 */
	private static ConnManage instance;

	/**
	 * 最大连接数
	 */
	private int maxConn = 20;
	
	/**
	 * 当前连接数
	 */
	private int curConn = 0;
	
	/**
	 * 数据库驱动
	 */
	private String driver;
	
	/**
	 * 数据库连接
	 */
	private String url;
	
	/**
	 * 数据库用户名
	 */
	private String username;
	
	/**
	 * 数据库密码
	 */
	private String password;
	
	/**
	 * 空闲的连接
	 */
	private Vector<Connection> freeConns = new Vector<Connection>();
	
	
	public ConnManage(){
		ResourceBundle rb = ResourceBundle.getBundle("conf");
		driver = rb.getString("driver");
		url = rb.getString("url");
		username = rb.getString("username");
		password = rb.getString("password");
		String maxConNum = rb.getString("maxconn");
		if(maxConNum != null && maxConNum.matches("[0-9]+")){
			maxConn = Integer.parseInt(maxConNum);
		}
	}

	/**
	 * 构造实例
	 * @return
	 */
	public synchronized static ConnManage getInstance() {
		if (instance == null) {
			instance = new ConnManage();
		}
		return instance;
	}

	/**
	 * 获得一个连接，如果没有空闲连接并且当前连接数小于最大连接数，就创建一个连接
	 * @return
	 */
	public synchronized Connection getConn() {
		Connection conn = null;
		if (freeConns.size() > 0) {
			// 得到空闲连接里面第一个连接
			conn = freeConns.firstElement();
			freeConns.removeElementAt(0);
			try {
				// 如果得到的这个连接失效了，就释放掉他，然后重新递归的调用本方法
				if (conn == null || conn.isClosed()) {
					conn = getConn();
				}
			} catch (SQLException e) {
				logger.error("获取数据库链接出错...");
			}
			// 如果现在没有空闲连接，并且当前连接数小于最大连接数，就新创建一个连接
		} else if (curConn <= maxConn) {
			conn = createConn();
		} 
		if (conn != null) {
			curConn++;
		}
		return conn;
	}

	/**
	 * 创建新的连接
	 */
	private Connection createConn() {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = (Connection) DriverManager.getConnection(url, username,
					password);
		} catch (ClassNotFoundException e) {
			logger.error("没有发现数据库驱动类...");
		} catch (SQLException e) {
			logger.error("连接数据库时出错...");
		}
		return conn;
	}

	/**
	 * 释放一个连接
	 */
	public synchronized void releaseConn(Connection conn) {
		freeConns.addElement(conn);
		curConn--;
		notifyAll();
	}

	/**
	 * 关闭所有连接
	 */
	public synchronized void releaseAllConns() {
		Enumeration<Connection> conns = freeConns.elements();
		while (conns.hasMoreElements()) {
			Connection conn = conns.nextElement();
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("关闭数据库连接时出错...");
			}
		}
		// 除去空闲连接当中的所有连接
		freeConns.removeAllElements();
	}
}