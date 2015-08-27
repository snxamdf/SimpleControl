package com.yhy.test.main;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import sc.yhy.data.nosql.redis.impl.RedisShardedImpl;

public class MainTest {

	public static void main(String[] args) throws Exception {
		try {
			RedisShardedImpl redisShardedImpl = null;
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

			List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
			shards.add(new JedisShardInfo("192.168.121.100", 7000, "master7000"));
			shards.add(new JedisShardInfo("192.168.121.100", 7001, "master7001"));
			shards.add(new JedisShardInfo("192.168.121.100", 7002, "master7002"));
			shards.add(new JedisShardInfo("192.168.121.100", 7003, "slave7003"));
			shards.add(new JedisShardInfo("192.168.121.100", 7004, "slave7004"));
			shards.add(new JedisShardInfo("192.168.121.100", 7005, "slave7005"));
			// 构造池
			pool = new ShardedJedisPool(config, shards);
			redisShardedImpl = new RedisShardedImpl(pool);
			System.out.println(redisShardedImpl.get("un"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
