package sc.yhy.data.nosql.redis.impl;

import redis.clients.jedis.JedisCluster;
import sc.yhy.data.nosql.redis.RedisCluster;

/**
 * Redis Cluster操作实现类
 * 
 * @author YHY
 *
 */
public class RedisClusterImpl implements RedisCluster {
	private JedisCluster cluster;

	public RedisClusterImpl() {
	}

	public RedisClusterImpl(JedisCluster cluster) {
		this.cluster = cluster;
	}

	@Override
	public String get(String key) {
		return cluster.get(key);
	}

	@Override
	public String set(String key, String value) {
		return cluster.set(key, value);
	}

	@Override
	public Long append(String key, String value) {
		return cluster.append(key, value);
	}

	@Override
	public Long del(String key) {
		return cluster.del(key);
	}

	@Override
	public Boolean exists(String key) {
		return cluster.exists(key);
	}

	@Override
	public String save() {
		return cluster.save();
	}

}
