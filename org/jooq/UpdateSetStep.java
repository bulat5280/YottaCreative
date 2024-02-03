package org.jooq;

import java.util.Map;

public interface UpdateSetStep<R extends Record> {
   @Support
   <T> UpdateSetMoreStep<R> set(Field<T> var1, T var2);

   @Support
   <T> UpdateSetMoreStep<R> set(Field<T> var1, Field<T> var2);

   @Support
   <T> UpdateSetMoreStep<R> set(Field<T> var1, Select<? extends Record1<T>> var2);

   @Support
   UpdateSetMoreStep<R> set(Map<? extends Field<?>, ?> var1);

   @Support
   UpdateSetMoreStep<R> set(Record var1);
}
