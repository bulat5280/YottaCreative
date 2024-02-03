package org.jooq;

import java.util.Collection;

public interface InsertValuesStepN<R extends Record> extends InsertOnDuplicateStep<R> {
   @Support
   InsertValuesStepN<R> values(Object... var1);

   @Support
   InsertValuesStepN<R> values(Field<?>... var1);

   @Support
   InsertValuesStepN<R> values(Collection<?> var1);

   @Support
   InsertOnDuplicateStep<R> select(Select<?> var1);
}
