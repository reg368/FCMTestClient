package hyweb.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionHelper {
	public static <T> List<T> findAll(Collection<T> coll, Checker<T> chk) {
	    List<T> l = new ArrayList<T>();
	    for (T obj : coll) {
	    	if (chk.check(obj)) {
	    		l.add(obj);
	    	}
	    }
	    return l;
	}
	
	public static interface Checker<T> {
	    public boolean check(T obj);
	}
}
