package com.yhy.test.service;

import org.bson.Document;

import sc.yhy.annotation.annot.Autowired;
import sc.yhy.annotation.annot.Service;
import sc.yhy.annotation.annot.Transaction;
import sc.yhy.annotation.annot.Value;
import sc.yhy.data.nosql.MongoDB;
import sc.yhy.data.nosql.MongoRepository;
import sc.yhy.data.sql.DataBase;
import sc.yhy.util.Util;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.yhy.test.dao.TranDao;
import com.yhy.test.entity.Tran;

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

	public String saveTestMongo() {
		MongoRepository repository = MongoDB.newInstance().setDataBase("test_db1").setCollection("testusers");
		Tran entity = new Tran();
		entity.setUid(Util.uuidOne());
		entity.setUname("张三" + Math.random());
		repository.insert(entity);
		FindIterable<Document> findIterable = repository.listDocuments(Filters.eq("uid", "c67db65f05af4c95b68458abc4d24ea5"));
		for (Document doc : findIterable) {
			return doc.toJson();
		}
		return null;
	}
}
