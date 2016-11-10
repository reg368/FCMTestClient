package hyweb.core.cache.impl.guava;

import hyweb.core.cache.CacheConfig;
import hyweb.core.cache.ValueProvider;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;

public class NormalGuavaCache<S, T> extends GuavaCache<S, T> {
	protected NormalGuavaCache(ValueProvider<S, T> valProvider, CacheConfig cfg){
		super();
		this.init(valProvider, cfg);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	void init(ValueProvider<S, T> valProvider, CacheConfig cfg) {		
		super.core = CacheBuilder.newBuilder()
			.concurrencyLevel(cfg.concurrencyLevel)
			.refreshAfterWrite(cfg.timeoutMinutes, TimeUnit.MINUTES)
			.initialCapacity(cfg.initialCapacity)
			.maximumSize(cfg.entrySize)
			.build(new DefaultCacheLoader<S, T>(valProvider));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get(S key) throws ExecutionException{
		return this.core.get(key);
	}
}
