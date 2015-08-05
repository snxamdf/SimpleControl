package sc.yhy.data;

import java.sql.SQLException;

/**
 * @time 2015-07-29
 * @author YHY
 *
 */
public class DataBase {

	public static void close() {
		try {
			DataRepositoryThreadLocal.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void commit() throws SQLException {
		DataRepositoryThreadLocal.commit();
	}

	public static void rollback() throws SQLException {
		DataRepositoryThreadLocal.rollback();
	}
}
