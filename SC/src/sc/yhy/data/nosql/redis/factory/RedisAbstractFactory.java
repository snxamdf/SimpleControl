package sc.yhy.data.nosql.redis.factory;

import sc.yhy.data.nosql.redis.Redis;

/**
 * Redis抽象工厂
 * 
 * @author YHY
 *
 */
public abstract class RedisAbstractFactory {
	/**
	 * 创建对像
	 * 
	 * @return
	 */
	public abstract Redis newInstance();
}
