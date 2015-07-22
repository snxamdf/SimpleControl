package sc.yhy.test.service;

import sc.yhy.annotation.Autowired;
import sc.yhy.test.dao.TestDao;

public class TestService {
	@Autowired
	private TestDao testDao;

	public String getStr() {
		testDao.print();
		return "杨红岩111111";
	}
}
