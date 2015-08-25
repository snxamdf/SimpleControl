package sc.yhy.data.nosql.mongo;

import sc.yhy.annotation.annot.Value;

public class MongoConfig {
	@Value("${mongodb.database}")
	static String dataBaseNames;
	@Value("${mongodb.host}")
	static String host;
	@Value("${mongodb.port}")
	static String port;
	@Value("${mongodb.auth}")
	static String auth;

	final static String $_MONGOD = "mongodb.";
	final static String NAME_$ = ".name";
	final static String PASS_$ = ".pass";
}
