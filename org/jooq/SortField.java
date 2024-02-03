package org.jooq;

public interface SortField<T> extends QueryPart {
   String getName();

   SortOrder getOrder();

   @Support
   SortField<T> nullsFirst();

   @Support
   SortField<T> nullsLast();
}
