package hyweb.core.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用ConcurrentHashMap 做出來的ConcurrentSet
 * @author A0074
 * @version 1.0.110715
 * @since xBox 1.0
 * @param <T>
 */
public class ConcurrentSet<T> implements Set<T> {
	private ConcurrentHashMap<T, Object> map = new ConcurrentHashMap<T, Object>();

	public ConcurrentSet() {
		this.map = new ConcurrentHashMap<T, Object>();
	}

	public ConcurrentSet(int initialCapacity) {
		this.map = new ConcurrentHashMap<T, Object>(initialCapacity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(T item) {
		boolean containsObj = map.containsKey(item);
		if (containsObj == false) {
			this.map.put(item, Boolean.TRUE);
		}
		return !containsObj;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addAll(Collection<? extends T> items) {
		boolean changed = false;
		for (T item : items) {
			changed = add(item) || changed;
		}
		return changed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		this.map.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(Object item) {
		return this.map.containsKey(item);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsAll(Collection<?> items) {
		return this.map.keySet().containsAll(items);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<T> iterator() {
		return this.map.keySet().iterator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Object item) {
		return this.map.remove(item, Boolean.TRUE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeAll(Collection<?> items) {
		return this.map.keySet().removeAll(items);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean retainAll(Collection<?> items) {
		return this.map.keySet().retainAll(items);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return this.map.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		return this.map.keySet().toArray();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] array) {
		return this.map.keySet().toArray(array);
	}
}