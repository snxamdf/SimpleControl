package sc.yhy.data.nosql.redis;

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

}
