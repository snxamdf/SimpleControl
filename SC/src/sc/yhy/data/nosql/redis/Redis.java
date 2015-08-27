package sc.yhy.data.nosql.redis;

import java.util.List;
import java.util.Map;

/**
 * Redis 接口
 * 
 * @author YHY
 *
 */
public interface Redis {

	public String get(String key);

	public String set(String key, String value);

	public Long append(String key, String value);

	public Long del(String key);

	public Boolean exists(String key);

	public String hmset(String key, Map<String, String> hash);

	public List<String> hmget(String key, String... fields);

	public Long hlen(String key);

	public Long hdel(String key, String... fields);

	public Map<String, String> hgetall(String key);

}
