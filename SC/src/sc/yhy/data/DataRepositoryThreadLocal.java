package sc.yhy.data;

import java.sql.SQLException;

class DataRepositoryThreadLocal {
	private static ThreadLocal<ConnectionBase> threadLocal = new ThreadLocal<ConnectionBase>();

	public static ConnectionBase getConnection() throws SQLException {
		ConnectionBase connectionBase = threadLocal.get();
		if (connectionBase == null) {
			connectionBase = new ConnectionBase();
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
