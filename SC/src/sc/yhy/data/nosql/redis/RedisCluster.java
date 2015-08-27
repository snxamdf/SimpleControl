package sc.yhy.data.nosql.redis;

/**
 * Redis Cluster接口
 * 
 * @author YHY
 *
 */
public interface RedisCluster extends Redis {
	public String save();
}
