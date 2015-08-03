package sc.yhy.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

class ConnectionBase {
	static final Logger logfile = Logger.getLogger(ConnectionBase.class.getName());
	private Connection conn = null;
	Statement st = null;
	PreparedStatement pps = null;
	ResultSet rs = null;
	boolean isAutoCommit = false;

	// 加载驱动
	static {
		try {
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logfile.log(Level.FINE, e.getMessage());
		}
	}

	/**
	 * ************** 初始化连接
	 *
	 * @throws java.sql.SQLException
	 */
	void initConn() throws SQLException {
		try {
			if (conn == null) {
				conn = DriverManager.getConnection("proxool.mysqldb");
			}
			conn.setAutoCommit(isAutoCommit);
		} catch (SQLException e) {
			logfile.log(Level.FINE, e.getMessage());
			throw e;
		}
	}

	/**
	 * 获取数据库连接
	 *
	 * @return
	 * @throws SQLException
	 */
	Connection getConnection() throws SQLException {
		if (conn == null) {
			initConn();
		}
		return conn;
	}

	/**
	 * 提交
	 *
	 * @throws java.sql.SQLException
	 */
	void commit() throws SQLException {
		if (conn != null) {
			conn.commit();
		}
	}

	/**
	 * 回滚
	 *
	 * @throws java.sql.SQLException
	 */
	void rollback() throws SQLException {
		if (conn != null) {
			conn.rollback();
		}
	}

	/**
	 * 回滚
	 *
	 * @throws java.sql.SQLException
	 */
	void rollback(Savepoint savepoint) throws SQLException {
		if (conn != null) {
			conn.rollback(savepoint);
		}
	}

	/**
	 * 关闭数据库各种资源Connection Statement PreparedStatement ResultSet的方法
	 */
	void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logfile.log(Level.FINE, e.getMessage());
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				logfile.log(Level.FINE, e.getMessage());
			}
		}
		if (pps != null) {
			try {
				pps.close();
			} catch (SQLException e) {
				logfile.log(Level.FINE, e.getMessage());
			}
		}
		try {
			if (conn != null && !conn.isClosed()) {
				try {
					conn.close();
					logfile.info("数据库对像" + conn + "关闭成功");
				} catch (SQLException e) {
					logfile.log(Level.FINE, e.getMessage());
				}
			}
		} catch (SQLException e) {
			logfile.log(Level.FINE, e.getMessage());
		}
	}

	PreparedStatement prepareStatement(String sql) throws SQLException {
		// pps = this.getConnection().prepareStatement(sql);
		return this.getConnection().prepareStatement(sql);
	}
}
