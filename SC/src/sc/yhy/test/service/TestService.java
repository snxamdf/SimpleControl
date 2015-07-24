package sc.yhy.test.service;

import java.util.Map;

import sc.yhy.annotation.Autowired;
import sc.yhy.test.dao.TestDao;

public class TestService {
	@Autowired
	private TestDao testDao;

	public Map<String, Object> getStr() {
		return testDao.print();
	}
}
