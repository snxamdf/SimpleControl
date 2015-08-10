package sc.yhy.data.nosql;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.beans.BeanMap;

import org.bson.Document;

import sc.yhy.annotation.annot.Column;
import sc.yhy.util.ReflectUtil;

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
	private MongoCollection<Document> collection;

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
		this.collection = mongoDatabase.getCollection(collection);
		return this.collection;
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

	public Map<String, Object> toMap(Object entity) {
		BeanMap beanMap = BeanMap.create(entity);
		Class<?> clases = beanMap.getClass();
		Field[] fields = clases.getFields();
		Map<String, Object> map = new HashMap<String, Object>();
		for (Field field : fields) {
			String fieldName = field.getName();
			if (ReflectUtil.isAnnotation(field, Column.class)) {
				Object value = beanMap.get(fieldName);
				map.put(fieldName, value);
			}
		}
		return map;
	}

	public Document document(Map<String, Object> map) {
		Document doc = new Document(map);
		return doc;
	}

	public void insert(Object entity) {
		insert(document(toMap(entity)));
	}

	public void insert(Map<String, Object> map) {
		collection.insertOne(document(map));
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
