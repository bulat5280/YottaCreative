package com.mysql.cj.api.x;

import java.util.Iterator;
import java.util.List;

public interface FetchResult<T> extends Iterator<T>, Iterable<T> {
   default boolean hasData() {
      return true;
   }

   default T fetchOne() {
      return this.hasNext() ? this.next() : null;
   }

   default Iterator<T> iterator() {
      return this.fetchAll().iterator();
   }

   long count();

   List<T> fetchAll();

   int getWarningsCount();

   Iterator<Warning> getWarnings();
}
