package com.yhy.test.service;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import sc.yhy.annotation.annot.Autowired;
import sc.yhy.annotation.annot.Service;
import sc.yhy.annotation.annot.Transaction;
import sc.yhy.annotation.annot.Value;
import sc.yhy.data.nosql.mongo.MongoDB;
import sc.yhy.data.nosql.mongo.MongoRepository;
import sc.yhy.data.sql.DataBase;
import sc.yhy.util.Util;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.yhy.test.dao.TranDao;

@Service
@Transaction
public class TranService {
	@Autowired
	private TranDao tranDao;

	@Value("${sc.annot.init.2}")
	private String stc2;

	public void saveTest() throws Exception {
		tranDao.saveTest();
		DataBase.close();
	}

	public String saveTestMongo()  throws Exception{
		tranDao.saveTest();
		DataBase.close();
		MongoRepository repository = MongoDB.INSTANCE.newInstance().setDataBase("test_db1").setCollection("testusers");
		String uuid = Util.uuidOne();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uuid);
		map.put("uname", "李四");
		map.put("age", "23");
		//repository.insert(map);
		Bson bson = Filters.and(Filters.regex("uname", "李四.*"));
		FindIterable<Document> findIterable = repository.listDocuments(bson).limit(10).skip(0);
		StringBuffer json = new StringBuffer("[");
		for (Document doc : findIterable) {
			json.append(doc.toJson()).append(",");
		}
		json.delete(json.length() - 1, json.length());
		json.append("]");
		return json.toString();
	}
}
