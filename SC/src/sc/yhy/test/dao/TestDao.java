package sc.yhy.test.dao;

import java.sql.SQLException;
import java.util.Map;

import sc.yhy.data.Connect;
import sc.yhy.data.DataBase;
import sc.yhy.test.entity.TestBean;

public class TestDao {
	public Map<String, Object> print() {
		Connect<TestBean> conn = DataBase.getMySqlConnection();
		try {
			Map<String, Object> map = conn.getOneRow("select * from tab_name");
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
