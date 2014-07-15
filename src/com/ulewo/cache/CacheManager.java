package com.ulewo.cache;

/**
 * 缓存管理 整个系统数据全在内存缓存中，基本上从不从数据库读取
 * 
 * @author Glping
 * 
 */
public interface CacheManager {
	/**
	 * 缓存对象
	 * @param key
	 * @param value
	 * @return 
	 */
	void add(final String key, Object value);

	/**
	 * 从缓存中获取数据
	 * @param <T>
	 * @param key
	 * @return
	 */
	<T> T get(final String key);

	/**
	 * 删除缓存数据
	 * @param key
	 * @return true:删除成功;false：删除的数据不存在
	 */
	boolean remove(final String key, Object value);
}
