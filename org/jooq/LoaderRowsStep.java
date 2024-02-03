package org.jooq;

import java.util.Collection;

public interface LoaderRowsStep<R extends Record> {
   @Support
   LoaderListenerStep<R> fields(Field<?>... var1);

   @Support
   LoaderListenerStep<R> fields(Collection<? extends Field<?>> var1);

   @Support
   LoaderListenerStep<R> fields(LoaderFieldMapper var1);
}
