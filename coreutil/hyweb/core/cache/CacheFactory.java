package hyweb.core.cache;

/**
 * Cache 提供者
 * @author A0074
 * @version 1.0.130203
 * @since xBox 1.0
 */
public interface CacheFactory<S, T> {
	/**
	 * 提供 cache 實體
	 * @param valProvider 怎麼取資料
	 * @param cfg 設定
	 * @return
	 */
	public Cache<S, T> newCache(ValueProvider<S, T> valProvider, CacheConfig cfg);
}
