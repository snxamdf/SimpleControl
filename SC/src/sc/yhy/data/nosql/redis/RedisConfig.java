package sc.yhy.data.nosql.redis;

import sc.yhy.annotation.annot.Value;

/**
 * Redis 配置文件加载类
 * 
 * @author YHY
 *
 */
public class RedisConfig {
	@Value("${sc.redis.ip}")
	static String ip;
	@Value("${sc.redis.prot}")
	static String prot;
	@Value("${sc.redis.name}")
	static String name;
	@Value("${sc.redis.password}")
	static String password;
}
