package sc.yhy.test.dao;

import java.sql.SQLException;
import java.util.Map;

import sc.yhy.data.Connect;
import sc.yhy.data.DataBase;
import sc.yhy.test.entity.TestBean;

public class TestDao {
	public void print() {
		Connect<TestBean> conn = DataBase.getMySqlConnection();
		try {
			Map<String, Object> map = conn.getOneRow("select * from tab_name");
			System.out.println(map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(conn);
		System.out.println("输出");
	}
}
