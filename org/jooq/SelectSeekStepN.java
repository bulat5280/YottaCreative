package org.jooq;

public interface SelectSeekStepN<R extends Record> extends SelectLimitStep<R> {
   SelectSeekLimitStep<R> seek(Object... var1);

   SelectSeekLimitStep<R> seek(Field<?>... var1);

   SelectSeekLimitStep<R> seekAfter(Object... var1);

   SelectSeekLimitStep<R> seekAfter(Field<?>... var1);

   SelectSeekLimitStep<R> seekBefore(Object... var1);

   SelectSeekLimitStep<R> seekBefore(Field<?>... var1);
}
