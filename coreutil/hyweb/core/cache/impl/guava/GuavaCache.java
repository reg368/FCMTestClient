package hyweb.core.cache.impl.guava;

import hyweb.core.cache.Cache;
import hyweb.core.cache.CacheConfig;
import hyweb.core.cache.ValueProvider;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * GuavaCache
 * 會主動重新載入timeout的cache value
 * @author A0074
 * @version 1.0.130203
 * @since xBox 1.0
 */
abstract public class GuavaCache<S, T> implements Cache<S, T> {
	protected LoadingCache<S, T> core;
	protected GuavaCache(){
		super();
	}

	abstract void init(ValueProvider<S, T> valProvider, CacheConfig cfg);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void put(S key, T val) {
		this.core.put(key, val);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidate(S key) {
		this.core.invalidate(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidateAll() {
		this.core.invalidateAll();
	}
}

class DefaultCacheLoader<S, T> extends CacheLoader<S, T>{
	private ValueProvider<S, T> valProvider;
	public DefaultCacheLoader(ValueProvider<S, T> valProvider){
		this.valProvider = valProvider;
	}
	@Override
	public T load(S key) throws Exception {
		return this.valProvider.load(key);
	}
}