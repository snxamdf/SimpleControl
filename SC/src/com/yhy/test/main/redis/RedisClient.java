package com.yhy.test.main.redis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import sc.yhy.util.RedisUtil;

public class RedisClient {
	// private Jedis jedis;// 非切片额客户端连接
	// private JedisPool jedisPool;// 非切片连接池
	private ShardedJedis shardedJedis;// 切片额客户端连接
	private ShardedJedisPool shardedJedisPool;// 切片连接池
	private JedisPoolConfig config;

	public RedisClient() {
		// initialPool();
		initialShardedPool();
		shardedJedis = shardedJedisPool.getResource();
		// jedis = jedisPool.getResource();

	}

	/**
	 * 初始化非切片池
	 */
	void initialPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(20);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(1000l);//
		config.setTestOnBorrow(false);

		// jedisPool = new JedisPool(config, "127.0.0.1", 6379);
	}

	/**
	 * 初始化切片池
	 */
	void initialShardedPool() {
		// 池基本配置
		config = new JedisPoolConfig();
		config.setMaxTotal(20);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(1000l);
		config.setTestOnBorrow(false);

		// slave链接
		JedisShardInfo shardInfo = null;
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();

		shardInfo = new JedisShardInfo("127.0.0.1", 6379, "master");
		shardInfo.setPassword("foobared");
		shards.add(shardInfo);
		// shardInfo = new JedisShardInfo("127.0.0.1", 6380, "slave1");
		// shardInfo.setPassword("foobared");
		// shards.add(shardInfo);
		// shardInfo = new JedisShardInfo("127.0.0.1", 6381, "slave2");
		// shardInfo.setPassword("foobared");
		// shards.add(shardInfo);

		// 构造池
		shardedJedisPool = new ShardedJedisPool(config, shards);
	}

	public void show() {
		KeyOperate();
		// jedisPool.returnResource(jedis);
		shardedJedisPool.returnResource(shardedJedis);
	}

	void KeyOperate() {
		System.out.println("======================key==========================");
		// 清空数据
		// System.out.println("清空库中所有数据：" + shardedJedis.flushDB());
		// 判断key否存在
		System.out.println("判断key999键是否存在：" + shardedJedis.exists("key999"));
		System.out.println("新增key001,value001键值对：" + shardedJedis.set("key001", "value001"));
		System.out.println("判断key001是否存在：" + shardedJedis.exists("key001"));
		// 输出系统中所有的key
		System.out.println("新增key002,value002键值对：" + shardedJedis.set("key002", "value002"));
		System.out.println("系统中所有键如下：");
		Set<String> keys = shardedJedis.hkeys("*");
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			System.out.println(key);
		}
		// 删除某个key,若key不存在，则忽略该命令。
		System.out.println("系统中删除key002: " + shardedJedis.del("key002"));
		System.out.println("判断key002是否存在：" + shardedJedis.exists("key002"));
		// 设置 key001的过期时间
		System.out.println("设置 key001的过期时间为5秒:" + shardedJedis.expire("key001", 5));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// 查看某个key的剩余生存时间,单位【秒】.永久生存或者不存在的都返回-1
		System.out.println("查看key001的剩余生存时间：" + shardedJedis.ttl("key001"));
		// 移除某个key的生存时间
		System.out.println("移除key001的生存时间：" + shardedJedis.persist("key001"));
		System.out.println("查看key001的剩余生存时间：" + shardedJedis.ttl("key001"));
		// 查看key所储存的值的类型
		System.out.println("查看key所储存的值的类型：" + shardedJedis.type("key001"));

		System.out.println(shardedJedis.set("username", "杨红岩"));
		/*
		 * 一些其他方法：1、修改键名：jedis.rename("key6", "key0");
		 * 2、将当前db的key移动到给定的db当中：jedis.move("foo", 1)
		 */
	}

	public static void main(String[] args) {
		// RedisClient rc = new RedisClient();
		// rc.show();
		// Set<HostAndPort> shap = new HashSet<HostAndPort>();
		// shap.add(new HostAndPort("127.0.0.1", 7000));
		//
		// jedisCluster = new JedisCluster(shap);
		// System.out.println(jedisCluster.get("key001"));
		RedisUtil ru = new RedisUtil("127.0.0.1", 6379, "master", "foobared");
		ru.set("username", "杨红岩11");
		System.out.println(ru.get("username"));
		System.out.println(ru.del("username"));
		System.out.println(ru.get("username"));
		System.out.println(ru.append("username", "123"));
		System.out.println(ru.get("username"));
		ru.sadd("ac", "dd", "bb");
		System.out.println(ru.smembers("ac"));
	}
}
