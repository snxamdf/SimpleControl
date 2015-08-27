package sc.yhy.data.nosql.redis.factory;

import redis.clients.jedis.JedisCluster;
import sc.yhy.data.nosql.redis.RedisCluster;
import sc.yhy.data.nosql.redis.impl.RedisClusterImpl;

/**
 * Redis 创建Cluster实例抽象工厂
 * 
 * @author YHY
 *
 */
public class RedisClusterFactory extends RedisAbstractFactory {
	private JedisCluster cluster;

	public RedisClusterFactory(JedisCluster cluster) {
		this.cluster = cluster;
	}

	public RedisCluster newInstance() {
		return new RedisClusterImpl(cluster);
	}

	public String auth(String password) {
		return cluster.auth(password);
	}
}
