package org.jooq;

import java.sql.CallableStatement;

public interface BindingGetStatementContext<U> extends Scope {
   CallableStatement statement();

   int index();

   void value(U var1);

   <T> BindingGetStatementContext<T> convert(Converter<? super T, ? extends U> var1);
}
