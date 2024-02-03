package org.jooq;

import java.sql.ResultSet;

public interface BindingGetResultSetContext<U> extends Scope {
   ResultSet resultSet();

   int index();

   void value(U var1);

   <T> BindingGetResultSetContext<T> convert(Converter<? super T, ? extends U> var1);
}
