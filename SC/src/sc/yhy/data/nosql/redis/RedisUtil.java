package sc.yhy.data.nosql.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import sc.yhy.annotation.annot.Value;

public class RedisUtil {
	@Value("${sc.redis.ip}")
	private static String ip;
	@Value("${sc.redis.prot}")
	private static String prot;
	@Value("${sc.redis.name}")
	private static String name;
	@Value("${sc.redis.password}")
	private static String password;

	private static Redis redis = null;

	public static Redis newInstance() {
		if (redis == null) {
			ShardedJedisPool pool;
			JedisPoolConfig config = new JedisPoolConfig();
			// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
			// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
			config.setMaxTotal(500);
			// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
			config.setMaxIdle(5);
			// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
			config.setMaxWaitMillis(1000 * 100);
			// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(true);

			JedisShardInfo shardInfo = null;
			List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
			String[] ips = ip.split(",");
			String[] prots = prot.split(",");
			String[] names = name.split(",");
			String[] passwords = password.split(",");
			for (int i = 0; i < ips.length; i++) {
				shardInfo = new JedisShardInfo(ips[i], Integer.valueOf(prots[i]), names[i]);
				if (passwords.length > i && passwords[i] != null && !"".equals(passwords[i])) {
					shardInfo.setPassword(passwords[i]);
				}
				shards.add(shardInfo);
			}
			// 构造池
			pool = new ShardedJedisPool(config, shards);
			redis = new Redis(pool);
		}
		return redis;
	}
}
