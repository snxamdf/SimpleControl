package sc.yhy.data.sql;

import java.sql.SQLException;

class DataRepositoryThreadLocal {
	private static ThreadLocal<ConnectionBase> threadLocal = new ThreadLocal<ConnectionBase>();

	/**
	 * 如果配置多个连接池 将随机连接一个数据库
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static ConnectionBase getConnection() throws SQLException {
		ConnectionBase connectionBase = threadLocal.get();
		if (connectionBase == null) {
			return getConnection(DataSourceType.getDataSourceAlias());
		}
		return connectionBase;
	}

	/**
	 * 指定连接池创建连接
	 * 
	 * @param alias
	 * @return
	 * @throws SQLException
	 */
	public static ConnectionBase getConnection(String alias) throws SQLException {
		ConnectionBase connectionBase = threadLocal.get();
		if (connectionBase == null) {
			connectionBase = new ConnectionBase(alias);
			threadLocal.set(connectionBase);
		}
		return threadLocal.get();
	}

	public static void removeConnection() {
		threadLocal.remove();
	}

	public static void closeConnection() throws SQLException {
		ConnectionBase connection = threadLocal.get();
		if (connection != null) {
			connection.close();
			removeConnection();
		}
	}

	public static void commit() throws SQLException {
		ConnectionBase connection = threadLocal.get();
		if (connection != null) {
			connection.commit();
		}
	}

	public static void rollback() throws SQLException {
		ConnectionBase connection = threadLocal.get();
		if (connection != null) {
			connection.rollback();
		}
	}
}
