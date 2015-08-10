package com.yhy.test.service;

import org.bson.Document;

import sc.yhy.annotation.annot.Autowired;
import sc.yhy.annotation.annot.Service;
import sc.yhy.annotation.annot.Transaction;
import sc.yhy.annotation.annot.Value;
import sc.yhy.data.nosql.MongoDB;
import sc.yhy.data.nosql.MongoRepository;
import sc.yhy.data.sql.DataBase;

import com.mongodb.client.MongoCollection;
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

	public void saveTestMongo() {
		MongoRepository repository = MongoDB.newInstance().setDataBase("test_db1").setCollection("testusers");
		repository.listAllSpecifiedDocumentFields();
		// mongoCollection.deleteMany(Filters.eq("name", "ZhangSan"));
	}
}
