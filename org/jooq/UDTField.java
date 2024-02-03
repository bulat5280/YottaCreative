package org.jooq;

public interface UDTField<R extends UDTRecord<R>, T> extends Field<T> {
   UDT<R> getUDT();
}
