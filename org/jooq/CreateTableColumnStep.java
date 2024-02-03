package org.jooq;

import java.util.Collection;

public interface CreateTableColumnStep extends CreateTableConstraintStep {
   @Support
   CreateTableColumnStep column(Field<?> var1);

   @Support
   <T> CreateTableColumnStep column(Field<T> var1, DataType<T> var2);

   @Support
   CreateTableColumnStep column(Name var1, DataType<?> var2);

   @Support
   CreateTableColumnStep column(String var1, DataType<?> var2);

   @Support
   CreateTableColumnStep columns(Field<?>... var1);

   @Support
   CreateTableColumnStep columns(Collection<? extends Field<?>> var1);
}
