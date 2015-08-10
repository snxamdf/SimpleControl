package sc.yhy.data.nosql;

import java.util.ArrayList;
import java.util.List;

import sc.yhy.annotation.annot.Value;
import sc.yhy.core.Entrance;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoDB {
	@Value("${mongodb.database}")
	private static String dataBaseNames;
	@Value("${mongodb.host}")
	private static String host;
	@Value("${mongodb.port}")
	private static String port;
	@Value("${mongodb.auth}")
	private static String auth;

	private static String $_MONGOD = "mongodb.";
	private static final String NAME_$ = ".name";
	private static final String PASS_$ = ".pass";
	private static MongoRepository mongoRepository;

	public static MongoRepository newInstance() {
		if ("true".equals(auth)) {
			if (mongoRepository == null) {
				String[] dbs = dataBaseNames.split(",");
				List<MongoCredential> mongoCredentialList = new ArrayList<MongoCredential>();
				for (String db : dbs) {
					String name = Entrance.getPropertie($_MONGOD + db + NAME_$);
					String pass = Entrance.getPropertie($_MONGOD + db + PASS_$);
					//权限验证
					MongoCredential mongoCredential = MongoCredential.createMongoCRCredential(name, db, pass.toCharArray());
					mongoCredentialList.add(mongoCredential);
				}
				ServerAddress addr = new ServerAddress(host, Integer.parseInt(port));
				MongoClient mongoClient = new MongoClient(addr, mongoCredentialList);
				// 获得MongoDBBaseRepository
				mongoRepository = new MongoRepository(mongoClient);
			}
		} else if ("false".equals(auth)) {
			ServerAddress addr = new ServerAddress(host, Integer.parseInt(port));
			MongoClient mongoClient = new MongoClient(addr);
			// 获得MongoDBBaseRepository
			mongoRepository = new MongoRepository(mongoClient);
		}
		return mongoRepository;
	}
}