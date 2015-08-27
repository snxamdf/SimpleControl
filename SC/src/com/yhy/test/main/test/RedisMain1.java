package com.yhy.test.main.test;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class RedisMain1 {

	private static JedisCluster jc;

	public static void main(String[] args) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(500);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(1000 * 100);
		config.setTestOnBorrow(true);

		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.121.100", 7000));
		nodes.add(new HostAndPort("192.168.121.100", 7001));
		nodes.add(new HostAndPort("192.168.121.100", 7002));
		nodes.add(new HostAndPort("192.168.121.100", 7003));
		nodes.add(new HostAndPort("192.168.121.100", 7004));
		nodes.add(new HostAndPort("192.168.121.100", 7005));
		jc = new JedisCluster(nodes, config);
		
/*		long l1 = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			jc.set("abc" + i, String.valueOf(Math.random()));
		}
		long l2 = System.currentTimeMillis();
		System.out.println((l2 - l1) / 1000);*/
		
		System.out.println(jc.get("ab99999"));
	}

}
