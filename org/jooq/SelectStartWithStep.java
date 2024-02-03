package org.jooq;

public interface SelectStartWithStep<R extends Record> extends SelectGroupByStep<R> {
   @Support({SQLDialect.CUBRID})
   SelectGroupByStep<R> startWith(Condition var1);

   @Support({SQLDialect.CUBRID})
   SelectGroupByStep<R> startWith(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support({SQLDialect.CUBRID})
   SelectGroupByStep<R> startWith(Boolean var1);

   @Support({SQLDialect.CUBRID})
   @PlainSQL
   SelectGroupByStep<R> startWith(SQL var1);

   @Support({SQLDialect.CUBRID})
   @PlainSQL
   SelectGroupByStep<R> startWith(String var1);

   @Support({SQLDialect.CUBRID})
   @PlainSQL
   SelectGroupByStep<R> startWith(String var1, Object... var2);

   @Support({SQLDialect.CUBRID})
   @PlainSQL
   SelectGroupByStep<R> startWith(String var1, QueryPart... var2);
}
