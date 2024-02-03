package org.jooq;

import java.sql.CallableStatement;

public interface BindingRegisterContext<U> extends Scope {
   CallableStatement statement();

   int index();

   <T> BindingRegisterContext<T> convert(Converter<? super T, ? extends U> var1);
}
