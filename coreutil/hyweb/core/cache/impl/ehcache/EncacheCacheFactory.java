package hyweb.core.cache.impl.ehcache;

import hyweb.core.cache.Cache;
import hyweb.core.cache.CacheConfig;
import hyweb.core.cache.CacheFactory;
import hyweb.core.cache.ValueProvider;

/**
 * Encache 提供者
 * @author A0074
 * @version 1.0.130203
 * @since xBox 1.0
 */
public class EncacheCacheFactory<S, T> implements CacheFactory<S, T> {

	@Override
	public Cache<S, T> newCache(ValueProvider<S, T> valProvider, CacheConfig cfg) {
		// TODO Auto-generated method stub
		return null;
	}

}
