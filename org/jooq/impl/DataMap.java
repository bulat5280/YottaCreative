package org.jooq.impl;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

final class DataMap extends AbstractMap<Object, Object> {
   final EnumMap<Tools.DataKey, Object> internal = new EnumMap(Tools.DataKey.class);
   Map<Object, Object> external;
   final Set<Entry<Object, Object>> entrySet = new DataMap.EntrySet();

   public final int size() {
      return this.internal().size() + this.external(false).size();
   }

   public final boolean isEmpty() {
      return this.internal().isEmpty() && this.external(false).isEmpty();
   }

   public final boolean containsKey(Object key) {
      return this.delegate(key, false).containsKey(key);
   }

   public final boolean containsValue(Object value) {
      return this.internal().containsValue(value) || this.external(false).containsValue(value);
   }

   public final Object get(Object key) {
      return this.delegate(key, false).get(key);
   }

   public final Object put(Object key, Object value) {
      return this.delegate(key, true).put(key, value);
   }

   public final Object remove(Object key) {
      return this.delegate(key, true).remove(key);
   }

   public final void clear() {
      this.internal().clear();
      this.external(true).clear();
   }

   public final Set<Entry<Object, Object>> entrySet() {
      return this.entrySet;
   }

   private final Map<Object, Object> internal() {
      return this.internal;
   }

   private final Map<Object, Object> external(boolean initialise) {
      if (this.external == null) {
         if (!initialise) {
            return Collections.emptyMap();
         }

         this.external = new HashMap();
      }

      return this.external;
   }

   private final Map<Object, Object> delegate(Object key, boolean initialise) {
      return key instanceof Tools.DataKey ? this.internal() : this.external(initialise);
   }

   private class EntrySet extends AbstractSet<Entry<Object, Object>> {
      private EntrySet() {
      }

      public final Iterator<Entry<Object, Object>> iterator() {
         return new Iterator<Entry<Object, Object>>() {
            final Iterator<Entry<Object, Object>> internalIterator = DataMap.this.internal().entrySet().iterator();
            final Iterator<Entry<Object, Object>> externalIterator = DataMap.this.external(false).entrySet().iterator();

            public final boolean hasNext() {
               return this.internalIterator.hasNext() || this.externalIterator.hasNext();
            }

            public final Entry<Object, Object> next() {
               return this.internalIterator.hasNext() ? (Entry)this.internalIterator.next() : (Entry)this.externalIterator.next();
            }

            public final void remove() {
               throw new UnsupportedOperationException();
            }
         };
      }

      public final int size() {
         return DataMap.this.size();
      }

      // $FF: synthetic method
      EntrySet(Object x1) {
         this();
      }
   }
}
