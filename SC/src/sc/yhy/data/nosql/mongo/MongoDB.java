package sc.yhy.data.nosql.mongo;

import java.util.ArrayList;
import java.util.List;

import sc.yhy.core.Entrance;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoDB {

	private static MongoRepository mongoRepository;

	public void close() {
		if (mongoRepository != null) {
			// mongoRepository.close();
		}
	}

	public static MongoRepository newInstance() {
		boolean auth = Boolean.parseBoolean(MongoConfig.auth);
		if (auth) {
			if (mongoRepository == null) {
				String[] dbs = MongoConfig.dataBaseNames.split(",");
				List<MongoCredential> mongoCredentialList = new ArrayList<MongoCredential>();
				for (String db : dbs) {
					String name = Entrance.getPropertie(MongoConfig.$_MONGOD + db + MongoConfig.NAME_$);
					String pass = Entrance.getPropertie(MongoConfig.$_MONGOD + db + MongoConfig.PASS_$);
					// 权限验证
					MongoCredential mongoCredential = MongoCredential.createMongoCRCredential(name, db, pass.toCharArray());
					mongoCredentialList.add(mongoCredential);
				}
				ServerAddress serverAddress = new ServerAddress(MongoConfig.host, Integer.parseInt(MongoConfig.port));
				// 获得MongoDBBaseRepository
				mongoRepository = new MongoRepository(serverAddress, mongoCredentialList);
			}
		} else if (!auth) {
			ServerAddress serverAddress = new ServerAddress(MongoConfig.host, Integer.parseInt(MongoConfig.port));
			// 获得MongoDBBaseRepository
			mongoRepository = new MongoRepository(serverAddress, null);
		}
		if (mongoRepository != null) {
			mongoRepository.init();
		}
		return mongoRepository;
	}
}
