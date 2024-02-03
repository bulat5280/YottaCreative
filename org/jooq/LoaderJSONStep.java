package org.jooq;

import java.util.Collection;

public interface LoaderJSONStep<R extends Record> {
   @Support
   LoaderJSONOptionsStep<R> fields(Field<?>... var1);

   @Support
   LoaderJSONOptionsStep<R> fields(Collection<? extends Field<?>> var1);

   @Support
   LoaderListenerStep<R> fields(LoaderFieldMapper var1);
}
