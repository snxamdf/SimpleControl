package sc.yhy.data;

import java.sql.SQLException;
/**
 * @time 2015-07-29
 * @author YHY
 *
 */
public class DataBase<T> {
	private static ThreadLocal<Connect<?>> oracleConnHolder = new ThreadLocal<Connect<?>>();
	private static ThreadLocal<Connect<?>> mySqlconnHolder = new ThreadLocal<Connect<?>>();

	public Connect<T> get() {
		return null;
	}

	public static <T> Connect<T> getOracleConnection() {
		@SuppressWarnings("unchecked")
		Connect<T> conn = ((Connect<T>) oracleConnHolder.get());
		if (conn == null) {
			try {
				conn = new OracleConnection<>();
				oracleConnHolder.set(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	public static <T> Connect<T> getMySqlConnection() {
		@SuppressWarnings("unchecked")
		Connect<T> conn = ((Connect<T>) mySqlconnHolder.get());
		if (conn == null) {
			try {
				conn = new MySqlConnection<>();
				mySqlconnHolder.set(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	public static void closeConnection() {
		Connect<?> oracleConn = oracleConnHolder.get();
		if (oracleConn != null) {
			oracleConn.close();
			oracleConnHolder.remove();
		}
		Connect<?> mySqlConn = mySqlconnHolder.get();
		if (mySqlConn != null) {
			mySqlConn.close();
			mySqlconnHolder.remove();
		}
	}

	public static void commit() throws SQLException {
		Connect<?> oracleConn = oracleConnHolder.get();
		if (oracleConn != null) {
			oracleConn.commit();
		}
		Connect<?> mySqlConn = mySqlconnHolder.get();
		if (mySqlConn != null) {
			mySqlConn.commit();
		}
	}

	public static void rollback() throws SQLException {
		Connect<?> oracleConn = oracleConnHolder.get();
		if (oracleConn != null) {
			oracleConn.rollback();
		}
		Connect<?> mySqlConn = mySqlconnHolder.get();
		if (mySqlConn != null) {
			mySqlConn.rollback();
		}
	}
}
