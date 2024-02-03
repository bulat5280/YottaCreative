package org.jooq;

import java.util.Collection;

public interface DivideByReturningStep {
   @Support
   Table<Record> returning(Field<?>... var1);

   @Support
   Table<Record> returning(Collection<? extends Field<?>> var1);
}
