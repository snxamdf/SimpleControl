package sc.yhy.data.nosql.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import sc.yhy.data.nosql.redis.factory.RedisClusterFactory;
import sc.yhy.data.nosql.redis.factory.RedisShardedFactory;

/**
 * redis 操作工具类
 * 
 * @author YHY
 *
 */
public class RedisUtil {
	private static RedisSharded redisSharded = null;
	private static RedisCluster redisCluster = null;

	/**
	 * 创建集群操作实例
	 * 
	 * @return
	 */
	public static RedisCluster newClusterInstance() {
		if (redisCluster == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(500);
			config.setMaxIdle(5);
			config.setMaxWaitMillis(1000 * 100);
			config.setTestOnBorrow(true);

			String[] ips = RedisConfig.ip.split(",");
			String[] prots = RedisConfig.prot.split(",");
			String password = RedisConfig.password;

			Set<HostAndPort> nodes = new HashSet<HostAndPort>(ips.length);
			for (int i = 0; i < ips.length; i++) {
				nodes.add(new HostAndPort(ips[i], Integer.valueOf(prots[i])));
			}
			RedisClusterFactory redisClusterFactory = new RedisClusterFactory(new JedisCluster(nodes, config));
			redisCluster = redisClusterFactory.newInstance();
			if (password != null && !"".equals(password)) {
				String auth = redisClusterFactory.auth(password);
				System.out.println("--auth password = " + auth);
			}
		}
		return redisCluster;
	}

	/**
	 * 创建集群切片操作实例
	 * 
	 * @return
	 */
	public static RedisSharded newShardedInstance() {
		if (redisSharded == null) {
			try {
				ShardedJedisPool pool;
				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxTotal(500);
				config.setMaxIdle(5);
				config.setMaxWaitMillis(1000 * 100);
				config.setTestOnBorrow(true);

				JedisShardInfo shardInfo = null;
				String[] ips = RedisConfig.ip.split(",");
				String[] prots = RedisConfig.prot.split(",");
				String[] names = RedisConfig.name.split(",");
				String password = RedisConfig.password;
				List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>(ips.length);
				for (int i = 0; i < ips.length; i++) {
					shardInfo = new JedisShardInfo(ips[i], Integer.valueOf(prots[i]), names[i]);
					if (password != null && !"".equals(password)) {
						shardInfo.setPassword(password);
					}
					shards.add(shardInfo);
				}
				// 构造池
				pool = new ShardedJedisPool(config, shards);
				RedisShardedFactory redisShardedFactory = new RedisShardedFactory(pool);
				redisSharded = redisShardedFactory.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				redisSharded = null;
			}
		}
		return redisSharded;
	}
}
