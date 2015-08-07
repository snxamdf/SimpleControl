package com.yhy.test.main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.cglib.beans.BeanMap;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;

public class MainTest {
	// 初始化
	static BeanMap beanMap = null;

	public static void main(String[] args) throws Exception {
		Mongodb.mongo();
	}

}

class Mongodb {
	private static MongoClient m;
	static {
		m = new MongoClient("localhost:27017");
	}

	public static void mongo() {
		ServerAddress sa = new ServerAddress("localhost", 27017);
		List<MongoCredential> mongoCredentialList = new ArrayList<MongoCredential>();
		mongoCredentialList.add(MongoCredential.createMongoCRCredential("admin", "test_db", "admin".toCharArray()));

		m = new MongoClient(sa, mongoCredentialList);

		// 获得db
		MongoDatabase md = m.getDatabase("test_db");

		// 获得集合
		MongoCollection<Document> mongoCollection = md.getCollection("testusers");
		insert(mongoCollection);
		listAllDocuments(mongoCollection);
		updateAllDocument(mongoCollection);
		listAllDocuments(mongoCollection);
		deleteMany(mongoCollection);
	}

	public static void deleteOne(MongoCollection<Document> collection) {
		System.out.println("delete one records age less than 24");
		collection.deleteOne(Filters.lt("age", 24));
	}

	public static void deleteMany(MongoCollection<Document> collection) {
		System.out.println("delete all records age less than 24");
		collection.deleteMany(Filters.lt("age", 24));
	}

	public static void updateOneDocument(MongoCollection<Document> collection) {
		System.out.println("updateDocument : update one records that named 'dreamoftch' to 'ZhangSan'");
		collection.updateOne(Filters.eq("name", "dreamoftch"), new Document("$set", new Document("name", "ZhangSan")));
	}

	public static void updateAllDocument(MongoCollection<Document> collection) {
		System.out.println("updateDocument : update all records that named 'dreamoftch' to 'ZhangSan'");
		collection.updateMany(Filters.eq("name", "dreamoftch"), new Document("$set", new Document("name", "ZhangSan")));
	}

	public static void listDatabases(MongoClient mongo) {
		// list all databases
		MongoIterable<String> allDatabases = mongo.listDatabaseNames();
		for (String db : allDatabases) {
			System.out.println("Database name: " + db);
		}
	}

	public static void listCollections(MongoDatabase database) {
		// list all databases
		MongoIterable<String> allCollections = database.listCollectionNames();
		for (String collection : allCollections) {
			System.out.println("Collection name: " + collection);
		}
	}

	public static void listAllDocuments(MongoCollection<Document> collection) {
		System.out.println("begin get all document >>>>>>");
		for (Document document : collection.find()) {
			System.out.println(document);
		}
		System.out.println("finish get all document >>>>>>");
	}

	public static void listAllSpecifiedDocumentFields(MongoCollection<Document> collection) {
		System.out.println("begin get all document(exclude '_id') >>>>>>");
		for (Document document : collection.find().projection(Projections.exclude("_id"))) {
			System.out.println(document);
		}
		System.out.println("finish get all document(exclude '_id') >>>>>>");
	}

	public static void insert(MongoCollection<Document> collection) {
		List<Document> documents = new ArrayList<Document>();
		for (int i = 0; i < 10; i++) {
			documents.add(new Document("name", "dreamoftch").append("age", (20 + i)).append("createdDate", new Date()));
		}
		collection.insertMany(documents);
	}

	public static void listDocumentWithFilter(MongoCollection<Document> collection) {
		System.out.println("begin get document(name: dreamoftch, age > 25) >>>>>>");
		for (Document document : collection.find(Filters.and(Filters.eq("name", "dreamoftch"), Filters.gt("age", 25)))) {
			System.out.println(document);
		}
		System.out.println("finish get document(name: dreamoftch, age > 25) >>>>>>");
	}

	public static void listDocumentWithFilterAndInReverseOrder(MongoCollection<Document> collection) {
		System.out.println("begin get document(name: dreamoftch, age > 25) >>>>>>");
		for (Document document : collection.find(Filters.and(Filters.eq("name", "dreamoftch"), Filters.gt("age", 25))).sort(Sorts.descending("age"))) {
			System.out.println(document);
		}
		System.out.println("finish get document(name: dreamoftch, age > 25) >>>>>>");
	}
}

class Test implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 500; i++) {
			try {
				pring();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void pring() throws Exception {
		URL url = new URL("http://localhost:8080/SC/tran/test.action");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("GET");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.connect();
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		String content = "key=j0r53nmbbd78x7m1pqml06u2&type=1&toemail=jiucool@gmail.com" + "&activatecode=" + URLEncoder.encode("久酷博客", "utf-8");
		out.writeBytes(content);
		out.flush();
		out.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
		String line = "";

		while ((line = reader.readLine()) != null) {
			// line = new String(line.getBytes(), "utf-8");
			// System.out.println(line);
		}
		System.out.println(line);
		reader.close();
		connection.disconnect();
	}
}
