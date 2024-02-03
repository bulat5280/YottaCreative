package org.jooq;

import java.sql.SQLOutput;

public interface BindingSetSQLOutputContext<U> extends Scope {
   SQLOutput output();

   U value();

   <T> BindingSetSQLOutputContext<T> convert(Converter<? extends T, ? super U> var1);
}
