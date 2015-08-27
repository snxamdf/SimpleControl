package sc.yhy.data.nosql.redis.factory;

import redis.clients.jedis.ShardedJedisPool;
import sc.yhy.data.nosql.redis.RedisSharded;
import sc.yhy.data.nosql.redis.impl.RedisShardedImpl;

/**
 * Redis 创建Sharded实例抽象工厂
 * 
 * @author YHY
 *
 */
public class RedisShardedFactory extends RedisAbstractFactory {
	private ShardedJedisPool pool;

	public RedisShardedFactory(ShardedJedisPool pool) {
		this.pool = pool;
	}

	public RedisSharded newInstance() {
		return new RedisShardedImpl(pool);
	}
}
