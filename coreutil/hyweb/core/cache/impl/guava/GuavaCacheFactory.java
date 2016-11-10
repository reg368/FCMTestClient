package hyweb.core.cache.impl.guava;

import hyweb.core.cache.Cache;
import hyweb.core.cache.CacheConfig;
import hyweb.core.cache.CacheFactory;
import hyweb.core.cache.ValueProvider;

/**
 * guava Cache 提供者
 * @author A0074
 * @version 1.0.130203
 * @since xBox 1.0
 */
public class GuavaCacheFactory<S, T> implements CacheFactory<S, T> {
	private GuavaCacheFactory(){
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cache<S, T> newCache(ValueProvider<S, T> valProvider, CacheConfig cfg) {
		if(cfg.isLazyLoad){
			return new LazyLoadGuavaCache<S, T>(valProvider, cfg);
		}else{
			return new NormalGuavaCache<S, T>(valProvider, cfg);
		}
	}
}
