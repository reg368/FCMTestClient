package hyweb.core.kit;

import hyweb.core.util.ConcurrentSet;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 集合工具
 * @author A0074
 * @version 1.0.110715
 * @since xBox 1.0
 */
public final class CollectionsKit {
	private CollectionsKit(){
		super();
	}

	/**
	 * 取得Map 
	 * @return
	 */
	public static<S, T> Map<S, T> newMap(){
		return new ConcurrentHashMap<S, T>(20);
	}

	/**
	 * 取得Map
	 * @return
	 */
	public static<T> Set<T> newSet(){
		return new ConcurrentSet<T>(20); 
	}

	/**
	 * 將Array轉為set
	 * @param array
	 * @return
	 */
	public static<T> Set<T> newSet(T[] array){
		Set<T> set = new ConcurrentSet<T>(array.length * 2);
		for(int i = 0 ; i < array.length ; i++){
			set.add(array[i]);
		}
		return set;
	}

	public static String join(List<? extends Object> src, String between){
		if(src == null){
			return StringKit.NULL_STRING;
		}else{
			StringBuilder ret = new StringBuilder(src.size() << 3);
			for(int i = 0 ; i < src.size() ; i++){
				ret.append(src.get(i)).append(between);
			}
			return ret.length() > 0 ? ret.substring(0, ret.length() - 1) : "";
		}
	}
}
