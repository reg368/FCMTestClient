package hyweb.core.cache;

/**
 * Cache
 * @author A0074
 * @version 1.0.130203
 * @since xBox 1.0
 */
public interface Cache<S, T> {
	/**
	 * 取值
	 * @param key
	 * @return
	 */
	public T get(S key) throws Exception;

	/**
	 * 塞值
	 * @param key
	 * @param val
	 */
	public void put(S key, T val);

	/**
	 * 清除名為key的cache
	 * @param key
	 */
	public void invalidate(S key);

	/**
	 * 清除所有cache
	 */
	public void invalidateAll();
}
