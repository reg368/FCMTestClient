package hyweb.core.cache;

/**
 * Cache 的 Value產生器
 * @author A0074
 * @version 1.0.130203
 * @since xBox 1.0
 */
public interface ValueProvider<S, T> {
	/**
	 * 產生值
	 * @param key
	 * @return
	 */
	public T load(S key) throws Exception;
}
