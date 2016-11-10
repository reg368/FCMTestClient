package hyweb.core.cache.impl.guava;

import hyweb.core.cache.CacheConfig;
import hyweb.core.cache.ValueProvider;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;

/**
 * 被動GuavaCache
 * 當存取時才會重新載入timeout的cache value
 * @author A0074
 * @version 1.0.130203
 * @since xBox 1.0
 */
public class LazyLoadGuavaCache<S, T> extends GuavaCache<S, T> {
	private ValueProvider<S, T> valProvider;
	protected LazyLoadGuavaCache(ValueProvider<S, T> valProvider, CacheConfig cfg){
		super();
		this.valProvider = valProvider;
		this.init(valProvider, cfg);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	void init(ValueProvider<S, T> valProvider, CacheConfig cfg) {		
		super.core = CacheBuilder.newBuilder()
			.concurrencyLevel(cfg.concurrencyLevel)
			.expireAfterWrite(cfg.timeoutMinutes, TimeUnit.MINUTES)
			.initialCapacity(cfg.initialCapacity)
			.maximumSize(cfg.entrySize)
			.build(new DefaultCacheLoader<S, T>(valProvider));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get(S key) throws Exception{
		T ret = super.core.get(key);
		if(ret == null){
			ret = this.valProvider.load(key);
			if(ret != null){
				super.core.put(key, ret);
			}
		}
		return ret;
	}
}
