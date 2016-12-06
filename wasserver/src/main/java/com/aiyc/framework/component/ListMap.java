package com.aiyc.framework.component;

import java.io.Serializable;
import java.util.*;

public class ListMap implements Map, Cloneable, Serializable {
	private class KeySet extends AbstractSet {

		final ListMap this$0;

		public Iterator iterator() {
			return keyLsit.iterator();
		}

		public int size() {
			return keyLsit.size();
		}

		private KeySet(ListMap this$0) {
			super();
			this.this$0 = this$0;
		}

	}

	private class ValueList extends AbstractList {

		final ListMap this$0;

		public Object get(int i) {
			return baseMap.get(keyLsit.get(i));
		}

		public int size() {
			return keyLsit.size();
		}

		private ValueList(ListMap this$0) {

			super();
			this.this$0 = this$0;
		}

	}

	private static final long serialVersionUID = 1L;
	private Map baseMap;
	private ArrayList keyLsit;
	private transient ValueList valueList;
	private transient KeySet keySet;

	public ListMap() {
		baseMap = new HashMap();
		keyLsit = new ArrayList();
	}

	public void clear() {
		baseMap.clear();
		keyLsit.clear();
	}

	public boolean containsKey(Object obj) {
		return baseMap.containsKey(obj);
	}

	public boolean containsValue(Object obj) {
		return baseMap.containsValue(obj);
	}

	public Set entrySet() {
		return baseMap.entrySet();
	}

	public boolean equals(Object obj) {
		return baseMap.equals(obj);
	}

	public Object get(Object obj) {
		return baseMap.get(obj);
	}

	public int hashCode() {
		return baseMap.hashCode();
	}

	public boolean isEmpty() {
		return baseMap.isEmpty();
	}

	public Set keySet() {
		if (keySet == null)
			keySet = new KeySet(this);
		return keySet;
	}

	public Object put(Object obj, Object obj1) {
		if (!keyLsit.contains(obj))
			keyLsit.add(obj);
		return baseMap.put(obj, obj1);
	}

	public void putAll(Map map) {
		java.util.Map.Entry entry;
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); put(
				entry.getKey(), entry.getValue()))
			entry = (java.util.Map.Entry) iterator.next();

	}

	public Object remove(Object obj) {
		keyLsit.remove(obj);
		return baseMap.remove(obj);
	}

	public int size() {
		return baseMap.size();
	}

	public Collection values() {
		if (valueList == null)
			valueList = new ValueList(this);
		return valueList;
	}

}
