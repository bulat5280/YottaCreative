package org.jooq;

import java.sql.SQLInput;

public interface BindingGetSQLInputContext<U> extends Scope {
   SQLInput input();

   void value(U var1);

   <T> BindingGetSQLInputContext<T> convert(Converter<? super T, ? extends U> var1);
}
