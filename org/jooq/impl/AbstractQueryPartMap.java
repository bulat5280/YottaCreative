package org.jooq.impl;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.jooq.Context;
import org.jooq.QueryPart;

abstract class AbstractQueryPartMap<K extends QueryPart, V extends QueryPart> extends AbstractQueryPart implements Map<K, V> {
   private static final long serialVersionUID = -8751499214223081415L;
   private final Map<K, V> map = new LinkedHashMap();

   public abstract void accept(Context<?> var1);

   public final int size() {
      return this.map.size();
   }

   public final boolean isEmpty() {
      return this.map.isEmpty();
   }

   public final boolean containsKey(Object key) {
      return this.map.containsKey(key);
   }

   public final boolean containsValue(Object value) {
      return this.map.containsValue(value);
   }

   public final V get(Object key) {
      return (QueryPart)this.map.get(key);
   }

   public final V put(K key, V value) {
      return (QueryPart)this.map.put(key, value);
   }

   public final V remove(Object key) {
      return (QueryPart)this.map.remove(key);
   }

   public final void putAll(Map<? extends K, ? extends V> m) {
      this.map.putAll(m);
   }

   public final void clear() {
      this.map.clear();
   }

   public final Set<K> keySet() {
      return this.map.keySet();
   }

   public final Collection<V> values() {
      return this.map.values();
   }

   public final Set<Entry<K, V>> entrySet() {
      return this.map.entrySet();
   }
}
