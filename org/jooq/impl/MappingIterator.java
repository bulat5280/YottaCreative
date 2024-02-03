package org.jooq.impl;

import java.util.Iterator;

final class MappingIterator<T, U> implements Iterator<U> {
   final Iterator<? extends T> delegate;
   final MappingIterator.Function<? super T, ? extends U> mapper;

   MappingIterator(Iterator<? extends T> delegate, MappingIterator.Function<? super T, ? extends U> mapper) {
      this.delegate = delegate;
      this.mapper = mapper;
   }

   public boolean hasNext() {
      return this.delegate.hasNext();
   }

   public U next() {
      return this.mapper.map(this.delegate.next());
   }

   public void remove() {
      this.delegate.remove();
   }

   interface Function<T, U> {
      U map(T var1);
   }
}
