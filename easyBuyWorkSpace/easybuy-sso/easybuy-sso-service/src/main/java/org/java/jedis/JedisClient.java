package org.java.jedis;
/**
 * 定义好的接口
 * @author yan
 *
 */
public interface JedisClient {
	/** 添加字符串. */
	String set(String key, String value);
	/** 取出字符串. */
	String get(String key);
	/** 判断key是否在redis中存在. */
	Boolean exists(String key);
	/** 设置过期时间. */
	Long expire(String key, int seconds);
	/** 查询剩余时间. */
	Long ttl(String key);
	/** 自增. */
	Long incr(String key);
	/** hash. */
	Long hset(String key, String field, String value);
	/** hash取出. */
	String hget(String key, String field);
	/** hash中的删除. */
	Long hdel(String key, String... field);
}