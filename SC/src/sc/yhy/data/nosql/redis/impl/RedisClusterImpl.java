package sc.yhy.data.nosql.redis.impl;

import java.util.List;
import java.util.Map;

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

	@Override
	public String hmset(String key, Map<String, String> hash) {
		return cluster.hmset(key, hash);
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		return cluster.hmget(key, fields);
	}

	@Override
	public Long hlen(String key) {
		return cluster.hlen(key);
	}

	@Override
	public Long hdel(String key, String... fields) {
		return cluster.hdel(key, fields);
	}

	@Override
	public Map<String, String> hgetall(String key) {
		return cluster.hgetAll(key);
	}

	@Override
	public String rpop(String key) {
		return cluster.rpop(key);
	}

	@Override
	public Long rpush(String key, String... strs) {
		return cluster.rpush(key, strs);
	}

}
