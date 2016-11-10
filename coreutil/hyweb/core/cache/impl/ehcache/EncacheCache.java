package hyweb.core.cache.impl.ehcache;

import hyweb.core.cache.Cache;

/**
 * EncacheCache
 * 會主動重新載入timeout的cache value
 * @author A0074
 * @version 1.0.130203
 * @since xBox 1.0
 */
abstract public class EncacheCache<S, T> implements Cache<S, T> {
	@Override
	public T get(S key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(S key, T val) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void invalidate(S key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void invalidateAll() {
		// TODO Auto-generated method stub
		
	}

}
