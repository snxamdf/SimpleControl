package sc.yhy.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.logging.Logger;

public abstract class BaseConnection<T> implements Connect<T> {
	static final Logger logfile = Logger.getLogger(AbstractConnect.class.getName());
	Connection conn = null;
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
		}
	}

	/**
	 * 获取数据库连接
	 *
	 * @return
	 */
	public Connection getConnection() {
		return this.conn;
	}

	/**
	 * 提交
	 *
	 * @throws java.sql.SQLException
	 */
	public void commit() throws SQLException {
		if (conn != null) {
			conn.commit();
		}
	}

	/**
	 * 回滚
	 *
	 * @throws java.sql.SQLException
	 */
	public void rollback() throws SQLException {
		if (conn != null) {
			conn.rollback();
		}
	}

	/**
	 * 回滚
	 *
	 * @throws java.sql.SQLException
	 */
	public void rollback(Savepoint savepoint) throws SQLException {
		if (conn != null) {
			conn.rollback(savepoint);
		}
	}

	/**
	 * 关闭数据库各种资源Connection Statement PreparedStatement ResultSet的方法
	 */
	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pps != null) {
			try {
				pps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			if (conn != null && !conn.isClosed()) {
				try {
					conn.close();
					logfile.info("数据库对像" + conn + "关闭成功");
				} catch (SQLException e) {
					e.printStackTrace();
					logfile.info("数据库对像" + conn + "关闭失败");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
