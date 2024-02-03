package org.jooq;

import java.util.Collection;

public interface DeleteQuery<R extends Record> extends ConditionProvider, Delete<R> {
   @Support
   void addConditions(Condition... var1);

   @Support
   void addConditions(Collection<? extends Condition> var1);

   @Support
   void addConditions(Operator var1, Condition... var2);

   @Support
   void addConditions(Operator var1, Collection<? extends Condition> var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   void setReturning();

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   void setReturning(Field<?>... var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   void setReturning(Collection<? extends Field<?>> var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   R getReturnedRecord();

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   Result<R> getReturnedRecords();
}
