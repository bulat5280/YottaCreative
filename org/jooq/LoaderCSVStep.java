package org.jooq;

import java.util.Collection;

public interface LoaderCSVStep<R extends Record> {
   @Support
   LoaderCSVOptionsStep<R> fields(Field<?>... var1);

   @Support
   LoaderCSVOptionsStep<R> fields(Collection<? extends Field<?>> var1);

   @Support
   LoaderListenerStep<R> fields(LoaderFieldMapper var1);
}
