package sc.yhy.annotation.transaction;

import java.sql.SQLException;

import sc.yhy.data.DataBase;

public class ScTransaction {
	public void commit() throws SQLException {
		DataBase.commit();
	}

	public void rollback() throws SQLException {
		DataBase.rollback();
	}
}
