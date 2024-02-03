package org.jooq;

import java.sql.PreparedStatement;

public interface BindingSetStatementContext<U> extends Scope {
   PreparedStatement statement();

   int index();

   U value();

   <T> BindingSetStatementContext<T> convert(Converter<? extends T, ? super U> var1);
}
