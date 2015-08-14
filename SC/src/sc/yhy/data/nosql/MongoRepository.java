package sc.yhy.data.nosql;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.cglib.beans.BeanMap;

import org.bson.Document;
import org.bson.conversions.Bson;

import sc.yhy.annotation.annot.Column;
import sc.yhy.util.ReflectUtil;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class MongoRepository {
	private ServerAddress serverAddress;
	private List<MongoCredential> mongoCredentialList;
	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	private MongoCollection<Document> collection;

	public MongoRepository() {
	}

	public MongoRepository(ServerAddress serverAddress, List<MongoCredential> mongoCredentialList) {
		this.serverAddress = serverAddress;
		this.mongoCredentialList = mongoCredentialList;
	}
	/**
	 * new MongoClient
	 */
	void init() {
		if (this.mongoClient == null) {
			if (mongoCredentialList != null) {
				this.mongoClient = new MongoClient(serverAddress, mongoCredentialList);
			} else {
				this.mongoClient = new MongoClient(serverAddress);
			}
		}
	}

	void close() {
		if (this.mongoClient != null) {
			this.mongoClient.close();
			this.mongoClient = null;
		}
	}

	public MongoCollection<Document> getCollection() {
		return collection;
	}

	/**
	 * 获取集合 表
	 * 
	 * @param collection
	 * @return MongoCollection<Document>
	 * @author YHY
	 */
	public MongoRepository setDataBaseAndCollection(String dataBase, String collection) {
		this.setDataBase(dataBase);
		this.setCollection(collection);
		return this;
	}

	/**
	 * 集合 表
	 * 
	 * @param collection
	 * @return MongoCollection<Document>
	 * @author YHY
	 */
	public MongoRepository setCollection(String collection) {
		this.collection = mongoDatabase.getCollection(collection);
		return this;
	}

	/**
	 * 数据库 db
	 * 
	 * @param dataBase
	 * @return
	 */
	public MongoRepository setDataBase(String dataBase) {
		mongoDatabase = mongoClient.getDatabase(dataBase);
		return this;
	}

	public Map<String, Object> toMap(Object entity) {
		BeanMap beanMap = BeanMap.create(entity);
		Class<?> clases = entity.getClass();
		Field[] fields = clases.getDeclaredFields();
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

	public void deleteOne(Bson bson) {
		collection.deleteOne(bson);
	}

	public void deleteMany(Bson bson) {
		collection.deleteMany(bson);
	}

	public void updateOneDocument(Bson bson, Document document) {
		collection.updateOne(bson, document);
	}

	public void updateAllDocument(Bson bson, Document document) {
		collection.updateMany(bson, document);
	}

	public FindIterable<Document> listDocuments() {
		return collection.find();
	}

	public FindIterable<Document> listDocuments(Bson bson) {
		return collection.find(bson);
	}

	public FindIterable<Document> listDocuments(Bson bson, String sort) {
		return collection.find(bson).sort(Sorts.descending(sort));
	}

	public void listAllSpecifiedDocumentFields() {
		for (Document document : collection.find().projection(Projections.exclude("_id"))) {
			System.out.println(document);
		}
	}

	void listDatabases(MongoClient mongo) {
		MongoIterable<String> allDatabases = mongo.listDatabaseNames();
		for (String db : allDatabases) {
			System.out.println("Database name: " + db);
		}
	}

	void listCollections(MongoDatabase database) {
		MongoIterable<String> allCollections = database.listCollectionNames();
		for (String collection : allCollections) {
			System.out.println("Collection name: " + collection);
		}
	}
}
