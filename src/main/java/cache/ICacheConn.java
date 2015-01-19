package cache;

import java.util.Date;

/**
 * cache连接.
 * 
 * @author harry.zu
 * 
 */
public interface ICacheConn {
	/**
	 * cache引擎类型.
	 * 
	 * @author harry.zu
	 * 
	 */
	public static enum CacheEngineType {
		MEMCACHED, DATABASE, COHERENCE;
	}

	String CACHE_ENGINE = "engine";

	/**
	 * 添加�?个指定的值到缓存�?.
	 * 
	 * @param key
	 *            关键�?
	 * @param value
	 *            �?
	 * 
	 * @return 是否成功
	 */
	boolean add(final String key, final Object value);

	/**
	 * 添加�?个指定的值到缓存中，并设置过期时�?.
	 * 
	 * @param key
	 *            关键�?
	 * @param value
	 *            �?
	 * @param expiry
	 *            过期时间
	 * 
	 * @return 是否成功
	 */
	boolean add(final String key, final Object value, final Date expiry);

	/**
	 * 替换缓存中的某个�?.
	 * 
	 * @param key
	 *            关键�?
	 * @param value
	 *            �?
	 * 
	 * @return 是否成功
	 */
	boolean replace(final String key, final Object value);

	/**
	 * 替换缓存中的某个�?.
	 * 
	 * @param key
	 *            关键�?
	 * @param value
	 *            �?
	 * @param expiry
	 *            过期时间
	 * 
	 * @return 是否成功
	 */
	boolean replace(final String key, final Object value, final Date expiry);

	/**
	 * 删除缓存中的某个�?.
	 * 
	 * @param key
	 *            关键�?
	 * 
	 * @return 是否成功
	 */
	boolean delete(final String key);

	/**
	 * 在指定时间内删除缓存中的某个�?.
	 * 
	 * @param key
	 *            关键�?
	 * @param date
	 *            删除时间
	 * 
	 * @return 是否成功
	 */
	boolean delete(final String key, final Date date);

	/**
	 * 根据指定的关键字获取对象.
	 * 
	 * @param key
	 *            关键�?
	 * 
	 * @return �?
	 */
	Object get(final String key);

	/**
	 * 添加�?个指定的值到缓存中，如果已经存在，则覆写.
	 * 
	 * @param key
	 *            关键�?
	 * @param value
	 *            �?
	 * 
	 * @return 是否成功
	 */
	boolean set(final String key, final Object value);

	/**
	 * 添加�?个指定的值到缓存中，如果已经存在，则覆写，并设置过期时间.
	 * 
	 * @param key
	 *            关键�?
	 * @param value
	 *            �?
	 * @param expiry
	 *            过期时间
	 * 
	 * @return 是否成功
	 */
	boolean set(final String key, final Object value, final Date expiry);
}