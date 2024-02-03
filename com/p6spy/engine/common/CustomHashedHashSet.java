package com.p6spy.engine.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class CustomHashedHashSet<T> extends HashSet<T> {
   private Map<Integer, T> map = new HashMap();
   final Hasher hasher;

   public CustomHashedHashSet(Hasher hasher) {
      this.hasher = hasher;
   }

   public boolean removeAll(Collection<?> c) {
      boolean modified = false;
      Iterator var3 = c.iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         if (this.contains(o)) {
            this.remove(o);
            modified = true;
         }
      }

      return modified;
   }

   public boolean containsAll(Collection<?> c) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection<? extends T> c) {
      boolean modified = false;
      Iterator var3 = c.iterator();

      while(var3.hasNext()) {
         T o = var3.next();
         if (this.add(o)) {
            modified = true;
         }
      }

      return modified;
   }

   public boolean retainAll(Collection<?> c) {
      boolean modified = false;
      Iterator var3 = c.iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         if (!this.contains(o)) {
            this.remove(o);
            modified = true;
         }
      }

      return modified;
   }

   public boolean contains(Object o) {
      return this.map.containsKey(this.hasher.getHashCode(o));
   }

   public boolean add(T o) {
      int hash = this.hasher.getHashCode(o);
      if (!this.map.containsKey(hash)) {
         this.map.put(hash, o);
         super.add(o);
         return true;
      } else {
         return false;
      }
   }

   public boolean remove(Object o) {
      int hash = this.hasher.getHashCode(o);
      if (this.map.containsKey(hash)) {
         super.remove(this.map.get(hash));
         this.map.remove(hash);
         return true;
      } else {
         return false;
      }
   }

   public Iterator<T> iterator() {
      return new CustomHashedHashSet.CustomHashedHashSetIterator(super.iterator());
   }

   public void clear() {
      this.map.clear();
      super.clear();
   }

   public Object clone() {
      throw new UnsupportedOperationException();
   }

   class CustomHashedHashSetIterator<E> implements Iterator<E> {
      private final Iterator<E> iterator;

      public CustomHashedHashSetIterator(Iterator<E> iterator) {
         this.iterator = iterator;
      }

      public boolean hasNext() {
         return this.iterator.hasNext();
      }

      public E next() {
         return this.iterator.next();
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
