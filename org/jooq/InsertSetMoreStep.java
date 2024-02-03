package org.jooq;

import java.util.Map;

public interface InsertSetMoreStep<R extends Record> extends InsertOnDuplicateStep<R> {
   @Support
   <T> InsertSetMoreStep<R> set(Field<T> var1, T var2);

   @Support
   <T> InsertSetMoreStep<R> set(Field<T> var1, Field<T> var2);

   @Support
   <T> InsertSetMoreStep<R> set(Field<T> var1, Select<? extends Record1<T>> var2);

   @Support
   InsertSetMoreStep<R> set(Map<? extends Field<?>, ?> var1);

   @Support
   InsertSetMoreStep<R> set(Record var1);

   @Support
   InsertSetStep<R> newRecord();
}
