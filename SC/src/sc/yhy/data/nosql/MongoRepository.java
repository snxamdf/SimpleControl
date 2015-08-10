package sc.yhy.data.nosql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class MongoRepository {
	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;

	public MongoRepository() {
	}

	public MongoRepository(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	/**
	 * 获取集合 表
	 * 
	 * @param collection
	 * @return MongoCollection<Document>
	 * @author YHY
	 */
	public MongoCollection<Document> getCollection(String dataBase, String collection) {
		this.getDataBase(dataBase);
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
		return mongoCollection;
	}

	/**
	 * 获取集合 表
	 * 
	 * @param collection
	 * @return MongoCollection<Document>
	 * @author YHY
	 */
	public MongoCollection<Document> getCollection(String collection) {
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
		return mongoCollection;
	}

	/**
	 * 获得数据库
	 * 
	 * @param dataBase
	 * @return
	 */
	public MongoRepository getDataBase(String dataBase) {
		mongoDatabase = mongoClient.getDatabase(dataBase);
		return this;
	}

	public void deleteOne(MongoCollection<Document> collection) {
		collection.deleteOne(Filters.lt("age", 24));
	}

	public void deleteMany(MongoCollection<Document> collection) {
		collection.deleteMany(Filters.lt("age", 24));
	}

	public void updateOneDocument(MongoCollection<Document> collection) {
		collection.updateOne(Filters.eq("name", "dreamoftch"), new Document("$set", new Document("name", "ZhangSan")));
	}

	public void updateAllDocument(MongoCollection<Document> collection) {
		collection.updateMany(Filters.eq("name", "dreamoftch"), new Document("$set", new Document("name", "ZhangSan")));
	}

	public void listDatabases(MongoClient mongo) {
		MongoIterable<String> allDatabases = mongo.listDatabaseNames();
		for (String db : allDatabases) {
			System.out.println("Database name: " + db);
		}
	}

	public void listCollections(MongoDatabase database) {
		MongoIterable<String> allCollections = database.listCollectionNames();
		for (String collection : allCollections) {
			System.out.println("Collection name: " + collection);
		}
	}

	public void listAllDocuments(MongoCollection<Document> collection) {
		for (Document document : collection.find()) {
			System.out.println(document);
		}
	}

	public void listAllSpecifiedDocumentFields(MongoCollection<Document> collection) {
		for (Document document : collection.find().projection(Projections.exclude("_id"))) {
			System.out.println(document);
		}
	}

	public void insert(MongoCollection<Document> collection) {
		List<Document> documents = new ArrayList<Document>();
		for (int i = 0; i < 10; i++) {
			documents.add(new Document("name", "dreamoftch").append("age", (20 + i)).append("createdDate", new Date()));
		}
		collection.insertMany(documents);
	}

	public void listDocumentWithFilter(MongoCollection<Document> collection) {
		for (Document document : collection.find(Filters.and(Filters.eq("name", "dreamoftch"), Filters.gt("age", 25)))) {
			System.out.println(document);
		}
	}

	public void listDocumentWithFilterAndInReverseOrder(MongoCollection<Document> collection) {
		for (Document document : collection.find(Filters.and(Filters.eq("name", "dreamoftch"), Filters.gt("age", 25))).sort(Sorts.descending("age"))) {
			System.out.println(document);
		}
	}
}
