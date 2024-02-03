package org.jooq;

public interface MergeOnStep<R extends Record> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   MergeOnConditionStep<R> on(Condition... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   MergeOnConditionStep<R> on(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   MergeOnConditionStep<R> on(Boolean var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   @PlainSQL
   MergeOnConditionStep<R> on(SQL var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   @PlainSQL
   MergeOnConditionStep<R> on(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   @PlainSQL
   MergeOnConditionStep<R> on(String var1, Object... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   @PlainSQL
   MergeOnConditionStep<R> on(String var1, QueryPart... var2);
}
