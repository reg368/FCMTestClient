package hyweb.core.cache;

import hyweb.core.kit.ClassKit;
import hyweb.core.kit.CollectionsKit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cache 管理器
 * @author A0074
 * @version 1.0.130203
 * @since xBox 1.0
 */
public class CacheManager {
	protected final static Logger LOG = LoggerFactory.getLogger(CacheManager.class);
	public final static String GUAVA_CACHE_FACTORY = "hyweb.core.cache.impl.guava.GuavaCacheFactory";
	public final static String ENCACHE_CACHE_FACTORY = "hyweb.core.cache.impl.ehcache.EncacheCacheFactory";
	protected final static Map<String, Cache<?, ?>> CACHE_MAPPING = CollectionsKit.newMap();
	protected final static Map<String, CacheConfig> CACHE_CFG = CollectionsKit.newMap();

	/**
	 * 以非同步的方式呼叫關閉某個Factory下所有的Cache實體
	 * @param name
	 * @return
	 */
	public static void invalidate(String name) {
		List<String> list = new ArrayList<String>(1);
		list.add(name);
		LOG.info("clean the cache which named " + name);
		new InvalidateThread(list.iterator()).run();
	}

	/**
	 * 以非同步的方式關閉所有Factory下所有的Cache實體
	 * @param name
	 * @return
	 */
	public static void invalidateAll() {
		LOG.info("clean all cache");
		new InvalidateThread(CacheManager.CACHE_MAPPING.keySet().iterator()).run();
	}

	/**
	 * 依照名稱取得快取，當該名稱未經過newCache初始化時回傳null
	 * @param name 資料提供者
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<S, T>Cache<S, T> getCache(S name) {
		return (Cache<S, T>)CacheManager.CACHE_MAPPING.get(name);
	}

	/**
	 * 建立新的快取實體，但名稱不能與現存者相同
	 * @param name 快取的名稱
	 * @param valProvider 資料提供者
	 * @param cfg 設定檔
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<S, T> Cache<S, T> newCache(String name, ValueProvider<S, T> valProvider, CacheConfig cfg) throws SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException{
		if(CacheManager.CACHE_MAPPING.containsKey(name)){
			throw new IllegalArgumentException("name:" + name + " is exist");
		}
		CacheFactory<S, T> factory = (CacheFactory<S, T>)ClassKit.createNewInstance(cfg.factoryName);
		Cache<S, T> cache = factory.newCache(valProvider, cfg);
		CacheManager.CACHE_MAPPING.put(name, cache);
		CacheManager.CACHE_CFG.put(name, cfg);
		return cache;
	}

	@SuppressWarnings("unused")
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(String key : CACHE_MAPPING.keySet()){
			Cache<?,?> cache = CACHE_MAPPING.get(key);
			CacheConfig cfg = CACHE_CFG.get(key);
			sb.append("{");
			sb.append("\"key\":").append(key);
			sb.append("\"cfg\":").append(cfg.toString());
			sb.append("}");
		}
		sb.append("]");
		return sb.toString();
	}
}
/**
 * 執行
 * @author A0074
 *
 */
class InvalidateThread implements Runnable{
	final private Iterator<String> it;
	public InvalidateThread(Iterator<String> it){
		this.it = it;
	}

	@Override
	public void run() {
		while(this.it.hasNext()){
			CacheManager.CACHE_MAPPING.get(it.next()).invalidateAll();
		}
		CacheManager.LOG.info("clean finish");
	}
}
