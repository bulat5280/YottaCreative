package org.jooq;

import java.util.Collection;
import java.util.Map;

public interface InsertQuery<R extends Record> extends StoreQuery<R>, Insert<R> {
   @Support
   void newRecord();

   @Support
   void addRecord(R var1);

   @Support({SQLDialect.POSTGRES_9_5})
   void onConflict(Field<?>... var1);

   @Support({SQLDialect.POSTGRES_9_5})
   void onConflict(Collection<? extends Field<?>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   void onDuplicateKeyUpdate(boolean var1);

   @Support
   void onDuplicateKeyIgnore(boolean var1);

   @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <T> void addValueForUpdate(Field<T> var1, T var2);

   @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <T> void addValueForUpdate(Field<T> var1, Field<T> var2);

   @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   void addValuesForUpdate(Map<? extends Field<?>, ?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   void setDefaultValues();

   @Support
   void setSelect(Field<?>[] var1, Select<?> var2);

   @Support
   void setReturning();

   @Support
   void setReturning(Identity<R, ?> var1);

   @Support
   void setReturning(Field<?>... var1);

   @Support
   void setReturning(Collection<? extends Field<?>> var1);

   @Support
   R getReturnedRecord();

   @Support
   Result<R> getReturnedRecords();
}
