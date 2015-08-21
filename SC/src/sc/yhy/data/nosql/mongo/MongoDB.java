package sc.yhy.data.nosql.mongo;

import java.util.ArrayList;
import java.util.List;

import sc.yhy.annotation.annot.Value;
import sc.yhy.core.Entrance;

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

	public static void close() {
		if (mongoRepository != null)
			mongoRepository.close();
	}

	public static MongoRepository newInstance() {
		boolean auth = Boolean.parseBoolean(MongoDB.auth);
		if (auth) {
			if (mongoRepository == null) {
				String[] dbs = dataBaseNames.split(",");
				List<MongoCredential> mongoCredentialList = new ArrayList<MongoCredential>();
				for (String db : dbs) {
					String name = Entrance.getPropertie($_MONGOD + db + NAME_$);
					String pass = Entrance.getPropertie($_MONGOD + db + PASS_$);
					// 权限验证
					MongoCredential mongoCredential = MongoCredential.createMongoCRCredential(name, db, pass.toCharArray());
					mongoCredentialList.add(mongoCredential);
				}
				ServerAddress serverAddress = new ServerAddress(host, Integer.parseInt(port));
				// 获得MongoDBBaseRepository
				mongoRepository = new MongoRepository(serverAddress, mongoCredentialList);
			}
		} else if (!auth) {
			ServerAddress serverAddress = new ServerAddress(host, Integer.parseInt(port));
			// 获得MongoDBBaseRepository
			mongoRepository = new MongoRepository(serverAddress, null);
		}
		if (mongoRepository != null) {
			mongoRepository.init();
		}
		return mongoRepository;
	}
}
