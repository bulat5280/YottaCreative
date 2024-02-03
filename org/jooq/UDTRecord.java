package org.jooq;

import java.sql.SQLData;

public interface UDTRecord<R extends UDTRecord<R>> extends Record, SQLData {
   UDT<R> getUDT();

   <T> R with(Field<T> var1, T var2);

   <T, U> R with(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);
}
