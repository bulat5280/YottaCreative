package org.jooq;

import java.util.Collection;
import java.util.Map;

public interface StoreQuery<R extends Record> extends Query {
   @Support
   void setRecord(R var1);

   @Support
   <T> void addValue(Field<T> var1, T var2);

   @Support
   <T> void addValue(Field<T> var1, Field<T> var2);

   @Support
   void addValues(Map<? extends Field<?>, ?> var1);

   @Support
   void setReturning();

   @Support
   void setReturning(Identity<R, ?> var1);

   @Support
   void setReturning(Field<?>... var1);

   @Support
   void setReturning(Collection<? extends Field<?>> var1);

   @Support
   R getReturnedRecord();

   @Support
   Result<R> getReturnedRecords();
}
